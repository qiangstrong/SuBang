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
	<title>人员管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript" src="js/back/set.js"></script>
	<script type="text/javascript" src="javascript/state.js"></script>
	<script>
		function deleteWorkers() {
			var workerids = getCheckedIds("workerid");
			if (workerids) {
				var url = "back/worker/delete.html";
				submit("workerids", url, workerids);
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
				<a href="back/worker/list.html">所有工作人员</a>
				<a href="back/worker/showadd.html">添加工作人员</a>
			</td>
		</tr>
		<tr>
			<td>
				<form name="searchArg" action="back/worker/search.html" method="post">
					类别：
					<select id="type" name="type" >
						<option value="2" selected="selected">人员姓名</option>
						<option value="3">手机号</option>
						<option value="4">区域</option>
					</select>
					关键词：
					<input id="arg" type="text" name="arg" />
					<input type="submit" value="确定" />
				</form>
			</td>
			<td align="right"><input type="button" value="删除" onclick="deleteWorkers()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('workerid')" /></th>
						<th>编号</th>
						<th>姓名</th>
						<th>核心</th>
						<th>手机号</th>
						<th>地址</th>
						<th>备注</th>
						<th>订单</th>
						<th>商城订单</th>
						<th>区域</th>
						<th>修改</th>
					</tr>
					<%count=0;%>
					<c:forEach var="worker" items="${workers}">
						<tr>
							<td><input type="checkbox" name="workerid" value="${worker.id}" /></td>
							<td><%=++count%></td>
							<td>${worker.name}</td>
							<td>${worker.coreDes}</td>
							<td>${worker.cellnum}</td>
							<td>${worker.detail}</td>
							<td>${worker.comment}</td>
							<td>
								<a href="back/worker/order.html?workerid=${worker.id}">订单</a>
							</td>
							<td>
								<a href="back/worker/record.html?workerid=${worker.id}">商城订单</a>
							</td>
							<td>
								<a href="back/worker/area.html?workerid=${worker.id}">区域</a>
							</td>
							<td>
								<a href="back/worker/showmodify.html?workerid=${worker.id}">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>