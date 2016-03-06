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
	<title>物品快照</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<script type="text/javascript" src="js/back/set.js"></script>
	<script>
		function deleteSnapshots() {
			var snapshotids = getCheckedIds("snapshotid");
			if (snapshotids) {
				var url = "back/order/deletesnapshot.html";
				submit("snapshotids", url, snapshotids);
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
			<td align="right"><a href="back/order/clothes/back.html">返回</a></td>
		</tr>
		<tr>
			<td align="right" colspan="2">
				<a href="back/order/showaddsnapshot.html?clothesid=${clothesid}">添加快照</a>
			</td>
		</tr>
		<tr>
			<td align="right" colspan="2"><input type="button" value="删除" onclick="deleteSnapshots()"/></td>
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
						<th><input type="checkbox" onclick="switchCheckboxs('snapshotid')" /></th>
						<th>编号</th>
						<th>图片</th>
					</tr>
					<%count=0;%>
					<c:forEach var="snapshot" items="${snapshots}">
						<tr>
							<td><input type="checkbox" name="snapshotid" value="${snapshot.id}" /></td>
							<td><%=++count%></td>
							<td>
								<a href="${snapshot.icon}"><img src="${snapshot.icon}" height="150"/></a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>