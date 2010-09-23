package com.la.runners.server.servlet;


import java.util.List;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.la.runners.client.EditorService;
import com.la.runners.server.dao.jdo.JdoRunDao;
import com.la.runners.shared.Run;

public class EditorServiceImpl extends RemoteServiceServlet implements EditorService {
	
	private static final long serialVersionUID = 1L;

	private JdoRunDao dao = new JdoRunDao(Run.class);
	
	@Override
	public void save(Run run) {
	    UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        run.setUserId(user.getUserId());
		dao.save(run);
	}

	@Override
	public Run get(Long id) {
		return dao.get(id);
	}

    @Override
    public List<Run> search() {
        return dao.search();
    }

}
