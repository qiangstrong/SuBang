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
<title>速帮</title>
<link href="css/weixin/ccsjsp/region/index/index.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/region/index/animation.css"
	rel="stylesheet" type="text/css">
<link href="css/weixin/ccsjsp/region/index/base.css" rel="stylesheet"
	type="text/css">
<link rel="stylesheet" type="text/css"
	href="css/weixin/ccsjsp/region/index/swipe.css">
</head>
<body style="zoom: 1;">

	<!-- 首页城市 start -->
	<div class="address_tab">
		<a class="cityName" href="weixin/region/city.html">
			<!-- 从此跳转到城市选择列表 --> <span>${city.name }</span> <em
			class="arrow-left"></em> </a>
		<!--<img class="homeSlogan" src="css/weixin/ccsjsp/region/index/homeSlogan.png">-->
		<!-- 网页右上角可以放置的图片-->
	</div>
	<div class="borderD"></div>
	<!-- 首页城市 end -->
	<div class="gloalbackground"></div>
	<div id="wrap" class="wrap-home">
		<!-- 头部：轮转广告 start-->
		<%@ include file="swipe_Ad.jsp"%>
		<!-- 头部：轮转广告 end-->

		<div class="menu-home">
			<!-- 主体：品类 start -->
			<!-- 一般品类 -->
			<div class="commonWashing">

				<c:forEach var="category" items="${categorys}">
					<a href="weixin/order/showadd.html?categoryid=${category.id}">
					<div class="menu-list">
						<img class="img-icon" src="${category.icon}" />
						<div class="list-right">
							<p class="list-type">${category.name }</p>
							<p class="list-price">${category.comment }</p>
						</div>
					</div>
					</a>
				</c:forEach>
					<!--  <a href="weixin/order/showadd.html?categoryid=3">
					<div class="menu-list">
						<img class="img-icon" src="image/info/category/家纺.png" />
						<div class="list-right">
							<p class="list-type">家纺</p>
							<p class="list-price">0000000</p>
						</div>
					</div>
					</a>-->
			</div>
			<div class="clearBoth"></div>

			<!-- 主体：品类 end -->

			<!-- 页底：功能按钮 start -->
			<div class="borderLinear">
				<img src="css/weixin/ccsjsp/region/index/dividingLine.png"> <span>了解速帮</span>
			</div>
			<div class="menu-service">
				<div class="service-list" align="center">
					<a href="/content/weixin/info/serviceintro.htm">
						<p class="ser-font">服务介绍</p> </a>
				</div>
				<div class="service-list" align="center">
					<!--<a href="weixin/region/scope.html?cityid=${city.id}">-->
					<a href="weixin/region/scope.html?cityid=${city.id}">
						<p class="ser-font">服务范围</p> </a>
				</div>
				<div class="clearBoth"></div>
				<!-- 页底：功能按钮 end -->
			</div>
		</div>
		<!-- 页脚导航-->
		<%@ include file="../common/menu.jsp"%>
	</div>
</body>
</html>