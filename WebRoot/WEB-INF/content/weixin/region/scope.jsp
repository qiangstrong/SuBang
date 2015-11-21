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
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta content="" name="pgv">
<title>${city.name}的服务范围</title>
<meta content="authenticity_token" name="csrf-param">
<meta content="NIXNSCrJ3Z4FBC6IQ/exFdWxkt0i6cHS8bDnuc7m+eI=" name="csrf-token">
<!--  <script src="css/weixin/ccsjsp/region/scope/application-4cf7f57c679623180c7288d7aec39ce7.js"></script>-->
<link href="css/weixin/ccsjsp/region/scope/mobile1.css" media="screen" rel="stylesheet">
<!--<link href="http://cdnwww.edaixi.com/assets/mobile-7aa04e852ce01cd353054af7a1b95cee.css" media="screen" rel="stylesheet">-->
<script type="text/javascript">window.onload = function(){
  if(isWeiXin()){
    var orderbtn = document.getElementById('orderBtn');
    orderbtn.style.display = "block";


  }else{
    var orderbtn = document.getElementById('orderBtn');

    orderbtn.style.display = "none";
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

<body style="background:#f8f8f8">
	<div id="container">
		<div id="content">
			<div class="map" id="beijing_map">
				<div class="area_list">${city.scopeText}</div>
				<img
					alt="由于网络原因地图无法显示" class="img-city" src="${city.scope}">
			</div>
		</div>
	</div>
</body>

</html>