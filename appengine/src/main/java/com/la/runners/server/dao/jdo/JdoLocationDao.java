package com.la.runners.server.dao.jdo;

import java.util.List;

import javax.jdo.Query;

import org.appengine.commons.dao.BaseDao;
import org.appengine.commons.dao.jdo.BaseDaoImpl;

import com.la.runners.shared.Location;

public class JdoLocationDao extends BaseDaoImpl<Location> implements BaseDao<Location> {

    private static final String RUN_ID = "runId";
    
    public JdoLocationDao() {
        super(Location.class);
    }

    public Location detach(Location l) {
        return detach(l);
    }
    
    public List<Location> search(final Long id) {
        return query(RUN_ID, id);
    }
    
    private List<Location> query(final String property, final Long id) {
        return executeQuery(new QueryPersonalizer(Location.class) {
            @Override
            public void get(Query q) {
                super.get(q);
                q.setFilter(property + " == " + id);
            }
        });
    }

}
