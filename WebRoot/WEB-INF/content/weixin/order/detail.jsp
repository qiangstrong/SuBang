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
<title>订单详情</title>
<link href="css/weixin/ccsjsp/order/index/base.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/order/index/wap.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/order/index/myOrder.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/order/index/index.css" rel="stylesheet"
	type="text/css">
<link href="css/weixin/ccsjsp/order/index/item.css" rel="stylesheet"
	type="text/css">
<!-- <script src="css/weixin/ccsjsp/order/index/hm.js"></script><script type="text/javascript" src="css/weixin/ccsjsp/order/index/jquery-1.8.2.min.js"></script>-->
</head>
<body style="padding-bottom:80px">
	<div class="myOrder_list order-detail-con">
		<p class="m15"></p>
		<ul>
			<!-- 订单状态 start -->
			<li class="order_item order_detail ">
				<div class="item_list_box item_list_title">
					<img src="css/weixin/ccsjsp/order/index/order_status.png">
					订单状态<b
						style="color:#097de7;font-weight:normal;float:right;margin-right:14px">${orderDetail.stateDes}
						</b>
				</div>
				<div class="borderD" style="margin-top:4px;margin-left:12px"></div>
				<div class="order-logictics">
					<div class="split-position2"></div>
					<span class="high-dottle"></span> <a style="color:#097de7">
						<!-- 设置一个到底部历史的锚点 <img
						src="css/weixin/ccsjsp/order/index/arrow-2.png"
						class="position-img"> -->
						<div class="con-text blue-color" style="padding-top:8px;">
							${historys.get(historys.size()-1).operationDes}
							<div style="color:#555;">${historys.get(historys.size()-1).time}
							</div>
							<!-- 读取最后一条清洗流程记录 -->
						</div> </a>
				</div>
				<div class="borderD2"></div>
				<div class="clearBoth"></div>
			</li>
			<!-- 订单状态 end -->

			<!-- 物品信息 start -->
			<c:if test="${orderDetail.state>=3&&orderDetail.state<=6}">
				<!--在已取走、已分拣、已返还、已评价中显示  -->
				<li class="order_item">
					<div class="item_list_box item_list_title">
						<img src="css/weixin/ccsjsp/order/index/order_cloth.png">
						物品信息
					</div>
					<div class="borderD" style="margin-top:4px;margin-left:4px"></div>
					<div class="item_list_box item_list_con">
						<div class="p5">
							清洗件数 <span class="pull-right"> ${clothess.size()}件</span>
						</div>
					</div>
					<table cellpadding="0" cellspacing="0" class="detail-text">
						<tbody>
							<tr>
								<th width="30%">名称</th>
								<th width="20%">颜色</th>
								<th>瑕疵</th>
							</tr>


							
							<c:forEach var="clothes" items="${clothess}">
								<tr>
									<td>${clothes.name}</td>
									<td>${clothes.color}</td>
									<td style="text-align:left">${clothes.flaw}</td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
					<div class="borderD2"></div>
				</li>
			</c:if>
			<!-- 衣物信息 end -->

			<!-- 支付信息 start -->
			<c:if test="${orderDetail.state>=2&&orderDetail.state<=6}">
				<!--在已支付、已取走、已分拣、已返还、已评价中显示  -->
				<li class="order_item ">
					<div class="item_list_box  ">
						<div class="item_list_title">
							<img src="css/weixin/ccsjsp/order/index/order_pay.png">
							支付信息
						</div>
						<div class="borderD" style="margin-top:4px;margin-left:12px"></div>
					</div>
					<div class="item_list_box item_list_con">
						<div class="p5">
							支付方式 <span class="pull-right">${orderDetail.payTypeDes}</span>
						</div>
						<div class="borderD"></div>
						<div class="p5">
							订单￥${orderDetail.money}+运费￥${orderDetail.freight}-优惠券￥${orderDetail.moneyTicket}
							<div>实付款：￥${orderDetail.money+orderDetail.freight-orderDetail.moneyTicket}</div>
						</div>
					</div>
					<div class="borderD2"></div>
				</li>
			</c:if>
			<!-- 支付信息 end -->

			<!-- 地址信息 start -->
			<li class="order_item ">
				<div class="item_list_box">
					<div class="item_list_title">
						<img src="css/weixin/ccsjsp/order/index/order_address2.png">
						地址信息
					</div>
					<div class="borderD" style="margin-top:4px;margin-left:14px"></div>
				</div>
				<div class="item_list_box item_list_con p5">
					<b class="blackFont">姓名：${orderDetail.addrname} </b> <span
						class="pull-right"><b class="blackFont">${orderDetail.addrcellnum}</b>
					</span>
					<div>${orderDetail.addrdetail}</div>
				</div>
				<div class="borderD2"></div>
			</li>
			<!-- 地址信息 end -->

			<!-- 订单信息 start -->
			<li class="order_item ">
				<div class="item_list_box">
					<div class="item_list_title">
						<img src="css/weixin/ccsjsp/order/index/order_detail2.png">
						订单信息
					</div>
					<div class="borderD" style="margin-top:4px;margin-left:14px"></div>
				</div>
				<div class="item_list_box item_list_con p5">
					<div class="item_list_box">
						订单编号：<b class="blackFont"> ${orderDetail.orderno} </b>
					</div>
					<div class="item_list_box">取件时间：${orderDetail.dateDes}
						${orderDetail.timeDes}</div>
					<div class="item_list_box">服务项目：${orderDetail.categoryname}</div>
					<div class="item_list_box">备注信息：${orderDetail.userComment}</div>
				</div>
				<div class="borderD2"></div>
			</li>
			<!-- 订单信息 end -->


			<!-- 取消订单/催单等 按钮 start -->
			<c:if test="${orderDetail.state==0}">
				<div class="white_bg cancel_detail">
					<!-- 已接受 -->
					<a href="weixin/region/ .html" id="order_link"
						class="public_order borderF">取消订单</a> <a
						href="weixin/region/ .html" id="complain_btn"
						class="public_order borderB">催单</a>
					<div class="clearBoth"></div>
				</div>
			</c:if>

			<c:if test="${orderDetail.state==1}">
				<!-- 已计价 -->
				<div class="white_bg cancel_detail">
					<a href="weixin/region/ .html" id="order_link"
						class="public_order borderF">取消订单</a> <a
						href="weixin/region/ .html" id="complain_btn"
						class="public_order borderB">支付</a>
					<div class="clearBoth"></div>
				</div>
			</c:if>
			<c:if test="${orderDetail.state==4}">
				<!-- 已分拣 -->
				<div class="white_bg cancel_detail">
					<a href="weixin/region/ .html " id="complain_btn"
						class="public_order borderB">送达</a>
					<div class="clearBoth"></div>
				</div>
			</c:if>
			<c:if test="${orderDetail.state==5}">
				<!-- 已返还 -->
				<div class="white_bg cancel_detail">
					<a href="weixin/region/ .html" id="complain_btn"
						class="public_order borderB">评价</a>
					<div class="clearBoth"></div>
				</div>
			</c:if>
			<!-- 取消订单/催单/分享领券/投诉 按钮 end -->
		</ul>
	</div>
	<ul>


		<div class="item_list_box p15" style="padding-top:0">物流信息</div>
		<li class="order_item">
			<div class="myOrder_list order-detail-con">
				<div class="order-logictics">
					<div class="split-position"></div>


					<c:forEach var="history" items="${historys}">
						<div>
							<span></span>
							<div class="con-text">
								<em>${history.operationDes}</em>
								<!-- 操作 -->
								<div>
									<small>${history.time}</small>
									<!-- 发生的时间 -->
								</div>
								<div class="borderD"></div>
							</div>
						</div>
					</c:forEach>

				</div>
			</div>
			<div class="borderD"></div>
		</li>
	</ul>
</body>
</html>