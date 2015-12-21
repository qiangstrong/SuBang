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
<meta charset="utf-8">
<base href="<%=basePath%>">
<meta
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no"
	name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta content="" name="pgv">
<link href="css/weixin/ccsjsp/user/index/jifen.css" rel="stylesheet"
	type="text/css">
<script src="css/weixin/ccsjsp/user/index/hm.js"></script>
<script type="text/javascript"
	src="css/weixin/ccsjsp/user/index/jquery-1.8.2.min.js"></script>
<title>我的余额</title>
</head>
<body id="card" ondragstart="return false;"
	onselectstart="return false;">
	<section class="my_jifen">
		<div class="white-bg radius_block">

			<div class="jifen_box">
				余额
				<div class="font16">
					${user.money}<small style="font-size:12px;margin-top:-3px;">元</small>
				</div>
			</div>
			<div class="chongzhi_shc">
				<a href="weixin/balance/parapay.html">去充值</a>
			</div>
			<div class="jifen_mingxi">
				<div class="borderD2"></div>
				<span>余额明细</span>
			</div>
		</div>
	</section>
	<section id="icard_details_list" class="jifen_list white-bg">
		<c:forEach var="balance" items="${balances}">
			<div class="jifen_itle2 feature_block icard_row">
				${balance.payTypeDes}
				<div class="add_jifen chongzhi_yue ">+${balance.money}</div>
				<div class="time2">${balance.timeDes}</div>
			</div>
			<div class="borderD"></div>
		</c:forEach>
		<!-- 
		<c:forEach begin="1" end="100">
			<div class="jifen_itle2 feature_block icard_row" page="1"
				rows_count="2">
				评论返现
				<div class="add_jifen chongzhi_yue ">+0.50</div>
				<div class="time2">2015-09-04 20:37</div>
			</div>
			<div class="borderD"></div>
		</c:forEach>
		 -->
		 

	</section>

	





</body>
</html>