<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="crm_app07.entity.UserEntity"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
UserEntity user = (UserEntity) session.getAttribute("user");
%>

<ul class="nav navbar-top-links navbar-right pull-right">
	<li>
		<div class="dropdown">
			<a class="profile-pic dropdown-toggle" data-toggle="dropdown"
				href="#"> <img src="plugins/images/users/varun.jpg"
				alt="user-img" width="36" class="img-circle" /> <b
				class="hidden-xs"> <%
					 if (user != null) {
					 	out.print(user.getEmail());
					 } else {
					 %> Cybersoft <%
					 }
					 %>
			</b>
			</a>


			<ul class="dropdown-menu">
				<li><a href="${pageContext.request.contextPath}/profile">Thông
						tin cá nhân</a></li>
				<li><a href="${pageContext.request.contextPath}/profile-task">Thống kê công việc</a></li>
				<li class="divider"></li>
				<li><a href="${pageContext.request.contextPath}/logout">Đăng
						xuất</a></li>
			</ul>
		</div>
	</li>
</ul>