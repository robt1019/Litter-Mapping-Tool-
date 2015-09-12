<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<% UserService us = UserServiceFactory.getUserService(); %>

<style>
	.spacer50 {
	    margin-top: 50px;
	}
	.spacer200 {
	    margin-top: 200px;
	}
</style>

	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Main content -->
		<section class="content">
			<div class="text-center h1 spacer200">
				<img alt="" src="img/trash_128x128.png">
			</div>
			<div class="text-center h3 spacer50">
				To access the Litter Mapping Tool, please <a href="<%= us.createLoginURL(request.getRequestURI()) %>?page=home">sign in</a> using your Google account.
			</div>
		</section>
		<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->
	<%@ include file="../footer.jsp"%>

</div>
<!-- ./wrapper -->

<!-- jQuery 2.1.4 -->
<script src="../plugins/jQuery/jQuery-2.1.4.min.js"></script>
<!-- Bootstrap 3.3.2 JS -->
<script src="../bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<!-- jQuery UI 1.11.1 -->
<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.min.js" type="text/javascript"></script>
<!-- Slimscroll -->
<script src="../plugins/slimScroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<!-- AdminLTE App -->
<script src="../dist/js/app.min.js" type="text/javascript"></script>

</body>
</html>