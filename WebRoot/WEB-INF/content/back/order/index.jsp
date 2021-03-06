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
	<script type="text/javascript" src="js/back/set.js"></script>
	<script type="text/javascript" src="js/back/state.js"></script>
	<script>
		function deleteOrders() {
			var orderids = getCheckedIds("orderid");
			if (orderids) {
				var url = "back/order/delete.html";
				submit("orderids", url, orderids);
			}
		}
		function cancelOrders() {
			var orderids = getCheckedIds("orderid");
			if (orderids) {
				var url = "back/order/cancel.html";
				submit("orderids", url, orderids);
			}
		}
		function restore(){
			<c:if test="${pageState!=null}">
				restoreOrderState('${pageState.searchArg.type}','${pageState.searchArg.arg}');
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
				<a href="back/order/curday.html">当日订单</a>	
				<a href="back/order/list.html">所有订单</a>								
			</td>
		</tr>
		<tr>
			<td>
				<form name="searchArg" action="back/order/search.html" method="post">
				<table>
					<tr>
					<td>
						类别：
						<select id="type" name="type" onchange="switchOrderState()">
							<option value="1">所有订单</option>
							<option value="2">订单状态</option>
							<option value="3">订单号</option>
							<option value="10">条码</option>
							<option value="5">用户手机号</option>
							<option value="6">商家名称</option>
						</select>
						关键词：
						<select id="arg0" name="arg" disabled="disabled">
							<option value="0" selected="selected">已接受</option>
							<option value="1">已计价</option>
							<option value="2">已支付</option>
							<option value="3">已取走</option>
							<option value="4">已洗衣</option>
							<option value="5">已送达</option>
							<option value="6">已评价</option>
							<option value="7">已取消</option>
						</select>
						<input id="arg1" name="arg" type="text"/>					
					</td>
					</tr>
					<tr>
					<td>
						起始日期：
						<input type="date" name="startTime" value="${pageState.searchArg.startTimeDes}"/>
						截止日期：
						<input type="date" name="endTime" value="${pageState.searchArg.endTimeDes}"/>							
						<input type="submit" value="确定" />
					</td>
					<tr>
				</table>
				</form>
			</td>
			<td align="right">
				<table>
					<tr><td>
						<input type="button" value="取消" onclick="cancelOrders()"/>
					</td></tr>
					<tr><td>
						<input type="button" value="删除" onclick="deleteOrders()"/>
					</td></tr>
				</table>
				
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<c:if test="${desMsg!=null}">
					${desMsg}
				</c:if>
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
				金额总计：${totalMoney }
			</td>
		</tr>
		<tr>
			<td colspan="2">
			<%!int count; %>
			<table border="1" cellpadding="5">
					<tr>
						<th><input type="checkbox" onclick="switchCheckboxs('orderid')" /></th>
						<th>编号</th>
						<th>订单号</th>
						<th>类别</th>
						<th>状态</th>		
						<th>金额</th>			
						<th>取件时间</th>						
						<th>条形码</th>
						<th>详情</th>
						<th>计价</th>
						<th>现金支付</th>	
						<th>余额支付</th>
						<th>取走</th>
						<th>送达</th>	
						<th>明细</th>					
						<th>历史</th>
						<th>修改</th>
					</tr>
					<%count=0;%>
					<c:forEach var="orderDetail" items="${orderDetails}">
						<tr>
							<td><input type="checkbox" name="orderid" value="${orderDetail.id}" /></td>
							<td><%=++count%></td>
							<td>${orderDetail.orderno}</td>
							<td>${orderDetail.categoryname}</td>
							<td>${orderDetail.stateDes}</td>
							<td>${orderDetail.totalMoneyDes}</td>
							<td>${orderDetail.dateDes} ${orderDetail.timeDes}</td>
							<td>${orderDetail.barcodeDes}</td>		
							<td>
							 	<a href="back/order/detail.html?orderid=${orderDetail.id}">详情</a>
							</td>	
							<td>
							 	<a href="back/order/showprice.html?orderid=${orderDetail.id}">计价</a>
							</td>
							<td>
							 	<a href="back/order/pay.html?orderid=${orderDetail.id}&payType=3">现金支付</a>
							</td>
							<td>
							 	<a href="back/order/pay.html?orderid=${orderDetail.id}&payType=0">余额支付</a>
							</td>
							<td>
							 	<a href="back/order/showfetch.html?orderid=${orderDetail.id}">取走</a>
							</td>
							<td>
							 	<a href="back/order/deliver.html?orderid=${orderDetail.id}">送达</a>
							</td>				
							<td>
							 	<a href="back/order/clothes.html?orderid=${orderDetail.id}">明细</a>
							</td>
							<td>
							 	<a href="back/order/history.html?orderid=${orderDetail.id}">历史</a>
							</td>
							<td>
							 	<a href="back/order/showmodify.html?orderid=${orderDetail.id}">修改</a>
							</td>
						</tr>
					</c:forEach>
			</table>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2">
			记录数：${pagination.recordnum }
			<a href="back/order/page.html?type=1&pageno=1">首页</a>
			<a href="back/order/page.html?type=0&pageno=${pagination.pageno }">上一页</a>
			${pagination.pageno }/${pagination.pagenum }
			<a href="back/order/page.html?type=2&pageno=${pagination.pageno }">下一页</a>
			<a href="back/order/page.html?type=1&pageno=${pagination.pagenum }">尾页</a>
			<form action="back/order/page.html" method="post" style="margin:0px;display: inline">
				<input name="type" type="hidden" value="1" />
				<input name="pageno" type="text" size="2"/>
				<input type="submit" value="跳转" />
			</form>
			</td>
		</tr>
	</table>
</body>
</html>