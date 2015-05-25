<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>">
	<title>用户登录</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
</head>
<body>
	<c:if test="${errMsg!=null}">
		<script>
			alert('${errMsg}');
		</script>
	</c:if>
	<form:form modelAttribute="admin" action="login.html" method="post">
		<table align="center">
			<tr>
				<td colspan="2">
					<c:if test="${infoMsg!=null}">
						${infoMsg}
					</c:if>
				</td>
			</tr>
			<tr>
				<td width="20%">用户名:</td>
				<td width="80%"><form:input path="username" /></td>
			</tr>
			<tr>
				<td width="20%">密码:</td>
				<td width="80%"><form:password path="password" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="登录" /></td>
				<td><input type="reset" value="重置" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>