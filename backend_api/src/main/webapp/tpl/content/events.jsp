<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.Date"%>
<%@ page import="org.littermappingtool.backend.utils.jsp.EventsDataLoader"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cf" uri="http://bristol.ac.uk/littermappingtool/backend/utils/JSTLFunctions"%>

<link href="/plugins/timepicker/bootstrap-timepicker.min.css" rel="stylesheet"/>

<% pageContext.setAttribute("loader", new EventsDataLoader()); %>

<!-- Left side column. contains the logo and sidebar -->
<%@ include file="../../tpl/content/left-sidebar.jsp"%>

<script type="text/javascript">
	var locationSelectDialog;
	$(function() {
		locationSelectDialog = $("#location-select-dialog").dialog({
			autoOpen: false,
			width: '80%',
			height: 610,
			modal: true,
			title: 'Location select dialog',
			buttons: {
				"Confirm": function() {
					var city = $("#event-map-dialog-city").text();
					var country = $("#event-map-dialog-country").text();
					if (city.length == 0) {
						$("#location-select-validation-error").text("Validation error: missing city");
						return;
					} else if (country.length == 0) {
						$("#location-select-validation-error").text("Validation error: missing country");
						return;
					} else {
						var loc = "";
						loc += $("#event-map-dialog-city").text() + ", ";
						loc += $("#event-map-dialog-country").text() + " (";
						loc += $("#event-map-dialog-latitude").text() + ", ";
						loc += $("#event-map-dialog-longitude").text() + ")";
						$("#event-location").val(loc);
						$("#event-location").css("border-color", "#d2d6de");
						locationSelectDialog.dialog("close");
					}
				},
				"Close": function() {
					locationSelectDialog.dialog("close");
				}
			}
		});
	});
	
	function validateInput(inputId) {
		if ($(inputId).val() != 0) {
			$(inputId).css("border-color", "#d2d6de");				
		}
	}
</script>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Events <small>Let's organize a new event!</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="/index.jsp?page=home"><i class="fa fa-home"></i> Home</a></li>
			<li class="active">Events</li>
		</ol>
	</section>

	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-md-3">
				<div class="box box-solid">
					<div class="box-header with-border">
						<h4 class="box-title">Quick Overview</h4>
					</div>
					<div class="box-body">
						<p>Total upcoming events: ${loader.totalUpcomingEvents}</p>
						<p>Upcoming events this month: ${loader.upcomingEventsThisMonth}</p>
					</div>
				</div>
				<div class="box box-solid">
					<form action="/upload-event" id="add-new-event-form" method="post" accept-charset="utf-8">
						<div class="box-header with-border">
							<h3 class="box-title">Add New Event</h3>
						</div>
						<div class="box-body">
							<div class="btn-group" style="width: 100%; margin-bottom: 10px;">
								<ul class="fc-color-picker" id="color-chooser">
									<li><a class="text-aqua" href="#"><i class="fa fa-square"></i></a></li>
									<li><a class="text-blue" href="#"><i class="fa fa-square"></i></a></li>
									<li><a class="text-light-blue" href="#"><i class="fa fa-square"></i></a></li>
									<li><a class="text-teal" href="#"><i class="fa fa-square"></i></a></li>
									<li><a class="text-yellow" href="#"><i class="fa fa-square"></i></a></li>
									<li><a class="text-orange" href="#"><i class="fa fa-square"></i></a></li>
									<li><a class="text-green" href="#"><i class="fa fa-square"></i></a></li>
									<li><a class="text-lime" href="#"><i class="fa fa-square"></i></a></li>
									<li><a class="text-red" href="#"><i class="fa fa-square"></i></a></li>
									<li><a class="text-purple" href="#"><i class="fa fa-square"></i></a></li>
									<li><a class="text-fuchsia" href="#"><i class="fa fa-square"></i></a></li>
									<li><a class="text-muted" href="#"><i class="fa fa-square"></i></a></li>
									<li><a class="text-navy" href="#"><i class="fa fa-square"></i></a></li>
								</ul>
							</div>
							<input id="event-color" name="event-color" type="hidden"/>
							<div class="form-group">
								<input id="event-title" name="event-title" type="text" class="form-control" placeholder="Event Title" onkeyup="validateInput('#event-title');">
							</div>
							<div class="form-group">
								<textarea id="event-description" name="event-description" class="form-control" rows="3" placeholder="Description"></textarea>
							</div>
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-calendar"></i>
									</div>
									<input id="event-date" name="event-date" type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask onkeyup="validateInput('#event-date');"/>
								</div>
							</div>
							<div class="bootstrap-timepicker">
								<div class="form-group">
									<div class="input-group">
										<div class="input-group-addon">
											<i class="fa fa-clock-o"></i>
										</div>
										<input id="event-time" name="event-time" type="text" class="form-control timepicker" onkeyup="validateInput('#event-time');"/>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-addon">
										<i class="fa fa-map-marker"></i>
									</div>
									<input id="event-location" name="event-location" type="text" class="form-control" placeholder="Click to add location..." onclick="openLocationSelectDialog();" readonly="readonly"/>
								</div>
							</div>
						</div>
						<div class="box-footer">
							<button id="add-new-event" type="submit" class="btn btn-primary btn-flat">Add event</button>
						</div>
					</form>
				</div>
			</div>
			<!-- /.col -->
			<div class="col-md-9">
				<div class="box box-primary">
					<div class="box-body no-padding">
						<!-- THE CALENDAR -->
						<div id="calendar"></div>
					</div>
					<!-- /.box-body -->
				</div>
				<!-- /. box -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->
<%@ include file="../footer.jsp"%>

<!-- Control Sidebar -->
<aside class="control-sidebar control-sidebar-dark">
	<!-- Create the tabs -->
	<ul class="nav nav-tabs nav-justified control-sidebar-tabs">
		<li class="active"><a href="#control-sidebar-settings-tab"
			data-toggle="tab"><i class="fa fa-gears"></i></a></li>
	</ul>
	<!-- Tab panes -->
	<div class="tab-content">
		<!-- Settings tab content -->
		<div class="tab-pane" id="control-sidebar-settings-tab">
			<form method="post">
				<h3 class="control-sidebar-heading">General Settings</h3>
			</form>
		</div>
		<!-- /.tab-pane -->
	</div>
</aside>
<!-- /.control-sidebar -->
<!-- Add the sidebar's background. This div must be placed immediately after the control sidebar -->
<div class='control-sidebar-bg'></div>
<!-- ./wrapper -->
<div id="event-details-dialog">
	<table class="event-dialog-table">
		<tr>
			<td class="event-dialog-td-name"><i class="fa fa-pencil-square-o">&nbsp;&nbsp;</i>Title:</td>
			<td class="event-dialog-td-value"><span id="event-details-dialog-title"></span></td>
		</tr>
		<tr>
			<td class="event-dialog-td-name"><i class="fa fa-pencil-square-o"></i>&nbsp;&nbsp;Description:</td>
			<td class="event-dialog-td-value"><span id="event-details-dialog-desc"></span></td>
		</tr>
		<tr>
			<td class="event-dialog-td-name"><i class="fa fa-calendar"></i>&nbsp;&nbsp;Date:</td>
			<td class="event-dialog-td-value"><span id="event-details-dialog-date"></span></td>
		</tr>
		<tr>
			<td class="event-dialog-td-name"><i class="fa fa-map-o"></i>&nbsp;&nbsp;City:</td>
			<td class="event-dialog-td-value"><span id="event-details-dialog-city"></span></td>
		</tr>
		<tr>
			<td class="event-dialog-td-name"><i class="fa fa-map-o"></i>&nbsp;&nbsp;Country:</td>
			<td class="event-dialog-td-value"><span id="event-details-dialog-country"></span></td>
		</tr>
		<tr>
			<td class="event-dialog-td-name"><i class="fa fa-globe"></i>&nbsp;&nbsp;Latitude:</td>
			<td class="event-dialog-td-value"><span id="event-details-dialog-latitude"></span></td>
		</tr>
		<tr>
			<td class="event-dialog-td-name"><i class="fa fa-globe"></i>&nbsp;&nbsp;Longitude:</td>
			<td class="event-dialog-td-value"><span id="event-details-dialog-longitude"></span></td>
		</tr>
	</table>
</div>
<div id="location-select-dialog">
	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js"></script>
	<div id="map-canvas-events"></div>
	<div id="map-info-bar">
		<div class="box box-default">
			<div class="box-header with-border">
				<h3 class="box-title">Info:</h3>
			</div>
			<div class="box-body">
				Click on the map to identify the event location.
			</div>
		</div>
		<div class="box box-default">
			<div class="box-header with-border">
				<h3 class="box-title">My location:</h3>
			</div>
			<div class="box-body">
				<a href="#" onclick="findUsersLocation();"><i class="fa fa-crosshairs"></i>&nbsp;&nbsp;Go to my location</a>
				<div class="horizontal_space_5"></div>
				<p><i class="fa fa-exclamation-triangle"></i> Approximate location based on internet routing. Needs to be supported / allowed by your browser.</p>
			</div>
		</div>
		<div class="box box-default">
			<div class="box-header with-border">
				<h3 class="box-title">Location details:</h3>
			</div>
			<div class="box-body">
				<table class="event-dialog-table">
					<tr>
						<td class="event-dialog-td-name"><i class="fa fa-map-o"></i>&nbsp;&nbsp;City:</td>
						<td class="event-dialog-td-value"><span id="event-map-dialog-city"></span></td>
					</tr>
					<tr>
						<td class="event-dialog-td-name"><i class="fa fa-map-o"></i>&nbsp;&nbsp;Country:</td>
						<td class="event-dialog-td-value"><span id="event-map-dialog-country"></span></td>
					</tr>
					<tr>
						<td class="event-dialog-td-name"><i class="fa fa-globe"></i>&nbsp;&nbsp;Latitude:</td>
						<td class="event-dialog-td-value"><span id="event-map-dialog-latitude"></span></td>
					</tr>
					<tr>
						<td class="event-dialog-td-name"><i class="fa fa-globe"></i>&nbsp;&nbsp;Longitude:</td>
						<td class="event-dialog-td-value"><span id="event-map-dialog-longitude"></span></td>
					</tr>
				</table>
			</div>
		</div>
		<div id="location-select-validation-error"></div>
	</div>
	<script type="text/javascript">
	
		var map;
		var directionsService = new google.maps.DirectionsService();
		var streetPosMarker = new google.maps.Marker();
		var exactPosMarker = new google.maps.Marker();
		var geocoder = new google.maps.Geocoder();
	
	    function initializeMap() {
	    	
	    	directionsDisplay = new google.maps.DirectionsRenderer();

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
	        directionsDisplay.setMap(map);
			google.maps.event.addListener(map,'click',function(e){fx(e.latLng)});
	        
	        $('#map-canvas-events').width($('#location-select-dialog').width() - 310).height($('#location-select-dialog').height());
	        $('#map-info-bar').height($('#location-select-dialog').height() - 4);
	    }
	    
	    function findUsersLocation() {
	    	
	    	if(navigator.geolocation) {
	    		
	    		navigator.geolocation.getCurrentPosition(function(position) {
	    			
	    			var pos = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
	    			map.setCenter(pos);
	    			map.setZoom(13);
	    			
	    		}, function() {
	    			handleNoGeolocation(true);
	    		});
	    		
	    	} else {
	    		// Browser doesn't support Geolocation
	    		handleNoGeolocation(false);
	    	}
	    }
	    
	    function handleNoGeolocation(errorFlag) {
	    	if (errorFlag == true) {
	    		alert("Geolocation service temporarily unavailable, please try again later.");
	    	} else {
	    		alert("Your browser doesn't support geolocation.");
	    	}
	    }
	    
	    function fx(latLng) {
	    	
	    	streetPosMarker.setMap(null);
	    	exactPosMarker.setMap(null);

	    	var request = {
	    		origin: latLng,
	    		destination: latLng,
	    		travelMode: google.maps.DirectionsTravelMode.DRIVING
	    	};
	    	
	    	directionsService.route(request, function(response, status) {
	    		
	    		if (status == google.maps.DirectionsStatus.OK) {
	    			
	    			var point = response.routes[0].legs[0];
	    			
	    			streetPosMarker.setOptions({map:map, position:point.start_location, title:"Nearest street/road (" +
	    					point.start_location.lat().toFixed(5) + ", " + point.start_location.lng().toFixed(5) + ")", icon: "img/marker-road-48x48.png"});
	    			exactPosMarker.setOptions({map:map, position:latLng, title:"New event location (" +
	    					latLng.lat().toFixed(5) + ", " + latLng.lng().toFixed(5) + ")", icon: "img/marker-location-48x48.png"});

	    			geocoder.geocode({"location": point.start_location}, function(results, status) {
	    				if (status == google.maps.GeocoderStatus.OK) {
	    					if (results[1]) {
	    						results[1].address_components.forEach(function(addComp) {
	    							addComp.types.forEach(function(type) {
	    								if (type == "country") {
		    								$("#event-map-dialog-country").text(addComp.short_name);
		    							} else if (type == "locality") {
		    								$("#event-map-dialog-city").text(addComp.short_name);		    								
		    							}
	    							});
	    						});	    								
	    					}
	    				}
	    			});
	    			
	    			$("#event-map-dialog-latitude").text(latLng.lat().toFixed(5));
	    			$("#event-map-dialog-longitude").text(latLng.lng().toFixed(5));
	    		}
	    	});
	    }
	    
		function openLocationSelectDialog() {
			locationSelectDialog.dialog("open");
			$("#event-map-dialog-country").text("");
			$("#event-map-dialog-city").text("");
			$("#event-map-dialog-latitude").text("");
			$("#event-map-dialog-longitude").text("");
			$("#location-select-validation-error").text("");
		    initializeMap();
		    google.maps.event.addDomListener(window, "load", initializeMap);
		}
	</script>
</div>

<!-- Page specific script -->
<script src="/plugins/timepicker/bootstrap-timepicker.min.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
		
		//Timepicker
        $(".timepicker").timepicker({
			showInputs: false,
			showMeridian: false
        });
		
        //Datemask dd/mm/yyyy
        $("#datemask").inputmask("dd/mm/yyyy", {"placeholder": "dd/mm/yyyy"});
        //Datemask2 mm/dd/yyyy
        $("#datemask2").inputmask("mm/dd/yyyy", {"placeholder": "mm/dd/yyyy"});
        //Money Euro
        $("[data-mask]").inputmask();

		/* initialize the calendar
		 -----------------------------------------------------------------*/
		//Date for the calendar events
		var date = new Date();
		var d = date.getDate(), m = date.getMonth(), y = date.getFullYear();
		$('#calendar').fullCalendar(
			{
				header : {
					left : 'prev,next today',
					center : 'title',
					right : 'month,agendaWeek,agendaDay'
				},
				buttonText : {
					today : 'today',
					month : 'month',
					week : 'week',
					day : 'day'
				},
				//timezone : 'local',
				//Load events
				events : [
					<c:if test="${fn:length(loader.allEvents) gt 0}">
						<c:set var="nowDate" value="<%=new Date().getTime()%>"/>
						<c:forEach var="event" items="${loader.allEvents}" varStatus="count">
							{
								title : '${cf:handleApostrophes(event.title)}',
								start : ${event.occurrenceDate},
								eventSources : ['${cf:handleApostrophes(event.title)}',
								                '${cf:handleApostrophes(event.description)}',
								                ${event.occurrenceDate},
								                '${event.city}',
								                '${event.country}',
								                ${event.latitude},
								                ${event.longitude}],
								<c:choose>
									<c:when test="${event.occurrenceDate < nowDate}">
										backgroundColor : "#ffffff",
										borderColor : "#999999",
										textColor: "#999999"
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${not empty event.colour}">
												backgroundColor : "${event.colour}",
												borderColor : "${event.colour}"
											</c:when>
											<c:otherwise>
												backgroundColor : "#0073b7",
												borderColor : "#0073b7"
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							}
							<c:if test="${count.index le fn:length(loader.allEvents) - 1}">,</c:if>
						</c:forEach>
					</c:if>
				],
				
				timeFormat: "H(:mm)",
				editable : false,
				droppable : true, // this allows things to be dropped onto the calendar !!!
				drop : function(date, allDay) { // this function is called when something is dropped

					// retrieve the dropped element's stored Event Object
					var originalEventObject = $(this).data('eventObject');

					// we need to copy it, so that multiple events don't have a reference to the same object
					var copiedEventObject = $.extend({}, originalEventObject);

					// assign it the date that was reported
					copiedEventObject.start = date;
					copiedEventObject.allDay = allDay;
					copiedEventObject.backgroundColor = $(this).css("background-color");
					copiedEventObject.borderColor = $(this).css("border-color");

					// render the event on the calendar
					// the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
					$('#calendar').fullCalendar('renderEvent', copiedEventObject, true);

				},
				eventClick: function(calEvent, jsEvent, view) {
					openEventDetailsDialog(calEvent);
				}
			});

		/* EVENT DIALOG */
		var eventDetailsDialog;
		$(function() {
			eventDetailsDialog = $("#event-details-dialog").dialog({
				autoOpen: false,
				width: $(window).width() < 500 ? '80%' : '30%',
				modal: true,
				buttons: {
					"Close": function() {
						eventDetailsDialog.dialog("close");
					}
				}
			});
		});
		
		function openEventDetailsDialog(calEvent) {
			eventDetailsDialog.dialog("open");
			$("#event-details-dialog").dialog("option", "title", "Event details");
			$("#event-details-dialog-title").text(calEvent.eventSources[0]);
			$("#event-details-dialog-desc").text(calEvent.eventSources[1]);
			$("#event-details-dialog-date").text(new Date(calEvent.eventSources[2]).toUTCString());
			$("#event-details-dialog-city").text(calEvent.eventSources[3]);
			$("#event-details-dialog-country").text(calEvent.eventSources[4]);
			$("#event-details-dialog-latitude").text(calEvent.eventSources[5]);
			$("#event-details-dialog-longitude").text(calEvent.eventSources[6]);
		}
		
		/* ADDING EVENTS */
		var currColor = "#3c8dbc"; //Red by default
		//Color chooser button
		var colorChooser = $("#color-chooser-btn");
		$("#color-chooser > li > a").click(function(e) {
			e.preventDefault();
			//Save color
			currColor = $(this).css("color");
			//Add color effect to button
			$('#add-new-event').css({
				"background-color" : currColor,
				"border-color" : currColor
			});
		});
		
		$("#add-new-event").click(function(e) {
			e.preventDefault();
			//Get value and make sure it is not null
			var eventTitle = $("#event-title").val();
			var validationError = false;
			if (eventTitle.length == 0) {
				$("#event-title").css("border-color", "red");
				validationError = true;
			}
			var eventDate = $("#event-date").val();
			if (eventDate.length == 0) {
				$("#event-date").css("border-color", "red");
				validationError = true;
			}
			var eventTime = $("#event-time").val();
			if (eventTime.length == 0) {
				$("#event-time").css("border-color", "red");
				validationError = true;
			}
			var eventLoc = $("#event-location").val();
			if (eventLoc.length == 0) {
				$("#event-location").css("border-color", "red");
				validationError = true;
			}
			
			if (validationError) {
				return;
			}

			$("#event-color").val(currColor);
			$("#add-new-event-form").submit();
		});
	});
</script>

</body>
</html>