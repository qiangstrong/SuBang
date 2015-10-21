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
<title>订单列表</title>
<link href="css/weixin/ccsjsp/order/index/base.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/order/index/wap.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/order/index/myOrder.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/order/index/index.css" rel="stylesheet"
	type="text/css">

</head>
<body style="padding-bottom:110px;">
	<div class="channel-nav three-nav">
		<c:choose>
			<c:when test="${type==3}">
				<!-- 此处需要恰当的判断条件:已完成 -->
				<ul>
					<li class=""><a href="weixin/order/index.html?type=2">服务中</a>
					</li>
					<li class="active"><a href="weixin/order/index.html?type=3">已完成</a>
					</li>
				</ul>
				<div class="borderD2"></div>
			</c:when>
			<c:otherwise>
				<ul>
					<li class="active"><a href="weixin/order/index.html?type=2">服务中</a>
					</li>
					<li class=" "><a href="weixin/order/index.html?type=3">已完成</a>
					</li>
				</ul>
				<div class="borderD2"></div>
			</c:otherwise>
		</c:choose>
	</div>

	<!-- 已登录 -->
	<!-- 我的订单 start -->
	<div class="myOrder" style="padding-bottom:60px;">
		<!-- 有订单 -->
		<ul id="add_order">
			<!-- 未完成订单 -->

			<!-- 1号未完成 -->

			<c:forEach var="orderDetail" items="${orderDetails}">
				<li><a href="weixin/order/detail.html?orderid=${orderDetail.id}"> <!-- 此链接到此订单详情 -->
						<div class="category_block">
							<!--洗衣 -->
							<div class="category_cloth">
								${orderDetail.categoryname}
							</div>

							<div class="order_status_div">
						     <span class="text-info">${orderDetail.stateDes}</span><!-- 使用getStateDes()， xxx。stateDes，去掉get后首字母小写 -->
								
							</div>
						</div> </a>
				</li>
				<div class="borderD" style="border-color:#eee"></div>
				<li class="order_item mobile-font"><a href="weixin/order/detail.html?orderid=${orderDetail.id}"> <!-- 此链接到此订单详情 -->
						<div class="order_box">
							<div class="item_list_box">订单编号：${orderDetail.orderno }</div>
							<div class="item_list_box">取件时间：${orderDetail.dateDes}
								${orderDetail.timeDes}</div>
							<div class="item_list_box">订单金额：${orderDetail.moneyDes}</div>
						</div> </a>
					<div class="clearBoth"></div></li>
				<!-- 1号未完成end -->
			</c:forEach>
			<div id="prompt"
				style="text-align:center;line-height:40px;color:#999;">无更多订单</div>
		</ul>

	</div>
	<!-- 我的订单 end -->



	<!-- 返回顶部图标 start -->
	<div class="scroll" id="scroll" style="display:none;">
		<img src="css/weixin/ccsjsp/order/index/back_top.png">
	</div>
	<!-- 返回顶部图标 end -->

	<!-- 页脚导航-->
	<%@ include file="../common/menu1.jsp"%>
</body>
</html>