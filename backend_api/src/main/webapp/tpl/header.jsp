<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="org.littermappingtool.backend.utils.PageUtils" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cf" uri="http://bristol.ac.uk/littermappingtool/backend/utils/JSTLFunctions" %>

<%
	String p = request.getParameter("page") != null ? request.getParameter("page") : "";
	UserService userService_h = UserServiceFactory.getUserService();
	User user_h = userService_h.getCurrentUser();
	String title = "";
	
	if (user_h != null) {
		title = PageUtils.getPageTitle(p);
	} else {
		title = "Litter Mapping Tool - login";
	}
%>

<!DOCTYPE html>
<html>
<head>
<title><%= title %></title>
<meta charset="UTF-8">

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css"/>
<link rel="stylesheet" type="text/css" href="../plugins/font-awesome/css/font-awesome.min.css"/>
<link rel="stylesheet" type="text/css" href="../plugins/ionicons/css/ionicons.min.css"/>
<link rel="stylesheet" type="text/css" href="../plugins/fullcalendar/fullcalendar.min.css"/>
<link rel="stylesheet" type="text/css" href="../plugins/fullcalendar/fullcalendar.print.css" media="print" />
<link rel="stylesheet" type="text/css" href="../dist/css/AdminLTE.min.css"/>
<link rel="stylesheet" type="text/css" href="../dist/css/skins/_all-skins.min.css"/>
<link rel="stylesheet" type="text/css" href="../dist/css/LitterMappingTool.css"/>
<link rel="stylesheet" type="text/css" href="../plugins/select2/select2.css" />
<link rel="stylesheet" type="text/css" href="../plugins/tooltipster/css/tooltipster.css" />

<!-- Linked Java Scripts -->
<script type="text/javascript" src="../plugins/jQuery/jquery.js"></script>
<script type="text/javascript" src="../plugins/jQueryUI/jquery-ui.js"></script>
<script type="text/javascript" src="../plugins/easy-notification/easy.notification.js"></script>
<script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../plugins/slimScroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="../plugins/fastclick/fastclick.min.js"></script>
<script type="text/javascript" src="../dist/js/app.min.js"></script>
<script type="text/javascript" src="../dist/js/littermappingtool-custom.js"></script>
<script type="text/javascript" src="../plugins/moment/moment.min.js"></script>
<script type="text/javascript" src="../plugins/fullcalendar/fullcalendar.min.js"></script>
<script type="text/javascript" src="../plugins/select2/select2.js"></script>
<script type="text/javascript" src="../plugins/tooltipster/js/jquery.tooltipster.min.js"></script>
<script type="text/javascript" src="../plugins/input-mask/jquery.inputmask.js"></script>
<script type="text/javascript" src="../plugins/input-mask/jquery.inputmask.date.extensions.js"></script>
<script type="text/javascript" src="../plugins/map/litters_clusterer.js"></script>

<!-- Custom Java Scripts -->
<!-- Loader (spinner) -->
<script type="text/javascript">
	$(document).ready(function() {
    	$('.tooltip').tooltipster();
    });
	//<![CDATA[
		$(window).load(function() {
			$('#status').fadeOut();
			$('#preloader').delay(350).fadeOut('slow');
			$('body').delay(350).css({'overflow':'visible'});
		})
	//]]>
</script>

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->

</head>
<body class="skin-blue sidebar-mini">
	<div id="preloader">
	    <div id="status">&nbsp;</div>
	</div>
	<%String messageType = (String) session.getAttribute("message-type");
	String message = (String) session.getAttribute("message");
	session.removeAttribute("message-type");
	session.removeAttribute("message");
	if (message != null) {%>
	<div id="notification_messsage">
		<script type="text/javascript">
			$(function() {
				$.easyNotification('<%=message%>', '<%=messageType%>');	
			});
		</script>
	</div>
	<%}%>
	<div class="wrapper">
		<header class="main-header">
		
			<!-- Logo -->
			<div class="logo">
				<!-- mini logo for sidebar mini 50x50 pixels -->
				<span class="logo-mini"></span>
				<!-- logo for regular state and mobile devices -->
				<span class="logo-lg"></span>
			</div>

			<!-- Header Navbar -->
			<nav class="navbar navbar-static-top" role="navigation">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button"> <span class="sr-only">Toggle navigation</span></a>
				<% if (user_h != null) { %>
					<!-- Navbar Right Menu -->
					<div class="navbar-custom-menu">
						<ul class="nav navbar-nav">
							<!-- User Account Menu -->
							<li class="dropdown user user-menu">
								<!-- Menu Toggle Button -->
								<a href="#" class="dropdown-toggle" data-toggle="dropdown"> <!-- The user image in the navbar-->
									<img src="../dist/img/avatar6.png" class="user-image" alt="User Image" /> <!-- hidden-xs hides the username on small devices so only the image appears. -->
									<span class="hidden-xs"><%=userService_h.getCurrentUser().getNickname()%></span>
								</a>
								<ul class="dropdown-menu">
									<!-- The user image in the menu -->
									<li class="user-header"><img src="../dist/img/avatar6.png"
										class="img-circle" alt="User Image" />
										<p>
											<%=userService_h.getCurrentUser().getNickname()%> <small><% if (userService_h.isUserAdmin()) { %>Administrator<%} else {%>Volunteer<%}%></small>
										</p>
									</li>
									<!-- Menu Footer-->
									<li class="user-footer">
										<div class="pull-right">
											<a href="<%=userService_h.createLogoutURL(request.getRequestURI())%>" class="btn btn-default btn-flat">Sign out</a>
										</div>
									</li>
								</ul>
							</li>
						</ul>
					</div>
				<% } %>
			</nav>
		</header>