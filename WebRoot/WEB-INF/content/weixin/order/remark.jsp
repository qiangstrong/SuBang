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
<link rel="stylesheet" type="text/css"
	href="/css/weixin/ccsjsp/addr/style1.3.8.5.css">
<meta charset="utf-8">
<meta
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no"
	name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta content="" name="pgv">
<title>评价</title>
<base href="<%=basePath%>">
<link href="/css/weixin/ccsjsp/addr/base.css" rel="stylesheet"
	type="text/css">
<link href="/css/weixin/ccsjsp/addr/order.css" rel="stylesheet"
	type="text/css">
<script src="/css/weixin/ccsjsp/addr/hm.js"></script>
<script type="text/javascript" src="js/weixin/user.js"></script>
<script type="text/javascript" src="js/weixin/jquery-1.7.1.min.js"></script>
</head>

<body style="zoom: 1;">
	<div id="mainWin" style="padding: 0">
		<div class="space-split10"></div>
		<form:form modelAttribute="order" action="weixin/order/remark.html"
			method="post">
			<input type="hidden" name="orderid" id="orderid" value="${orderid}">
			<div class="white-bg">
				<div class="borderD2"></div>
				<div class="borderD"></div>
				<div class="m_input m_input_5" style="margin-bottom: 0">
					<div class="input">
						<span style="color:#8a8a8a"><form:errors path="ramark" />
							<!-- 显示输入错误 --> </span>
						<textarea placeholder="请输入你对本次服务的评价" id="remark" name="remark"
							class="textarea-address"> ${remark}</textarea>
					</div>
				</div>
			</div>
			<div class="borderD2"></div>
			<!-- 信息 end -->


			<!-- 保存 start -->
			<sectoin class="true-btn">
			<div class="">
				<input type="submit" class="btn_order y_hover J_ytag" value="保存">
			</div>
			</sectoin>
			<!-- 保存 end -->
		</form:form>
	</div>

</body>
</html>