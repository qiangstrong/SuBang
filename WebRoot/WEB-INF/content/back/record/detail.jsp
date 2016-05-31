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
	<script type="text/javascript" src="js/back/set.js"></script>
	<script type="text/javascript" src="js/back/state.js"></script>
</head>
<body >
	<%@ include file="../common/header.jsp"%>
	<table align="center">	
		<tr>
			<td colspan="2" align="right"><a href="back/record/index/back.html">返回</a></td>
		</tr>
		<tr>
			<th colspan="2">订单信息</th>			
		</tr>
		<tr>
			<td>订单号:</td>
			<td>${recordDetail.orderno}</td>
		</tr>
		<tr>
			<td>状态:</td>
			<td>${recordDetail.stateDes}</td>
		</tr>
		<tr>
			<td>下单时间:</td>
			<td>${recordDetail.timeDes}</td>
		</tr>
		<tr>
			<td>商品名称:</td>
			<td>${recordDetail.name}</td>
		</tr>
		<tr>
			<th colspan="2">用户信息</th>			
		</tr>
		<tr>
			<td>手机号:</td>
			<td>${user.cellnum}</td>
		</tr>
		<tr>
			<th colspan="2">地址信息</th>			
		</tr>
		<tr>
			<td>姓名:</td>
			<td>${addrDetail.name}</td>
		</tr>
		<tr>
			<td>手机号:</td>
			<td>${addrDetail.cellnum}</td>
		</tr>
		<tr>
			<td>区域:</td>
			<td>${addrDetail.cityname} ${addrDetail.districtname} ${addrDetail.regionname}</td>
		</tr>
		<tr>
			<td>地址:</td>
			<td>${addrDetail.detail}</td>
		</tr>
		<tr>
			<th colspan="2">取衣员信息</th>			
		</tr>
		<tr>
			<td>姓名:</td>
			<td>${worker.name}</td>
		</tr>
		<tr>
			<td>手机号:</td>
			<td>${worker.cellnum}</td>
		</tr>
		<tr>
			<th colspan="2">支付信息</th>			
		</tr>
		<tr>
			<td>方式:</td>
			<td>${recordDetail.payTypeDes}</td>
		</tr>
		<tr>
			<td>金额:</td>
			<td>${recordDetail.money}元</td>
		</tr>
		<tr>
			<td>积分:</td>
			<td>${recordDetail.score}</td>
		</tr>
	</table>
</body>
</html>