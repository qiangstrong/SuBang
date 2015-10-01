<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>">
	<title>支付</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
	<script type="text/javascript">
		function pay(){
			WeixinJSBridge.invoke('getBrandWCPayRequest',${json},function(res){
				if(res.err_msg == 'get_brand_wcpay_request:ok'){
					window.location.href = "weixin/order/index.html?type=1";
				}else if(res.err_msg == 'get_brand_wcpay_request:fail'){					
					alert(res.err_msg);
				}
			});
		}
	</script>
</head>
<body>
<c:if test="${infoMsg!=null}">
	<table align="left">
		<tr>
			<td>
				<strong>支付失败</strong>
			</td>
		<tr>
		<tr>
			<td>${infoMsg }</td>
		<tr>
	</table>	
</c:if>
<c:if test="${infoMsg ==null}">
	<table align="left">
		<tr>
			<td>
				<strong>支付详情</strong>
			</td>
		<tr>
		<tr>
			<td class="label">订单号：</td>
		<tr>
		<tr>
			<td>${order.orderno }</td>
		<tr>
		<tr>
			<td class="label">金额：</td>
		<tr>
		<tr>
			<td>${order.price }</td>
		<tr>
		<tr>
			<td>
				<input class="submit" type="button" value="微信支付" onclick="pay()"/>
			</td>
		</tr>
	</table>
</c:if>
</body>
</html>