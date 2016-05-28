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
	<title>优惠码管理</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteTicketCodes() {
			var ticketCodeids = getCheckedIds("ticketCodeid");
			if (ticketCodeids) {
				var url = "back/activity/deleteticketcode.html";
				submit("ticketCodeids", url, ticketCodeids);
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
				<a href="back/activity/showaddticketcode.html">添加优惠码</a>				
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteTicketCodes()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('ticketCodeid')" /></th>
						<th>编号</th>
						<th>优惠码</th>
						<th>是否兑换</th>
						<th>起始日期</th>	
						<th>截止日期</th>		
					</tr>
					<%count=0;%>
					<c:forEach var="ticketCode" items="${ticketCodes}">
						<tr>
							<td><input type="checkbox" name="ticketCodeid" value="${ticketCode.id}" /></td>
							<td><%=++count%></td>
							<td>${ticketCode.codeno}</td>
							<td>${ticketCode.validDes}</td>
							<td>${ticketCode.startDes}</td>
							<td>${ticketCode.endDes}</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2">
			记录数：${pagination.recordnum }
			<a href="back/activity/ticketcode/page.html?type=1&pageno=1">首页</a>
			<a href="back/activity/ticketcode/page.html?type=0&pageno=${pagination.pageno }">上一页</a>
			${pagination.pageno }/${pagination.pagenum }
			<a href="back/activity/ticketcode/page.html?type=2&pageno=${pagination.pageno }">下一页</a>
			<a href="back/activity/ticketcode/page.html?type=1&pageno=${pagination.pagenum }">尾页</a>
			<form action="back/activity/ticketcode/page.html" method="post" style="margin:0px;display: inline">
				<input name="type" type="hidden" value="1" />
				<input name="pageno" type="text" size="2"/>
				<input type="submit" value="跳转" />
			</form>
			</td>
		</tr>
	</table>
</body>
</html>