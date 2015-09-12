<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>

<%
	String body = request.getParameter("page") != null ? request.getParameter("page") : "";
	UserService userService = UserServiceFactory.getUserService();
	User user = userService.getCurrentUser();
%>

<%@ include file="tpl/header.jsp"%>

<%-- Not logged in --%>
<%
	if (user == null) {
%>

	<%@ include file="tpl/content/login.jsp"%>

<%-- Logged in --%>
<%
	} else {
%>

<%-- Home --%>

<%
	if (body.equals("") || body.equals("home")) {
%>
	<%@ include file="tpl/content/home.jsp"%>

<%-- Other pages --%>

<%
	} else if (body.equals("map")) {
%>
	<%@ include file="tpl/content/map.jsp"%>
	
<%
	} else if (body.equals("events")) {
%>
	<%@ include file="tpl/content/events.jsp"%>

<%
	} else if (body.equals("import")) {
%>
	<%@ include file="tpl/content/import.jsp"%>
	
<%
	} else if (body.equals("manage")) {
%>
	<%@ include file="tpl/content/manage.jsp"%>
	
<%	
	} else if (body.equals("upload-litter")) {
%>
	<%@ include file="tpl/content/upload-litter.jsp"%>
	
<%	
	} else if (body.equals("upload-bin")) {
%>
	<%@ include file="tpl/content/upload-bin.jsp"%>

<%-- Error --%>

<%
	} else if (body.equals("error")) {
%>
	<%@ include file="tpl/system/500.jsp"%>

<%-- Default --%>

<%
	} else {
%>
	<%@ include file="tpl/system/404.jsp"%>
<%
	}}
%>
