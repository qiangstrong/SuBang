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
<title>城市列表</title>
<link href="css/weixin/ccsjsp/region/index/index.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/common/menu/footer.css" rel="stylesheet"
	type="text/css">

</head>
<body style="padding-bottom: 40px; zoom: 1;">
	<div class="city_list_block">
		<div class="city-title">请您选择所在城市</div>
		<ul class="city_list white-bg">
			<c:forEach var="city" items="${citys}">
				<li><a city_id="${city.id}" href="weixin/region/index.html?cityid=${city.id}"> ${city.name} </a>
				</li>
				<div class="borderD"></div>
			</c:forEach>
		</ul>
	</div>
</body>
</html>

