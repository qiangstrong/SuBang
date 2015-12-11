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
	<title>衣物类型管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteClothesTypes() {
			var clothesTypeids = getCheckedIds("clothesTypeid");
			if (clothesTypeids) {
				var url = "back/price/deleteclothestype.html";
				submit("clothesTypeids", url, clothesTypeids);
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
				<a href="back/price/category/back.html">返回</a>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2">
				<a href="back/price/clothestype.html?categoryid=${categoryid}&type=5">不完成类型</a>
				<a href="back/price/clothestype.html?categoryid=${categoryid}&type=1">所有类型</a>
				<a href="back/price/showaddclothestype.html?categoryid=${categoryid}">添加类型</a>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteClothesTypes()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('clothesTypeid')" /></th>
						<th>名称</th>
						<th>金额</th>						
						<th>修改</th>
					</tr>
					<c:forEach var="clothesType" items="${clothesTypes}">
						<tr>
							<td><input type="checkbox" name="clothesTypeid" value="${clothesType.id}" /></td>
							<td>${clothesType.name}</td>
							<td>${clothesType.money}</td>							
							<td>
								<a href="back/price/showmodifyclothestype.html?clothesTypeid=${clothesType.id}">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>