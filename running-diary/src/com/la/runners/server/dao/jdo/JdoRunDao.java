package com.la.runners.server.dao.jdo;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import org.appengine.commons.dao.BaseDao;
import org.appengine.commons.dao.jdo.BaseDaoImpl;

import javax.jdo.Query;
import com.la.runners.shared.Run;

public class JdoRunDao extends BaseDaoImpl<Run> implements BaseDao<Run> {
    
    private static final String ORDER = "created desc";
    
    public JdoRunDao(Class<Run> clazz) {
        super(clazz);
    }

    @SuppressWarnings("unchecked")
    public List<Run> search() {
        PersistenceManager pm = getPM();
        Query q = pm.newQuery(Run.class);
        q.setRange(0, PAGE_SIZE);
        q.setOrdering(ORDER);
        
        List<Run> runs = (List<Run>) q.execute();
        if(runs == null) {
            runs = new ArrayList<Run>();
        }
        return runs;
    }
   
}
