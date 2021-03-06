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
				<a href="back/user/showadd.html">添加用户</a>
			</td>
		</tr>
		<tr>
			<td>
				<form name="searchArg" action="back/user/search.html" method="post">
					类别：
					<select id="type" name="type" >
						<option value="3" selected="selected">手机号</option>
						<option value="6">会员号</option>
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
			<%!int count; %>
			<table border="1" cellpadding="5">
					<tr>
						<th><input type="checkbox" onclick="switchCheckboxs('userid')" /></th>
						<th>编号</th>
						<th>会员号</th>
						<th>手机号</th>
						<th>积分</th>
						<th>余额</th>
						<th>收益</th>
						<th>来源</th>	
						<th>充值</th>
						<th>下单</th>					
						<th>地址</th>
						<th>卡券</th>
						<th>订单</th>		
						<th>商城订单</th>					
						<th>充值记录</th>
						<th>收益记录</th>
						<th>下级用户</th>
						<th>修改</th>
					</tr>
					<%count=0;%>
					<c:forEach var="user" items="${users}">
						<tr>
							<td><input type="checkbox" name="userid" value="${user.id}" /></td>
							<td><%=++count%></td>
							<td>${user.usernoDes}</td>
							<td>${user.cellnum}</td>
							<td>${user.score}</td>
							<td>${user.money}</td>
							<td>${user.salary}</td>
							<td>${user.clientDes}</td>
							<td>
								<a href="back/user/showrecharge.html?userid=${user.id}">充值</a>
							</td>
							<td>
								<a href="back/user/addorder.html?userid=${user.id}">下单</a>
							</td>
							<td>
								<a href="back/user/addr.html?userid=${user.id}">地址</a>
							</td>
							<td>
								<a href="back/user/ticket.html?userid=${user.id}">卡券</a>
							</td>						
							<td>
								<a href="back/user/order.html?userid=${user.id}">订单</a>
							</td>
							<td>
								<a href="back/user/record.html?userid=${user.id}">商城订单</a>
							</td>
							<td>
								<a href="back/user/balance.html?userid=${user.id}">余额记录</a>
							</td>
							<td>
								<a href="back/user/salary.html?userid=${user.id}">收益记录</a>
							</td>
							<td>
								<a href="back/user/user.html?userid=${user.id}">下级用户</a>
							</td>
							<td>
								<a href="back/user/showmodify.html?userid=${user.id}">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2">
			记录数：${pagination.recordnum }
			<a href="back/user/page.html?type=1&pageno=1">首页</a>
			<a href="back/user/page.html?type=0&pageno=${pagination.pageno }">上一页</a>
			${pagination.pageno }/${pagination.pagenum }
			<a href="back/user/page.html?type=2&pageno=${pagination.pageno }">下一页</a>
			<a href="back/user/page.html?type=1&pageno=${pagination.pagenum }">尾页</a>
			<form action="back/user/page.html" method="post" style="margin:0px;display: inline">
				<input name="type" type="hidden" value="1" />
				<input name="pageno" type="text" size="2"/>
				<input type="submit" value="跳转" />
			</form>
			</td>
		</tr>
	</table>
</body>
</html>