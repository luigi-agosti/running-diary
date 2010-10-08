package com.la.runners.server.dao.jdo;

import org.appengine.commons.dao.BaseDao;
import org.appengine.commons.dao.jdo.BaseDaoImpl;

import com.la.runners.shared.Invite;

public class JdoInviteDao extends BaseDaoImpl<Invite> implements BaseDao<Invite> {

    public JdoInviteDao(Class<Invite> clazz) {
        super(clazz);
    }

}
