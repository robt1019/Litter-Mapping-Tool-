<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.Date"%>
<%@ page import="org.littermappingtool.backend.utils.jsp.UploadLitterDataLoader"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cf" uri="http://bristol.ac.uk/littermappingtool/backend/utils/JSTLFunctions"%>

<link href="/plugins/timepicker/bootstrap-timepicker.min.css" rel="stylesheet"/>

<% 
	pageContext.setAttribute("loader_lu", new UploadLitterDataLoader());
	UserService userService_lu = UserServiceFactory.getUserService();
	pageContext.setAttribute("userEmail", userService_lu.getCurrentUser().getEmail());
%>

<!-- Left side column. contains the logo and sidebar -->
<%@ include file="../../tpl/content/left-sidebar.jsp"%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Litter uploader
		</h1>
		<ol class="breadcrumb">
			<li><a href="/index.jsp?page=home"><i class="fa fa-home"></i> Home</a></li>
			<li class="active">Upload litter</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js"></script>
		<div id="map-canvas-events"></div>
		<div id="map-info-bar">
			<form action="/persist-litter" id="upload-litter-item-form" method="post" accept-charset="utf-8">
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
						<h3 class="box-title">Litter details:</h3>
					</div>
					<div class="box-body">					
						<div class="form-group">
							<label>Date:</label>
							<input id="litter-date-time" name="litter-date-time" type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask />
						</div>
						<div class="form-group">
							<label>Brands:</label>
							<select name="brands-list" id="brands-list" class="data-mng-list">
								<c:if test="${fn:length(loader_lu.brands) gt 0}">
									<c:forEach var="brand" items="${loader_lu.brands}" varStatus="count">
										<option></option>
										<option value="${brand.name}">${brand.name}</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
						<div class="form-group">
							<label>Types:</label>
							<select name="litter-types-list" id="litter-types-list" class="data-mng-list">
								<c:if test="${fn:length(loader_lu.litterTypes) gt 0}">
									<c:forEach var="litterType" items="${loader_lu.litterTypes}" varStatus="count">
										<option></option>
										<option value="${litterType.name}">${litterType.name}</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
						<div class="form-group">
							<label>Events:</label>
							<select name="events-list" id="events-list" class="data-mng-list">
								<c:if test="${fn:length(loader_lu.events) gt 0}">
									<c:forEach var="event" items="${loader_lu.events}" varStatus="count">
										<option></option>
										<option value="${event.id}">${event.title} [${event.latitude},${event.longitude}]</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
						<div class="horizontal_space_5"></div>
						<a href="#" onclick="findEventLocation();"><i class="fa fa-crosshairs"></i>&nbsp;&nbsp;Go to selected event location</a>
					</div>
				</div>
				<input id="user-email" name="user-email" type="hidden" value="${userEmail}"/>
				<input id="latitude" name="latitude" type="hidden"/>
				<input id="longitude" name="longitude" type="hidden"/>
				<div class="box box-default">
					<div class="box-body">
						<button class="btn btn-info btn-flat" type="button" id="upload-litter-item">Upload</button>
					</div>
				</div>
			</form>
		</div>
		<script type="text/javascript">
		
			 $("#litter-date-time").inputmask("dd/mm/yyyy", {"placeholder": "dd/mm/yyyy"});
			
			// Event select init
			
			$(function() {			
				$("#brands-list").select2({ allowClear: false });
				$("#litter-types-list").select2({ allowClear: false });
				$("#events-list").select2({ allowClear: false });
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
		    
		    function findEventLocation() {
		    	
		    	var eventListValue = $('#events-list option:selected').text();
		    	var latitude = eventListValue.substring(eventListValue.indexOf("[") + 1, eventListValue.indexOf(",") - 1);
		    	var longitude = eventListValue.substring(eventListValue.indexOf(",") + 1, eventListValue.indexOf("]"));
		    	
    			var pos = new google.maps.LatLng(latitude, longitude);
    			map.setCenter(pos);
    			map.setZoom(17);
		    }
		    
		    function fx(latLng) {
		    	
		    	exactPosMarker.setMap(null);
	
		    	var request = {
		    		origin: latLng,
		    		destination: latLng,
		    		travelMode: google.maps.DirectionsTravelMode.DRIVING
		    	};
		    	
    			exactPosMarker.setOptions({map:map, position:latLng, title:"Litter location (" + latLng.lat().toFixed(5) + ", " + latLng.lng().toFixed(5) + ")", icon: "img/marker-location-48x48.png"});
    			
    			$("#map-dialog-latitude").text(latLng.lat().toFixed(5));
    			$("#map-dialog-longitude").text(latLng.lng().toFixed(5));
    			$("#latitude").val(latLng.lat().toFixed(5));
    			$("#longitude").val(latLng.lng().toFixed(5));
		    		
		    }
		    
			initializeMap();
			google.maps.event.addDomListener(window, "load", initializeMap);
			
			$("#upload-litter-item").click(function(e) {
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

				$("#upload-litter-item-form").submit();
			});
		</script>
	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->
<%@ include file="../footer.jsp"%>

</body>
</html>