<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.Date"%>
<%@ page import="org.littermappingtool.backend.utils.jsp.UploadBinDataLoader"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cf" uri="http://bristol.ac.uk/littermappingtool/backend/utils/JSTLFunctions"%>

<link href="/plugins/timepicker/bootstrap-timepicker.min.css" rel="stylesheet"/>

<% 
	pageContext.setAttribute("loader_bu", new UploadBinDataLoader());
	UserService userService_bu = UserServiceFactory.getUserService();
	pageContext.setAttribute("userEmail", userService_bu.getCurrentUser().getEmail());
%>

<!-- Left side column. contains the logo and sidebar -->
<%@ include file="../../tpl/content/left-sidebar.jsp"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Bin uploader
		</h1>
		<ol class="breadcrumb">
			<li><a href="/index.jsp?page=home"><i class="fa fa-home"></i> Home</a></li>
			<li class="active">Upload bin</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js"></script>
		<div id="map-canvas-events"></div>
		<div id="map-info-bar">
			<form action="/persist-bin" id="upload-bin-item-form" method="post" accept-charset="utf-8">
				<div class="box box-default">
					<div class="box-header with-border">
						<h3 class="box-title">Location details:</h3>
					</div>
					<div class="box-body">
						<table class="event-dialog-table">
							<tr>
								<td class="event-dialog-td-name"><i class="fa fa-globe"></i>&nbsp;&nbsp;Latitude:</td>
								<td class="event-dialog-td-value"><span id="map-dialog-latitude"></span></td>
							</tr>
							<tr>
								<td class="event-dialog-td-name"><i class="fa fa-globe"></i>&nbsp;&nbsp;Longitude:</td>
								<td class="event-dialog-td-value"><span id="map-dialog-longitude"></span></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="box box-default">
					<div class="box-header with-border">
						<h3 class="box-title">Bin details:</h3>
					</div>
					<div class="box-body">					
						<div class="form-group">
							<label>Date:</label>
							<input id="bin-date-time" name="bin-date-time" type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask />
						</div>
						<div class="form-group">
							<label>Types:</label>
							<select name="bin-types-list" id="bin-types-list" class="data-mng-list">
								<c:if test="${fn:length(loader_bu.binTypes) gt 0}">
									<c:forEach var="binType" items="${loader_bu.binTypes}" varStatus="count">
										<option></option>
										<option value="${binType.name}">${binType.name}</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
						<div class="horizontal_space_5"></div>
					</div>
				</div>
				<input id="user-email" name="user-email" type="hidden" value="${userEmail}"/>
				<input id="latitude" name="latitude" type="hidden"/>
				<input id="longitude" name="longitude" type="hidden"/>
				<div class="box box-default">
					<div class="box-body">
						<button class="btn btn-info btn-flat" type="button" id="upload-bin-item">Upload</button>
					</div>
				</div>
			</form>
		</div>
		<script type="text/javascript">
		
			 $("#bin-date-time").inputmask("dd/mm/yyyy", {"placeholder": "dd/mm/yyyy"});
			
			// Event select init
			
			$(function() {			
				$("#bin-types-list").select2({ allowClear: false });
			});
			
			// Map scripts
		
			var map;
			var exactPosMarker = new google.maps.Marker();
		
		    function initializeMap() {
		    	
		        // Set map coordinates with lat and lng
		        var cord = new google.maps.LatLng(54.479802, -2.451904);
	
		        // Set map options
		        var mapOptions = {
		            zoom: 6,
		            center: cord,
		            mapTypeId: google.maps.MapTypeId.ROADMAP
		        };
	
		        // Set map
		        map = new google.maps.Map(document.getElementById('map-canvas-events'), mapOptions);
				google.maps.event.addListener(map, 'click', function(e){ fx(e.latLng) });
		        
		        $('#map-canvas-events').width(700);
		        $('#map-canvas-events').height(500);
		    }
		    
		    function fx(latLng) {
		    	
		    	exactPosMarker.setMap(null);
	
		    	var request = {
		    		origin: latLng,
		    		destination: latLng,
		    		travelMode: google.maps.DirectionsTravelMode.DRIVING
		    	};
		    	
    			exactPosMarker.setOptions({map:map, position:latLng, title:"Bin location (" + latLng.lat().toFixed(5) + ", " + latLng.lng().toFixed(5) + ")", icon: "img/marker-location-48x48.png"});
    			
    			$("#map-dialog-latitude").text(latLng.lat().toFixed(5));
    			$("#map-dialog-longitude").text(latLng.lng().toFixed(5));
    			$("#latitude").val(latLng.lat().toFixed(5));
    			$("#longitude").val(latLng.lng().toFixed(5));
		    		
		    }
		    
			initializeMap();
			google.maps.event.addDomListener(window, "load", initializeMap);
			
			$("#upload-bin-item").click(function(e) {
				e.preventDefault();
				//Get value and make sure it is not null
				var validationError = false;
				
				var lat = $("#latitude").val();
				if (lat.length == 0) {
					validationError = true;
				}
				
				var lng = $("#longitude").val();
				if (lng.length == 0) {
					validationError = true;
				}

				if (validationError) {
					return;
				}

				$("#upload-bin-item-form").submit();
			});
		</script>
	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->
<%@ include file="../footer.jsp"%>

</body>
</html>