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
	<title>城市管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteCitys() {
			var cityids = getCheckedIds("cityid");
			if (cityids) {
				var url = "back/region/deletecity.html";
				submit("cityids", url, cityids);
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
			<td align="right" colspan="2">
				<a href="back/region/incomplete.html">不完整区域</a>
				<a href="back/region/showaddcity.html">添加城市</a>				
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteCitys()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('cityid')" /></th>
						<th>编号</th>
						<th>名称</th>
						<th>服务范围描述</th>
						<th>区</th>	
						<th>服务</th>					
						<th>修改</th>
					</tr>
					<%count=0;%>
					<c:forEach var="city" items="${citys}">
						<tr>
							<td><input type="checkbox" name="cityid" value="${city.id}" /></td>
							<td><%=++count%></td>
							<td>${city.name}</td>
							<td>${city.scopeText}</td>
							<td>
								<a href="back/region/district.html?cityid=${city.id}">区</a>
							</td>
							<td>
								<a href="back/region/service.html?cityid=${city.id}">服务</a>
							</td>
							<td>
								<a href="back/region/showmodifycity.html?cityid=${city.id}">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>