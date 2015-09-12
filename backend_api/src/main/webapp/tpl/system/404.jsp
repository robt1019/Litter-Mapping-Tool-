<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="../../tpl/content/left-sidebar.jsp"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			404 Error Page
		</h1>
		<ol class="breadcrumb">
			<li><a href="/index.jsp?page=home"><i class="fa fa-home"></i> Home</a></li>
			<li class="active">404 Error</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="error-page">
			<h2 class="headline text-yellow"> 404</h2>
			<div class="error-content">
				<h3><i class="fa fa-warning text-yellow"></i> Oops! Page not found.</h3>
				<p>
					We could not find the page you were looking for.
				</p>
			</div>
		</div>
	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->
<%@ include file="../footer.jsp"%>

</body>
</html>