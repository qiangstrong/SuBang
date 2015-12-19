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
<link rel="stylesheet" type="text/css"
	href="/css/weixin/ccsjsp/addr/style1.3.8.5.css">
<meta charset="utf-8">
<meta
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no"
	name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta content="" name="pgv">
<title>添加地址</title>
<base href="<%=basePath%>">
<link href="/css/weixin/ccsjsp/addr/base.css" rel="stylesheet"
	type="text/css">
<link href="/css/weixin/ccsjsp/addr/order.css" rel="stylesheet"
	type="text/css">
<script src="/css/weixin/ccsjsp/addr/hm.js"></script>
<script type="text/javascript" src="js/weixin/user.js"></script>
<!-- <script type="text/javascript" src="/css/weixin/ccsjsp/addr/jquery-1.7.2.min.js"></script> -->
<script type="text/javascript" src="js/weixin/jquery-1.7.1.min.js"></script>
<script>
	function updateDistrict(data) {
		var dataDistrict = data[0];
		var objectDistrict = document.getElementById('districtid');
		objectDistrict.length = 0;
		for ( var i = 0; i < dataDistrict.length; i++) {
			objectDistrict.add(new Option(dataDistrict[i].name,
					dataDistrict[i].id));
		}

		var dataRegion = data[1];
		var objectRegion = document.getElementById('regionid');
		objectRegion.length = 0;
		for ( var i = 0; i < dataRegion.length; i++) {
			objectRegion.add(new Option(dataRegion[i].name, dataRegion[i].id));
		}
	}
	function updateRegion(data) {
		var object = document.getElementById('regionid');
		object.length = 0;
		for ( var i = 0; i < data.length; i++) {
			object.add(new Option(data[i].name, data[i].id));
		}
	}
</script>
</head>

<body style="zoom: 1;">
	<div id="mainWin" style="padding: 0">
		<div class="space-split10"></div>
		<form:form modelAttribute="addr" action="weixin/addr/add.html"
			method="post">

			<!-- 地址信息 start -->
			<div class="white-bg">
				<div class="m_input select y_hover" id="select_wrap">
					<div id="cmbArea_wrap" class="select y_hover" style="width: 85%;">
						<select class="city_select" id="cityid" name="cityid"
							style="font-size:14px;"  onchange="getData('cityid','weixin/addr/select.html',updateDistrict)">
							<c:forEach var="city" items="${citys}">
								<option value="${city.id}"
									<c:if test="${city.id==defaultCityid }">selected="selected"</c:if> >
									${city.name}</option>
							</c:forEach>
						</select>
					</div>
					<img src="/css/weixin/ccsjsp/addr/order_arrow.png" class="order_arrow" style="margin-top: 17px; margin-right: 25px;">
				</div>

				<div class="borderD2"></div>
				<div class="m_input select y_hover" id="select_wrap">
					<div id="cmbArea_wrap" class="select y_hover" style="width: 85%;">
						<select class="J_area select_J_ytag" id="districtid"
							name="districtid" style="font-size:14px;"
							onchange="getData('districtid','weixin/addr/select.html',updateRegion)">
							<c:forEach var="district" items="${districts}">
								<option value="${district.id }"
									<c:if test="${district.id==defaultDistrictid }">selected="selected"</c:if>>
									${district.name }</option>
							</c:forEach>
						</select>
					</div>
					<img src="/css/weixin/ccsjsp/addr/order_arrow.png" class="order_arrow" style="margin-top: 17px; margin-right: 25px;">
				</div>
				<div class="borderD2"></div>
				<div class="m_input select y_hover" id="select_wrap">
					<div id="cmbArea_wrap" class="select y_hover" style="width: 85%;">
						<select class="J_area select_J_ytag" id="regionid" name="regionid" style="font-size:14px;">
							<c:forEach var="region" items="${regions}">
								<option value="${region.id }"
									<c:if test="${region.id==defaultRegionid }">selected="selected"</c:if>>
									${region.name }</option>
							</c:forEach>
						</select>
					</div>
					<img src="/css/weixin/ccsjsp/addr/order_arrow.png" class="order_arrow" style="margin-top: 17px; margin-right: 25px;">
					<div class="clearBoth"></div>
				</div>
				<div class="borderD"></div>
				<div class="m_input m_input_5" style="margin-bottom: 0">
					<div class="input" style="margin-top: 10px">
						<span style="color:#8a8a8a"><form:errors path="detail" />
							<!-- 长度需要在1和50之间 -->
						</span>
						<input placeholder="请输入门牌号等详细地址" id="detail" name="detail"
							class="textarea-address">
						<!-- <textarea placeholder="请输入门牌号等详细地址" id="detail" name="detail"
							class="textarea-address"> </textarea> -->
					</div>
				</div>
			</div>
			<div class="borderD2"></div>
			<!-- 地址信息 end -->

			<div style="height: 10px; background: #f0f0f0"></div>

			<!-- 个人基本信息 start -->
			<div class="borderD2"></div>
			<section class="section_4" style="padding: 1px">
				<div class="m_input m_input_5">
					<div class="input" style="margin-top: 10px">
						<span style="color:#8a8a8a"><form:errors path="name" />
							<!-- 1到4 -->
						</span> <input placeholder="您的姓名" value="${addr.name}" maxlength="30"
							id="name" name="name" class="address_input">
					</div>
				</div>
				<div class="borderD2"></div>
				<div class="m_input m_input_5">
					<div class="input" style="margin-top: 10px">
						<span style="color:#8a8a8a"><form:errors path="cellnum" />
							<!-- 11位 -->
						</span> <input placeholder="手机号码" value="${addr.cellnum}" id="cellnum"
							name="cellnum" type="tel" maxlength="11" name="cellnum"
							class="cleartet need_clear">
					</div>
				</div>
			</section>
			<!-- 个人基本信息 end -->

			<!-- 保存 start -->
			<sectoin class="true-btn">
			<div class="">
				<input type="submit" class="btn_order y_hover J_ytag" value="保存">
			</div>
			</sectoin>
			<!-- 保存 end -->
		</form:form>
	</div>

</body>
</html>