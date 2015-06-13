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
	<title>查看订单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<table align="center">
		<tr>
			<td>
				<a href="weixin/order/index.html?type=1">服务中</a>
			</td>
			<td>
				<a href="weixin/order/index.html?type=2">已完成</a>
			</td>
		</tr>
		<c:forEach var="order" items="${orders }">
		<tr>
			<td colspan="2">				
				<table>
					<tr>
						<td>订单号：</td>
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
						<td>
							<c:if test="${order.state==0 }">
							<a href="weixin/order/cancel.html?orderid=${order.id}">取消</a>
							<a href="weixin/order/fetch.html?orderid=${order.id}">取走</a>
							</c:if>
						</td>
						<td>
							<a href="weixin/order/detail.html?orderid=${order.id}">详情</a>
						</td>
					<tr>
				</table>				
			</td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>