<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ include file="../../tpl/content/left-sidebar.jsp"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			500 Error Page
		</h1>
		<ol class="breadcrumb">
			<li><a href="/index.jsp?page=home"><i class="fa fa-home"></i> Home</a></li>
			<li class="active">500 Error</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="error-page">
			<h2 class="headline text-yellow"> 500</h2>
			<div class="error-content">
				<h3><i class="fa fa-warning text-yellow"></i> Oops! Something went wrong.</h3>
				<p>
					We will work on fixing that right away.
				</p>
				<p>
					<%
						String errorText = request.getParameter("error-text");
						pageContext.setAttribute("errorText", errorText);
					%>
					<c:if test="${not empty errorText}">
						<b>Error description:</b><br>
						${errorText}
					</c:if>
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