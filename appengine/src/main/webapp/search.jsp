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
		<script type="text/javascript" language="javascript" src="search/search.nocache.js"></script>
	</head>
	<body>
		<div id="header-background-small">
			<div id="header-small">
				<div id="logo">
					<h1>Running diary</h1>
					<h2>Keep track of all training</h2>
				</div>
			</div>
			<div id="menu">
				<div id="menu-btn">
			    	<a href="home.jsp">Home</a>
				</div>
				<div id="menu-btn-selected">
			    	<a href="search.jsp">Find friends</a>
				</div>
				<div id="menu-btn">
			    	<a href="statistics.jsp">Statistics</a>
				</div>
				<div id="menu-btn">
			    	<a href="profile.jsp">Profile</a>
				</div>
				<div id="menu-btn">
					 <a href="download.jsp">Download</a>
				</div>
				<div id="menu-btn">
			    	<%if (user != null) {%>
						 <a href="<%= userService.createLogoutURL("/") %>">Sign out <%=user.getNickname()%></a>
					<%}%>
				</div>
			</div>
		</div>
		<div id="gwtHook"></div>
	</body>
</html>