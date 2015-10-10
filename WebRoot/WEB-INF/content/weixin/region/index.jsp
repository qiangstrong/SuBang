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
	<title>速帮</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
</head>
<body>
	<%@ include file="../common/menu.jsp"%>
	<table align="left">
		<tr>
			<td>
				${city.name }
			</td>
		</tr>
		<c:forEach var="category" items="${categorys }">
		<tr>
			<td>				
				<table>
					<tr>
						<td colspan="2">
							<img height="50px" src="${category.icon}" />
						</td>
					</tr>
					<tr>
						<td>名称：</td>
						<td>${category.name }</td>
					</tr>
					<tr>
						<td>备注：</td>
						<td>${category.comment }</td>
					</tr>
					<tr>
						<td colspan="2" align="right">
							<a href="weixin/order/showadd.html?categoryid=${category.id}">下单</a>
						</td>
					<tr>
				</table>				
			</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>