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
	<title>横幅管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteBanners() {
			var bannerids = getCheckedIds("bannerid");
			if (bannerids) {
				var url = "back/activity/deletebanner.html";
				submit("bannerids", url, bannerids);
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
	<%@ include file="activityheader.jsp"%>
	<table align="center">
		<tr>
			<td align="right" colspan="2">
				<a href="back/activity/showaddbanner.html">添加横幅</a>				
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteBanners()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('bannerid')" /></th>
						<th>编号</th>
						<th>序号</th>
						<th>链接</th>
						<th>备注</th>			
						<th>修改</th>
					</tr>
					<%count=0;%>
					<c:forEach var="banner" items="${banners}">
						<tr>
							<td><input type="checkbox" name="bannerid" value="${banner.id}" /></td>
							<td><%=++count%></td>
							<td>${banner.seq}</td>
							<td>${banner.link}</td>
							<td>${banner.comment}</td>
							<td>
								<a href="back/activity/showmodifybanner.html?bannerid=${banner.id}">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>