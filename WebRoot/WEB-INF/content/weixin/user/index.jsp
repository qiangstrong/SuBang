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
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>
<body>
	<%@ include file="../common/menu.jsp"%>
	<table>
		<tr>
			<td><img height="50px" src="${user.photo}" /></td>
			<td>
				<table>
					<tr>
						<td>${user.nickname }</td>						
					</tr>
					<tr>
						<c:if test="${user.cellnum!=null }">
						<td>${user.cellnum }</td>
						</c:if>
						<c:if test="${user.cellnum==null }">
						<td class="operation">
							<a href="weixin/user/showvalidate.html">绑定手机号</a>
						</td>
						</c:if>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
	<ul class="list">
        <li><a href="weixin/order/index.html?type=1">我的订单</a></li>
        <li><a href="weixin/addr/index.html">我的地址</a></li>
        <li><a href="weixin/info/term.html">服务条款</a></li>
    </ul>
    
</body>
</html>