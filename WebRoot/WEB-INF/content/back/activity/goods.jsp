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
	<title>商品管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteGoodss() {
			var goodsids = getCheckedIds("goodsid");
			if (goodsids) {
				var url = "back/activity/deletegoods.html";
				submit("goodsids", url, goodsids);
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
	<%@ include file="activityheader.jsp"%>
	<table align="center">
		<tr>
			<td align="right" colspan="2">
				<a href="back/activity/showaddgoods.html">添加商品</a>				
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteGoodss()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('goodsid')" /></th>
						<th>编号</th>
						<th>名称</th>
						<th>金额</th>
						<th>积分</th>	
						<th>数量</th>					
						<th>修改</th>
					</tr>
					<%count=0;%>
					<c:forEach var="goods" items="${goodss}">
						<tr>
							<td><input type="checkbox" name="goodsid" value="${goods.id}" /></td>
							<td><%=++count%></td>
							<td>${goods.name}</td>
							<td>${goods.money}</td>
							<td>${goods.score}</td>
							<td>${goods.count}</td>
							<td>
								<a href="back/activity/showmodifygoods.html?goodsid=${goods.id}">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>