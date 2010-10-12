
package com.la.runners.server.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.la.runners.server.dao.jdo.JdoInviteDao;
import com.la.runners.server.dao.jdo.JdoProfileDao;
import com.la.runners.shared.Invite;
import com.la.runners.shared.Profile;

public class InviteRegistrationServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(InviteRegistrationServlet.class
            .getSimpleName());

    private static final long serialVersionUID = 1L;
    
    private JdoInviteDao jdoInvite = new JdoInviteDao();
    private JdoProfileDao jdoProfile = new JdoProfileDao();
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }
    
    private void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        logger.info("registering invitation : " + token);
        
        //This is copied from the ServiceImpl!!!
        Invite invite = jdoInvite.get(token);
        String userId = getUserId();
        if(invite != null) {
            Profile senderProfile = jdoProfile.get(invite.getSenderUserId());
            if(senderProfile.getFollowers() == null) {
                senderProfile.setFollowers(new ArrayList<String>());
            }
            senderProfile.getFollowers().add(userId);
            jdoProfile.save(senderProfile);
            
            Profile receiverProfile = jdoProfile.get(userId);
            if(receiverProfile == null) {
                receiverProfile = new Profile();
            }
            receiverProfile.setUserId(userId);
            receiverProfile.setCreated(new Date());
            receiverProfile.setFollowers(Arrays.asList(invite.getSenderUserId()));
            receiverProfile.setModified(new Date());
            jdoProfile.save(receiverProfile);
        }
        
        invite.setReceiverUserId(userId);
        invite.setUsedDate(new Date());
        jdoInvite.save(invite);
        resp.sendRedirect("/profile.jsp");
    }
    
    private String getUserId() {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        return user.getUserId();
    }
}
