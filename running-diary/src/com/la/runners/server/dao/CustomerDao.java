package com.la.runners.server.dao;

import com.la.runners.shared.Run;

public interface CustomerDao {

    Run persist(Run run);
    
    Run get(Long uid);
    
}
