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
<meta charset="utf-8">
<meta
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no"
	name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta content="" name="pgv">
<script src="/css/weixin/ccsjsp/info/hm.js"></script>
<script type="text/javascript"
	src="/css/weixin/ccsjsp/info/jquery-1.8.2.min.js"></script>
<link href="/css/weixin/ccsjsp/info/base.css" rel="stylesheet"
	type="text/css">
<link href="/css/weixin/ccsjsp/info/buy.css" rel="stylesheet"
	type="text/css">

<base href="<%=basePath%>">
<title>用户反馈</title>
<script language="javascript">
	var sArray = new Object;
	sArray[0] = new Image;
	sArray[0].src = "/css/weixin/ccsjsp/info/icon_star_1.png";
	for ( var i = 1; i < 6; i++) {
		sArray[i] = new Image();
		sArray[i].src = "/css/weixin/ccsjsp/info/icon_star_2.png";
	}
	var starTimer;
	var pro;
	var rate;
	function initStar() {
		try {
			setProfix("star_");
			setStars(document.getElementById("rating").value, 'rating');
			setProfix("quality_");
			setStars(document.getElementById("quality").value, 'quality');
			setProfix("software_");
			setStars(document.getElementById("software").value, 'software');
			setProfix("service_");
			setStars(document.getElementById("service").value,
					'service');
		} catch (e) {
		}
	}
	function showStars(starNum, rate) {
		try {
			clearStarTimer();
			greyStars();
			colorStars(starNum);
		} catch (e) {
		}
		//setStars(starNum,rate);
	}
	function setProfix(profix) {
		pro = profix;
	}
	function colorStars(starNum) {
		try {
			for ( var i = 1; i <= starNum; i++) {
				var tmpStar = document.getElementById(pro + i);
				tmpStar.src = sArray[starNum].src;
			}
		} catch (e) {
		}
	}
	function greyStars() {
		try {
			for ( var i = 1; i < 6; i++) {
				var tmpStar = document.getElementById(pro + i);
				tmpStar.src = sArray[0].src;
			}
		} catch (e) {
		}
	}
	function greyAll(curpro, currate) {
		try {
			document.getElementById(currate).value = "";
			for ( var i = 1; i < 6; i++) {
				var tmpStar = document.getElementById(curpro + i);
				tmpStar.src = sArray[0].src;
			}
		} catch (e) {
		}
	}
	function setStars(starNum, rate) {
		rate = rate;
		try {
			clearStarTimer();
			var rating = document.getElementById(rate);
			rating.value = starNum;
			showStars(starNum);
		} catch (e) {
		}
	}
	function clearStars(currate) {
		rate = currate;
		try {
			var rating = document.getElementById(rate);
			if (rating.value != '') {
				setStars(rating.value, rate);
			} else {
				greyStars();
			}
		} catch (e) {
		}
	}
	function resetStars() {
		try {
			clearStarTimer();
			var rating = document.getElementById(rate);
			if (rating.value != '') {
				setStars(rating.value, rate);
			} else {
				greyStars();
			}
		} catch (e) {
		}
	}
	function clearStarTimer() {
		if (starTimer) {
			clearTimeout(starTimer);
			starTimer = null;
		}
	}
</script>
</head>
<body style="zoom: 1;">
	<input id="rating" name="rating" value="4" type="hidden"> <input
			id="quality" name="quality" value="4" type="hidden"> <input
			id="software" name="software" value="4" type="hidden"> <input
			id="service" name="service" value="4" type="hidden">
	<div class="pay_font">
		<span>谢谢您的小星星！</span>
		<div class="clearBoth"></div>
	</div>
	<div class="pay_manner white_bg">
		<div class="item_list_box">
			整体评价<span class="pull-right orange_color"></span> <span class="Select">
				<a
				onmouseover="javascript:setProfix('star_');showStars(1,'rating');"
				onmouseout="javascript:setProfix('star_');clearStars('rating');"
				href="javascript:setProfix('star_');setStars(1,'rating');"><img
					id="star_1" title="差(1)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('star_');showStars(2,'rating');"
				onmouseout="javascript:setProfix('star_');clearStars('rating');"
				href="javascript:setProfix('star_');setStars(2,'rating');"><img
					id="star_2" title="一般(2)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('star_');showStars(3,'rating');"
				onmouseout="javascript:setProfix('star_');clearStars('rating');"
				href="javascript:setProfix('star_');setStars
				(3,'rating');"><img
					id="star_3" title="好(3)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('star_');showStars(4,'rating');"
				onmouseout="javascript:setProfix('star_');clearStars('rating');"
				href="javascript:setProfix('star_');setStars(4,'rating');"><img
					id="star_4" title="很好(4)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('star_');showStars(5,'rating');"
				onmouseout="javascript:setProfix('star_');clearStars('rating');"
				href="javascript:setProfix('star_');setStars(5,'rating');"><img
					id="star_5" title="非常好(5)"
					src="/css/weixin/ccsjsp/info/icon_star_1.png"> </a> </span>

		</div>
		</div>
			<div class="borderD"></div>
	<div class="clear"></div>
		<div class="pay_manner white_bg">
		<div class="item_list_box">
			洗衣质量<span class="pull-right orange_color"></span> <span class="Select">
				<a
				onmouseover="javascript:setProfix('quality_');showStars(1,'quality');"
				onmouseout="javascript:setProfix('quality_');clearStars('quality');"
				href="javascript:setProfix('quality_');setStars(1,'quality');"><img
					id="quality_1" title="差(1)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('quality_');showStars(2,'quality');"
				onmouseout="javascript:setProfix('quality_');clearStars('quality');"
				href="javascript:setProfix('quality_');setStars(2,'quality');"><img
					id="quality_2" title="一般(2)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('quality_');showStars(3,'quality');"
				onmouseout="javascript:setProfix('quality_');clearStars('quality');"
				href="javascript:setProfix('quality_');setStars(3,'quality');"><img
					id="quality_3" title="好(3)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('quality_');showStars(4,'quality');"
				onmouseout="javascript:setProfix('quality_');clearStars('quality');"
				href="javascript:setProfix('quality_');setStars(4,'quality');"><img
					id="quality_4" title="很好(4)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('quality_');showStars(5,'quality');"
				onmouseout="javascript:setProfix('quality_');clearStars('quality');"
				href="javascript:setProfix('quality_');setStars(5,'quality');"><img
					id="quality_5" title="非常好(5)"
					src="/css/weixin/ccsjsp/info/icon_star_1.png"> </a> </span>
		</div>
		<div class="item_list_box">
			服务态度<span class="pull-right orange_color"></span> <span class="Select">
				<a
				onmouseover="javascript:setProfix('service_');showStars(1,'service');"
				onmouseout="javascript:setProfix('service_');clearStars('service');"
				href="javascript:setProfix('service_');setStars(1,'service');"><img
					id="service_1" title="差(1)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('service_');showStars(2,'service');"
				onmouseout="javascript:setProfix('service_');clearStars('service');"
				href="javascript:setProfix('service_');setStars(2,'service');"><img
					id="service_2" title="一般(2)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('service_');showStars(3,'service');"
				onmouseout="javascript:setProfix('service_');clearStars('service');"
				href="javascript:setProfix('service_');setStars(3,'service');"><img
					id="service_3" title="好(3)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('service_');showStars(4,'service');"
				onmouseout="javascript:setProfix('service_');clearStars('service');"
				href="javascript:setProfix('service_');setStars(4,'service');"><img
					id="service_4" title="很好(4)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('service_');showStars(5,'service');"
				onmouseout="javascript:setProfix('service_');clearStars('service');"
				href="javascript:setProfix('service_');setStars(5,'service');"><img
					id="service_5" title="非常好(5)"
					src="/css/weixin/ccsjsp/info/icon_star_1.png"> </a> </span>
		</div>
		<div class="item_list_box">
			软件质量<span class="pull-right orange_color"></span> <span
				class="Select"> <a
				onmouseover="javascript:setProfix('software_');showStars(1,'software');"
				onmouseout="javascript:setProfix('software_');clearStars('software');"
				href="javascript:setProfix('software_');setStars(1,'software');"><img
					id="software_1" title="差(1)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('software_');showStars(2,'software');"
				onmouseout="javascript:setProfix('software_');clearStars('software');"
				href="javascript:setProfix('software_');setStars(2,'software');"><img
					id="software_2" title="一般(2)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('software_');showStars(3,'software');"
				onmouseout="javascript:setProfix('software_');clearStars('software');"
				href="javascript:setProfix('software_');setStars(3,'software');"><img
					id="software_3" title="好(3)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('software_');showStars(4,'software');"
				onmouseout="javascript:setProfix('software_');clearStars('software');"
				href="javascript:setProfix('software_');setStars(4,'software');"><img
					id="software_4" title="很好(4)"
					src="/css/weixin/ccsjsp/info/icon_star_2.png"> </a><a
				onmouseover="javascript:setProfix('software_');showStars(5,'software');"
				onmouseout="javascript:setProfix('software_');clearStars('software');"
				href="javascript:setProfix('software_');setStars(5,'software');"><img
					id="software_5" title="非常好(5)"
					src="/css/weixin/ccsjsp/info/icon_star_1.png"> </a> </span>
		</div>
	</div>
	<div class="borderD"></div>
	<div class="clear"></div>
	<section>
					<textarea class="textarea" id="userComment" name="userComment" 
						onfocus="this.style.color=&#39;#3e3e3e&#39;;"
						style="color: #c1c1c1; margin: 10px" placeholder="其他评价（选填）"></textarea>
	</section>



	
	<div class="borderD"></div>
	<div class="clear"></div>
	<div class="item_list_btn">
		<div class="chongzhi_btns" style="padding-right:0">

			<button type="submit" id="feedback" class="pay_btn link-btn-all"
				onclick="toFeedback();">提交</button>
		</div>
	</div>

	

	<script type="text/javascript">
		function toFeedback() {
			var form = document.createElement("form");

			var input = document.createElement("input");
			input.setAttribute("name", "comment");
			input.setAttribute("type", "hidden");
			var str = "整体评价：" + $("input[name='rating']").val()
					+ "。 洗衣质量：" + $("input[name='quality']").val()
					+ "。 服务态度：" + $("input[name='service']").val()
					+ "。 软件质量： " + $("input[name='software']").val() + "。 备注：" + $("#userComment").val();
			
			input.setAttribute("value", str);

			form.appendChild(input);
			form.method = "POST";
			form.action = "weixin/info/addfeedback.html";

			document.body.appendChild(form);
			form.submit();
		}
	</script>
</body>
</html>