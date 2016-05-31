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
	<title>商城订单管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript" src="js/back/set.js"></script>
	<script type="text/javascript" src="js/back/state.js"></script>
	<script>
		function deleteRecords() {
			var recordids = getCheckedIds("recordid");
			if (recordids) {
				var url = "back/record/delete.html";
				submit("recordids", url, recordids);
			}
		}
		function restore(){
			<c:if test="${pageState!=null}">
				restoreRecordState('${pageState.searchArg.type}','${pageState.searchArg.arg}');
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
				<a href="back/record/curday.html">当日订单</a>	
				<a href="back/record/list.html">所有订单</a>								
			</td>
		</tr>
		<tr>
			<td>
				<form name="searchArg" action="back/record/search.html" method="post">
				<table>
					<tr>
					<td>
						类别：
						<select id="type" name="type" onchange="switchRecordState()">
							<option value="1">所有订单</option>
							<option value="2">订单状态</option>
							<option value="3">订单号</option>
							<option value="5">用户手机号</option>
						</select>
						关键词：
						<select id="arg0" name="arg" disabled="disabled">
							<option value="2" selected="selected">已支付</option>
							<option value="5">已送达</option>
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
						<input type="button" value="删除" onclick="deleteRecords()"/>
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
			<%!int count; %>
			<table border="1" cellpadding="5">
					<tr>
						<th><input type="checkbox" onclick="switchCheckboxs('recordid')" /></th>
						<th>编号</th>
						<th>订单号</th>
						<th>状态</th>				
						<th>下单时间</th>
						<th>商品名称</th>	
						<th>支付类型</th>							
						<th>详情</th>
						<th>送达</th>	
					</tr>
					<%count=0;%>
					<c:forEach var="recordDetail" items="${recordDetails}">
						<tr>
							<td><input type="checkbox" name="recordid" value="${recordDetail.id}" /></td>
							<td><%=++count%></td>
							<td>${recordDetail.orderno}</td>
							<td>${recordDetail.stateDes}</td>
							<td>${recordDetail.timeDes}</td>
							<td>${recordDetail.name}</td>		
							<td>${recordDetail.payTypeDes}</td>	
							<td>
							 	<a href="back/record/detail.html?recordid=${recordDetail.id}">详情</a>
							</td>	
							<td>
							 	<a href="back/record/deliver.html?recordid=${recordDetail.id}">送达</a>
							</td>				
						</tr>
					</c:forEach>
			</table>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2">
			记录数：${pagination.recordnum }
			<a href="back/record/page.html?type=1&pageno=1">首页</a>
			<a href="back/record/page.html?type=0&pageno=${pagination.pageno }">上一页</a>
			${pagination.pageno }/${pagination.pagenum }
			<a href="back/record/page.html?type=2&pageno=${pagination.pageno }">下一页</a>
			<a href="back/record/page.html?type=1&pageno=${pagination.pagenum }">尾页</a>
			<form action="back/record/page.html" method="post" style="margin:0px;display: inline">
				<input name="type" type="hidden" value="1" />
				<input name="pageno" type="text" size="2"/>
				<input type="submit" value="跳转" />
			</form>
			</td>
		</tr>
	</table>
</body>
</html>