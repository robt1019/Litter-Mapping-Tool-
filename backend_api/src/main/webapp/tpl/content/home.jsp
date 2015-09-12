<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="org.littermappingtool.backend.utils.jsp.MainPageDataLoader"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="cf" uri="http://bristol.ac.uk/littermappingtool/backend/utils/JSTLFunctions"%>

<!-- Left side column. contains the logo and sidebar -->
<%@ include file="../../tpl/content/left-sidebar.jsp"%>

<%
	pageContext.setAttribute("loader_mp", new MainPageDataLoader(user_service.getCurrentUser().getEmail()));
%>

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			Home <small>Quick overview, leader board and achievements</small>
		</h1>
		<ol class="breadcrumb">
			<li><i class="fa fa-home"></i> Home</li>
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<!-- Small boxes (Stat box) -->
		<div class="row">
			<div class="col-lg-3 col-xs-6">
				<!-- small box -->
				<div class="small-box bg-aqua">
					<div class="inner">
						<h3 id="lb_position"><c:if test="${loader_mp.currentUserBoardPosition eq 0}">-</c:if></h3>
						<p>Leader board position</p>
					</div>
					<div class="icon">
						<i class="ion ion-person-stalker"></i>
					</div>
					<div class="small-box-footer"></div>
				</div>
			</div>
			<!-- ./col -->
			<div class="col-lg-3 col-xs-6">
				<!-- small box -->
				<div class="small-box bg-green">
					<div class="inner">
						<h3>${loader_mp.currentUserScore}</h3>
						<p>Total points</p>
					</div>
					<div class="icon">
						<i class="ion ion-trash-a"></i>
					</div>
					<div class="small-box-footer"></div>
				</div>
			</div>
			<!-- ./col -->
			<div class="col-lg-3 col-xs-6">
				<!-- small box -->
				<div class="small-box bg-yellow">
					<div class="inner">
						<h3>${loader_mp.currentUserTotalAchievements}</h3>
						<p>Unlocked achievements</p>
					</div>
					<div class="icon">
						<i class="ion ion-ribbon-b"></i>
					</div>
					<div class="small-box-footer"></div>
				</div>
			</div>
			<!-- ./col -->
			<div class="col-lg-3 col-xs-6">
				<!-- small box -->
				<div class="small-box bg-red">
					<div class="inner">
						<h3>${loader_mp.totalUpcomingEvents}</h3>
						<p>Upcoming events</p>
					</div>
					<div class="icon">
						<i class="ion ion-calendar"></i>
					</div>
					<div class="small-box-footer"></div>
				</div>
			</div>
			<!-- ./col -->
		</div>
		<!-- /.row -->
		<div class="row">
			<!-- Left col -->
			<div class="col-md-6">
				<h3 class="box-title">Leader board:</h3>
				<div class="box">
					<div class="box-header">
						<!--
						<h3 class="box-title"></h3>
						<div class="box-tools">
							<ul class="pagination pagination-sm no-margin pull-right">
								<li><a href="#">&laquo;</a></li>
								<li><a href="#">1</a></li>
								<li><a href="#">2</a></li>
								<li><a href="#">3</a></li>
								<li><a href="#">4</a></li>
								<li><a href="#">5</a></li>
								<li><a href="#">&raquo;</a></li>
							</ul>
						</div>
						 -->
					</div>
					<!-- /.box-header -->
					<div class="box-body no-padding">
						<table class="table">
							<c:choose>
								<c:when test="${fn:length(loader_mp.leaderBoard) gt 0}">
									<tr>
										<th style="width: 40px">#</th>
										<th>User name</th>
										<th style="width: 100px">Picked items</th>
										<th style="width: 100px">Total points</th>
									</tr>
									<c:set var="position" scope="session" value="0"/>
									<c:set var="tmpPoints" scope="session" value="-1"/>
									<c:forEach var="user" items="${loader_mp.leaderBoard}" varStatus="count">
										<c:if test="${tmpPoints != user.points}">
											<c:set var="position" scope="session" value="${position + 1}"/>
										</c:if>
										<c:set var="tmpPoints" scope="session" value="${user.points}"/>
										<tr>
											<td>${position}.</td>
											<td>${user.userName}
											<c:if test="${loader_mp.currentUserEmail eq user.email}">
												&nbsp;&nbsp;<i class="fa fa-user" title="your account" style="cursor: pointer; color: #4D4D4D;"></i>
												<script>
													$("#lb_position").text("${position}");
												</script>
											</c:if>
											</td>
											<td align="center"><span class="badge bg-blue">${user.points}</span></td>
											<td align="center"><span class="badge bg-blue">${user.totalPoints}</span></td>
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
			<!-- /.col -->

			<!-- Right col -->
			<div class="col-md-6">	
				<h3 class="box-title">Achievements:</h3>
				<!-- Info Boxes Style 2 -->
				<c:choose>
					<c:when test="${loader_mp.currentUserTotalLitters >= 10}">
						<div class="info-box bg-yellow">
							<span class="info-box-icon"><i class="ion ion-ribbon-b"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">Achievement unlocked (50 points)</span> <span class="info-box-number">Newbie</span>
								<div class="progress">
									<div class="progress-bar" style="width: 100%"></div>
								</div>
								<span class="progress-description"> Pickup 10 items to unlock this achievement</span>
							</div>
							<!-- /.info-box-content -->
						</div>
					</c:when>
					<c:otherwise>
						<div class="info-box bg-gray">
							<span class="info-box-icon"><i class="ion ion-ribbon-b"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">Achievement locked (50 points)</span> <span class="info-box-number">Newbie</span>
								<div class="progress">
									<div class="progress-bar" style="width: 100%"></div>
								</div>
								<span class="progress-description"> Pickup 10 items to unlock this achievement</span>
							</div>
							<!-- /.info-box-content -->
						</div>
					</c:otherwise>
				</c:choose>
				<!-- /.info-box -->
				<!-- Info Boxes Style 2 -->
				<c:choose>
					<c:when test="${loader_mp.currentUserWeekender eq true}">
						<div class="info-box bg-yellow">
							<span class="info-box-icon"><i class="ion ion-ribbon-b"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">Achievement unlocked (100 points)</span> <span class="info-box-number">Weekender</span>
								<div class="progress">
									<div class="progress-bar" style="width: 100%"></div>
								</div>
								<span class="progress-description"> Participate in any weekend event to unlock this achievement</span>
							</div>
							<!-- /.info-box-content -->
						</div>						
					</c:when>
					<c:otherwise>
						<div class="info-box bg-gray">
							<span class="info-box-icon"><i class="ion ion-ribbon-b"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">Achievement locked (100 points)</span> <span class="info-box-number">Weekender</span>
								<div class="progress">
									<div class="progress-bar" style="width: 100%"></div>
								</div>
								<span class="progress-description"> Participate in any weekend event to unlock this achievement</span>
							</div>
							<!-- /.info-box-content -->
						</div>
					</c:otherwise>
				</c:choose>
				<!-- /.info-box -->
				<!-- Info Boxes Style 2 -->
				<c:choose>
					<c:when test="${loader_mp.currentUserRegular eq true}">
						<div class="info-box bg-yellow">
							<span class="info-box-icon"><i class="ion ion-ribbon-b"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">Achievement unlocked (150 points)</span> <span class="info-box-number">Regular</span>
								<div class="progress">
									<div class="progress-bar" style="width: 100%"></div>
								</div>
								<span class="progress-description"> Attend any three events to unlock this achievement</span>
							</div>
							<!-- /.info-box-content -->
						</div>						
					</c:when>
					<c:otherwise>
						<div class="info-box bg-gray">
							<span class="info-box-icon"><i class="ion ion-ribbon-b"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">Achievement locked (150 points)</span> <span class="info-box-number">Regular</span>
								<div class="progress">
									<div class="progress-bar" style="width: 100%"></div>
								</div>
								<span class="progress-description"> Attend any three events to unlock this achievement</span>
							</div>
							<!-- /.info-box-content -->
						</div>						
					</c:otherwise>
				</c:choose>
				<!-- /.info-box -->
				<!-- Info Boxes Style 2 -->
				<c:choose>
					<c:when test="${loader_mp.currentUserTotalLitters >= 200}">
						<div class="info-box bg-yellow">
							<span class="info-box-icon"><i class="ion ion-ribbon-b"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">Achievement unlocked (200 points)</span> <span class="info-box-number">Contributor</span>
								<div class="progress">
									<div class="progress-bar" style="width: 100%"></div>
								</div>
								<span class="progress-description"> Pickup 200 items to unlock this achievement</span>
							</div>
							<!-- /.info-box-content -->
						</div>						
					</c:when>
					<c:otherwise>
						<div class="info-box bg-gray">
							<span class="info-box-icon"><i class="ion ion-ribbon-b"></i></span>
							<div class="info-box-content">
								<span class="info-box-text">Achievement locked (200 points)</span> <span class="info-box-number">Contributor</span>
								<div class="progress">
									<div class="progress-bar" style="width: 100%"></div>
								</div>
								<span class="progress-description"> Pickup 200 items to unlock this achievement</span>
							</div>
							<!-- /.info-box-content -->
						</div>						
					</c:otherwise>
				</c:choose>
				<!-- /.info-box -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</section>
	<!-- /.content -->
</div>
<!-- /.content-wrapper -->
<%@ include file="../footer.jsp"%>

</body>
</html>