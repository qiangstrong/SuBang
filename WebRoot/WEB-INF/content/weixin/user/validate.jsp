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
	<title>绑定手机号</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
</head>
<body>
	<%@ include file="../common/header.jsp"%>
	<table align="center">
		<tr>
			<td>
				<c:if test="${infoMsg!=null}">
					${infoMsg}
				</c:if>
			</td>
		</tr>
		<tr>
			<td>
				<c:if test="${state==0 }">
				<form action="weixin/user/cellnum.html" method="post">
					<table>
						<tr>
							<td>手机号:</td>
							<td>
								<input id="cellnum" type="text" name="cellnum" value="${cellnum }"/>
							</td>
						</tr>
						<tr>
							<td align="left">
								<input type="submit" value="确定" />
							</td>
						</tr>
					</table>
				</form>			
				</c:if>
				
				<c:if test="${state==1 }">
				<form action="weixin/user/authcode.html" method="post">
					<table>
						<tr>
							<td>验证码:</td>
							<td>
								<input id="authcode" type="text" name="authcode"/>
							</td>
						</tr>
						<tr>
							<td align="left">
								<input type="submit" value="确定" />
							</td>
						</tr>
					</table>
				</form>	
				</c:if>
			</td>
		</tr>
	</table>
</body>
</html>