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
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>
<body>
	<%@ include file="../common/menu.jsp"%>
	<table align="left">
		<tr>
			<td class="label"><strong>订单号：</strong></td>
		</tr>
		<tr>
			<td>${order.orderno }</td>
		</tr>
		<tr>
			<td class="label">订单类别：</td>
		</tr>
		<tr>
			<td>${order.categoryDes }</td>
		</tr>
		<tr>
			<td class="label">订单状态：</td>
		</tr>
		<tr>
			<td>${order.stateDes }</td>
		</tr>
		<tr>
			<td class="label">订单金额：</td>
		</tr>
		<tr>
			<td>${order.priceDes }</td>
		</tr>	
		<tr>
			<td class="label">取件时间：</td>
		</tr>
		<tr>
			<td>${order.dateDes } ${order.timeDes }</td>
		</tr>
		<tr>
			<td class="label">订单备注：</td>
		</tr>
		<tr>
			<td>${order.comment }</td>
		</tr>
		<tr>
			<td class="label">订单地址：</td>
		</tr>
		<tr>
			<td>${addrDetail }</td>
		</tr>		
	</table>
	<hr>
	<table>
		<tr>
			<td class="label" colspan="2">
				<strong>订单追踪：</strong>
			</td>
		</tr>
		<c:forEach var="history" items="${historys}">
		<tr>
			<td class="label">${history.operationDes }：</td>
			<td>${history.time }</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>