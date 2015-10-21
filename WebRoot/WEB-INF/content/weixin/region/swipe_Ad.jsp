<!-- 轮转广告 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta charset="utf-8">
  <meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
  <meta content="yes" name="apple-mobile-web-app-capable">
  <meta content="black" name="apple-mobile-web-app-status-bar-style">
  <meta content="telephone=no" name="format-detection">
  <meta content="email=no" name="format-detection">
  <meta content="" name="pgv">
  <title>速帮</title>
  <link rel="stylesheet" type="text/css" href="css/weixin/ccsjsp/region/index/swipe.css">
 </head>

  
  <body>
   <!-- 头部：轮转广告 start-->  
  <div class="addWrap">
  <div class="swipe" id="mySwipe">
    <div class="swipe-wrap">
      <div><a href="javascript:;"><img class="img-responsive" src="css/weixin/ccsjsp/region/index/710x240.png"/></a></div>
      <div><a href="javascript:;"><img class="img-responsive" src="css/weixin/ccsjsp/region/index/710x240.png"/></a></div>
      <div><a href="javascript:;"><img class="img-responsive" src="css/weixin/ccsjsp/region/index/710x240.png"/></a></div>
    </div>
  </div>
  <ul id="position">
    <li class="cur"></li>
    <li class=""></li>
    <li class=""></li>
  </ul>
</div>
  <!-- 头部：轮转广告 end-->
<script src="css/weixin/ccsjsp/region/index/swipe.js"></script> 
<script type="text/javascript">
var bullets = document.getElementById('position').getElementsByTagName('li');
var banner = Swipe(document.getElementById('mySwipe'), {
	auto: 2000,
	continuous: true,
	disableScroll:false,
	callback: function(pos) {
		var i = bullets.length;
		while (i--) {
		  bullets[i].className = ' ';
		}
		bullets[pos].className = 'cur';
	}
});
</script>
  </body>
</html>
