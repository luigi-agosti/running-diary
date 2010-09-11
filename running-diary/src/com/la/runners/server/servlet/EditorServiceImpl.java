package com.la.runners.server.servlet;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.la.runners.client.EditorService;
import com.la.runners.server.dao.jdo.JdoRunDao;
import com.la.runners.shared.Run;

public class EditorServiceImpl extends RemoteServiceServlet implements EditorService {
	
	private static final long serialVersionUID = 1L;

	private JdoRunDao dao = new JdoRunDao(Run.class);
	
	@Override
	public void save(Run run) {
		dao.save(run);
	}

	@Override
	public Run get(Long id) {
		return dao.get(id);
	}

}
