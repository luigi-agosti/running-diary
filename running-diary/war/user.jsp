<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%UserService userService = UserServiceFactory.getUserService(); User user = userService.getCurrentUser();%>
<html>
	<head>
		<title>User Home</title>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<jsp:include page="/fragments/style.css"/>
		<script type="text/javascript" language="javascript" src="editor/editor.nocache.js"></script>
	</head>
	<body>
		<div id="logout">
	    	<%if (user != null) {%>
				 <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Sign out <%=user.getNickname()%></a>
			<%}%>
		</div>
		<div id="menu">
			
		</div>
		<div id="gwtcontent">
			<div id="gwtHook"></div>
		</div>
	</body>
</html>