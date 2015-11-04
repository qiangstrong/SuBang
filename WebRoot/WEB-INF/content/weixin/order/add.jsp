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
<title>下单</title>
<link href="css/weixin/ccsjsp/order/index/order.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/order/index/base.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/order/index/index.css" rel="stylesheet"
	type="text/css">
<!--  <link rel="stylesheet" type="text/css" href="css/style.css"/>  -->
<script type="text/javascript" src="js/weixin/user.js"></script>
<script type="text/javascript" src="js/weixin/jquery-1.7.1.min.js"></script>
<script>
		function updateTime(data){//data是前台页面传回的数据，ajax异步请求，选择一个日期后，便向后台发送jason串，然后slect根据后台返回更改日期时间选项
			var objectTime=document.getElementById('time');//time是指html中控件的id， 
			objectTime.length=0;
			for(var i=0;i<data.length;i++){
				objectTime.add(new Option(data[i].text,data[i].value));//更新time控件数据状态
			}			 
		}		
	</script>
</head>
<body class="Bg-gray" style="zoom: 1;">
	<div>
		<div class="tip_bar">
			<span class="color_8a8a8a">预约成功后请等待速帮上门取件<em
				class="priceColor">${category.name}</em> </span>
		</div>

		<form:form id="oderform" modelAttribute="order"
			action="weixin/order/add.html" method="post">
			<input type="hidden" name="categoryid" id="categoryid"
				value="${order.categoryid}">

			<div class="wrap_index">
				<!-- 有常用地址 -->
				<div class="add_bg"></div>
				<section class="section" id="select_address">
					<div class="wrap_filter" style="display:none"></div>
					<span class="arrow-left" style="top:44%"></span>
					<form:errors path="addrid" />
					<c:if test="${fn:length(addrDetails) == 0 }">
						<div class="address_icon icon_gray"></div>
						<a href="weixin/addr/showadd.html"><span class="m_input">
								添加地址</span> </a>
						<span class="arrow-left" style="top:44%"></span>
					</c:if>
					<c:if test="${fn:length(addrDetails) != 0 }">
						<div class="selectBox  selectcont y_hover">
							<select class="select01 range J_shippingDateSelect select_J_ytag"
								id="addrid" name="addrid"
								onfocus="this.style.color=&#39;#3e3e3e&#39;;">
								<!--弹出  -->
								<c:forEach var="addrDetail" items="${addrDetails}">
									<option value="${addrDetail.id }"
										<c:if test="${addrDetail.id==defaultAddrid }">selected="selected"</c:if>>
										${addrDetail}</option>
								</c:forEach>
							</select>
							<div class="Selected  selectBx" id="washing_time_div">请选择详细地址</div>
							<span class="arrow-left" style="top:36%"></span>
						</div>
					</c:if>
					<div class="borderD2" style="padding-top:9px;margin-left:10px"></div>
				</section>

				<section>
					<textarea class="textarea" id="addridtxt" name="addridtxt"
						readonly="readonly" onfocus="this.style.color=&#39;#3e3e3e&#39;;"
						placeholder="选择后在这里显示详细地址"></textarea>
				</section>

				<script type="text/javascript">
                        document.getElementById("addrid").onchange=function(){
                        document.getElementById("addridtxt").value=this.options[this.options.selectedIndex].text;
                        }
                        </script>




				<!--  <div class="wrap_index">-->
				<!-- 服务日期 start -->
				<div class="borderD2"></div>
				<section class="section">
					<div class="date_icon icon_gray"></div>
					<div id="J_shipDateTemplateWrapper">
						<div class="m_input J_shipTimeContainer">
							<div class="selectBox  selectcont y_hover">

								<!-- 弹出的时间选择窗口 -->
								<select
									class="select01 datepicker J_shippingDateSelect select_J_ytag"
									id="date" name="date"
									onchange="getData('date','weixin/order/select.html',updateTime)"
									onfocus="this.style.color=&#39;#3e3e3e&#39;;"
									style="color: rgb(62, 62, 62);">

									<c:forEach var="date" items="${dates}">
										<option value="${date.value }">${date.text }</option>
									</c:forEach>
								</select>

								<div class="Selected  selectBx" id="washing_date_div">
									请选择服务日期</div>
								<span class="arrow-left" style="top:36%"></span>
							</div>
							<div class="borderD2" style="padding-top:9px;margin-left:10px"></div>
						</div>
					</div>
				</section>
				<!-- 服务日期 end -->

				<!-- 服务时段 start -->
				<section class="section">
					<div class="time_icon icon_gray"></div>
					<div id="J_shipDateTemplateWrapper">
						<div class="m_input J_shipTimeContainer">
							<div class="selectBox  selectcont y_hover">
								<select
									class="select01 range J_shippingDateSelect select_J_ytag"
									id="time" name="time"
									onfocus="this.style.color=&#39;#3e3e3e&#39;;">
									<!--弹出  -->
									<c:forEach var="time" items="${times}">
										<option value="${time.value }">${time.text }</option>
									</c:forEach>
								</select>
								<div class="Selected  selectBx" id="washing_time_div">
									请选择服务时段</div>
								<span class="arrow-left" style="top:36%"></span>
							</div>
						</div>
					</div>
				</section>
				<!-- 服务时段 end -->



				<div class="add_bg"></div>
				<div class="p5"></div>


				<div class="borderD2"></div>
				<section>
				<span class="show_erro">
					<form:errors path="userComment" /><!-- 字符超过一百报错 -->
				</span>
					<textarea class="textarea" id="userComment" name="userComment"
						onfocus="this.style.color=&#39;#3e3e3e&#39;;"
						style="color: #c1c1c1;" placeholder="请填写备注（选填）">${order.userComment}</textarea>
				</section>
				<div class="borderD2"></div>
			</div>

			<!-- <div class="tuijian_coupon">
			<span class="quan">券</span><span class="quan_text">您暂无可用优惠券</span>
		</div> -->

			<!-- 立即预约 start -->
			<div class="order_bottom">

				<div class="delivery_fee">
					<ul>
						<li>订单金额不满 10 元收取 10 元运费</li>
					</ul>
				</div>

			</div>
			<input type="button" value="立即预约" class="btn_order Book"
				onclick="this.form.submit()">
		</form:form>
		<!-- 立即预约 end -->
		<div class="menu-service">
			<div class="service-list" align="center">
				<a href="weixin/info/serviceintro.html">
					<p class="ser-font">服务介绍</p> </a>
			</div>
			<!--  <div class="service-list" align="center">
				<a href="weixin/region/scope.html?cityid=${city.id}">
					<p class="ser-font">服务范围</p> </a>
			</div>
			-->
			<div class="service-list" align="center">
				<a href="weixin/price/index.html?categoryid=${category.id}">
					<p class="ser-font">价目指引</p> </a>
			</div>
			<div class="clearBoth"></div>
			<!-- 页底：功能按钮 end -->
		</div>
	</div>
</body>
</html>