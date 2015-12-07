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
				<li><a
					href="weixin/order/detail.html?orderid=${orderDetail.id}"> <!-- 此链接到此订单详情 -->
						<div class="category_block">
							<!--洗衣 -->
							
							<c:choose>
							<c:when test="${orderDetail.categoryid==2}">
							<div class="category_cloth">
							<img class="icon-washing" src="css/weixin/ccsjsp/order/index/xi_shoes.png" style="position:relative;">
							${orderDetail.categoryname}</div>
							</c:when>
							<c:otherwise>
							<div class="category_cloth">
							<img class="icon-washing" src="css/weixin/ccsjsp/order/index/xi_cloth.png" style="position:relative;">
							${orderDetail.categoryname}</div>
							</c:otherwise>
							</c:choose>
														
							<div class="order_status_div">
								<span class="text-info">${orderDetail.stateDes}</span>
								<!-- 使用getStateDes()， xxx。stateDes，去掉get后首字母小写 -->

							</div>
						</div> </a></li>
				<div class="borderD" style="border-color:#eee"></div>
				<li class="order_item mobile-font"><a
					href="weixin/order/detail.html?orderid=${orderDetail.id}"> <!-- 此链接到此订单详情 -->
						<div class="order_box">
							<div class="item_list_box">订单编号：${orderDetail.orderno }</div>
							<div class="item_list_box">取件时间：${orderDetail.dateDes}
								${orderDetail.timeDes}</div>
							<div class="item_list_box">订单金额：${orderDetail.totalMoneyDes}</div>
						</div> </a>
					<div class="clearBoth"></div> <c:if test="${orderDetail.state==0}">
						<div class="item_list_money navlist">
							<!-- 已接受 -->
							<div class="item_list_box">
								<div class="borderD"></div>
								<a class="order_link order_blue" id="order_link"
									href="weixin/order/cancel.html?orderid=${orderDetail.id}">取消</a>
								<div class="clearBoth"></div>
							</div>
						</div>
					</c:if> <c:if test="${orderDetail.state==1}">
						<!-- 已计价 -->
						<div class="item_list_box">
							<a href="weixin/order/cancel.html?orderid=${orderDetail.id}" id="order_link"
								class="order_link order_blue">取消</a> <a
								href="weixin/order/parapay.html?orderid=${orderDetail.id}" id="order_link"
								class="order_link order_blue">支付</a>
							<div class="clearBoth"></div>
						</div>
					</c:if> <c:if test="${orderDetail.state==4}">
						<!-- 已分拣 -->
						<div class="item_list_box">
							<a href="weixin/order/deliver.html?orderid=${orderDetail.id}" id="order_link"
								class="order_link order_blue">送达</a>
							<div class="clearBoth"></div>
						</div>
					</c:if> <c:if test="${orderDetail.state==5}">
						<!-- 已返还 -->
						<div class="item_list_box">
							<a href="javascript:void(0)"
								id="order_link" class="order_link order_blue">
							<!--  <a href="weixin/order/showremark.html?orderid=${orderDetail.id}"
								id="order_link" class="order_link order_blue">-->
								评价</a>
							<div class="clearBoth"></div>
						</div>
					</c:if> <!-- 取消订单/分享领券/投诉 按钮 end -->

					<div class="clearBoth"></div>
					<div class="clearBoth"></div>
				</li>

				<!-- 取消订单等 按钮 start -->






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