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
	<%@ include file="infoheader.jsp"%>
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
						<th>服务电话</th>
						<td>${info.phone}</td>
					</tr>
					<tr>
						<th>分享返现</th>
						<td>${info.shareMoney}</td>
					</tr>
					<tr>
						<th>提现阈值</th>
						<td>${info.salaryLimit}</td>
					</tr>
					<tr>
						<th>上一级提成</th>
						<td>${info.prom0}%</td>
					</tr>
					<tr>
						<th>上两级提成</th>
						<td>${info.prom1}%</td>
					</tr>
					<tr>
						<th>上三级提成</th>
						<td>${info.prom2}%</td>
					</tr>
					<tr>
						<th>修改</th>
						<td><a href="back/info/showmodifyinfo.html?infoid=${info.id}">修改</a></td>
					</tr>	
				</c:forEach>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>