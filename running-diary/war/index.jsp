<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%UserService userService = UserServiceFactory.getUserService(); User user = userService.getCurrentUser();%>
<html>
	<head>
		<title>Running Diary</title>
		<meta name="description" content="Running Diary where to keep and analyzing your exercise data" />
		<meta name="google-site-verification" content="_2xl145Mddf4kPSbu7T3WlLme2wN1QPZusLJGgMefZ8" />
		<jsp:include page="/fragments/style.css"/>
	</head>
	<body>
		<div id="header">
			<div id="logo">
				<h1>Running diary</h1>
				<h2>Keep track of all training</h2>
			</div>
			<div id="menu">
			</div>
		</div>
		<div id="header-background"></div>
		<center>
			<div id="content">
				<div class="title">What is this about?</h2></div>			
				<div class="sentence">
					I'm a runner and I had the pleasure to read the book "The runner's handbook" of  Bob Glover. 
					From it I have learned a couple of things :
				</div>
				<div class="list">
					<ul>
						<li>Keeping a diary will help you to not drop out of your training</li>
						<li>Running should be a social thing</li>
					</ul> 
				</div>
				<div class="sentence">
					So I though why not build a web site to make things easy...
				</div>
				<div class="title">What you can do with this web site?</div>
				<div class="list"> 
					<ul>
						<li>You can store all your tringing</li>
						<li>Use our android application</li>
						<li>Share with friends your training</li>
						<li>Schedule a training program</li>
					</ul>
				</div>
				<div class="sentence">
					It is really easy to use, and you don't have to register an account for it, just use your google account.					
				</div>
				<div class="visible">
					<%if (user != null) {%>
						<a href="user.jsp">go to your home</a>
						<a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out <%=user.getNickname()%></a>
					<%} else {%>
						<a href="user.jsp">try it now!</a>
					<%}%>
				</div>
				<div class="sentence">
					I will be very glad to who will give me some feedback about the website and 
					the android applcation. If you would like some funtionality to be added just drop me 
					an email.
				</div>
			</div>
		</center>
		<jsp:include page="/fragments/footer.jsp"/>
		<jsp:include page="/fragments/analytics.html"/>
	</body>
</html>