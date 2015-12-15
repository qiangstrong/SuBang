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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<link href="css/weixin/ccsjsp/order/index/buy.css" rel="stylesheet"
	type="text/css">
<script type="text/javascript">
		function pay(){
			WeixinJSBridge.invoke('getBrandWCPayRequest',${json},function(res){
				if(res.err_msg == 'get_brand_wcpay_request:ok'){
					window.location.href = "weixin/order/index.html?type=2";
				}else if(res.err_msg == 'get_brand_wcpay_request:fail'){					
					alert("支付失败。");
				}
			});
		}
	</script>
</head>
<body style="zoom: 1;">
	<div class="pay_font">
		<span>支付详情</span>
		<div class="clearBoth"></div>
	</div>
	
	<div class="pay_manner white_bg">
	<div class="item_list_box">
				订单号： ${orderDetail.orderno} <span
					class="pull-right orange_color"></span>
	</div>
	<div class="item_list_box">
				支付金额： ${orderDetail.actualMoney}<span
					class="pull-right orange_color"></span>
	</div>
	</div> 
	<div class="pay_font">
		<span>收款方</span>
		<div class="clearBoth"></div>
	</div>
	<div class="pay_manner white_bg">
			<div class="item_list_box">
				葫芦岛速帮网络技术有限公司  
			</div>
			<div class="borderD"></div>
			<div class="clear"></div>
			<div class="item_list_btn">
				<div class="chongzhi_btns" style="padding-right:0">

					<button type="submit" id="submit_pay" class="pay_btn link-btn-all"
						onclick="pay()">立即支付</button>
				</div>
			</div>
			<c:if test="${infoMsg!=null}">
				<div class="item_list_btn">
					<div class="chongzhi_btns" style="padding-right:0; margin-top: 80px">
						<button id="erro" class="pay_btn link-btn-all"
							style="background: rgb(169,169,169)" >
							${infoMsg }</button>
					</div>
				</div>
			</c:if>
	</div>


</body>
</html>