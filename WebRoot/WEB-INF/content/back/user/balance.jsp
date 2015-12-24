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
	<title>余额记录</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<table align="center">
		<tr>
			<td>${desMsg}</td>
			<td align="right"><a href="back/user/index/back.html">返回</a></td>
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
					<th>编号</th>
					<th>交易号</th>
					<th>类型</th>
					<th>金额</th>
					<th>时间</th>
				</tr>
				<%count=0;%>
				<c:forEach var="balance" items="${balances}">
					<tr>
						<td><%=++count%></td>
						<td>${balance.orderno}</td>
						<td>${balance.payTypeDes}</td>
						<td>${balance.moneyDes}</td>
						<td>${balance.timeDes}</td>
					</tr>
				</c:forEach>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>