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
<title>充值</title>
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
<c:if test="${infoMsg!=null}">
		<script>
			alert('${infoMsg}');
		</script>
	</c:if>
	<div class="icard_bg text-center"
		style="background: url(css/weixin/ccsjsp/order/index/ichange_bg.png) repeat-x;">
		<div class="p5">
			<table>
				<tbody>
					<tr>
						<td><img src="css/weixin/ccsjsp/order/index/icon_person.png">
						</td>
						<td>${user.cellnum}</td>
					</tr>
					<tr>
						<td><img src="css/weixin/ccsjsp/order/index/1icon_money.png">
						</td>
						<td class="orange_color">余额：${user.money}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="p5"></div>
	</div>

	<div class="p5"></div>
	<div class="p5"></div>

	<!-- 在线充值 start -->
	<div id="input_jquery">
		<form:form id="recharge_form" modelAttribute="payArg"
			action="weixin/balance/prepay.html" method="post">


			<table width="100%" cellspacing="0" cellpadding="0"
				class="table-price">
				<tbody>
					<c:forEach var="rebate" items="${rebates}" varStatus="st">
						<c:if test="${(st.count)%2==1}">
							<tr>
						</c:if>

						<td style="width: 50%;border-bottom:1px solid #eee">
							<div class="charge_money select_sum" sum="${rebate.money}">
								<span>${rebate.toString()}</span> <img
									src="css/weixin/ccsjsp/order/index/icon_choose.png"
									class="select_charge" style="display: none">
							</div></td>


						<c:if test="${(st.count)%2==0}">
							</tr>
						</c:if>
					</c:forEach>


					<c:if test="${rebates.size()%2==0}">
						<tr>
							<td style="width: 50%;border-bottom:1px solid #eee">
								<div class="charge_money">
									<span></span>
								</div></td>
					</c:if>
				   <td style="background:#fff"><input type="hidden" value=""
						name="fee_2" id="select_sum"> <input
						class="charge_money input_sum" type="tel" name="fee"
						id="input_sum" placeholder="输入其他金额" maxlength="6"
						style="ime-mode: disabled; color: #c1c1c1;" autocomplete="off"
						oninput="value=value.replace(/[^\d.]/g, &#39;&#39;);"></td>
					</tr> 
				
				</tbody>
			</table>
			<input type="hidden" value="150" name="money" id="total_sum">





			<div class="p5"></div>
			<div class="p5"></div>
			<div class="p5"></div>
			<div class="pay_manner white_bg">
				<div class="item_list_box">
					<img src="css/weixin/ccsjsp/order/index/wexin_pay_icon.png">
					微信支付 <span class="pull-right orange_color" style="right: 40px;">
						<input id="ali_pay1" type="radio" name="payType" value="1"
						checked="checked"> </span>
				</div>
				<div class="borderD2"></div>
				 
				<!--  <div class="item_list_box">
					<img src="css/weixin/ccsjsp/order/index/ali_pay_icon.png">
					支付宝支付 <span class="pull-right orange_color" style= "right: 40px;"> <input
						id="ali_pay2" type="radio" name="payType" value="2"> </span>
				</div>-->

			</div>
			<table width="100%" cellspacing="0" cellpadding="0"
				class="table-price">
				<tbody>
					<tr>
						<td colspan="2">
							<div class="p5"></div>
							<div class="footReturn p10">
								<button type="button" value="确定" id="recharge_btn"
									class="coupon-btn">立即充值</button>
							</div></td>
					</tr>
				</tbody>
			</table>

		</form:form>
	</div>
	<!-- 在线充值 end -->



	<!-- 提示浮层 start -->
	<div id="codFloat" style="display:none" class="cod">
		<div class="wx_mask"></div>
		<div class="wx_confirm ">
			<div class="wx_confirm_inner" id="wx_confirm_float">
				<div class="wx_confirm_hd">
					<div class="wx_confirm_tit" id="show_mes">出错了，请稍后再试！</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 提示浮层 end -->

	<script type="text/javascript">
var tags = "1";
var flag = 1;
$(document).ready(function() {
	// 选择金额
	$("div.select_sum").on('click', function() {
		$("#input_sum").val('').attr("placeholder", "输入其他金额");
		var sel_img = $(this).find("img.select_charge");
		var unsel_img = $("img.select_charge").not(sel_img);
		sel_img.toggle();
		unsel_img.hide();
		var sum = $(this).attr('sum');
		if(sel_img.css('display') == 'none'){
			sum = '';
		}
		// 选中金额放入隐藏域
		$("#select_sum").val(sum);
	});
	$("div.select_sum:first").click();
	
	// 输入金额
	$("#input_sum").focus(function() {
		$("#select_sum").val('');
		$(this).css({'color':'#3e3e3e'});
		$("img.select_charge").hide();
	});
	
	// 提交金额
	$("#recharge_btn").on('click', function(){
		
		if(recharge_sum()){
		      var select_sum = $.trim($("#select_sum").val()); // 选择金额
	          var input_sum = $.trim($("#input_sum").val());   // 输入金额
	          var total_sum = select_sum+input_sum
	          $("#total_sum").val(total_sum);
			  $("#recharge_btn").attr("disabled", true);
		      $("#recharge_form").submit();
		}
		return;
	});
	
	
	
});

function alert_1(title) {
	if(title){
		$("#show_mes").html(title);
	}
	$("#codFloat").show().delay(2000).hide(0);
}

function recharge_sum() {
	var select_sum = $.trim($("#select_sum").val()); // 选择金额
	var input_sum = $.trim($("#input_sum").val());   // 输入金额
	if (!select_sum && !input_sum) {
		alert_1("请您选择或输入充值金额");
		return false;
	}else{
		return true;
	}
	var re = /^\d+\.{0,1}\d{0,2}$/;
	if (select_sum > 0 && (input_sum <= 0 || input_sum > 9999 || !re.test(input_sum))) {
		alert_1("请您输入合理金额");
		return false;
	}else{
		return true;
	}
}
</script>




</body>
</html>