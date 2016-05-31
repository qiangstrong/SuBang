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
<title>积分商城</title>
<link href="css/weixin/ccsjsp/activity/list-page-v11.19.css"
	rel="stylesheet" type="text/css">
<link href="css/weixin/ccsjsp/activity/normalize.min.css"
	rel="stylesheet" type="text/css">
<style>
.theme-color {
	color: #11c3bc;
}

.theme-bgcolor {
	background-color: #11c3bc;
}

.theme-bordercolor {
	border-color: #11c3bc;
}
/* radio */
.radio-group.cur {
	border-color: #11c3bc;
}

.radio-group .cur-arrow {
	border-color: #11c3bc transparent transparent #11c3bc;
}

.description a {
	color: #11c3bc;
}
</style>
</head>
<body style="padding-bottom: 68px; zoom: 1;">
	<section id="main">
		<div class="recommend single-row">
			<c:forEach var="TicketType" items="${ticketTypes}">

				<div class="item">
				 <a href="weixin/activity/detail.html?tickettypeid=${TicketType.id}"><img src="${TicketType.icon}"></a>
					 <a href="weixin/activity/detail.html?tickettypeid=${TicketType.id}">
					<div class="item-info">
						<h3>${TicketType.name}</h3>
						<h3>金额：${TicketType.money}</h3>
						<h3>积分：${TicketType.score}</h3>
						<h3>截止期：${TicketType.deadlineDes}</h3>
						<!-- <p class="theme-color">1000积分</p> -->
					</div>
					</a>
					
				
				<div class="tag" style="border-bottom-color:;">
					<span></span>
				</div>
				<button class="theme-color theme-bordercolor"><a href="weixin/ticket/add.html?tickettypeid=${TicketType.id}">兑换</a></button>
                   
                </div>
			</c:forEach>
		</div>
	</section>




</body>
</html>