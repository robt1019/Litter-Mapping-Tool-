<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="org.littermappingtool.backend.utils.jsp.DatastoreMngDataLoader"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cf" uri="http://bristol.ac.uk/littermappingtool/backend/utils/JSTLFunctions"%>

<%@ include file="../../tpl/content/left-sidebar.jsp"%>

<% pageContext.setAttribute("loader", new DatastoreMngDataLoader()); %>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Manage data <small>Insert, update, or delete backend data</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="/index.jsp?page=home"><i class="fa fa-home"></i>Home</a></li>
			<li class="active">Manage data</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-md-6">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Brands</h3>
						<div class="box-tools manage-new-entity">
							<a href="javascript:;" onclick="openAddDialog('brand')"><i class="fa fa-plus-circle manage-icon"></i> add new brand</a>
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-body no-padding">
						<table class="table">
							<c:choose>
								<c:when test="${fn:length(loader.brands) gt 0}">
									<tr>
										<th style="width: 30px">#</th>
										<th>ID</th>
										<th>Brand name</th>
										<th style="width: 130px">Update / delete</th>
									</tr>
									<c:forEach var="brand" items="${loader.brands}" varStatus="count">
										<tr>
											<td>${count.index + 1}</td>
											<td>${brand.id}</td>
											<td>${brand.name}</td>
											<td>
												<a href="javascript:;" onclick="openEditDialog('${brand.id}', 'brand', '${cf:handleApostrophes(brand.name)}');"><i class="fa fa-wrench manage-icon"></i></a>
												<form id="delete-brand-data" class="manage-form" action="/delete-sys-entity" method="post" accept-charset="utf-8">
													<a href="javascript:;" onclick="parentNode.submit();"><i class="fa fa-remove manage-icon"></i></a>
													<input type="hidden" name="item-id" value="${brand.id}"></input>
													<input type="hidden" name="item-type" value="brand"></input>
												</form>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<p class="manage-no-data">No data to display.</p>
								</c:otherwise>
							</c:choose>
						</table>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
			<div class="col-md-6">
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Litter types</h3>
						<div class="box-tools manage-new-entity">
							<a href="javascript:;" onclick="openAddDialog('litter-type')"><i class="fa fa-plus-circle manage-icon"></i> add new litter type</a>
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-body no-padding">
						<table class="table">
							<c:choose>
								<c:when test="${fn:length(loader.litterTypes) gt 0}">
									<tr>
										<th style="width: 30px">#</th>
										<th>ID</th>
										<th>Litter type name</th>
										<th style="width: 130px">Update / delete</th>
									</tr>
									<c:forEach var="litterType" items="${loader.litterTypes}" varStatus="count">
										<tr>
											<td>${count.index + 1}</td>
											<td>${litterType.id}</td>
											<td>${litterType.name}</td>
											<td>
												<a href="javascript:;" onclick="openEditDialog('${litterType.id}', 'litter-type', '${litterType.name}');"><i class="fa fa-wrench manage-icon"></i></a>
												<form id="delete-litterType-data" class="manage-form" action="/delete-sys-entity" method="post" accept-charset="utf-8">
													<a href="javascript:;" onclick="parentNode.submit();"><i class="fa fa-remove manage-icon"></i></a>
													<input type="hidden" name="item-id" value="${litterType.id}"></input>
													<input type="hidden" name="item-type" value="litter-type"></input>
												</form>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<p class="manage-no-data">No data to display.</p>
								</c:otherwise>
							</c:choose>
						</table>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Bin types</h3>
						<div class="box-tools manage-new-entity">
							<a href="javascript:;" onclick="openAddDialog('bin-type')"><i class="fa fa-plus-circle manage-icon"></i> add new bin type</a>
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-body no-padding">
						<table class="table">
							<c:choose>
								<c:when test="${fn:length(loader.binTypes) gt 0}">
									<tr>
										<th style="width: 30px">#</th>
										<th>ID</th>
										<th>Bin type name</th>
										<th style="width: 130px">Update / delete</th>
									</tr>
									<c:forEach var="binType" items="${loader.binTypes}" varStatus="count">
										<tr>
											<td>${count.index + 1}</td>
											<td>${binType.id}</td>
											<td>${binType.name}</td>
											<td>
												<a href="javascript:;" onclick="openEditDialog('${binType.id}', 'bin-type', '${binType.name}');"><i class="fa fa-wrench manage-icon"></i></a>
												<form id="delete-binType-data" class="manage-form" action="/delete-sys-entity" method="post" accept-charset="utf-8">
													<a href="javascript:;" onclick="parentNode.submit();"><i class="fa fa-remove manage-icon"></i></a>
													<input type="hidden" name="item-id" value="${binType.id}"></input>
													<input type="hidden" name="item-type" value="bin-type"></input>
												</form>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<p class="manage-no-data">No data to display.</p>
								</c:otherwise>
							</c:choose>
						</table>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Delete all entities - for DEV purposes only</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<form action="/delete-all-litters" method="get" accept-charset="utf-8">
							<a href="javascript:;" onclick="parentNode.submit();"><i class="fa fa-remove manage-icon"></i> Delete all litters</a>
						</form>
						<form action="/delete-all-bins" method="get" accept-charset="utf-8">
							<a href="javascript:;" onclick="parentNode.submit();"><i class="fa fa-remove manage-icon"></i> Delete all bins</a>
						</form>
						<form action="/delete-all-events" method="get" accept-charset="utf-8">
							<a href="javascript:;" onclick="parentNode.submit();"><i class="fa fa-remove manage-icon"></i> Delete all events</a>
						</form>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
				<div class="box box-primary">
					<div class="box-header">
						<h3 class="box-title">Add entities - for DEV purposes only</h3>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<a href="index.jsp?page=upload-litter"><i class="fa fa-plus-circle manage-icon"></i> Upload litter item</a><br>
						<a href="index.jsp?page=upload-bin"><i class="fa fa-plus-circle manage-icon"></i> Upload bin item</a>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /.box -->
			</div>
		</div>
	</section>
	<!-- Dialog for a new item -->
	<div id="add-dialog">
		<div class="form-group">
			<form id="add-dialog-form" action="/persist-sys-entity" method="post" accept-charset="utf-8">
				<input type="hidden" id="add-dialog-item-type" name="item-type"></input>	
				<label>Name:</label>
				<input id="add-dialog-item-name" type="text" class="form-control" name="item-name">
				<div class="modal-body">
					<div class="first-column">
						<div class="form-group">
							<label>Available searchable terms:</label>
							<span class="clear-added-items"><a onclick="clearAllSearchables()"><i class="fa fa-times"></i> clear</a></span>
							<span id="operation-ok-searchable"><i class="fa fa-check green-color">&nbsp;&nbsp;</i></span>
							<select id="add-dialog-items-searchable-terms"  multiple="multiple" name="items-searchable-terms" style="width: 100%;">
							</select>
						</div>
						<div class="form-group">
							<label>Add a new searchable term:</label>
							<div class="input-group">
								<input id="add-dialog-item-searchable-term" type="text" class="form-control" name="item-searchable">
								<span class="input-group-btn">
									<button class="btn btn-info btn-flat" type="button" id="add-searchable">Add</button>
								</span>
							</div>
						</div>
					</div>
					<div class="second-column">
						<div class="form-group">
							<label>Available categories:</label>
							<span class="clear-added-items" id="clear-added-items-add"><a onclick="clearAllCategories()"><i class="fa fa-times"></i> clear</a></span>
							<span id="operation-ok-category"><i class="fa fa-check green-color">&nbsp;&nbsp;</i></span>
							<select id="add-dialog-items-categories"  multiple="multiple" name="items-categories" style="width: 100%;">
							</select>
						</div>
						<div class="form-group">	
							<label>Add a new category:</label>
							<div class="input-group">
								<input id="add-dialog-item-category" type="text" class="form-control" name="item-category">
								<span class="input-group-btn">
									<button class="btn btn-info btn-flat" type="button" id="add-category">Add</button>
								</span>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
    <script type="text/javascript">
    
	    $(document).ready(function() {
	    	$("#operation-ok-searchable").hide();
	    	$("#operation-ok-category").hide();
	    });
	    
	    function clearAllSearchables() {
	    	$('#add-dialog-items-searchable-terms').empty();
			$("#add-dialog-items-searchable-terms").select2("destroy");
			$("#add-dialog-items-searchable-terms").select2();	    	
	    	$("#operation-ok-searchable").show().delay(1300).fadeOut();
	    }
	    
	    function clearAllCategories() {
	    	$('#add-dialog-items-categories').empty();
			$("#add-dialog-items-categories").select2("destroy");
			$("#add-dialog-items-categories").select2();
	    	$("#operation-ok-category").show().delay(1300).fadeOut();
	    }
    
    	// Init Select2 components
    	
    	$(function() {			
			$("#add-dialog-items-searchable-terms").select2({ allowClear: true });
			$("#add-dialog-items-categories").select2({ allowClear: true });
		});
    	
    	// New searchable/category
    	
    	$("#add-searchable").click(function(){
    		var value = $("#add-dialog-item-searchable-term").val();
    		if (value.length == 0) return;
    		$("#add-dialog-item-searchable-term").val("");
	    	$('#add-dialog-items-searchable-terms')
				.append($("<option></option>")
				.attr("value", value)
				.text(value));
	    	$("#operation-ok-searchable").show().delay(1300).fadeOut();
    	});
    	
    	$("#add-category").click(function(){
    		var value = $("#add-dialog-item-category").val();
    		if (value.length == 0) return;
    		$("#add-dialog-item-category").val("");
	    	$('#add-dialog-items-categories')
				.append($("<option></option>")
				.attr("value", value)
				.text(value));
	    	$("#operation-ok-category").show().delay(1300).fadeOut();
    	});
    	
    	// Init the add item dialog
    	
		var addItemDialog;
    	
		$(function() {
			addItemDialog = $("#add-dialog").dialog({
				autoOpen: false,
				width: 630,
				modal: true,
				buttons: {
					"Add": function() {
						$('#add-dialog-form').submit();
					},
					"Close": function() {
						addItemDialog.dialog("close");
					}
				}
			});
		});
		
		function openAddDialog(itemType) {
			
			// Empty data in multi-selectors
			$('#add-dialog-items-searchable-terms').empty();
			$("#add-dialog-items-searchable-terms").select2("destroy");
			$("#add-dialog-items-searchable-terms").select2();	
			$('#add-dialog-items-categories').empty();
			$("#add-dialog-items-categories").select2("destroy");
			$("#add-dialog-items-categories").select2();
			
			// Define title
			var dialogTitle = "";
			if (itemType.localeCompare("brand") == 0) {
				
				dialogTitle = "Add new brand";
				$('#add-dialog-items-categories').prop('disabled', false);
				$('#add-dialog-item-category').prop('disabled', false);
				$('#clear-added-items-add').css('visibility', 'visible');
				
			} else if (itemType.localeCompare("litter-type") == 0) {
				
				dialogTitle = "Add new litter type";
				$('#add-dialog-items-categories').prop('disabled', true);
				$('#add-dialog-item-category').prop('disabled', true);
				$('#clear-added-items-add').css('visibility', 'hidden');
				
			} else if (itemType.localeCompare("bin-type") == 0) {
				
				dialogTitle = "Add new bin type";
				$('#add-dialog-items-categories').prop('disabled', true);
				$('#add-dialog-item-category').prop('disabled', true);
				$('#clear-added-items-add').css('visibility', 'hidden');
				
			}
			
			// 
			addItemDialog.dialog("open");
			$('#add-dialog').dialog('option', 'title', dialogTitle);
			$('#add-dialog-item-type').val(itemType);
			$('#add-dialog-item-name').val("");
		}
    </script>
	<!-- Dialog for updating an existing item -->
	<div id="update-dialog">
		<div class="form-group">
			<form id="update-dialog-form" action="/update-sys-entity" method="post" accept-charset="utf-8">
				<input type="hidden" id="update-dialog-item-id" name="item-id"></input>
				<input type="hidden" id="update-dialog-item-type" name="item-type"></input>	
				<label>Name:</label>
				<input id="update-dialog-item-name" type="text" class="form-control" name="item-new-name">
				<div class="modal-body">
					<div class="first-column">
						<div class="form-group">
							<label>Available searchable terms:</label>
							<span class="clear-added-items"><a onclick="clearAllSearchablesUpDia()"><i class="fa fa-times"></i> clear</a></span>
							<span id="operation-ok-searchable-upDia"><i class="fa fa-check green-color">&nbsp;&nbsp;</i></span>
							<select id="update-dialog-items-searchable-terms"  multiple="multiple" name="items-searchable-terms" style="width: 100%;">
							</select>
						</div>
						<div class="form-group">
							<label>Add a new searchable term:</label>
							<div class="input-group">
								<input id="update-dialog-item-searchable-term" type="text" class="form-control" name="item-searchable">
								<span class="input-group-btn">
									<button class="btn btn-info btn-flat" type="button" id="add-searchable-upDia">Add</button>
								</span>
							</div>
						</div>
					</div>
					<div class="second-column">
						<div class="form-group">
							<label>Available categories:</label>
							<span class="clear-added-items" id="clear-added-items-UpDia"><a onclick="clearAllCategoriesUpDia()"><i class="fa fa-times"></i> clear</a></span>
							<span id="operation-ok-category-upDia"><i class="fa fa-check green-color">&nbsp;&nbsp;</i></span>
							<select id="update-dialog-items-categories"  multiple="multiple" name="items-categories" style="width: 100%;">
							</select>
						</div>
						<div class="form-group">	
							<label>Add a new category:</label>
							<div class="input-group">
								<input id="update-dialog-item-category" type="text" class="form-control" name="item-category">
								<span class="input-group-btn">
									<button class="btn btn-info btn-flat" type="button" id="add-category-upDia">Add</button>
								</span>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
    <script type="text/javascript">
    
	    $(document).ready(function() {
	    	$("#operation-ok-searchable-upDia").hide();
	    	$("#operation-ok-category-upDia").hide();
	    });
	    
	    function clearAllSearchablesUpDia() {
	    	$('#update-dialog-items-searchable-terms').empty();
			$("#update-dialog-items-searchable-terms").select2("destroy");
			$("#update-dialog-items-searchable-terms").select2();
	    	$("#operation-ok-searchable-upDia").show().delay(1300).fadeOut();
	    }
	    
	    function clearAllCategoriesUpDia() {
	    	$('#update-dialog-items-categories').empty();
			$("#update-dialog-items-categories").select2("destroy");
			$("#update-dialog-items-categories").select2();
	    	$("#operation-ok-category-upDia").show().delay(1300).fadeOut();
	    }
	
		// Init Select2 components
		
		$(function() {			
			$("#update-dialog-items-searchable-terms").select2({ allowClear: true });
			$("#update-dialog-items-categories").select2({ allowClear: true });
		});
		
    	// New searchable/category
    	
    	$("#add-searchable-upDia").click(function(){
    		var value = $("#update-dialog-item-searchable-term").val();
    		if (value.length == 0) return;
    		$("#update-dialog-item-searchable-term").val("");
	    	$('#update-dialog-items-searchable-terms')
				.append($("<option></option>")
				.attr("value", value)
				.text(value));
	    	$("#operation-ok-searchable-upDia").show().delay(1300).fadeOut();
    	});
    	
    	$("#add-category-upDia").click(function(){
    		var value = $("#update-dialog-item-category").val();
    		if (value.length == 0) return;
    		$("#update-dialog-item-category").val("");
	    	$('#update-dialog-items-categories')
				.append($("<option></option>")
				.attr("value", value)
				.text(value));
	    	$("#operation-ok-category-upDia").show().delay(1300).fadeOut();
    	});
		
		// Init the update item dialog
    
		var updateItemDialog;
		
		$(function() {
			updateItemDialog = $("#update-dialog").dialog({
				autoOpen: false,
				width: 630,
				modal: true,
				buttons: {
					"Update": function() {
						$('#update-dialog-form').submit();
					},
					"Close": function() {
						updateItemDialog.dialog("close");
					}
				}
			});
		});
		
		function openEditDialog(itemId, itemType, itemName) {
			
			var dialogTitle, formData, jsonData;
			
			// Empty data in multi-selectors
			$('#update-dialog-items-searchable-terms').empty();
			$('#update-dialog-items-categories').empty();
			
			// Get all data based on item type
			if (itemType.localeCompare("brand") == 0) {
				
				dialogTitle = "Update brand " + itemName;
				
				jsonData = ${loader.brandsJsonString};
				formData = $.grep(jsonData, function (element, index) {
					return element.id == itemId;
				});
				
				// Set searchables
				if (formData[0].searchTerms != null) {
					jQuery.each(formData[0].searchTerms, function(i, searchTerm) {
						$('#update-dialog-items-searchable-terms')
							.append($("<option></option>")
							.attr("selected", "selected")
							.attr("value", searchTerm)
							.text(searchTerm));
					});
				}
				
				// Set categories
				if (formData[0].categories != null) {
					jQuery.each(formData[0].categories, function(i, category) {
						$('#update-dialog-items-categories')
							.append($("<option></option>")
							.attr("selected", "selected")
							.attr("value", category)
							.text(category));
					});
				}
				
				$('#update-dialog-items-categories').prop('disabled', false);
				$('#update-dialog-item-category').prop('disabled', false);
				$('#clear-added-items-UpDia').css('visibility', 'visible');
				
			} else if (itemType.localeCompare("litter-type") == 0) {
				
				dialogTitle = "Update litter type " + itemName;
				
				jsonData = ${loader.litterTypesJsonString};
				formData = $.grep(jsonData, function (element, index) {
					return element.id == itemId;
				});
				
				// Set searchables
				if (formData[0].searchTerms != null) {
					jQuery.each(formData[0].searchTerms, function(i, searchTerm) {
						$('#update-dialog-items-searchable-terms')
							.append($("<option></option>")
							.attr("selected", "selected")
							.attr("value", searchTerm)
							.text(searchTerm));
					});
				}
				
				$('#update-dialog-items-categories').prop('disabled', true);
				$('#update-dialog-item-category').prop('disabled', true);
				$('#clear-added-items-UpDia').css('visibility', 'hidden');
				
			} else if (itemType.localeCompare("bin-type") == 0) {
				
				dialogTitle = "Update bin item " + itemName;
				
				jsonData = ${loader.binTypesJsonString};
				formData = $.grep(jsonData, function (element, index) {
					return element.id == itemId;
				});
				
				// Set searchables
				if (formData[0].searchTerms != null) {
					jQuery.each(formData[0].searchTerms, function(i, searchTerm) {
						$('#update-dialog-items-searchable-terms')
							.append($("<option></option>")
							.attr("selected", "selected")
							.attr("value", searchTerm)
							.text(searchTerm));
					});
				}
				
				$('#update-dialog-items-categories').prop('disabled', true);
				$('#update-dialog-item-category').prop('disabled', true);
				$('#clear-added-items-UpDia').css('visibility', 'hidden');
				
			}
			
			// Refresh/display data in multi-selectors
			$("#update-dialog-items-searchable-terms").select2("destroy");
			$("#update-dialog-items-searchable-terms").select2();
			$("#update-dialog-items-categories").select2("destroy");
			$("#update-dialog-items-categories").select2();

			// Set other values
			$('#update-dialog').dialog('option', 'title', dialogTitle);
			$('#update-dialog-item-id').val(itemId);
			$('#update-dialog-item-type').val(itemType);
			$('#update-dialog-item-name').val(itemName);
			
			// Open dialog
			updateItemDialog.dialog("open");
		}
		
		$(window).resize(function() {
		    $("#add-dialog, #update-dialog").dialog("option", "position", {my: "center", at: "center", of: window});
		});
		

    </script>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->
<%@ include file="../footer.jsp"%>

</body>
</html>