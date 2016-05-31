<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
<meta content="telephone=no" name="format-detection">
<base href="<%=basePath%>">
<title>${ticketType.name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet" href="css/weixin/ccsjsp/activity/normalize.min.css?t=20151016">
<link rel="stylesheet" href="css/weixin/ccsjsp/activity/modal.min.css">
<link rel="stylesheet" href="css/weixin/ccsjsp/activity/detail.css?t=20151110">
<style>
.powered-logo {
	width: 27px;
}
</style>
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
<body style="zoom: 1;">
<div id="db-content" style="padding-bottom: 64px;"> 
  
   
  <div class="db-banner-style">
    <div class="db-banner-swipe" id="swipe" style="visibility: visible;">
      <div> 
       
        
        <a><img src="css/weixin/ccsjsp/activity/ticket_banner.png"></a> 
        
      
      </div>
    </div>
    <div class="db-banner-position"> <strong>1</strong><span>/1</span> </div>
  </div>
  <header>
    <div class="item-info">
      <div class="left">
        <h3>${ticketType.name}</h3>
        <p>抢兑中</p>
      </div>
      <div class="right">
        <div class="theme-color">
          <p>${ticketType.score}</p>
          <span>积分</span></div>
        <span>￥${ticketType.money}元</span> </div>
    </div>
    <div class="validDate"> <i class="arrow"></i><strong>截止期：</strong><span>${ticketType.deadlineDes}</span> </div>
  </header>
  <section>
    <p class="title"> <i class="arrow"></i><span>详情说明：</span> </p>
    <div class="description">
    ${ticketType.comment}
    </div>
  </section>
</div>

</body>
</html>