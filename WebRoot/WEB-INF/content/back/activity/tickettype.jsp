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
	<title>卡券类型管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteTicketTypes() {
			var ticketTypeids = getCheckedIds("ticketTypeid");
			if (ticketTypeids) {
				var url = "back/activity/deletetickettype.html";
				submit("ticketTypeids", url, ticketTypeids);
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
				<a href="back/activity/showaddtickettype.html">添加类型</a>				
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteTicketTypes()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('ticketTypeid')" /></th>
						<th>名称</th>
						<th>金额</th>
						<th>积分</th>	
						<th>期限</th>		
						<th>类别</th>				
						<th>修改</th>
					</tr>
					<c:forEach var="ticketType" items="${ticketTypes}">
						<tr>
							<td><input type="checkbox" name="ticketTypeid" value="${ticketType.id}" /></td>
							<td>${ticketType.name}</td>
							<td>${ticketType.money}</td>
							<td>${ticketType.score}</td>
							<td>${ticketType.deadlineDes}</td>
							<td>${ticketType.categoryname}</td>
							<td>
								<a href="<c:url value="back/activity/showmodifytickettype.html?ticketTypeid=${ticketType.id}"/>">修改</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>