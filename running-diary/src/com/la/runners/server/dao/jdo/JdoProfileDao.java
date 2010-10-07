package com.la.runners.server.dao.jdo;

import java.util.List;

import javax.jdo.Query;

import org.appengine.commons.dao.BaseDao;
import org.appengine.commons.dao.jdo.BaseDaoImpl;

import com.la.runners.shared.Profile;

public class JdoProfileDao extends BaseDaoImpl<Profile> implements BaseDao<Profile> {
    
    public JdoProfileDao(Class<Profile> clazz) {
        super(Profile.class);
    }

    public List<Profile> getFollowers(final String userId) {
        return executeQuery(new QueryPersonalizer(Profile.class) {
            @Override
            public void get(Query q) {
                super.get(q);
                q.setFilter(getStringFilter("followers", userId));
            }
        });
    }
}
