package com.la.runners.server.dao.jdo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import org.appengine.commons.dao.BaseDao;
import org.appengine.commons.dao.jdo.BaseDaoImpl;

import javax.jdo.Query;
import com.la.runners.shared.Run;

public class JdoRunDao extends BaseDaoImpl<Run> implements BaseDao<Run> {
    
    private static final String FILTER = "userId == userIdParam && month == monthParam && year == yearParam";
    
    private static final String FILTER_PARAMS_DECLARATION = "String userIdParam, Integer monthParam, Integer yearParam";
    
    private static final String ORDER = "date desc";
    
    public JdoRunDao() {
        super(Run.class);
    }

    @SuppressWarnings("unchecked")
    public List<Run> search(Integer year, Integer month, String userId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if(year == null) {
            year = calendar.get(Calendar.YEAR);
        }
        if(month == null) {
            month = calendar.get(Calendar.MONTH);
        }
        PersistenceManager pm = getPM();
        Query q = pm.newQuery(Run.class);
        q.setFilter(FILTER);
        q.declareParameters(FILTER_PARAMS_DECLARATION);
        q.setOrdering(ORDER);
        
        List<Run> runs = (List<Run>) q.execute(userId, month, year);
        if(runs == null) {
            runs = new ArrayList<Run>();
        }
        return runs;
    }
   
}
