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
	<title>类别管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteCategorys() {
			var categoryids = getCheckedIds("categoryid");
			if (categoryids) {
				var url = "back/price/deletecategory.html";
				submit("categoryids", url, categoryids);
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
				<a href="back/price/showaddcategory.html">添加类别</a>				
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteCategorys()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('categoryid')" /></th>
						<th>编号</th>
						<th>名称</th>
						<th>备注</th>
						<th>价格</th>	
						<th>衣物类型</th>					
						<th>修改</th>
					</tr>
					<%count=0;%>
					<c:forEach var="category" items="${categorys}">
						<tr>
							<td><input type="checkbox" name="categoryid" value="${category.id}" /></td>
							<td><%=++count%></td>
							<td>${category.name}</td>
							<td>${category.comment}</td>
							<td>
								<a href="back/price/price.html?categoryid=${category.id}">价格</a>
							</td>
							<td>
								<a href="back/price/clothestype.html?categoryid=${category.id}&type=1">衣物类型</a>
							</td>
							<td>
								<a href="back/price/showmodifycategory.html?categoryid=${category.id}">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>