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
		function deleteTickets() {
			var ticketids = getCheckedIds("ticketid");
			if (ticketids) {
				var url = "back/user/deleteticket.html";
				submit("ticketids", url, ticketids);
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
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteTickets()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('ticketid')" /></th>
						<th>名称</th>
						<th>类别</th>
						<th>金额</th>
						<th>积分</th>
						<th>期限</th>
					</tr>
					<c:forEach var="ticketDetail" items="${ticketDetails}">
						<tr>
							<td><input type="checkbox" name="ticketid" value="${ticketDetail.id}" /></td>
							<td>${ticketDetail.name}</td>
							<td>${ticketDetail.categoryname}</td>
							<td>${ticketDetail.money}</td>
							<td>${ticketDetail.score}</td>
							<td>${ticketDetail.deadline}</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>