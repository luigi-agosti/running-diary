package com.la.runners.server.dao.jdo;

import java.util.logging.Logger;

import javax.jdo.JDOOptimisticVerificationException;
import javax.jdo.PersistenceManager;

import com.la.runners.server.dao.CustomerDao;
import com.la.runners.shared.Run;

public class JdoCustomerDao extends JdoBaseDao implements CustomerDao {
    
    private static final Logger log = Logger.getLogger(JdoCustomerDao.class.getName());
    
    @Override
    public Run persist(Run toPersist) {
        PersistenceManager pm = getPM();
        pm.currentTransaction().begin();
        try {
            Run run = get(toPersist.getId());
            if(run != null) {            
                toPersist.setId(run.getId());       
            }
            run = pm.makePersistent(toPersist);
            pm.currentTransaction().commit();
            return run;
        } catch(JDOOptimisticVerificationException e) {
            log.severe("JDOOptimisticVerificationException " + e.getMessage());
            throw new RuntimeException("JDOOptimisticVerificationException", e);
        } finally {      
            if (pm.currentTransaction().isActive()) {
                pm.currentTransaction().rollback();
            }
            pm.close();
        }
    }

    @Override
    public Run get(Long id) {
        if(id == null) {
            return null;
        }
        return getPM().getObjectById(Run.class, id);
    }
   
}
