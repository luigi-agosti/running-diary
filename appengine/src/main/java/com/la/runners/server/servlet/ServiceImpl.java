package com.la.runners.server.servlet;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.la.runners.client.Service;
import com.la.runners.server.dao.jdo.JdoInviteDao;
import com.la.runners.server.dao.jdo.JdoLocationDao;
import com.la.runners.server.dao.jdo.JdoProfileDao;
import com.la.runners.server.dao.jdo.JdoRunDao;
import com.la.runners.shared.Invite;
import com.la.runners.shared.Location;
import com.la.runners.shared.Profile;
import com.la.runners.shared.Run;
import com.la.runners.shared.ServerException;

public class ServiceImpl extends RemoteServiceServlet implements Service {
	
    private static final Logger logger = Logger.getLogger(ServiceImpl.class.getName());
    
	private static final long serialVersionUID = 1L;
	
	private JdoRunDao runDao = new JdoRunDao();
	private JdoProfileDao profileDao = new JdoProfileDao();
	private JdoInviteDao inviteDao = new JdoInviteDao();
	private JdoLocationDao locationDao = new JdoLocationDao();
	
	@Override
	public void save(Run run) {
        run.setUserId(getUserId());
        runDao.save(run);
	}
	
    @Override
    public void save(Profile profile) {
        if(profile.getUserId() == null) {
            profile.setUserId(getUserId());
            profile.setCreated(new Date());
            profile.setFollowers(new ArrayList<String>());
        }
        profile.setModified(new Date());
        profileDao.save(profile);
    }

	@Override
	public Run getRun(Long id) {
		return runDao.get(id);
	}

	@Override
	public Profile getProfile() {
	    Profile profile = profileDao.get(getUserId());
	    if(profile != null) {
	        List<String> followers = profile.getFollowers();
	        if(followers != null) {
	            List<String> fs = new ArrayList<String>();
	            for(String f : followers) {
	               fs.add(f); 
	            }
	            profile.setFollowers(fs);	            
	        } else {
	            profile.setFollowers(new ArrayList<String>());
	        }
	    }
	    return profile;
	}

    @Override
    public List<Run> search(Integer year, Integer month) {
        logger.info("Search with month : " + month + " year : " + year);
    	List<Run> result = runDao.search(year, month, getUserId());
    	if(result == null) {
    		return null;
    	}
    	List<Run> runs = new ArrayList<Run>();
        for(Run run : result) {
        	Run newRun  = new Run();
        	newRun.setId(run.getId());
        	newRun.setDistance(run.getDistance());
        	newRun.setCreated(run.getCreated());
        	newRun.setSpeed(run.getSpeed());
        	newRun.setYear(run.getYear());
        	newRun.setMonth(run.getMonth());
        	newRun.setDay(run.getDay());
        	newRun.setTime(run.getTime());
        	newRun.setShare(run.getShare());
        	newRun.setShoes(run.getShoes());
        	newRun.setWeight(run.getWeight());
        	newRun.setNote(run.getNote());
        	newRun.setHeartRate(run.getHeartRate());
        	newRun.setModified(run.getModified());
        	runs.add(newRun);
        }
    	return runs;
    }

    @Override
    public void deleteProfile() {
        profileDao.delete(getUserId());
    }

    @Override
    public List<Profile> getFollowers() {
        List<Profile> profiles = profileDao.getFollowers(getUserId());
        List<Profile> followers = new ArrayList<Profile>();
        for(Profile profile : profiles) {
            followers.add(new Profile(profile.getUserId(), profile.getNickname()));
        }
        return followers;
    }

    private String getUserId() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        return user.getUserId();
    }

    @Override
    public List<Profile> searchProfile(String nickname) {
        List<Profile> profiles = profileDao.search(nickname);
        List<Profile> followers = new ArrayList<Profile>();
        for(Profile profile : profiles) {
            followers.add(new Profile(profile.getUserId(), profile.getNickname()));
        }
        return followers;
    }

    @Override
    public void sendInvite(String email, String message) throws ServerException {
        if(email != null) {
            logger.log(Level.SEVERE, "Send invite can't procede because the email is null");
            throw new ServerException("Send invite can't procede because the email is not defined");
        }
        Invite invite = persistInvite(null, email);
        sendEmail(email, invite);
    }
    
    @Override
    public void sendInvite(String recipientUserId) throws ServerException {
        persistInvite(recipientUserId, null);
    }
    
    private Invite persistInvite(String recipientUserId, String recipientEmail) throws ServerException {
        Invite invite = new Invite();
        Profile senderProfile = getProfile();
        if(senderProfile != null) {
            invite.setSenderNickname(senderProfile.getNickname());
            invite.setSenderUserId(senderProfile.getUserId());            
        } else {
            invite.setSenderUserId(getUserId());
        }        
        
        if(inviteDao.isDuplication(invite.getSenderUserId(), recipientUserId, recipientEmail)) {
            throw new ServerException("The invite is alredy waiting for the user to accept");
        }
        
        if(recipientUserId != null) {
            invite.setReceiverUserId(recipientUserId);
        }
        String token = UUID.randomUUID().toString();
        invite.setToken(token);
        invite.setSentDate(new Date());
        invite.setReceiverEmail(recipientEmail);
        inviteDao.save(invite);
        return invite;
    }

    private void sendEmail(String recipientEmail, Invite invite) throws ServerException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            Message msg = new MimeMessage(session);
            String nickname = invite.getSenderNickname();
            UserService userService = UserServiceFactory.getUserService();
            User user = userService.getCurrentUser();
            String senderEmail = user.getEmail();
            if(nickname == null) {
                nickname = senderEmail;
            }
            msg.setFrom(new InternetAddress(senderEmail, nickname));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(recipientEmail));
            msg.setSubject(nickname + " send you an invite to join the site running-diary.appspot.com");
            msg.setText("Please if you recognize your friend and you want to try the web site follow this link : " +
            		"<a href=\"http://running-diary.appspot.com/invite?token=" + invite.getToken() + "\">running-diary.appspot.com/invite</a>");
            Transport.send(msg);
        } catch (AddressException e) {
            logger.log(Level.SEVERE, "AddressException", e);
            throw new ServerException(e.getMessage());
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "MessagingException", e);
            throw new ServerException(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            logger.log(Level.SEVERE, "UnsupportedEncodingException", e);
            throw new ServerException(e.getMessage());
        }
    }

    @Override
    public void removeFollower(String followerUserId) {
        Profile followerProfile = profileDao.get(followerUserId);
        Profile currentProfile = getProfile();
        if(currentProfile == null) {
            return;
        }
        if(currentProfile.getFollowers() != null) {
            currentProfile.getFollowers().remove(followerUserId);
            profileDao.save(currentProfile);
        }
        if(followerProfile != null) {
            if(followerProfile.getFollowers() != null) {
                followerProfile.getFollowers().remove(getUserId());
                profileDao.save(followerProfile);
            }
        }
    }

    @Override
    public void addFollower(String followerUserId) {
        Profile followerProfile = profileDao.get(followerUserId);
        Profile currentProfile = getProfile();
        if(currentProfile == null) {
            return;
        }
        if(currentProfile.getFollowers() != null) {
            if(!currentProfile.getFollowers().contains(followerUserId)) {
                currentProfile.getFollowers().add(followerUserId);
                profileDao.save(currentProfile);
            }
        }
        if(followerProfile != null) {
            if(followerProfile.getFollowers() != null) {
                String userId = currentProfile.getUserId();
                if(!followerProfile.getFollowers().contains(userId)) {
                    followerProfile.getFollowers().add(userId);
                    profileDao.save(followerProfile);
                }
            }
        }
    }
    
    @Override
    public void acceptInvite(String token) {
        Invite invite = inviteDao.get(token);
        String userId = getUserId();
        if(invite != null) {
            Profile senderProfile = profileDao.get(invite.getSenderUserId());
            if(senderProfile.getFollowers() == null) {
                senderProfile.setFollowers(new ArrayList<String>());
            }
            senderProfile.getFollowers().add(userId);
            profileDao.save(senderProfile);
            
            Profile receiverProfile = profileDao.get(userId);
            if(receiverProfile == null) {
                receiverProfile = new Profile();
            }
            receiverProfile.setUserId(userId);
            receiverProfile.setCreated(new Date());
            receiverProfile.setFollowers(Arrays.asList(invite.getSenderUserId()));
            receiverProfile.setModified(new Date());
            profileDao.save(receiverProfile);
        }
        
        invite.setReceiverUserId(userId);
        invite.setUsedDate(new Date());
        inviteDao.save(invite);
    }

    @Override
    public List<Invite> getInvites() {
        List<Invite> invites = inviteDao.search(getUserId());
        List<Invite> newInivites = new ArrayList<Invite>();
        for(Invite invite : invites) {
            Invite newInvite = new Invite();
            newInvite.setToken(invite.getToken());
            newInvite.setSenderUserId(invite.getSenderUserId());
            newInvite.setSenderNickname(invite.getSenderNickname());
            newInivites.add(newInvite);
        }
        return newInivites;
    }

    @Override
    public void rejectInvite(String token) {
        Invite invite = inviteDao.get(token);
        invite.setUsedDate(new Date(0));
        inviteDao.save(invite);
    }

    @Override
    public List<Location> getLocations(Long id) {
        List<Location> locations = locationDao.search(id);
        List<Location> detatchList = new ArrayList<Location>();
        for(Location location : locations) {
            detatchList.add(locationDao.detach(location));
        }
        return detatchList;
        
        
//        List<Location> locations = new ArrayList<Location>();
//        Location location = new Location();
//        location.setLatitude(Long.valueOf(51533205));
//        location.setLongitude(Long.valueOf(-122501));
//        locations.add(location);
//        location = new Location();
//        location.setLatitude(Long.valueOf(51531514));
//        location.setLongitude(Long.valueOf(-122552));
//        locations.add(location);
//        location = new Location();
//        location.setLatitude(Long.valueOf(51531317));
//        location.setLongitude(Long.valueOf(-122519));
//        locations.add(location);
//        location = new Location();
//        location.setLatitude(Long.valueOf(51531269));
//        location.setLongitude(Long.valueOf(-122392));
//        locations.add(location);
//        return locations;
    }

    @Override
    public void deleteRuns(List<Long> ids) {
        for(Long id : ids) {
            runDao.delete(id);
        }
    }
    
}
