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
<title>个人中心</title>
<link rel="stylesheet" type="text/css"
	href="css/weixin/ccsjsp/user/index/card.css" media="all">
<!-- <script src="css/weixin/ccsjsp/user/index/hm.js"></script><script type="text/javascript" src="css/weixin/ccsjsp/user/index/jquery-1.8.2.min.js"></script> -->
</head>

<body id="card" ondragstart="return false;"
	onselectstart="return false;" class="white_bg"
	style="padding-bottom: 54px; zoom: 1;">
	<div class="card" id="icard">
		<img src="css/weixin/ccsjsp/user/index/center_banner.png"
			class="card-bg-card">
		 <img src="css/weixin/ccsjsp/user/index/avatar.png" class="subang_logo">
		<div class="loginInfo">
			${user.cellnum}
			<a href="weixin/balance/parapay.html" class="link-chongzhi" style="padding:5px 0;"> 充值 <img
				alt=" " src="css/weixin/ccsjsp/user/index/chongzhi_jt.png"> </a>
			<p>我们只做最专业的品质服务</p>
		</div>
	</div>
	<nav class="main-nav white-bg">
		<div class="subnav" style="border-left:1px solid #eee;">
			<a href="content/weixin/user/scoreintro.htm">
			<!--  <a href="javascript:void(0)"> -->
				<div class="icon-block">
					<img alt=" " src="css/weixin/ccsjsp/user/index/mine_score_icon.png">积分
				</div>
				<div class="yue_font">${user.score}分</div> </a>
		</div>
		<div class="subnav">
		<a href="weixin/balance/index.html">
			<!-- <a href="javascript:void(0)"> -->
				<div class="icon-block">
					<img alt=" " src="css/weixin/ccsjsp/user/index/mine_money_icon.png">余额
				</div>
				<div class="yue_font">${user.money}元</div> </a>
			
		</div>
	</nav>
	<div class="clear"></div>
	<div class="borderD2"></div>
	<div style="height:14px"></div>
	<div class="cardexplain">
		<ul class="round operate navlist">
			<li class="mobile-font">
				<table class="icardTable">
					<tbody>
						<tr>
							<td style="border:0;width:42px;"><img alt=""
								src="css/weixin/ccsjsp/user/index/changyong_add.png">
							</td>
							<td><a href="weixin/addr/index.html">
									<div class="list-titel no-img">
										常用地址 <img alt=""
											src="css/weixin/ccsjsp/user/index/rignt_icon.png"
											class="icon-right-card">
									</div> </a>
								<div class="borderD"></div>
							</td>
						</tr>
						<tr>
							<td style="border:0;width:42px;"><img alt=""
								src="css/weixin/ccsjsp/user/index/mine_ticket_icon.png">
							</td>
							<td>
							<a href="javascript:void(0)">
						    <a href="weixin/ticket/index.html">
									<div class="list-titel no-img">
										优惠券<img alt=""
											src="css/weixin/ccsjsp/user/index/rignt_icon.png"
											class="icon-right-card">
									</div> </a> 
								<div class="borderD" style=" margin-bottom: -4px;"></div>
							</td>
						</tr>
						<tr>
							<td style="border:0;width:42px;"><img alt=""
								src="css/weixin/ccsjsp/user/index/jifen_shc.png">
							</td>
							<td style="position:relative">
							<a href="weixin/activity/index.html">
								
									<div class="list-titel no-img">
										积分商城 <img alt=""
											src="css/weixin/ccsjsp/user/index/rignt_icon.png"
											class="icon-right-card">
									</div> </a> 
									<a class="my_jifen">
									<div>
										<img alt="" src="css/weixin/ccsjsp/user/index/jifen_icon2.png">
										我的积分:${user.score}
									</div> </a>
								<div class="borderD" style=" margin-bottom: -4px;"></div>
							</td>
						</tr>

					</tbody>
				</table></li>
			<div class="borderD2"></div>
		</ul>
	</div>
	<div class="cardexplain">
		<ul class="round operate navlist"
			style="border-radius:0 0 10px 10px;border-top:0">
			<li class="mobile-font">
				<table class="icardTable">
					<tbody>
						<tr>
							<td style="border:0;width:42px;"><img alt=" "
								src="css/weixin/ccsjsp/user/index/yijian_fank.png">
							</td>
							<td>
							<!-- <a href="javascript:void(0)">-->
							<a href="weixin/info/showaddfeedback.html">
									<div class="list-titel no-img">
										意见反馈 <img alt=""
											src="css/weixin/ccsjsp/user/index/rignt_icon.png"
											class="icon-right-card">
									</div> </a>
								<div class="borderD"></div>
							</td>
						</tr>
						<tr>
							<td><img alt=""
								src="css/weixin/ccsjsp/user/index/question_icon2.png">
							</td>
							<td><a href="weixin/info/faq.html">
									<div class="list-titel no-img">
										常见问题 <img alt=""
											src="css/weixin/ccsjsp/user/index/rignt_icon.png"
											class="icon-right-card">
									</div> </a>
									<div class="borderD"></div>
							</td>
						</tr>
						<tr>
							<td><img alt=""
								src="css/weixin/ccsjsp/user/index/user_xieyi2.png">
							</td>
							<td><a href="/content/weixin/info/term.html">
									<div class="list-titel no-img">
										用户协议 <img alt=""
											src="css/weixin/ccsjsp/user/index/rignt_icon.png"
											class="icon-right-card">
									</div> </a>
									<div class="borderD"></div>
							</td>
						</tr>
						<tr>
							<td style="border:0;width:42px;"><img alt=""
								src="css/weixin/ccsjsp/user/index/welcome_phone.png">
							</td>
							<td><a href="weixin/user/showchgcellnum.html">
									<div class="list-titel no-img">
										修改注册手机号 <img alt=""
											src="css/weixin/ccsjsp/user/index/rignt_icon.png"
											class="icon-right-card">
									</div> </a>
								<div class="borderD"></div>
							</td>
						</tr>
						<tr>
							<td style="border:0;width:42px;"><img alt=""
								src="css/weixin/ccsjsp/user/index/welcome_pas.png">
							</td>
							<td><a href="weixin/user/showchgpassword.html">
									<div class="list-titel no-img">
										修改密码 <img alt=""
											src="css/weixin/ccsjsp/user/index/rignt_icon.png"
											class="icon-right-card">
									</div> </a>
								<div class="borderD"></div>
							</td>
						</tr>
						
					</tbody>
				</table>
				<div class="borderD2"></div></li>
			<div style="height:14px;background:#f0f0f0"></div>
			<li class="tel-text">
				<div><a href="tel:${phone}" style="color: #000; font-size: 18px;"><img alt=""
											src="css/weixin/ccsjsp/user/index/phone.png" style="height: 18px;"> 客服热线：  ${phone}</a></div></li>
		</ul>
		<div class="borderD2"></div>
	</div>
	<!-- ／我的订单 -->

	<!-- 页脚导航-->
	<%@ include file="../common/menu2.jsp"%>
</body>
</html>