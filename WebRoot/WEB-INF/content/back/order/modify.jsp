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
	<title>修改订单信息</title>
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
			<td>
				<c:if test="${infoMsg!=null}">
					${infoMsg}
				</c:if>
			</td>
			<td align="right"></td>
		</tr>
		<tr>
			<td colspan="2">
				<form:form modelAttribute="order" action="back/order/modify.html" method="post">
					<form:hidden path="id"/>
					<form:hidden path="orderno"/>
					<table>
						<tr>
							<td>订单号：</td>
							<td><form:input path="orderno" disabled="true" size="21"/></td>
						</tr>
						<tr>
							<td>商家：</td>
							<td>    
								<form:select path="laundryid" items="${laundrys}" itemLabel="name" itemValue="id"></form:select>  
        					</td>
						</tr>
						<tr>
							<td>取衣员：</td>
							<td>    
								<form:select path="workerid" items="${workers}" itemLabel="name" itemValue="id"></form:select>  
        					</td>
						</tr>
						<tr>
							<td><input type="submit" value="修改" /></td>
							<td><input type="reset" value="重置" /></td>
						</tr>
					</table>
				</form:form>
			</td>
		</tr>
	</table>
</body>
</html>