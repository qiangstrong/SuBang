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
	<title>个人中心</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<table align="center">
		<tr>
			<td><img width="100" src="${user.photo}" /></td>
			<td>${user.nickname }</td>
		</tr>
		<tr>
			<c:if test="${user.cellnum!=null }">
			<td>手机号</td>
			<td>${user.cellnum }</td>
			</c:if>
			<c:if test="${user.cellnum==null }">
			<td colspan="2">
				<a href="weixin/user/showvalidate.html">绑定手机号</a>
			</td>
			</c:if>
		</tr>
		<tr>
			<td colspan="2">
				<a href="weixin/addr/index.html">查看地址</a>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<a href="weixin/info/term.html">服务条款</a>
			</td>
		</tr>
	</table>
</body>
</html>