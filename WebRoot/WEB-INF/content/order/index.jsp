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
	<title>订单管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript" src="javascript/set.js"></script>
	<script type="text/javascript" src="javascript/state.js"></script>
	<script>
		function deleteOrders() {
			var orderids = getCheckedIds("orderid");
			if (orderids) {
				var url = "order/delete.html";
				submit("orderids", url, orderids);
			}
		}
		function finishOrders() {
			var orderids = getCheckedIds("orderid");
			if (orderids) {
				var url = "order/finish.html";
				submit("orderids", url, orderids);
			}
		}
		function cancelOrders() {
			var orderids = getCheckedIds("orderid");
			if (orderids) {
				var url = "order/cancel.html";
				submit("orderids", url, orderids);
			}
		}
		function restore(){
			<c:if test="${pageState!=null}">
				restoreSearchState('${pageState.searchArg.type}','${pageState.searchArg.arg}');
			</c:if>
		}
	</script>
</head>
<body onload="restore()">
	<c:if test="${errMsg!=null}">
		<script>
			alert('${errMsg}');
		</script>
	</c:if>
	<%@ include file="../common/header.jsp"%>
	<table align="center">
		<tr>
			<td align="right" colspan="2">
				<a href="order/search.html?type=2&arg=0">已接受</a>
				<a href="order/search.html?type=2&arg=1">已取走</a>
				<a href="order/search.html?type=2&arg=2">已完成</a>
				<a href="order/search.html?type=2&arg=3">已取消</a>
				<a href="order/list.html">所有订单</a>				
			</td>
		</tr>
		<tr>
			<td>
				<form name="searchArg" action="order/search.html" method="post">
					类别：
					<select id="type" name="type" >
						<option value="4" selected="selected">订单号</option>
						<option value="5">用户昵称</option>
						<option value="6">用户手机号</option>
						<option value="7">商家名称</option>
					</select>
					关键词：
					<input id="arg" type="text" name="arg" />
					<input type="submit" value="确定" />
				</form>
			</td>
			<td align="right">
				<input type="button" value="结束" onclick="finishOrders()"/>
				<input type="button" value="取消" onclick="cancelOrders()"/>
				<input type="button" value="删除" onclick="deleteOrders()"/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<c:if test="${infoMsg!=null}">
					${infoMsg}
				</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="2">
			<table border="1">
					<tr>
						<th><input type="checkbox"
							onclick="switchCheckboxs('orderid')" /></th>
						<th>订单号</th>
						<th>类别</th>
						<th>状态</th>
						<th>价格</th>
						<th>取件日期</th>
						<th>取件时间</th>
						<th>备注</th>
						<th>用户昵称</th>
						<th>地址姓名</th>
						<th>地址手机号</th>
						<th>城市名称</th>
						<th>区名称</th>
						<th>小区名称</th>
						<th>详细地址</th>
						<th>工作人员姓名</th>
						<th>工作人员手机号</th>
						<th>历史</th>
						<th>修改</th>						
					</tr>
					<c:forEach var="orderDetail" items="${orderDetails}">
						<tr>
							<td><input type="checkbox" name="orderid" value="${orderDetail.id}" /></td>
							<td>${orderDetail.orderno}</td>
							<td>${orderDetail.categoryDes}</td>
							<td>${orderDetail.stateDes}</td>
							<td>${orderDetail.price}</td>
							<td>${orderDetail.date}</td>
							<td>${orderDetail.timeDes}</td>
							<td>${orderDetail.comment}</td>
							<td>${orderDetail.nickname}</td>
							<td>${orderDetail.addrname}</td>
							<td>${orderDetail.addrcellnum}</td>
							<td>${orderDetail.cityname}</td>
							<td>${orderDetail.districtname}</td>
							<td>${orderDetail.regionname}</td>
							<td>${orderDetail.addrdetail}</td>
							<td>${orderDetail.workername}</td>
							<td>${orderDetail.workercellnum}</td>
							<td>
							 	<a href="<c:url value="/order/history.html?orderid=${orderDetail.id}"/>">历史</a>
							</td>
							<td>
							 	<a href="<c:url value="/order/showmodify.html?orderid=${orderDetail.id}"/>">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>