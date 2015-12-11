<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath%>">
	<title>订单详情</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript" src="js/back/set.js"></script>
	<script type="text/javascript" src="js/back/state.js"></script>
</head>
<body >
	<%@ include file="../common/header.jsp"%>
	<table align="center">	
		<tr>
			<td colspan="2" align="right"><a href="back/order/index/back.html">返回</a></td>
		</tr>
		<tr>
			<th colspan="2">订单信息</th>			
		</tr>
		<tr>
			<td>订单号:</td>
			<td>${orderDetail.orderno}</td>
		</tr>
		<tr>
			<td>类别:</td>
			<td>${orderDetail.categoryname}</td>
		</tr>
		<tr>
			<td>状态:</td>
			<td>${orderDetail.stateDes}</td>
		</tr>
		<tr>
			<td>取件时间:</td>
			<td>${orderDetail.dateDes} ${orderDetail.timeDes}</td>
		</tr>
		<tr>
			<td>条形码:</td>
			<td>${orderDetail.barcodeDes}</td>
		</tr>
		<tr>
			<th colspan="2">用户信息</th>			
		</tr>
		<tr>
			<td>手机号:</td>
			<td>${orderDetail.cellnum}</td>
		</tr>
		<tr>
			<td>订单备注:</td>
			<td>${orderDetail.userComment}</td>
		</tr>
		<tr>
			<td>订单评价:</td>
			<td>${orderDetail.remark}</td>
		</tr>
		<tr>
			<th colspan="2">地址信息</th>			
		</tr>
		<tr>
			<td>姓名:</td>
			<td>${orderDetail.addrname}</td>
		</tr>
		<tr>
			<td>手机号:</td>
			<td>${orderDetail.addrcellnum}</td>
		</tr>
		<tr>
			<td>区域:</td>
			<td>${orderDetail.cityname} ${orderDetail.districtname} ${orderDetail.regionname}</td>
		</tr>
		<tr>
			<td>地址:</td>
			<td>${orderDetail.addrdetail}</td>
		</tr>
		<tr>
			<th colspan="2">取衣员信息</th>			
		</tr>
		<tr>
			<td>姓名:</td>
			<td>${orderDetail.workername}</td>
		</tr>
		<tr>
			<td>手机号:</td>
			<td>${orderDetail.workercellnum}</td>
		</tr>
		<tr>
			<td>订单备注:</td>
			<td>${orderDetail.workerComment}</td>
		</tr>
		<tr>
			<th colspan="2">支付信息</th>			
		</tr>
		<tr>
			<td>方式:</td>
			<td>${orderDetail.payTypeDes}</td>
		</tr>
		<tr>
			<td>描述:</td>
			<td>${orderDetail.paymentDes}</td>
		</tr>
		<tr>
			<td>实付:</td>
			<td>${orderDetail.actualMoneyDes}元</td>
		</tr>
	</table>
</body>
</html>