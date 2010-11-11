package com.la.runners.server.dao.jdo;

import java.util.List;

import javax.jdo.Query;

import org.appengine.commons.dao.BaseDao;
import org.appengine.commons.dao.jdo.BaseDaoImpl;

import com.la.runners.shared.Profile;

public class JdoProfileDao extends BaseDaoImpl<Profile> implements BaseDao<Profile> {
    
    private static final String FOLLOWERS = "followers";
    
    private static final String NICKNAME = "nickname";
    
    private static final String USER_ID = "userId";
    
    public JdoProfileDao() {
        super(Profile.class);
    }

    public List<Profile> getFollowers(final String userId) {
        return searchByProperty(FOLLOWERS, userId);
    }

    public List<Profile> search(final String nickname) {
        return query(NICKNAME, nickname);
    }
    
    private List<Profile> query(final String property, final String nickname) {
        return executeQuery(new QueryPersonalizer(Profile.class) {
            @Override
            public void get(Query q) {
                super.get(q);
                q.setFilter(getStringFilter(property, nickname));
            }
        });
    }
    
    public Profile get(String userId) {
        return getByProperty(USER_ID, STRING, userId);
    }
    
    public void delete(String userId) {
        deleteByProperty(USER_ID, STRING, userId);
    }
    
}
