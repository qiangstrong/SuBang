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
	<title>修改产品运营信息</title>
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
	<table align="center">
		<tr>
			<td>
				<c:if test="${infoMsg!=null}">
					${infoMsg}
				</c:if>
			</td>
			<td align="right"><a href="back/info/index.html">返回</a></td>
		</tr>
		<tr>
			<td colspan="2">
				<form action="back/info/modify.html" method="post" enctype="multipart/form-data">
					<input type="hidden" name="id" value="${info.id}"/>
					<input type="hidden" name="price_path" value="${info.price_path}"/>
					<input type="hidden" name="scope_path" value="${info.scope_path}"/>
					<table border="1" cellpadding="5">
						<tr>
							<th>价目表</th>
							<td><input type="file" name="price_img"/></td>
						</tr>
						<tr>
							<th>价格描述</th>
							<td><textarea rows="5" cols="135" name="price_text">${info.price_text}</textarea></td>
						</tr>
						<tr>
							<th>服务范围</th>
							<td><input type="file" name="scope_img"/></td>
						</tr>
						<tr>
							<th>服务范围描述</th>
							<td><textarea rows="5" cols="135" name="scope_text">${info.scope_text}</textarea></td>
						</tr>
						<tr>
							<th>关于</th>
							<td><textarea rows="10" cols="135" name="about">${info.about}</textarea></td>
						</tr>
						<tr>
							<th>服务条款</th>
							<td><textarea rows="50" cols="135" name="term">${info.term}</textarea></td>
						</tr>
						<tr>
							<th>服务电话</th>
							<td><textarea rows="5" cols="135" name="phone">${info.phone}</textarea></td>
						</tr>
						<tr>
							<td colspan="2" align="right">
								<input type="submit" value="修改" />
								<input type="reset" value="重置" />								
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>
</body>
</html>