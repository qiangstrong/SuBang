<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta content="" name="pgv">
<base href="<%=basePath%>">
<title>优惠券使用说明</title>
<meta content="authenticity_token" name="csrf-param">
<meta content="Hij+/eHaowIxOEoyfu6jcrxWYQrmARS/zdFYuoaZ2XM=" name="csrf-token">
<link href="css/weixin/ccsjsp/ticket/index/mobile-4a.css" media="screen" rel="stylesheet">
<script src="css/weixin/ccsjsp/ticket/index/question-405066dfbcd21aa33ace9e50d18f96cc.js"></script>
</head>
<body scroll="no">
<div id="container">
  <div class="order_weixin" id="orderurl" style="display:none"><span class="arrow-left"></span><a class="btn-info fanhui" href="javascript:history.back(-1)"></a></div>
  <div class="coupon_content"><img alt="Coupon title" class="img-title" src="css/weixin/ccsjsp/ticket/index/coupon_title.png">
    <p class="step-text"><img alt="Step1" src="css/weixin/ccsjsp/ticket/index/step1.png">支付时，选择可用优惠券。</p>
    <img alt="Coupon 01" class="imgBig" src="css/weixin/ccsjsp/ticket/index/coupon_01.png">
    <p class="step-text"><img alt="Step2" src="css/weixin/ccsjsp/ticket/index/step2.png">彩色券可用，灰色券不可用。</p>
    <img alt="Coupon 02" class="imgBig" src="css/weixin/ccsjsp/ticket/index/coupon_02.png">
    <div class="step-text"><img alt="Step3" src="css/weixin/ccsjsp/ticket/index/step3.png">不同品类优惠券不可用
      <p>未满指定金额优惠券不可用，优惠券未到期不可用。</p>
    </div>
    <img alt="Coupon 03" class="imgBig" src="css/weixin/ccsjsp/ticket/index/coupon_03.png">
    <div class="step-text"><img alt="Step4" src="css/weixin/ccsjsp/ticket/index/step4.png"></div>
    <div class="info-list">
      <p>每单只能用一张优惠券；</p>
      <p>优惠券使用后无法退还；</p>
      <p>优惠券不找零不兑现；</p>
      <p>优惠券过期后会自动消失；</p>
    </div>
  </div>
</div>
</body>
</html>