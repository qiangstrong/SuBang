<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no"
	name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta content="" name="pgv">
<title>支付结果</title>
<link href="css/weixin/ccsjsp/activity/base.css" rel="stylesheet" type="text/css">
<link href="css/weixin/ccsjsp/activity/myOrder.css" rel="stylesheet" type="text/css">
<link href="css/weixin/ccsjsp/activity/index.css" rel="stylesheet" type="text/css">
<link href="css/weixin/ccsjsp/activity/wap.css" rel="stylesheet" type="text/css">

</head>
<body>
<body style="padding-bottom:110px;">
	<div class="channel-nav three-nav">
		<ul>
			<li class="active">支付结果
			</li>

		</ul>
		<div class="borderD2"></div>
	</div>

	<!-- 已登录 -->
	<!-- 我的订单 start -->
	<div class="myOrder" style="padding-bottom:60px;">
		<!-- 无订单 -->
		<c:if test="${infoMsg!=null}">
			<div class="no-order text-center">
				<p>${infoMsg}</p>
			</div>

		</c:if>
	</div>
</body>
</html>