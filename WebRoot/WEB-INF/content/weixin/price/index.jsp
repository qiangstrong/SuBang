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
<html lang="en" style="overflow-x:hidden">
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
<script
	src="css/weixin/ccsjsp/price/jquery-1.10.1.min-405066dfbcd21aa33ace9e50d18f96cc.js"></script>
<script
	src="css/weixin/ccsjsp/price/price_helper-5e11375e718f8a3eebfc752fa7666b1b.js"></script>
<link href="css/weixin/ccsjsp/price/price.css" rel="stylesheet"
	type="text/css">
<title>洗衣家纺</title>
<meta content="authenticity_token" name="csrf-param">
<meta content="HyiiKD/E88uHBVBldzvyW7Z8BEneh5YgAhuLXSQMNiE="
	name="csrf-token">

<script type="text/javascript">window.onload = function(){

  if(isWeiXin()){
    var orderlink = document.getElementById('orderlink');

    orderlink.style.display = "block";


  }
}
function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}</script>
</head>
<body class="gray_bg price-navbar" id="price_navbar" onmousewheel=""
	scroll="no" style="zoom: 1;">
	
	<div class="order_weixin" id="orderurl">
		<c:if test="${categoryid==1}">
		<a class="nav_a active_nav"
			href="weixin/price/index.html?categoryid=1" id="by_cloth_wx">洗衣计价</a><a
			class="nav_a " href="weixin/price/index.html?categoryid=2"
			id="by_home_textile_wx">洗鞋计价</a> <a class="nav_a "
			href="weixin/price/index.html?categoryid=3" id="by_home_textile_wx">家纺计价</a>
		<a class="nav_right " href="weixin/price/bag.html" id="by_bag_wx">按袋计价</a>
		</c:if>
		<c:if test="${categoryid==2}">
		<a class="nav_a"
			href="weixin/price/index.html?categoryid=1" id="by_cloth_wx">洗衣计价</a><a
			class="nav_a active_nav" href="weixin/price/index.html?categoryid=2"
			id="by_home_textile_wx">洗鞋计价</a> <a class="nav_a "
			href="weixin/price/index.html?categoryid=3" id="by_home_textile_wx">家纺计价</a>
		<a class="nav_right " href="weixin/price/bag.html" id="by_bag_wx">按袋计价</a>
		</c:if>
		<c:if test="${categoryid==3}">
		<a class="nav_a"
			href="weixin/price/index.html?categoryid=1" id="by_cloth_wx">洗衣计价</a><a
			class="nav_a " href="weixin/price/index.html?categoryid=2"
			id="by_home_textile_wx">洗鞋计价</a> <a class="nav_a active_nav"
			href="weixin/price/index.html?categoryid=3" id="by_home_textile_wx">家纺计价</a>
		<a class="nav_right " href="weixin/price/bag.html" id="by_bag_wx">按袋计价</a>
		</c:if>
	</div>
	
	<div id="container">
		<div class="washing_cloth" style="margin-bottom: 80px;">
			<div class="title">
				<div class="text-center">专业洗衣服务，72小时内送回</div>
			</div>
			<div class="price_list">
				<p class="p5" style="padding-top:0">按件计价</p>
				<div class="tab_nav_price" id="tab">
					<c:forEach var="price" items="${prices}">
						<span class="on">${price.money}元/件</span>
					</c:forEach>
				</div>
				<div id="list">
					<c:forEach var="price" items="${prices}" varStatus="p_status">
						<div class="price_div current_border" id="content-2535">
							<p>${price.comment}</p>
							<c:forEach var="clothesTypes" items="${clothesTypesList}"
								varStatus="t_status">
								<c:if test="${p_status.count==t_status.count}">
									<c:forEach var="clothesType" items="${clothesTypes}">
										<div class="block_pic">
											<div class="pic">
												<img alt="${clothesType.name}" src="${clothesType.icon}">
												<div>${clothesType.name}</div>
											</div>
										</div>
									</c:forEach>
								</c:if>
							</c:forEach>
						</div>
					</c:forEach>
				</div>
			</div>
			<p class="price_title">· 羽绒服、棉服等厚重衣物预计3-5天送回</p>
		</div>
		<script type="text/javascript">$(function(){
　　$(".tab_nav_price span").first().addClass("on").siblings().removeClass("on");
  　　$("#list > .price_div").first().show().addClass('current_border').siblings().hide();
  　　$(".tab_nav_price span").click(function(){
     $("#list > .price_div").removeClass('current_border');
　　  var index = $(".tab_nav_price span").index(this);
  　　$(this).addClass("on").siblings().removeClass("on");
  　　$("#list > .price_div").eq(index).show().addClass('current_border').siblings().hide();
　　});
});</script>
	</div>
	<script type="text/javascript">$(".washing_cloth,.shechipin_cloth").css("margin-bottom","80px");</script>
</body>
</html>