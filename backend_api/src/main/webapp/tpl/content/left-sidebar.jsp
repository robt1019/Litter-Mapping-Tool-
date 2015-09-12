<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="org.littermappingtool.backend.utils.jsp.LeftBarDataLoader" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cf" uri="http://bristol.ac.uk/littermappingtool/backend/utils/JSTLFunctions"%>

<% 
	String page_name = request.getParameter("page") != null ? request.getParameter("page") : "";
	UserService user_service = UserServiceFactory.getUserService();
	pageContext.setAttribute("loader_lb", new LeftBarDataLoader());
	String userName = user_service.getCurrentUser().getNickname();
	userName = (userName.length() > 17 ? userName.substring(0, 17) + " .." : userName);
%>

<!-- sidebar: style can be found in sidebar.less -->
<aside class="main-sidebar">
	<section class="sidebar">
		<!-- Sidebar user panel -->
		<div class="user-panel">
			<div class="pull-left image">
				<img src="../dist/img/avatar6.png" class="img-circle" alt="User Image" />
			</div>
			<div class="pull-left info">
				<p><%=userName%></p>
				<% if (user_service.isUserAdmin()) { %>[admin]<%} else {%>[volunteer]<%}%>
			</div>
		</div>
		<!-- sidebar menu: : style can be found in sidebar.less -->
		<ul class="sidebar-menu">
			<li class="header">MAIN NAVIGATION</li>
			<li <% if (page_name.matches("home")) { %>class="active" <% } %>><a href="/index.jsp?page=home"><i class="fa fa-home"></i> <span>Home</span></a></li>
			<li <% if (page_name.matches("map")) { %>class="active" <% } %>><a href="/index.jsp?page=map"><i class="fa fa-map-marker"></i> <span>Map</span></a></li>
			<li <% if (page_name.matches("events")) { %>class="active" <% } %>><a href="/index.jsp?page=events"><i class="fa fa-calendar"></i> <span>Events</span> <small class="label pull-right bg-red">${loader_lb.totalUpcomingEvents}</small></a></li>
			<li <% if (page_name.matches("import")) { %>class="active" <% } %>><a href="/index.jsp?page=import"><i class="fa fa-database"></i> <span>Import data</span></a></li>
			<li <% if (page_name.matches("manage")) { %>class="active" <% } %>><a href="/index.jsp?page=manage"><i class="fa fa-wrench"></i> <span>Manage data</span></a></li>
		</ul>
	</section>
</aside>
<!-- /.sidebar -->