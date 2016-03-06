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
	<title>商家管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript" src="js/back/set.js"></script>
	<script type="text/javascript" src="js/back/state.js"></script>
	<script>
		function deleteLaundrys() {
			var laundryids = getCheckedIds("laundryid");
			if (laundryids) {
				var url = "back/laundry/delete.html";
				submit("laundryids", url, laundryids);
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
				<a href="back/laundry/list.html">所有商家</a>
				<a href="back/laundry/showadd.html">添加商家</a>
			</td>
		</tr>
		<tr>
			<td>
				<form name="searchArg" action="back/laundry/search.html" method="post">
					类别：
					<select id="type" name="type" >
						<option value="2" selected="selected">商家名称</option>
						<option value="3">手机号</option>
					</select>
					关键词：
					<input id="arg" type="text" name="arg" />
					<input type="submit" value="确定" />
				</form>
			</td>
			<td align="right"><input type="button" value="删除" onclick="deleteLaundrys()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('laundryid')" /></th>
						<th>编号</th>
						<th>商家名称</th>
						<th>手机号</th>
						<th>地址</th>
						<th>备注</th>
						<th>订单</th>
						<th>价格</th>
						<th>修改</th>
					</tr>
					<%count=0;%>
					<c:forEach var="laundry" items="${laundrys}">
						<tr>
							<td><input type="checkbox" name="laundryid" value="${laundry.id}" /></td>
							<td><%=++count%></td>
							<td>${laundry.name}</td>
							<td>${laundry.cellnum}</td>
							<td>${laundry.detail}</td>
							<td>${laundry.comment}</td>
							<td>
								<a href="back/laundry/order.html?laundryid=${laundry.id}">订单</a>
							</td>
							<td>
								<a href="back/laundry/cost.html?laundryid=${laundry.id}">价格</a>
							</td>
							<td>
								<a href="back/laundry/showmodify.html?laundryid=${laundry.id}">修改</a>
							</td>
						</tr>
					</c:forEach>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>