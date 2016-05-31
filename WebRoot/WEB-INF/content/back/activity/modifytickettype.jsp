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
	<title>修改卡券类型信息</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
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
			<td>
				<c:if test="${infoMsg!=null}">
					${infoMsg}
				</c:if>
			</td>
			<td align="right"><a href="${backLink}">返回</a></td>
		</tr>
		<tr>
			<td colspan="2">
				<form:form modelAttribute="ticketType" action="back/activity/modifytickettype.html" method="post" enctype="multipart/form-data">
					<form:hidden path="id"/>					
					<table>
						<tr>
							<td></td>
							<td><form:errors path="name" /></td>
						</tr>
						<tr>
							<td>名称：</td>
							<td><form:input path="name" /></td>
						</tr>
						<form:hidden path="icon"/>
						<tr>
							<td>图标：</td>
							<td><img  src="${ticketType.icon}" /></td>
						</tr>
						<tr>
							<td>图标：</td>
							<td><input type="file" name="iconImg"/></td>
						</tr>
						<form:hidden path="poster"/>
						<tr>
							<td>大图标：</td>
							<td><img  src="${ticketType.poster}" /></td>
						</tr>
						<tr>
							<td>大图标：</td>
							<td><input type="file" name="posterImg"/></td>
						</tr>
						<tr>
							<td></td>
							<td><form:errors path="money" /></td>
						</tr>
						<tr>
							<td>金额：</td>
							<td><form:input path="money" /></td>
						</tr>
						<tr>
							<td></td>
							<td><form:errors path="score" /></td>
						</tr>
						<tr>
							<td>积分：</td>
							<td><form:input path="score"/></td>
						</tr>
						<tr>
							<td></td>
							<td><form:errors path="deadline" /></td>
						</tr>
						<tr>
							<td>期限：</td>
							<td><input type="date" name="deadline" value="${ticketType.deadlineDes}"/></td>
						</tr>
						<tr>
							<td></td>
							<td><form:errors path="comment" /></td>
						</tr>
						<tr>
							<td>备注：</td>
							<td><textarea rows="5" cols="135" name="comment">${ticketType.comment}</textarea></td>
						</tr>
						<tr>
							<td>类别：</td>
							<td>    
								<form:select path="categoryid" items="${categorys}" itemLabel="name" itemValue="id"></form:select>  
        					</td>
						</tr>	
						<tr >
							<td><input type="submit" value="修改" /></td>
							<td><input type="reset" value="重置" /></td>
						</tr>
					</table>
				</form:form>
			</td>
		</tr>
	</table>
</body>
</html>