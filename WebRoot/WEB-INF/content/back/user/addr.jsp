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
	<title>用户地址</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<script type="text/javascript" src="javascript/set.js"></script>
	<script>
		function deleteAddrs() {
			var addrids = getCheckedIds("addrid");
			if (addrids) {
				var url = "back/user/deleteaddr.html";
				submit("addrids", url, addrids);
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
			<td align="right"><a href="back/user/index.html?type=1">返回</a></td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteAddrs()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('addrid')" /></th>
						<th>姓名</th>
						<th>手机号</th>
						<th>城市名称</th>
						<th>区名称</th>
						<th>小区名称</th>
						<th>详细地址</th>
					</tr>
					<c:forEach var="addrDetail" items="${addrDetails}">
						<tr>
							<td><input type="checkbox" name="addrid" value="${addrDetail.id}" /></td>
							<td>${addrDetail.name}</td>
							<td>${addrDetail.cellnum}</td>
							<td>${addrDetail.cityname}</td>
							<td>${addrDetail.districtname}</td>
							<td>${addrDetail.regionname}</td>
							<td>${addrDetail.detail}</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>