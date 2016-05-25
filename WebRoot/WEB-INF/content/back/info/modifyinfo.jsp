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
	<title>修改产品运营信息</title>
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
			<td>
				<c:if test="${infoMsg!=null}">
					${infoMsg}
				</c:if>
			</td>
			<td align="right"><a href="${backLink}">返回</a></td>
		</tr>
		<tr>
			<td colspan="2">
				<form:form modelAttribute="info" action="back/info/modifyinfo.html" method="post">
					<form:hidden path="id"/>					
					<table>
						<tr>
							<td></td>
							<td><form:errors path="phone" /></td>
						</tr>
						<tr>
							<td>服务电话：</td>
							<td><form:input path="phone" /></td>
						</tr>
						<tr>
							<td></td>
							<td><form:errors path="shareMoney" /></td>
						</tr>
						<tr>
							<td>分享返现：</td>
							<td><form:input path="shareMoney" /></td>
						</tr>
						<tr>
							<td></td>
							<td><form:errors path="salaryLimit" /></td>
						</tr>
						<tr>
							<td>提现阈值：</td>
							<td><form:input path="salaryLimit" /></td>
						</tr>
						<tr>
							<td></td>
							<td><form:errors path="prom0" /></td>
						</tr>
						<tr>
							<td>上一级提成：</td>
							<td><form:input path="prom0" />%</td>
						</tr>
						<tr>
							<td></td>
							<td><form:errors path="prom1" /></td>
						</tr>
						<tr>
							<td>上两级提成：</td>
							<td><form:input path="prom1" />%</td>
						</tr>
						<tr>
							<td></td>
							<td><form:errors path="prom2" /></td>
						</tr>
						<tr>
							<td>上三级提成：</td>
							<td><form:input path="prom2" />%</td>
						</tr>
						<tr>
							<td colspan="2" align="right">
								<input type="submit" value="修改" />
								<input type="reset" value="重置" />								
							</td>
						</tr>
					</table>
				</form:form>
			</td>
		</tr>
	</table>
</body>
</html>