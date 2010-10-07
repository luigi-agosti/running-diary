package com.la.runners.server.servlet;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.la.runners.client.Service;
import com.la.runners.server.dao.jdo.JdoProfileDao;
import com.la.runners.server.dao.jdo.JdoRunDao;
import com.la.runners.shared.Profile;
import com.la.runners.shared.Run;

public class ServiceImpl extends RemoteServiceServlet implements Service {
	
	private static final long serialVersionUID = 1L;

	private static final String USER_ID = "userId";
	private static final String STRING = "String";
	
	private JdoRunDao runDao = new JdoRunDao(Run.class);
	private JdoProfileDao profileDao = new JdoProfileDao(Profile.class);
	
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
	    Profile profile = profileDao.getByProperty(USER_ID, STRING, getUserId());
	    if(profile != null) {
	        profile.setFollowers(null);
	    }
	    return profile;
	}

    @Override
    public List<Run> search(Integer year, Integer month) {
    	List<Run> result = runDao.search(year, month, getUserId());
    	if(result == null) {
    		return null;
    	}
    	List<Run> runs = new ArrayList<Run>();
        for(Run run : result) {
        	Run newRun  = new Run();
        	newRun.setId(run.getId());
        	newRun.setDistance(run.getDistance());
        	newRun.setDate(run.getDate());
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
        profileDao.deleteByProperty(USER_ID, STRING, getUserId());
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
}
