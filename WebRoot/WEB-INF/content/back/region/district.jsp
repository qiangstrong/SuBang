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
	<title>区管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteDistricts() {
			var districtids = getCheckedIds("districtid");
			if (districtids) {
				var url = "back/region/deletedistrict.html";
				submit("districtids", url, districtids);
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
			<td align="right"></td>
		</tr>
		<tr>
			<td align="right" colspan="2"><a href="<c:url value="back/region/showadddistrict.html?cityid=${cityid}"/>">添加区</a></td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteDistricts()"/></td>
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
			<table border="1" cellpadding="5">
					<tr>
						<th><input type="checkbox" onclick="switchCheckboxs('districtid')" /></th>
						<th>名称</th>
						<th>小区</th>
						<th>修改</th>
					</tr>
					<c:forEach var="district" items="${districts}">
						<tr>
							<td><input type="checkbox" name="districtid" value="${district.id}" /></td>
							<td>${district.name}</td>
							<td>
								<a href="<c:url value="back/region/region.html?districtid=${district.id}"/>">小区</a>
							</td>
							<td>
								<a href="<c:url value="back/region/showmodifydistrict.html?districtid=${district.id}"/>">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>