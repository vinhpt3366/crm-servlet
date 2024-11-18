<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.Cookie"%>

<%@ page import="crm_app07.entity.UserEntity"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- <%
UserEntity user = (UserEntity) session.getAttribute("user");
%>
 --%>
<%
Cookie[] cookies = request.getCookies();
String roleId = null;

if (cookies != null) {
    for (Cookie cookie : cookies) {
        if ("roleID".equals(cookie.getName())) {
            roleId = cookie.getValue();
            break;
        }
    }
}

int roleID = (roleId != null) ? Integer.parseInt(roleId) : -1;
%>

<%-- menu.jsp --%>
<ul class="nav" id="side-menu">
	<%
	if (roleID == 1) {
	%>
	<li style="padding: 10px 0 0;"><a
		href="${pageContext.request.contextPath}/" class="waves-effect"> <i
			class="fa fa-clock-o fa-fw" aria-hidden="true"></i><span
			class="hide-menu">Dashboard</span></a></li>
	<li><a href="${pageContext.request.contextPath}/users"
		class="waves-effect"> <i class="fa fa-user fa-fw"
			aria-hidden="true"></i><span class="hide-menu">Thành viên</span></a></li>
	<li><a href="${pageContext.request.contextPath}/roles"
		class="waves-effect"> <i class="fa fa-modx fa-fw"
			aria-hidden="true"></i><span class="hide-menu">Quyền</span></a></li>
	<li><a href="${pageContext.request.contextPath}/projects"
		class="waves-effect"> <i class="fa fa-table fa-fw"
			aria-hidden="true"></i><span class="hide-menu">Dự án</span></a></li>
	<li><a href="${pageContext.request.contextPath}/tasks"
		class="waves-effect"> <i class="fa fa-table fa-fw"
			aria-hidden="true"></i><span class="hide-menu">Công việc</span></a></li>
	<li><a href="blank.jsp" class="waves-effect"> <i
			class="fa fa-columns fa-fw" aria-hidden="true"></i><span
			class="hide-menu">Blank Page</span></a></li>
	<li><a href="404.jsp" class="waves-effect"> <i
			class="fa fa-info-circle fa-fw" aria-hidden="true"></i><span
			class="hide-menu">Error 404</span></a></li>
	<%
	} else if (roleID == 2) {
	%>
	<li style="padding: 10px 0 0;"><a
		href="${pageContext.request.contextPath}/" class="waves-effect"> <i
			class="fa fa-clock-o fa-fw" aria-hidden="true"></i><span
			class="hide-menu">Dashboard</span></a></li>
	<li><a href="${pageContext.request.contextPath}/users"
		class="waves-effect"> <i class="fa fa-user fa-fw"
			aria-hidden="true"></i><span class="hide-menu">Thành viên</span></a></li>
	<li><a href="${pageContext.request.contextPath}/projects"
		class="waves-effect"> <i class="fa fa-table fa-fw"
			aria-hidden="true"></i><span class="hide-menu">Dự án</span></a></li>
	<li><a href="${pageContext.request.contextPath}/tasks"
		class="waves-effect"> <i class="fa fa-table fa-fw"
			aria-hidden="true"></i><span class="hide-menu">Công việc</span></a></li>
	<li><a href="blank.jsp" class="waves-effect"> <i
			class="fa fa-columns fa-fw" aria-hidden="true"></i><span
			class="hide-menu">Blank Page</span></a></li>
	<li><a href="404.jsp" class="waves-effect"> <i
			class="fa fa-info-circle fa-fw" aria-hidden="true"></i><span
			class="hide-menu">Error 404</span></a></li>
	<%
	} else {
	%>
	<li style="padding: 10px 0 0;"><a
		href="${pageContext.request.contextPath}/" class="waves-effect"> <i
			class="fa fa-clock-o fa-fw" aria-hidden="true"></i><span
			class="hide-menu">Dashboard</span></a></li>
	<%-- <li><a href="${pageContext.request.contextPath}/tasks"
		class="waves-effect"> <i class="fa fa-table fa-fw"
			aria-hidden="true"></i><span class="hide-menu">Công việc</span></a></li> --%>
	<li><a href="blank.jsp" class="waves-effect"> <i
			class="fa fa-columns fa-fw" aria-hidden="true"></i><span
			class="hide-menu">Blank Page</span></a></li>
	<li><a href="404.jsp" class="waves-effect"> <i
			class="fa fa-info-circle fa-fw" aria-hidden="true"></i><span
			class="hide-menu">Error 404</span></a></li>
	<%
	}
	%>
</ul>
