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
	<title>修改信息</title>
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
	<%@ include file="../common/header.jsp"%>
	<table align="center">
		<tr>
			<td>
				<c:if test="${infoMsg!=null}">
					${infoMsg}
				</c:if>
			</td>
			<td align="right"><a href="misc/admin.html">返回</a></td>
		</tr>
		<tr>
			<td colspan="2">
				<form:form modelAttribute="adminBean" action="misc/modifyadmin.html" method="post">
					<form:hidden path="id"/>
					<table>
						<tr>
							<th>用户名</th>
							<td><form:input path="username" /></td>
						</tr>
						<tr>
							<th>密码</th>
							<td><form:password path="password" /></td>
						</tr>
						<tr>
							<th>确认密码</th>
							<td><form:password path="passwordCopy" /></td>
						</tr>
						<tr>
							<td colspan="2" align="right">
								<input type="submit" value="修改" />
								<input type="reset" value="重置" />								
							</td>
						</tr>
					</table>
				</form:form>
			</td>
		</tr>
	</table>
</body>
</html>