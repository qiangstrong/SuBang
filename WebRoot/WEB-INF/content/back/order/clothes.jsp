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
	<title>物品明细</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteClothess() {
			var clothesids = getCheckedIds("clothesid");
			if (clothesids) {
				var url = "back/order/deleteclothes.html";
				submit("clothesids", url, clothesids);
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
			<td align="right"><a href="back/order/index/back.html">返回</a></td>
		</tr>
		<tr>
			<td align="right" colspan="2">
				<a href="back/order/showaddclothes.html?orderid=${orderid}">添加衣物</a>
				<a href="back/order/check.html?orderid=${orderid}">完成</a>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteClothess()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('clothesid')" /></th>
						<th>名称</th>
						<th>颜色</th>
						<th>瑕疵</th>
					</tr>
					<c:forEach var="clothes" items="${clothess}">
						<tr>
							<td><input type="checkbox" name="clothesid" value="${clothes.id}" /></td>
							<td>${clothes.name}</td>
							<td>${clothes.color}</td>
							<td>${clothes.flaw}</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>