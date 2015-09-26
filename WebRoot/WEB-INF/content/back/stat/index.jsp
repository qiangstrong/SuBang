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
	<title>数据统计</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script type="text/javascript" src="javascript/stat.js"></script>   
	<script>
		function restore(){
			<c:if test="${pageState!=null}">
				restoreStatState('${pageState.statArg.type0}','${pageState.statArg.type1}','${pageState.statArg.type2}');
			</c:if>
		}
	</script> 
</head>
<body onload="restore()">
	<c:if test="${errMsg!=null}">
		<script>
			alert('${errMsg}');
		</script>
	</c:if>
	<%@ include file="../common/header.jsp"%>
	<table align="center">
		<tr>
			<td colspan="2">
				<form name="statArg" action="back/stat/index.html" method="post">
					<table border="1" cellpadding="5">
						<tr>
							<td>
								<label for="id0">按区域</label>
								<input type="radio" name="type0" id="id0" checked="checked" value="1" onclick="swicthStatState()"/>
							</td>
							<td>
								<label for="id00">订单量</label>
								<input type="radio" name="type1" id="id00" checked="checked" value="0"/>
							</td>
							<td>
								<label for="id01">客单量</label>
								<input type="radio" name="type1" id="id01" value="1"/>
							</td>
							<td>
								<label>区域级别</label>							
								<select id="id02" name="type2" >
									<option value="0" selected="selected">城市</option>
									<option value="1">区</option>
									<option value="2">小区</option>
								</select>
							</td>
							<td></td>
						</tr>
						<tr>
							<td>
								<label for="id1">按用户</label>
								<input type="radio" name="type0" id="id1" value="2" onclick="swicthStatState()"/>
							</td>
							<td>
								<label for="id10">订单量</label>
								<input type="radio" name="type1" id="id10" value="0" disabled="disabled"/>
							</td>
							<td>
								<label for="id11">消费单价</label>
								<input type="radio" name="type1" id="id11" value="1" disabled="disabled"/>
							</td>
							<td></td>
							<td>
								<input type="submit" value="确定" />
							</td>
						</tr>
					</table>
				</form>
			</td>
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
						<th>名称</th>
						<th>数量</th>
					</tr>
					<c:forEach var="statItem" items="${statItems}">
						<tr>
							<td>${statItem.name}</td>
							<td>${statItem.quantity}</td>
						</tr>
					</c:forEach>
			</table>
			</td>
		</tr>
	</table>
</body>
</html>