package com.la.runners.server.dao.jdo;

import javax.jdo.PersistenceManager;

import org.appengine.commons.dao.PMF;

public class JdoBaseDao {

	protected PersistenceManager getPM() {
		return PMF.get().getPersistenceManager();
	}
	
}
