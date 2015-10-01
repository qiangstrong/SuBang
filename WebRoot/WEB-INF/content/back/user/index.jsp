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
	<title>用户管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
	<script type="text/javascript" src="js/back/set.js"></script>
	<script type="text/javascript" src="js/back/state.js"></script>
	<script>
		function deleteUsers() {
			var userids = getCheckedIds("userid");
			if (userids) {
				var url = "back/user/delete.html";
				submit("userids", url, userids);
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
				<a href="back/user/list.html">所有用户</a>
			</td>
		</tr>
		<tr>
			<td>
				<form name="searchArg" action="back/user/search.html" method="post">
					类别：
					<select id="type" name="type" >
						<option value="3" selected="selected">手机号</option>
					</select>
					关键词：
					<input id="arg" type="text" name="arg" />
					<input type="submit" value="确定" />
				</form>
			</td>
			<td align="right"><input type="button" value="删除" onclick="deleteUsers()"/></td>
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
			<table border="1" cellpadding="5">
					<tr>
						<th><input type="checkbox" onclick="switchCheckboxs('userid')" /></th>
						
						<th>手机号</th>
						<th>积分</th>
						<th>余额</th>
						<th>地址</th>
						<th>卡券</th>
						<th>订单</th>
					</tr>
					<c:forEach var="user" items="${users}">
						<tr>
							<td><input type="checkbox" name="userid" value="${user.id}" /></td>
							<td>${user.cellnum}</td>
							<td>${user.score}</td>
							<td>${user.money}</td>
							<td>
								<a href="<c:url value="back/user/ticket.html?userid=${user.id}"/>">卡券</a>
							</td>
							<td>
								<a href="<c:url value="back/user/addr.html?userid=${user.id}"/>">地址</a>
							</td>
							<td>
								<a href="<c:url value="back/user/order.html?userid=${user.id}"/>">订单</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>