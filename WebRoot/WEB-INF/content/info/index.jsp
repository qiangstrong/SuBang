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
	<title>产品运营</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
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
			<td colspan="2">
				<c:if test="${infoMsg!=null}">
					${infoMsg}
				</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="2">
			<table border="1" cellpadding="5">
				<c:forEach var="info" items="${infos}">
					<tr>
						<th>价目表</th>
						<td><img width="1000" src="${info.price_path}" /></td>
					</tr>
					<tr>
						<th>价格描述</th>
						<td><textarea rows="5" cols="135" disabled="disabled">${info.price_text}</textarea></td>
					</tr>
					<tr>
						<th>服务范围</th>
						<td><img width="1000" src="${info.scope_path}" /></td>
					</tr>
					<tr>
						<th>服务范围描述</th>
						<td><textarea rows="5" cols="135" disabled="disabled">${info.scope_text}</textarea></td>
					</tr>
					<tr>
						<th>关于</th>
						<td><textarea rows="10" cols="135" disabled="disabled">${info.about}</textarea></td>
					</tr>
					<tr>
						<th>服务条款</th>
						<td><textarea rows="50" cols="135" disabled="disabled">${info.term}</textarea></td>
					</tr>
					<tr>
						<th>服务电话</th>
						<td><textarea rows="5" cols="135" disabled="disabled">${info.phone}</textarea></td>
					</tr>
					<tr>
						<th>修改</th>
						<td><a href="<c:url value="/info/showmodify.html?infoid=${info.id}"/>">修改</a></td>
					</tr>	
				</c:forEach>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>