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
	<title>折扣管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteRebates() {
			var rebateids = getCheckedIds("rebateid");
			if (rebateids) {
				var url = "back/activity/deleterebate.html";
				submit("rebateids", url, rebateids);
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
				<a href="back/activity/showaddrebate.html">添加折扣</a>				
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteRebates()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('rebateid')" /></th>
						<th>金额</th>
						<th>折扣</th>				
						<th>修改</th>
					</tr>
					<c:forEach var="rebate" items="${rebates}">
						<tr>
							<td><input type="checkbox" name="rebateid" value="${rebate.id}" /></td>
							<td>${rebate.money}</td>
							<td>${rebate.benefit}</td>
							<td>
								<a href="<c:url value="back/activity/showmodifyrebate.html?rebateid=${rebate.id}"/>">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>