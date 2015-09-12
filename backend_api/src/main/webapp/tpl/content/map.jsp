<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="org.littermappingtool.backend.utils.jsp.MapDataLoader"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cf" uri="http://bristol.ac.uk/littermappingtool/backend/utils/JSTLFunctions" %>

<!-- Left side column. contains the logo and sidebar -->
<%@ include file="../../tpl/content/left-sidebar.jsp"%>

<%
	pageContext.setAttribute("loader", new MapDataLoader());
%>

<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js"></script>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">

	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Map <small>See past events and collected litters, see where bins are located</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="/index.jsp?page=home"><i class="fa fa-home"></i>Home</a></li>
			<li class="active">Map</li>
		</ol>
	</section>	

	<div class="map-button" id="map-new-bins">
		<i class="fa fa-trash-o"></i>&nbsp;&nbsp;show new bins
	</div>

	<div class="map-button" id="map-printer">
		<i class="fa fa-print"></i>&nbsp;&nbsp;print map of this event
	</div>

	<div id="map-statistic-win">
		<div class="map-statistic-win-inner">
			<b>Overview:</b>
			<span class="map-stat-row" id="map-statistic-place"></span>
			<span class="map-stat-row" id="map-statistic-event"></span>
			<span class="map-stat-row" id="map-statistic-non-event"></span>
		</div>
		
		<div class="map-statistic-win-inner">
			<div class="map-stat-row">
				<b>Top items (brand (type), count):</b>
				<ul id="map-statistic-top-items"></ul>
			</div>
		</div>
		
		<div class="map-statistic-win-inner-sm">
			<b>Radius (metres):</b>
			<div class="row margin">
				<div class="input-group" id="radius-update">
					<input type="text" value="300" id="non-event-items-radius" class="form-control tooltip" title="Radius for individually picked items">
					<span class="input-group-btn">
						<button class="btn btn-info btn-flat" type="button" id="non-event-items-radius-btn">Set</button>
					</span>
				</div>
			</div>
		</div>
		
		<div class="map-statistic-win-inner-sm">
			<b>Time frame (days):</b>
			<div class="row margin">
				<div class="input-group" id="time-update">
					<input type="text" value="7" id="non-event-items-time" class="form-control tooltip" title="Time frame for individually picked items (plus/minus {days} from event date, or minus {days} from now if event is not selected)">
					<span class="input-group-btn">
						<button class="btn btn-info btn-flat" type="button" id="non-event-items-time-btn">Set</button>
					</span>
				</div>
			</div>
		</div>
	</div>

	<script>
	
		// Add a method to Circle object so we can check whether or not it contains the a point on the map
		google.maps.Circle.prototype.contains = function(latLng) {
		    return this.getBounds().contains(latLng) && google.maps.geometry.spherical.computeDistanceBetween(this.getCenter(), latLng) <= this.getRadius();
		}
	
		// Define variables
		var map, marker, latLng, circle, selectedEventDate, radius, staticMapURL = null;
		var infowindow = new google.maps.InfoWindow();
		var centerPosMarker = new google.maps.Marker();
		var nonEventLittersJsonString = ${loader.noneEventLittersJson};
		var litterMarkers = [];
		var nonEventLitterMarkers = [];
		var topItems = new HashTable();
		var timeFrameNonEventItems = $("#non-event-items-time").val();
		var URL_LENGTH_LIMIT = 2048;
		var staticMapZoom = 17;
		var staticMapSetup = "&zoom=" + staticMapZoom + "&size=640x640&maptype=roadmap";
		var staticMapLocationURL, staticMapLitterMarkersURL, staticMapNonEventLitterMarkersURL, staticMapBinMarkersURL, staticMapEventCity, staticMapEventDate = null; 
		var littersClusterer = null;
		
		// Update radius of circle for non-event items		
		$("#non-event-items-radius-btn").click(function() {
			
			radius = $("#non-event-items-radius").val();
			circle.setRadius(parseInt(radius));
			removeNonEventLitterMarkers();
			displayNonEventMarkers();
			
	    	// clear & prepare data for statistic
	    	topItems.clear();
	    	
	    	for (var i in litterMarkers) {
				var key = litterMarkers[i].getTitle(); // Title eq to key
				var value = topItems.getItem(key);
				topItems.put(key, value == null ? 1 : value + 1);
	    	}
	    	
	    	for (var i in nonEventLitterMarkers) {
				var key = nonEventLitterMarkers[i].getTitle();  // Title eq to key
				var value = topItems.getItem(key);
				topItems.put(key, value == null ? 1 : value + 1);
	    	}
	    	
			// update map statistic
			$('#map-statistic-non-event').text('Total individually picked items: ' + nonEventLitterMarkers.length);
			var mpItems = getMostPrevalentLitter(topItems);
			mapStatisticTopItems.innerHTML = "";
			for (var i = 0; i < mpItems.length; i++) {
				mapStatisticTopItems.innerHTML = mapStatisticTopItems.innerHTML + '<li>' + mpItems[i][0] + ', #' + mpItems[i][1] + '</li>';
			}
		});
		
		// Update time frame for non-event items		
		$("#non-event-items-time-btn").click(function() {
			
			timeFrameNonEventItems = $("#non-event-items-time").val();
			removeNonEventLitterMarkers();
			displayNonEventMarkers();
			
	    	// clear & prepare data for statistic
	    	topItems.clear();
	    	
	    	for (var i in litterMarkers) {
				var key = litterMarkers[i].getTitle(); // Title eq to key
				var value = topItems.getItem(key);
				topItems.put(key, value == null ? 1 : value + 1);
	    	}
	    	
	    	for (var i in nonEventLitterMarkers) {
				var key = nonEventLitterMarkers[i].getTitle();  // Title eq to key
				var value = topItems.getItem(key);
				topItems.put(key, value == null ? 1 : value + 1);
	    	}
	    	
			// update map statistic
			$('#map-statistic-non-event').text('Total individually picked items: ' + nonEventLitterMarkers.length);
			var mpItems = getMostPrevalentLitter(topItems);
			mapStatisticTopItems.innerHTML = "";
			for (var i = 0; i < mpItems.length; i++) {
				mapStatisticTopItems.innerHTML = mapStatisticTopItems.innerHTML + '<li>' + mpItems[i][0] + ', #' + mpItems[i][1] + '</li>';
			}
		});
		
	    google.maps.event.addListener(centerPosMarker,'dragend',function(event) {
	    	
	    	// handle movement
	    	fx(event.latLng);
	    	
	    	// clear & prepare data for statistic
	    	topItems.clear();
	    	
	    	for (var i in litterMarkers) {
				var key = litterMarkers[i].getTitle(); // Title eq to key
				var value = topItems.getItem(key);
				topItems.put(key, value == null ? 1 : value + 1);
	    	}
	    	
	    	for (var i in nonEventLitterMarkers) {
				var key = nonEventLitterMarkers[i].getTitle();  // Title eq to key
				var value = topItems.getItem(key);
				topItems.put(key, value == null ? 1 : value + 1);
	    	}
	    	
			// update map statistic
			$('#map-statistic-non-event').text('Total individually picked items: ' + nonEventLitterMarkers.length);
			var mpItems = getMostPrevalentLitter(topItems);
			mapStatisticTopItems.innerHTML = "";
			for (var i = 0; i < mpItems.length; i++) {
				mapStatisticTopItems.innerHTML = mapStatisticTopItems.innerHTML + '<li>' + mpItems[i][0] + ', #' + mpItems[i][1] + '</li>';
			}
	    });
		
		function displayBinMarkers() {
			
			// Add marker type to the print URL
			<c:choose>
				<c:when test="${fn:length(loader.bins) gt 0}">
					staticMapBinMarkersURL = "&markers=icon:http://littermappingtool.appspot.com/s/t.png";
				</c:when>
				<c:otherwise>
					staticMapBinMarkersURL = "";
				</c:otherwise>
			</c:choose>
				
			// Show any bins
			<c:if test="${fn:length(loader.bins) gt 0}">
				<c:forEach var="bin" items="${loader.bins}" varStatus="count">
				
					latLng = new google.maps.LatLng(${bin.latitude}, ${bin.longitude});

					// Set a marker
					marker = new google.maps.Marker({
						position: latLng,
						draggable: false,
						icon: 'img/trash_icon_map_32x32.png',
						map: map
					});
	
					// Allow each marker to have an info window
					infoWindow = new google.maps.InfoWindow();
					google.maps.event.addListener(marker, 'click', (function(marker) {
						return function() {
							infoWindow.setContent(
								'<div class="info_content">' +
									'<div class="info_content_left">' +
										'<h3>Bin (${bin.binType})</h3>' +
										'<i class="fa fa-globe"></i> Latitude: ${bin.latitude}<br>' +
										'<i class="fa fa-globe"></i> Longitude: ${bin.longitude}<br>' +
										'<i class="fa fa-calendar"></i> Date added: ${cf:dateToDateTimeString(bin.created)}<br>' +
										'<i class="fa fa-user"></i> Added by: ${bin.userEmail}' +
									'</div>' +
									'<div class="info_content_right">' +
									'</div>' +
								'</div>'
							);
							infoWindow.open(map, marker);
						}
					})(marker, ${count.index}));
					
					// Add bin marker to the URL
					staticMapBinMarkersURL += "|" + ${bin.latitude} + "," + ${bin.longitude};

				</c:forEach>
			</c:if>
			
		}
		
		function displayNonEventMarkers() {
			
			// Add marker type to the print URL
			if (nonEventLittersJsonString.length > 0) {
				staticMapNonEventLitterMarkersURL = "&markers=icon:http://littermappingtool.appspot.com/s/y.png";
			} else {
				staticMapNonEventLitterMarkersURL = "";
			}

			// Show any non-event litter marker 
			jQuery.each(nonEventLittersJsonString, function(i, litter) {
				
				latLng = new google.maps.LatLng(litter.latitude, litter.longitude);
				var timeFrameDays = parseInt($("#non-event-items-time").val());
				var occDate = new Date(litter.created);
				var date, minusBorderDate, plusBorderDate;
				
				if (selectedEventDate != null) {
					date = new Date(selectedEventDate);
				} else {
					date = new Date();					
				}
				
				plusBorderDate = date.addDays(timeFrameDays);
				minusBorderDate = date.addDays(-timeFrameDays);

				if ((occDate <= plusBorderDate && occDate >= minusBorderDate) && circle.contains(latLng)) {
					
					var dateTimeCreated = printTimeStamp(litter.created);
					var brand, litterType;
					
					if (!litter.brand) brand = 'unknown';
					else if (litter.brand === 'other') brand = litter.otherBrandName;
					else brand = litter.brand
					
					if (!litter.litterType) litterType = 'unknown';
					else if (litter.litterType === 'other') litterType = litter.otherLitterType;
					else litterType = litter.litterType;
					
					// Set a marker
					marker = new google.maps.Marker({
						position: latLng,
						draggable: false,
						icon: 'img/litter_yellow_icon_map_32x32.png',
						map: map,
						title: brand + ' (' + litterType + ')'
					});
		
					// Allow each marker to have an info window
					infoWindow = new google.maps.InfoWindow();
					
					google.maps.event.addListener(marker, 'click', (function(marker) {
						return function() {
							infoWindow.setContent(
								'<div class="info_content">' +
									'<div class="info_content_left">' +
										'<h3>' + brand + ' (' + litterType + ')</h3>' +
										'<i class="fa fa-globe"></i> Latitude: ' + litter.latitude + '<br>' +
										'<i class="fa fa-globe"></i> Longitude: ' + litter.longitude + '<br>' +
										'<i class="fa fa-calendar"></i> Date picked: ' + dateTimeCreated[0] + " " + dateTimeCreated[1] + '<br>' +
										'<i class="fa fa-user"></i> Picked by: ' + litter.userEmail + '<br>' +
										'<i class="fa fa-info-circle"></i> Info: individually picked item' +
									'</div>' +
									'<div class="info_content_right">' +
									'</div>' +
								'</div>'
							);
							infoWindow.open(map, marker);
						}
					})(marker));
					
					// Add bin marker to the URL
					staticMapNonEventLitterMarkersURL += "|" + litter.latitude + "," + litter.longitude;

					// Push marker to the array of non event markers, so we can access it later
					nonEventLitterMarkers.push(marker);

					// Put brand/type to the hash table, so we will get an overview of top items
					var key = brand + ' (' + litterType + ')';
					var value = topItems.getItem(key);
					topItems.put(key, value == null ? 1 : value + 1);
				}
			});
		}
		
		// Called when clicked on map
	    function fx(latLng) {
	    	
	    	// Remove any previous non event markers and the center pos marker
	    	removeNonEventLitterMarkers();
	    	centerPosMarker.setMap(null);
	
	    	// Set the new marker and the circle around it
			centerPosMarker.setOptions({map:map, position:latLng, icon: "img/center_48x48.png", draggable: true});		
			var radius = $("#non-event-items-radius").val();
			circle.setRadius(parseInt(radius));
			circle.setCenter(latLng);
			
			// Display the non event markers and bin markers, provided they are within the circle
			displayBinMarkers();
			displayNonEventMarkers();
			
	    	// clear & prepare data for statistic
	    	topItems.clear();
	    	
	    	for (var i in litterMarkers) {
				var key = litterMarkers[i].getTitle(); // Title eq to key
				var value = topItems.getItem(key);
				topItems.put(key, value == null ? 1 : value + 1);
	    	}
	    	
	    	for (var i in nonEventLitterMarkers) {
				var key = nonEventLitterMarkers[i].getTitle();  // Title eq to key
				var value = topItems.getItem(key);
				topItems.put(key, value == null ? 1 : value + 1);
	    	}
	    	
			// update map statistic
			$('#map-statistic-non-event').text('Total individually picked items: ' + nonEventLitterMarkers.length);
			var mpItems = getMostPrevalentLitter(topItems);
			mapStatisticTopItems.innerHTML = "";
			for (var i = 0; i < mpItems.length; i++) {
				mapStatisticTopItems.innerHTML = mapStatisticTopItems.innerHTML + '<li>' + mpItems[i][0] + ', #' + mpItems[i][1] + '</li>';
			}
			
			// make sure the statistic is visible
			$('#map-statistic-win').css('visibility', 'visible');
	    }
	    
		function removeNonEventLitterMarkers() {
			for (var i = 0; i < nonEventLitterMarkers.length; i++ ) {
				nonEventLitterMarkers[i].setMap(null);
			}
			nonEventLitterMarkers.length = 0;
		}
	
		function initialize() {
			map = new google.maps.Map(document.getElementById('map-canvas'), {
				zoom: 6,
				center: new google.maps.LatLng(54.479802, -2.451904),
				mapTypeId: google.maps.MapTypeId.ROADMAP
			});
			
			google.maps.event.addListener(map, 'click', function(e){ fx(e.latLng) });
			
		    circle = new google.maps.Circle({
				strokeColor: '#75A319',
				strokeOpacity: 0.8,
				strokeWeight: 2,
				fillColor: '#E0EBCC',
				fillOpacity: 0.35,
				map: map,
				radius: 1000
		    });
		    
			displayBinMarkers();
			
		    google.maps.event.addListener(map, 'zoom_changed', function() {
		    	staticMapZoom = map.getZoom();
		    	staticMapSetup = "&zoom=" + staticMapZoom + "&size=640x640&maptype=roadmap";
		    	staticMapURL = staticMapLocationURL + staticMapSetup + staticMapLitterMarkersURL + staticMapNonEventLitterMarkersURL + staticMapBinMarkersURL;
		    });
		}
	
		google.maps.event.addDomListener(window, 'load', initialize);
		
	</script>
	
	<!-- Main content -->
	<section>
		<div id="map-timeline-wrapper">
			<div class="col-md-14">		
				<div id="map-timeline">
					<ul class="timeline" id="timeline"></ul>
				</div>
				<script type="text/javascript">
				
					var timeLine = document.getElementById('timeline');
					var mapStatisticTopItems = document.getElementById('map-statistic-top-items');
					var jsonString = ${loader.timeLineEventsJson};
					var occDate, nowDate, daysDif, cleanUpStatus, littersCurrentEvent, littersPreviousEvent, litterStatus, date, printDate, printTime, center;
					var totalRootElements = 0;
					
					jQuery.each(jsonString, function(i, jsonObj) {
						
						totalRootElements++;
						
						// check whether the area was cleaned recently (< 14 is ok, < 30 is warning, > 30 is not ok)
						occDate = new Date(jsonObj[0].event.occurrenceDate);
						nowDate = new Date();
						daysDif = DateDiff.inDays(occDate, nowDate);

						// append city to the timeline; which will group all events
						timeLine.innerHTML = timeLine.innerHTML + "<li class='time-label'><span class='bg-green'>" + jsonObj[0].event.city + "</span></li>";
						
						// area details
						var totalItems = 0;
						timeLine.innerHTML = timeLine.innerHTML +
						"<li><i class='fa fa-info bg-green'></i><div class='timeline-item'>" +
							"<div class='timeline-body'>" +
								"<span id='total-picked-" + jsonObj[0].event.id + "'></span><br>" +
							"</div>" +
						"</div></li>";
						
						if (daysDif < 14) { 
							timeLine.innerHTML = timeLine.innerHTML +
							"<li><i class='fa fa-smile-o bg-green'></i>" +
								"<div class='timeline-item'><div class='timeline-body'><span>Cleaned " + daysDif + " day(s) ago</span><br></div></div>" +
							"</li>";
						} else if (daysDif < 30) {
							timeLine.innerHTML = timeLine.innerHTML +
							"<li><i class='fa fa-meh-o bg-orange'></i>" +
								"<div class='timeline-item'><div class='timeline-body'><span>Cleaned " + daysDif + " day(s) ago</span><br></div></div>" +
							"</li>";
						} else {
							timeLine.innerHTML = timeLine.innerHTML +
							"<li><i class='fa fa-frown-o bg-red'></i>" +
								"<div class='timeline-item'><div class='timeline-body'><span>Cleaned " + daysDif + " day(s) ago</span><br></div></div>" +
							"</li>";
						}
						
						jQuery.each(jsonObj, function(j, eventWithLitters) {
							
							// add items from this event to total
							totalItems += jsonObj[j].litters.length;
							
							// compare the number of items in current and previous event (if availabe)
							if (j < jsonObj.length - 1) {
								
								littersCurrentEvent = jsonObj[j].litters.length;
								littersPreviousEvent = jsonObj[j + 1].litters.length;
								
								if (littersCurrentEvent > littersPreviousEvent) {
									litterStatus = "<i class='fa fa-long-arrow-up red-color'></i> +" + (littersCurrentEvent - littersPreviousEvent);									
								} else if (littersCurrentEvent < littersPreviousEvent) {
									litterStatus = "<i class='fa fa-long-arrow-down green-color'></i> -" + (littersPreviousEvent - littersCurrentEvent);									
								} else {
									litterStatus = "equal#";
								}
								
							} else {
								litterStatus = "";
							}
							
							// get/format the occurrence date
							var dateTime = printTimeStamp(eventWithLitters.event.occurrenceDate);
							
							// create/append the timeline item
							timeLine.innerHTML = timeLine.innerHTML +
							"<li><i class='fa fa-calendar bg-blue'></i><div class='timeline-item'>" +
								"<div class='timeline-header'><span class='time'><i class='fa fa-calendar-o'></i> " + dateTime[0] + "&nbsp;&nbsp;<i class='fa fa-clock-o'></i> " + dateTime[1] + "</span></div>" +
								"<div class='timeline-body'><span >Picked this event: " + eventWithLitters.litters.length + "</span></div>" +
								"<div class='timeline-footer'><a class='btn btn-primary btn-xs'  onclick='displayLitters(\"" + eventWithLitters.event.city + "\"," + j + "," + eventWithLitters.event.occurrenceDate + ")'>Display items</a>&nbsp;&nbsp;" + litterStatus + "</div>" +
							"</div></li>";
						});

						$('#total-picked-' + jsonObj[0].event.id).text('Total picked: ' + totalItems);
					});

					if (totalRootElements > 0) { 
						timeLine.innerHTML = timeLine.innerHTML + "<li><i class='fa fa-calendar bg-gray'></i></li>";	
					}
					
					// dynamically display all litters for a particular event
					function displayLitters(city, objId, date) {
						
						staticMapEventCity = city;
						staticMapEventDate = date;
						
						// prepare map print URL
						if (jsonString[city][objId].litters.length > 0) {
							staticMapLitterMarkersURL = "&markers=icon:http://littermappingtool.appspot.com/s/g.png";
						} else {
							staticMapLitterMarkersURL = "";
						}
						
						// reset map
						topItems.clear();
						removeLitterMarkers();
						if (littersClusterer) littersClusterer.clearMarkers();
						
						// set event id
						selectedEventDate = jsonString[city][objId].event.occurrenceDate;
						
						jQuery.each(jsonString[city][objId].litters, function(i, litter) {

							var dateTimeCreated = printTimeStamp(litter.created);
							var brand, litterType;
							
							if (!litter.brand) brand = 'unknown';
							else if (litter.brand === 'other') brand = litter.otherBrandName;
							else brand = litter.brand
							
							if (!litter.litterType) litterType = 'unknown';
							else if (litter.litterType === 'other') litterType = litter.otherLitterType;
							else litterType = litter.litterType;
							
							// Set marker's position and the marker
							latLng = new google.maps.LatLng(litter.latitude, litter.longitude);
							marker = new google.maps.Marker({
								position: latLng,
								draggable: false,
								icon: 'img/litter_green_icon_map_32x32.png',
								map: map,
								title: brand + ' (' + litterType + ')'
							});

							// Allow each marker to have an info window
							infoWindow = new google.maps.InfoWindow();
							
							google.maps.event.addListener(marker, 'click', (function(marker) {
								return function() {
									infoWindow.setContent(
										'<div class="info_content">' +
											'<div class="info_content_left">' +
												'<h3>' + brand + ' (' + litterType + ')</h3>' +
												'<i class="fa fa-globe"></i> Latitude: ' + litter.latitude + '<br>' +
												'<i class="fa fa-globe"></i> Longitude: ' + litter.longitude + '<br>' +
												'<i class="fa fa-calendar"></i> Date picked: ' + dateTimeCreated[0] + " " + dateTimeCreated[1] + '<br>' +
												'<i class="fa fa-user"></i> Picked by: ' + litter.userEmail + '<br>' +
												'<i class="fa fa-info-circle"></i> Info: item picked during an event' +
											'</div>' +
											'<div class="info_content_right">' +
											'</div>' +
										'</div>'
									);
									infoWindow.open(map, marker);
								}
							})(marker));
							
							litterMarkers.push(marker);
							
							// Put brand/type to the hash table, so we will get an overview of top items
							var key = brand + ' (' + litterType + ')';
							var value = topItems.getItem(key);
							topItems.put(key, value == null ? 1 : value + 1);
							
							// Add maker to print URL
							staticMapLitterMarkersURL += "|" + litter.latitude + "," + litter.longitude;
						});
						
						// center map to event's coordinates
						center = new google.maps.LatLng(jsonString[city][objId].event.latitude, jsonString[city][objId].event.longitude);
						map.setCenter(center);
						map.setZoom(17);
						
						// show non event markers too
						fx(center);
						
						// prepare map statistic
						var timeStamp = printCompactTimeStamp(date);
						$('#map-statistic-place').text('Event: ' + city + ', ' + timeStamp[0]);
						$('#map-statistic-event').text('Total items picked in this event: ' + jsonString[city][objId].litters.length);
						$('#map-statistic-non-event').text('Total individually picked items: ' + nonEventLitterMarkers.length);
						$('#map-statistic-win').css('visibility', 'visible');
						
						var mpItems = getMostPrevalentLitter(topItems);
						mapStatisticTopItems.innerHTML = "";
						for (var i = 0; i < mpItems.length; i++) {
							mapStatisticTopItems.innerHTML = mapStatisticTopItems.innerHTML + '<li>' + mpItems[i][0] + ', #' + mpItems[i][1] + '</li>';
						}
						
						// show map buttons
						$('#map-printer, #map-new-bins').css('visibility', 'visible');
					}
					
					$('#map-new-bins').click(function() {
						littersClusterer = new MarkerClusterer(map);
						littersClusterer.addMarkers(litterMarkers);
						littersClusterer.addMarkers(nonEventLitterMarkers);
						
						if (map.getZoom() != 17) map.setZoom(17);
						if (center) map.setCenter(center);
					});
					
					$('#map-printer').click(function() {
						staticMapLocationURL = "https://maps.googleapis.com/maps/api/staticmap?center=" + map.getCenter().lat() + "," + map.getCenter().lng();
						staticMapURL = staticMapLocationURL + staticMapSetup + staticMapLitterMarkersURL + staticMapNonEventLitterMarkersURL + staticMapBinMarkersURL;
						if (staticMapURL.length > URL_LENGTH_LIMIT) {
							alert("Can't print the map, too many markers.");
							return;
						} else {
							saveToDisk(staticMapURL, "event-map" +
									(staticMapEventCity ? "-" + staticMapEventCity : "") +
									(staticMapEventDate ? "-" + staticMapEventDate : "") + ".png");
						}
					});
					
					function saveToDisk(fileURL, fileName) {
						var a = $("<a>")
						    .attr("href", fileURL)
						    .attr("download", fileName)
						    .appendTo("body");
						a[0].click();
						a.remove();
					}
					
					// Remove all litter markers
					function removeLitterMarkers() {
						for (var i = 0; i < litterMarkers.length; i++) {
							litterMarkers[i].setMap(null);
						}
						litterMarkers.length = 0;
					}

				</script>
			</div>
			<!-- /.col -->
		</div>
		<div>			
			<div id="map-canvas" style="margin-top: 15px;"></div>
		</div>
	</section>
</div>
<script>
	var h = $(window).height() - 157;
	
	$(window).load(function() {
		$("#map-canvas").height(h);
		$("#map-timeline").height(h - 15);
		$("#map-new-bins").css("top", h - 54);
		$("#map-printer").css("top", h - 22);
		$("#map-statistic-win").css("top", h + 9);
	});
	
	$(window).resize(function() {
		$("#map-canvas").height(h);
		$("#map-timeline").height(h - 15);
		$("#map-new-bins").css("top", h - 54);
		$("#map-printer").css("top", h - 22);
		$("#map-statistic-win").css("top", h + 9);
	});
</script>
<!-- /.content -->

<%@ include file="../footer.jsp"%>

</body>
</html>