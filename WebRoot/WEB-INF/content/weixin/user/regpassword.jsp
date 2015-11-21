<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<title>注册密码</title>
<link href="css/weixin/ccsjsp/user/index/welcome.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/user/index/base.css" rel="stylesheet"
	type="text/css">
</head>
<body>
	<div class="welcome-index" id="wx_mask">
		<form name=form1 id=form1 action="weixin/user/regpassword.html"
			method="post">
			<table>
				<tbody>
					<tr>
						<td width="65%"><label class="input_wrap" for="tel">
								<img src="css/weixin/ccsjsp/user/index/welcome_pas.png"> <input
								name="password" id="password" type="password"
								value="${password}" maxlength="20" placeholder="请输入密码">
						</label></td>
					</tr>
					<tr>
						<td><label class="input_wrap" for="code"> <img
								src="css/weixin/ccsjsp/user/index/welcome_pas.png"> <input
								name="passwordd" id="passwordd" type="password"
								value="${password}" maxlength="20" placeholder="请重新输入密码">
						</label></td>
					</tr>


					<c:if test="${infoMsg!=null}">
						<tr>
							<td class="p10"><span id="erro" class="show-erro"
								style="opacity:0.8">${infoMsg}</span></td>
						</tr>
					</c:if>
					<tr>
						<td class="p10"><input id="determine" class="btn-public"
							style="opacity:0.8" type="submit" value="确定"
							onclick="return checkvalue();"></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		function checkvalue() {
		     if(document.form1.password.value.length>50){
             alert("请将密码长度控制在1到50之间！");
             return false;
             }
             if(document.form1.password.value.length<1){
             alert("请将密码长度控制在1到50之间！");
             return false;
             }
			if (document.form1.password.value != document.form1.passwordd.value) {
				alert("密码与验证密码不一致！");
				return false;
			}
			return true;
		}
	</script>
</body>
</html>