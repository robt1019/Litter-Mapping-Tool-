<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>

<%@ include file="../../tpl/content/left-sidebar.jsp"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Import <small>Import bin data using a CSV file</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="/index.jsp?page=home"><i class="fa fa-home"></i>Home</a></li>
			<li class="active">Import</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-md-4">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Bin data import</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<!-- form start -->
						<% BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); %>
						<form action="<%= blobstoreService.createUploadUrl("/upload-blob") %>" method="post" enctype="multipart/form-data">
							<div class="box-body">
								<div class="form-group">
									<label for="exampleInputFile">File input</label>
									<input type="file" name="data">
									<p class="help-block">Please provide a CSV file with bin data.</p>
								</div>
							</div>
							<div class="box-footer">
								<button type="submit" class="btn btn-primary btn-flat">Import</button>
							</div>
						</form>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
			<div class="col-md-8">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Bin data - CSV file example</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body no-padding">
						<table class="table">
							<tr>
								<th>Bin type</th>
								<th>Longitude</th>
								<th>Latitude</th>
								<th>Added</th>
							</tr>
							<tr>
								<td>Recycling</td>
								<td>51.457892</td>
								<td>-2.602454</td>
								<td>14/07/2015 14:01:10</td>
							</tr>
							<tr>
								<td>Landfill</td>
								<td>51.457464</td>
								<td>-2.602577</td>
								<td>13/07/2015 15:01:10</td>
							</tr>
							<tr>
								<td>Recycling</td>
								<td>51.457237</td>
								<td>-2.602459</td>
								<td>11/07/2015 11:30:09</td>
							</tr>
							<tr>
								<td>Landfill</td>
								<td>51.457050</td>
								<td>-2.602003</td>
								<td>11/07/2015 12:01:11</td>
							</tr>
							<tr>
								<td>Recycling</td>
								<td>51.457087</td>
								<td>-2.601477</td>
								<td>13/07/2015 15:01:10</td>
							</tr>
							<tr>
								<td>Landfill</td>
								<td>51.457428</td>
								<td>-2.601252</td>
								<td>16/07/2015 14:07:10</td>
							</tr>
							<tr>
								<td>Recycling</td>
								<td>51.457668</td>
								<td>-2.601601</td>
								<td>10/07/2015 15:01:10</td>
							</tr>
						</table>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
		</div>
	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->
<%@ include file="../footer.jsp"%>

</body>
</html>