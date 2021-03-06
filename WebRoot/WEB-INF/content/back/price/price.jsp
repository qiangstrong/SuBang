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
	<title>小类管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deletePrices() {
			var priceids = getCheckedIds("priceid");
			if (priceids) {
				var url = "back/price/deleteprice.html";
				submit("priceids", url, priceids);
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
			<td>${desMsg}</td>
			<td align="right">
				<a href="back/price/category/back.html">返回</a>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><a href="back/price/showaddprice.html?categoryid=${categoryid}">添加小类</a></td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deletePrices()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('priceid')" /></th>
						<th>编号</th>
						<th>名称</th>
						<th>备注</th>
						<th>修改</th>
					</tr>
					<%count=0;%>
					<c:forEach var="price" items="${prices}">
						<tr>
							<td><input type="checkbox" name="priceid" value="${price.id}" /></td>
							<td><%=++count%></td>
							<td>${price.name}</td>
							<td>${price.comment}</td>
							<td>
								<a href="back/price/showmodifyprice.html?priceid=${price.id}">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>