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
	<title>商户价格管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteCosts() {
			var costids = getCheckedIds("costid");
			if (costids) {
				var url = "back/laundry/deletecost.html";
				submit("costids", url, costids);
			}
		}
	</script>  
</head>
<body>
	<c:if test="${errMsg!=null}">
		<script>
			alert('${errMsg}');
		</script>
	</c:if>
	<%@ include file="../common/header.jsp"%>
	<table align="center">
		<tr>
			<td>${desMsg}</td>
			<td align="right">
				<a href="back/laundry/index/back.html">返回</a>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><a href="back/laundry/showaddcost.html?laundryid=${laundryid}">添加价格</a></td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteCosts()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('costid')" /></th>
						<th>编号</th>
						<th>名称</th>
						<th>金额</th>
						<th>修改</th>
					</tr>
					<%count=0;%>
					<c:forEach var="cost" items="${costs}">
						<tr>
							<td><input type="checkbox" name="costid" value="${cost.id}" /></td>
							<td><%=++count%></td>
							<td>${cost.articlename}</td>
							<td>${cost.moneyDes}</td>
							<td>
								<a href="back/laundry/showmodifycost.html?costid=${cost.id}">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>