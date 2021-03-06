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
	<title>颜色管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteArticles() {
			var colorids = getCheckedIds("colorid");
			if (colorids) {
				var url = "back/price/deletecolor.html";
				submit("colorids", url, colorids);
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
	<%@ include file="priceheader.jsp"%>
	<table align="center">
		<tr>
			<td align="right" colspan="2">
				<a href="back/price/showaddcolor.html">添加颜色</a>				
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteColors()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('colorid')" /></th>
						<th>编号</th>
						<th>名称</th>
						<th>修改</th>
					</tr>
					<%count=0;%>
					<c:forEach var="color" items="${colors}">
						<tr>
							<td><input type="checkbox" name="colorid" value="${color.id}" /></td>
							<td><%=++count%></td>
							<td>${color.name}</td>
							<td>
								<a href="back/price/showmodifycolor.html?colorid=${color.id}">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>