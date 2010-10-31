package com.la.runners.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.la.runners.shared.Invite;
import com.la.runners.shared.Location;
import com.la.runners.shared.Profile;
import com.la.runners.shared.Run;
import com.la.runners.shared.ServerException;

@RemoteServiceRelativePath("service")
public interface Service extends RemoteService {

	void save(Run run);
	
	void save(Profile profile);

	Run getRun(Long id);
	
	Profile getProfile();
	
	void deleteProfile();
	
	List<Profile> getFollowers();
	
	List<Run> search(Integer year, Integer month);
	
	List<Profile> searchProfile(String nickname);

    void sendInvite(String email, String message) throws ServerException;
	
    void removeFollower(String followerUserId);
    
    void addFollower(String followerUserId);

    List<Invite> getInvites();

    void acceptInvite(String token);

    void rejectInvite(String token);

    void sendInvite(String recipientUserId) throws ServerException;

    List<Location> getLocations(String token);

    void deleteRuns(List<Long> ids);
    
}
