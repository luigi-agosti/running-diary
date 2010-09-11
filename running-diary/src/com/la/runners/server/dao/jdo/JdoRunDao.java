package com.la.runners.server.dao.jdo;

import org.appengine.commons.dao.BaseDao;
import org.appengine.commons.dao.jdo.BaseDaoImpl;

import com.la.runners.shared.Run;

public class JdoRunDao extends BaseDaoImpl<Run> implements BaseDao<Run> {
    
    public JdoRunDao(Class<Run> clazz) {
        super(clazz);
    }
   
}
