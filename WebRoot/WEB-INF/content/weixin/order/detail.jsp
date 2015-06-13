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
	<title>订单详情</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<table align="center">
		<tr>
			<td><strong>订单号：</strong></td>
			<td>${order.orderno }</td>
		</tr>
		<tr>
			<td>订单类别：</td>
			<td>${order.categoryDes }</td>
		</tr>
		<tr>
			<td>订单状态：</td>
			<td>${order.stateDes }</td>
		</tr>
		<tr>
			<td>订单金额：</td>
			<td>${order.priceDes }</td>
		</tr>	
		<tr>
			<td>取件时间：</td>
			<td>${order.dateDes } ${order.timeDes }</td>
		</tr>
		<tr>
			<td>订单备注：</td>
			<td>${order.comment }</td>
		</tr>
		<tr>
			<td>订单地址：</td>
			<td>${addrDetail }</td>
		</tr>
		<tr>
			<td colspan="2">
				<strong>订单追踪：</strong>
			</td>
		</tr>
		<c:forEach var="history" items="${historys}">
		<tr>
			<td>${history.operationDes }</td>
			<td>${history.time }</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>