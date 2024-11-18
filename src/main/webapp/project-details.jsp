<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html lang="en">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!-- Favicon icon -->
<link rel="icon" type="image/png" sizes="16x16"
	href="plugins/images/favicon.png">
<title>Pixel Admin</title>
<!-- Bootstrap Core CSS -->
<link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Menu CSS -->
<link
	href="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.css"
	rel="stylesheet">
<!-- Animation CSS -->
<link href="css/animate.css" rel="stylesheet">
<!-- Custom CSS -->
<link href="css/style.css" rel="stylesheet">
<!-- color CSS you can use different color css from css/colors folder -->
<!-- We have chosen the skin-blue (blue.css) for this starter
          page. However, you can choose any other skin from folder css / colors .
-->
<link href="css/colors/blue-dark.css" id="theme" rel="stylesheet">
<link rel="stylesheet" href="./css/custom.css">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>

<body>
	<!-- Preloader -->
	<div class="preloader">
		<div class="cssload-speeding-wheel"></div>
	</div>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top m-b-0">
			<div class="navbar-header">
				<a class="navbar-toggle hidden-sm hidden-md hidden-lg "
					href="javascript:void(0)" data-toggle="collapse"
					data-target=".navbar-collapse"> <i class="fa fa-bars"></i>
				</a>
				<div class="top-left-part">
					<a class="logo" href="index.html"> <b> <img
							src="plugins/images/pixeladmin-logo.png" alt="home" />
					</b> <span class="hidden-xs"> <img
							src="plugins/images/pixeladmin-text.png" alt="home" />
					</span>
					</a>
				</div>
				<ul class="nav navbar-top-links navbar-left m-l-20 hidden-xs">
					<li>
						<form role="search" class="app-search hidden-xs">
							<input type="text" placeholder="Search..." class="form-control">
							<a href=""> <i class="fa fa-search"></i>
							</a>
						</form>
					</li>
				</ul>
				<jsp:include page="dropdown-menu.jsp" />

			</div>
			<!-- /.navbar-header -->
			<!-- /.navbar-top-links -->
			<!-- /.navbar-static-side -->
		</nav>
		<!-- Left navbar-header -->
		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse slimscrollsidebar">
				<jsp:include page="menu.jsp" />

			</div>
		</div>
		<!-- Left navbar-header end -->
		<!-- Page Content -->
		<div id="page-wrapper">
			<div class="container-fluid">
				<div class="row bg-title">
					<div class="col-lg-3 col-md-4 col-sm-4 col-xs-12">
						<h4 class="page-title">Chi tiết dự án</h4>
					</div>
					<div class="col-lg-9 col-sm-8 col-md-8 col-xs-12">
						<ol class="breadcrumb">
							<li><a href="#">Dashboard</a></li>
							<li class="active">Blank Page</li>
						</ol>
					</div>
					<!-- /.col-lg-12 -->
				</div>
				<!-- BEGIN THỐNG KÊ -->
				<div class="row">
					<!--col -->
					<!-- <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="white-box">
                            <div class="col-in row">
                                <div class="col-md-6 col-sm-6 col-xs-6"> <i data-icon="E"
                                        class="linea-icon linea-basic"></i>
                                    <h5 class="text-muted vb">CHƯA BẮT ĐẦU</h5>
                                </div>
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <h3 class="counter text-right m-t-15 text-danger">20%</h3>
                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-danger" role="progressbar"
                                            aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    /.col
                    col
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="white-box">
                            <div class="col-in row">
                                <div class="col-md-6 col-sm-6 col-xs-6"> <i class="linea-icon linea-basic"
                                        data-icon="&#xe01b;"></i>
                                    <h5 class="text-muted vb">ĐANG THỰC HIỆN</h5>
                                </div>
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <h3 class="counter text-right m-t-15 text-megna">50%</h3>
                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-megna" role="progressbar"
                                            aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 50%">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    /.col
                    col
                    <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                        <div class="white-box">
                            <div class="col-in row">
                                <div class="col-md-6 col-sm-6 col-xs-6"> <i class="linea-icon linea-basic"
                                        data-icon="&#xe00b;"></i>
                                    <h5 class="text-muted vb">HOÀN THÀNH</h5>
                                </div>
                                <div class="col-md-6 col-sm-6 col-xs-6">
                                    <h3 class="counter text-right m-t-15 text-primary">30%</h3>
                                </div>
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-primary" role="progressbar"
                                            aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 30%">
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div> -->


					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
						<div class="white-box">
							<div class="col-in row">
								<div class="col-xs-12">
									<h3 class="counter text-right m-t-15 text-danger">
										<%=request.getAttribute("notStartedPercentage")%>%
									</h3>
								</div>
								<div class="col-xs-12">
									<i data-icon="E" class="linea-icon linea-basic"></i>
									<h5 class="text-muted vb text-center">CHƯA BẮT ĐẦU</h5>
								</div>
								<div class="col-md-12 col-sm-12 col-xs-12">
									<div class="progress">
										<div class="progress-bar progress-bar-danger"
											role="progressbar" aria-valuenow="40" aria-valuemin="0"
											aria-valuemax="100"
											style="width: <%=request.getAttribute("notStartedPercentage")%>%"></div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
						<div class="white-box">
							<div class="col-in row">
								<div class="col-xs-12">
									<h3 class="counter text-right m-t-15 text-danger">
										<%=request.getAttribute("inProgressPercentage")%>%
									</h3>
								</div>
								<div class="col-xs-12">
									<i data-icon="E" class="linea-icon linea-basic"></i>
									<h5 class="text-muted vb text-center">ĐANG THỰC HIỆN</h5>
								</div>
								<div class="col-md-12 col-sm-12 col-xs-12">
									<div class="progress">
										<div class="progress-bar progress-bar-danger"
											role="progressbar" aria-valuenow="40" aria-valuemin="0"
											aria-valuemax="100"
											style="width: <%=request.getAttribute("inProgressPercentage")%>%"></div>
									</div>
								</div>
							</div>
						</div>
					</div>


					<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
						<div class="white-box">
							<div class="col-in row">
								<div class="col-xs-12">
									<h3 class="counter text-right m-t-15 text-danger">
										<%=request.getAttribute("completedPercentage")%>%
									</h3>
								</div>
								<div class="col-xs-12">
									<i data-icon="E" class="linea-icon linea-basic"></i>
									<h5 class="text-muted vb text-center">HOÀN THÀNH</h5>
								</div>
								<div class="col-md-12 col-sm-12 col-xs-12">
									<div class="progress">
										<div class="progress-bar progress-bar-danger"
											role="progressbar" aria-valuenow="40" aria-valuemin="0"
											aria-valuemax="100"
											style="width: <%=request.getAttribute("completedPercentage")%>%"></div>
									</div>
								</div>
							</div>
						</div>
					</div>

					<!-- /.col -->
				</div>
				<!-- END THỐNG KÊ -->

				<!-- BEGIN DANH SÁCH CÔNG VIỆC -->
				
<div class="row">
    <c:forEach var="taskDetail" items="${userTaskDetailsEntity}">
        <div class="col-xs-12">
            <a href="#" class="group-title">
                <img width="30" src="plugins/images/users/pawandeep.jpg" class="img-circle" />
                <span>${taskDetail.fullName}</span>
            </a>
        </div>

        <c:forEach var="entry" items="${taskDetail.tasksByStatus}">
            <div class="col-md-4">
                <div class="white-box">
                     <h3 class="box-title">
                        <c:choose>
                            <c:when test="${entry.key == 1}">Chưa thực hiện</c:when>
                            <c:when test="${entry.key == 2}">Đang thực hiện</c:when>
                            <c:when test="${entry.key == 3}">Hoàn thành</c:when>
                            <c:otherwise>Không xác định</c:otherwise>
                        </c:choose>
                    </h3>
                    <div class="message-center">
                        <c:forEach var="task" items="${entry.value}">
                            <a href="#">
                                <div class="mail-contnet">
                                    <h5>${task.name}</h5>
                                    <span class="mail-desc">${task.description}</span>
                                    <span class="time">Từ ${task.startDate} đến ${task.endDate}</span>
                                </div>
                            </a>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </c:forEach>
    </c:forEach>
</div>

				<!-- END DANH SÁCH CÔNG VIỆC -->
			</div>
			<!-- /.container-fluid -->
			<footer class="footer text-center"> 2018 &copy; myclass.com
			</footer>
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->
	<!-- jQuery -->
	<script src="plugins/bower_components/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="bootstrap/dist/js/bootstrap.min.js"></script>
	<!-- Menu Plugin JavaScript -->
	<script
		src="plugins/bower_components/sidebar-nav/dist/sidebar-nav.min.js"></script>
	<!--slimscroll JavaScript -->
	<script src="js/jquery.slimscroll.js"></script>
	<!--Wave Effects -->
	<script src="js/waves.js"></script>
	<!-- Custom Theme JavaScript -->
	<script src="js/custom.min.js"></script>
</body>

</html>