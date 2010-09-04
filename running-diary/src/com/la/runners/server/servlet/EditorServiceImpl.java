package com.la.runners.server.servlet;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.la.runners.client.EditorService;
import com.la.runners.server.dao.CustomerDao;
import com.la.runners.server.dao.jdo.JdoCustomerDao;
import com.la.runners.shared.Run;

public class EditorServiceImpl extends RemoteServiceServlet implements EditorService {
	
	private static final long serialVersionUID = 1L;

	private CustomerDao dao = new JdoCustomerDao();
	
	@Override
	public void save(Run run) {
		dao.persist(run);
	}

	@Override
	public Run get(Long id) {
		return dao.get(id);
	}

}
