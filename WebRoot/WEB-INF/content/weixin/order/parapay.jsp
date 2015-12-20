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
<meta charset="utf-8">
<meta
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no"
	name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta content="" name="pgv">
<base href="<%=basePath%>">
<title>支付</title>
<link href="css/weixin/ccsjsp/order/index/base.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/order/index/footer.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/order/index/wap.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/order/index/myOrder.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/order/index/buy.css" rel="stylesheet"
	type="text/css">
<script src="css/weixin/ccsjsp/order/index/hm.js"></script>
<script type="text/javascript"
	src="css/weixin/ccsjsp/order/index/jquery-1.8.2.min.js"></script>
</head>
<body style="zoom: 1;">
	<div class="icard_bg text-center"
		style="background: url(css/weixin/ccsjsp/order/index/ichange_bg.png) repeat-x;">
		<div class="p5">
			<table>
				<tbody>
					<tr>
						<td style="padding-top: 1px; width: 20%;">
						</td>
						<td style="padding-top: 1px;">订单号：${orderDetail.orderno}</td>
					</tr>
					<tr>
						<td style="padding-top: 1px; width: 20%;">
						</td>
						<td style="padding-top: 1px;">实付金额：${orderDetail.actualMoneyDes}</td>
					</tr>
					<tr>
						<td style="padding-top: 1px; width: 20%;">
						</td>
						<td style="padding-top: 1px;">${orderDetail.paymentDes}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="p5"></div>
	</div>

	<div class="p5"></div>
	<div class="p5"></div>
	<div class="p5"></div>

	<!-- 在线充值 start -->
	<div id="input_jquery">
		<form:form id="recharge_form" modelAttribute="payArg"
			action="weixin/order/prepay.html" method="post">
			<input type="hidden" name="orderid" id="orderid"
				value="${orderDetail.id}">


			<!--  如果orderDetail.isTicket为false,用户可以选择一张优惠券；否则，用户不能选择。这由前端保证。 -->
			<c:if test="${orderDetail.isTicket()==false}">
				<div class="pay_manner white_bg">
					<a href="weixin/order/ticket.html?orderid=${orderDetail.id}">
						<div class="item_list_box">
							<img src=" ${ticketDetail.icon}"
								style="width: 75px; margin-top: 3px;">
							优惠券  ${ticketDetail.name} <span class="pull-right orange_color">
								点击选择 </span>
						</div> 
						</a>
				</div>
			<input type="hidden" name="ticketid" id="ticketid"
				value="${ticketDetail.id}"><!-- 应该传什么???? -->
			</c:if>





			<div class="p5"></div>
			<div class="pay_manner white_bg">
				<div class="item_list_box">
					<img src="css/weixin/ccsjsp/order/index/balance_pay_icon.png"> 余额支付 <span
						class="pull-right orange_color" style="right: 40px;"> <input
						id="ali_pay1" type="radio" name="payType" value="0"
						checked="checked"> </span>
				</div>
				<div class="borderD2"></div>
				<div class="item_list_box">
					<img src="css/weixin/ccsjsp/order/index/wexin_pay_icon.png"> 微信支付 <span
						class="pull-right orange_color" style="right: 40px;"> <input
						id="ali_pay2" type="radio" name="payType" value="1"> </span>
				</div>
				<div class="borderD2"></div>
				 
				<div class="item_list_box">
					<img src="css/weixin/ccsjsp/order/index/ali_pay_icon.png"> 支付宝支付 <span
						class="pull-right orange_color" style="right: 40px;"> <input
						id="ali_pay3" type="radio" name="payType" value="2"> </span>
				</div>
				<div class="borderD2"></div>
			</div>
			<table width="100%" cellspacing="0" cellpadding="0"
				class="table-price">
				<tbody>
					<tr>
						<td colspan="2"><div class="p5"></div>
							<div class="footReturn p10">
								<input type="button" value="立即支付" class="coupon-btn"
									onclick="this.form.submit()">

							</div></td>
					</tr>
				</tbody>
			</table>
		</form:form>
	</div>
	<!-- 在线充值 end -->

</body>
</html>