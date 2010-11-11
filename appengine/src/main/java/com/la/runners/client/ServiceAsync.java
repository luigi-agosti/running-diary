package com.la.runners.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.la.runners.shared.Invite;
import com.la.runners.shared.Location;
import com.la.runners.shared.Profile;
import com.la.runners.shared.Run;
import com.la.runners.shared.ServerException;

public interface ServiceAsync {

	void save(Run run, AsyncCallback<Void> callback);
	
	void save(Profile profile, AsyncCallback<Void> callback);

	void getRun(Long id, AsyncCallback<Run> callback);
	
	void getProfile(AsyncCallback<Profile> callback);

    void search(Integer year, Integer month, AsyncCallback<List<Run>> callback);

    void deleteProfile(AsyncCallback<Void> callback);

    void getFollowers(AsyncCallback<List<Profile>> callback);

    void searchProfile(String nickname, AsyncCallback<List<Profile>> callback);
    
    void sendInvite(String email, String message, AsyncCallback<Void> callback) throws ServerException;
    
    void sendInvite(String recipientUserId, AsyncCallback<Void> callback) throws ServerException;

    void addFollower(String followerUserId, AsyncCallback<Void> callback);

    void removeFollower(String followerUserId, AsyncCallback<Void> callback);

    void getInvites(AsyncCallback<List<Invite>> asyncCallback);

    void acceptInvite(String token, AsyncCallback<Void> callback);

    void rejectInvite(String token, AsyncCallback<Void> asyncCallback);

    void getLocations(Long id, AsyncCallback<List<Location>> asyncCallback);

    void deleteRuns(List<Long> ids, AsyncCallback<Void> asyncCallback);

}
