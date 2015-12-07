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
<title>地址选择</title>
<link href="css/weixin/ccsjsp/order/index/order.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/order/index/base.css" rel="stylesheet"
	type="text/css">
<body
	style="padding-bottom: 60px; background-color: rgb(240, 240, 240); zoom: 1; background-position: initial initial; background-repeat: initial initial;">
	<div id="all_address">
		<div data-role="content">
			<!-- 服务地址 start -->

			<table class="order-place-table swipe-delete" cellpadding="0"
				cellspacing="0">
				<tbody>
					<c:forEach var="addrDetail" items="${addrDetails}">
			
						<tr id="address_list_2478947" class="address_list">
						
						<td width="16%" class="white-bg"><span><img
									src="css/weixin/ccsjsp/order/index/order_detail_addr.png" style="height:25px">
							</span>
							</td>
							<!-- <a href="weixin/order/showadd.html?categoryid=${category.id}"> -->
						
							<td style="padding:0 0px">
								<a href="weixin/order/showadd.html?categoryid=${categoryid}&addrid=${addrDetail.id}">
								<div class="ui-btn ui-icon-carat-r" style="height: 62px">
											<div>
												<span ontouchstart="" class="bigf address_text">${addrDetail.name
													}</span> <span>${addrDetail.cellnum }</span>

											</div>
											<p ontouchstart="" class="address_text p_address" style="margin-top: 1px;">${addrDetail.cityname
													}  ${addrDetail.districtname }  ${addrDetail.regionname}</p>
											
											<p ontouchstart="" class="address_text p_address"
												style="margin-top: 1px; top: 52px;">
												${addrDetail.detail }</p>


											<img src="/css/weixin/ccsjsp/addr/order_arrow.png"
												class="order_arrow">
										</div> 
								
								</a>
								<div class="borderD"></div>
							</td>
												
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="borderD"></div>
			<!-- 服务地址 end -->
			<div style="height:10px;background:#f0f0f0"></div>
		</div>
	</div>

</body>
</html>