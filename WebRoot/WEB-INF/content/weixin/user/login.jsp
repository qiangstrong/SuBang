<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<base href="<%=basePath%>">
<title>登录</title>
<link href="css/weixin/ccsjsp/user/index/welcome.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/user/index/base.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<div class="welcome-index" id="wx_mask">
		<form modelAttribute="user" action="weixin/user/login.html"
			method="post">
			<table>
				<tbody>
					<tr>
						<td width="65%"><label class="input_wrap" for="tel">
								<img src="css/weixin/ccsjsp/user/index/welcome_phone.png">
								<input name="cellnum" id="cellnum" type="tel" maxlength="11"
								placeholder="请输入手机号" value=""
								onkeyup="value=value.replace(/[^\d]/g,&#39;&#39;)"
								onbeforepaste="clipboardData.setData(&#39;text&#39;,clipboardData.getData(&#39;text&#39;).replace(/[^\d]/g,&#39;&#39;))">
						</label>
						</td>
					</tr>
					<tr>
						<td><label class="input_wrap" for="code"> <img
								src="css/weixin/ccsjsp/user/index/welcome_pas.png"> <input
								name="password" id="password" type="password" maxlength="20"
								placeholder="密码"> </label>
						</td>
					</tr>
					<tr>
						<td class="p10"><button id="binding" class="btn-public"
								style="opacity:0.8" onclick="this.form.submit()">登录</button>
						</td>
					</tr>
					<tr>
						<!--  <td class="p10"><a href="weixin/user/showregcellnum.html"><button id="regbtn" class="btn-public"  style="opacity:0.8">注册</button></a></td>-->
					</tr>
				</tbody>
			</table>
		</form>
		<table>
			<tbody>
				<tr>
					<td class="p10"><a href="weixin/user/showregcellnum.html"><button
								id="regbtn" class="btn-public" style="opacity:0.8">注册</button>
					</a></td>
				</tr>
			</tbody>
		</table>
	</div>


</body>
</html>