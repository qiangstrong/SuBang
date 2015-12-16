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
	<title>通知管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteNotices() {
			var noticeids = getCheckedIds("noticeid");
			if (noticeids) {
				var url = "back/info/deletenotice.html";
				submit("noticeids", url, noticeids);
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
	<%@ include file="infoheader.jsp"%>
	<table align="center">
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteNotices()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('noticeid')" /></th>
						<th>编号</th>
						<th>时间</th>
						<th>代码</th>
						<th>消息</th>
					</tr>
					<%count=0;%>
					<c:forEach var="notice" items="${notices}">
						<tr>
							<td><input type="checkbox" name="noticeid" value="${notice.id}" /></td>
							<td><%=++count%></td>
							<td>${notice.time}</td>
							<td>${notice.code}</td>	
							<td>${notice.msg}</td>						
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>