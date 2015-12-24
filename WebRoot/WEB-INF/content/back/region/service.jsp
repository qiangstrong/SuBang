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
	<title>服务管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteServices() {
			var serviceids = getCheckedIds("serviceid");
			if (serviceids) {
				var url = "back/region/deleteservice.html";
				submit("serviceids", url, serviceids);
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
	<table align="center">
		<tr>
			<td>${desMsg}</td>
			<td align="right">
				<a href="back/region/city/back.html">返回</a>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><a href="back/region/showaddservice.html?cityid=${cityid}">添加服务</a></td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteServices()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('serviceid')" /></th>
						<th>编号</th>
						<th>序号</th>
						<th>可用</th>
						<th>名称</th>
						<th>修改</th>
					</tr>
					<%count=0;%>
					<c:forEach var="service" items="${services}">
						<tr>
							<td><input type="checkbox" name="serviceid" value="${service.id}" /></td>
							<td><%=++count%></td>
							<td>${service.seq}</td>
							<td>${service.validDes}</td>
							<td>${service.categoryname}</td>
							<td>
								<a href="back/region/showmodifyservice.html?serviceid=${service.id}">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>