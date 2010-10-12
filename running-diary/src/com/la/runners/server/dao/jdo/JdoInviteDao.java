package com.la.runners.server.dao.jdo;

import java.util.List;

import javax.jdo.Query;

import org.appengine.commons.dao.BaseDao;
import org.appengine.commons.dao.jdo.BaseDaoImpl;

import com.la.runners.shared.Invite;

public class JdoInviteDao extends BaseDaoImpl<Invite> implements BaseDao<Invite> {

    private static final String TOKEN = "token";
    
    private static final String USED_DATE = "usedDate";
    
    private static final String RECEIVER_USER_ID = "receiverUserId";
    
    public JdoInviteDao() {
        super(Invite.class);
    }
    
    public Invite get(String token) {
        return getByProperty(TOKEN, STRING, token);
    }

    public List<Invite> search(final String userId) {
        return executeQuery(new QueryPersonalizer(Invite.class) {
            @Override
            public void get(Query q) {
                super.get(q);
                q.setFilter(getStringFilter(RECEIVER_USER_ID, userId) + " && " + USED_DATE + " == null");
            }
        });
    }

}
