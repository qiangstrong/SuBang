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
	<title>查看地址</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>
<body>
	<%@ include file="../common/menu.jsp"%>
	<table align="left">
		<c:forEach var="addrDetail" items="${addrDetails }">
		<tr>
			<td>${addrDetail.name } ${addrDetail.cellnum }</td>
		</tr>
		<tr>
			<td>${addrDetail.cityname } ${addrDetail.districtname } ${addrDetail.regionname }</td>
		</tr>
		<tr>
			<td>${addrDetail.detail }</td>
		</tr>
		<tr>
			<td class="operation" align="right"><a href="weixin/addr/delete.html?addrid=${addrDetail.id}">删除</a></td>
		</tr>
		</c:forEach>
	</table>
	<hr>
	<table>
		<tr>
			<td class="operation" align="center">
				<a href="weixin/addr/showadd.html">添加地址</a>
			</td>
		</tr>
	</table>
</body>
</html>