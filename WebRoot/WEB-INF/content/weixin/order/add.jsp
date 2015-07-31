<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	<title>下单</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<link rel="stylesheet" type="text/css" href="css/style.css"/>  
	<script type="text/javascript" src="javascript/user.js"></script> 
	<script type="text/javascript" src="javascript/jquery-1.7.1.min.js"></script>
	<script>
		function updateTime(data){
			var objectTime=document.getElementById('time'); 
			objectTime.length=0;
			for(var i=0;i<data.length;i++){
				objectTime.add(new Option(data[i].text,data[i].value));
			}			 
		}		
	</script> 
</head>
<body>	
	<%@ include file="../common/menu.jsp" %>
	<table align="left">
		<tr>
		<td>
			<form:form  modelAttribute="order" action="weixin/order/add.html" method="post">
				<table>
					<tr>
						<td class="label">类别：</td>
					</tr>
					<tr>
						<td>
							<select id="category" name="category">
								<option value="0">衣服</option>
								<option value="1">鞋</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label">取件日期：</td>
					</tr>
					<tr>
						<td>
							<select id="date" name="date" onchange="getData('date','weixin/order/select.html',updateTime)">
								<c:forEach var="date" items="${dates}">
								<option value="${date.value }">${date.text }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label">取件时间：</td>
					</tr>
					<tr>
						<td>
							<select id="time" name="time" >
								<c:forEach var="time" items="${times}">
								<option value="${time.value }">${time.text }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label">地址：</td>
					</tr>
					<tr>
						<td class="error"><form:errors path="addrid" /></td>
					</tr>
					<tr>
						<c:if test="${fn:length(addrDetails) == 0 }">
						<td class="operation"><a  href="weixin/addr/showadd.html">添加地址</a></td>
						</c:if>
						<c:if test="${fn:length(addrDetails) != 0 }">
						<td>
							<select id="addrid" name="addrid">
								<c:forEach var="addrDetail" items="${addrDetails}">
								<option value="${addrDetail.id }" <c:if test="${addrDetail.id==defaultAddrid }">selected="selected"</c:if>>
									${addrDetail}
								</option>								
								</c:forEach>
							</select>
						</td>
						</c:if>
					</tr>
					<tr>
						<td class="label">备注：</td>
					</tr>
					<tr>
						<td class="error"><form:errors path="comment" /></td>
					</tr>
					<tr>
						<td>
							<textarea id="comment" name="comment">${order.comment }</textarea>
						</td>						
					</tr>
					<tr>
						<td>
							<input class="submit" type="submit" value="提交" />
						</td>
					</tr>
				</table>
			</form:form>
		</td>
		</tr>
		<tr> 
		<td>
			<table>
				<tr>
					<td>
						<a class="link" href="weixin/info/term.html">服务条款</a>
					</td>
					<td>
						<a class="link" href="weixin/info/price.html">价目表</a>
					</td>
					<td>
						<a class="link" href="weixin/info/scope.html">服务范围</a>
					</td>
				</tr>
			</table>
		</td>
		</tr>
	</table>
</body>
</html>