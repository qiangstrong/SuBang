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
	<title>添加地址</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no"/>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<link rel="stylesheet" type="text/css" href="css/style.css"/>
	<script type="text/javascript" src="js/weixin/user.js"></script> 
	<script type="text/javascript" src="js/weixin/jquery-1.7.1.min.js"></script>
	<script>
		function updateDistrict(data){
			var dataDistrict=data[0];
			var objectDistrict=document.getElementById('districtid'); 
			objectDistrict.length=0;
			for(var i=0;i<dataDistrict.length;i++){
				objectDistrict.add(new Option(dataDistrict[i].name,dataDistrict[i].id));
			}			
			
			var dataRegion=data[1];
			var objectRegion=document.getElementById('regionid'); 
			objectRegion.length=0;
			for(var i=0;i<dataRegion.length;i++){
				objectRegion.add(new Option(dataRegion[i].name,dataRegion[i].id));
			}	 
		}	
		function updateRegion(data){
			var object=document.getElementById('regionid'); 
			object.length=0;
			for(var i=0;i<data.length;i++){
				object.add(new Option(data[i].name,data[i].id));
			}			 
		}	
	</script>  
</head>
<body>
	<%@ include file="../common/menu.jsp" %>
	<table align="left">
		<tr>
		<td>
			<form:form modelAttribute="addr" action="weixin/addr/add.html" method="post">
				<table>					
					<tr>
						<td class="label"><span class="required">*</span>名称：</td>
					</tr>
					<tr>
						<td class="error"><form:errors path="name" /></td>
					</tr>
					<tr>
						<td><form:input class="inputtext" path="name" /></td>
					</tr>				
					<tr>
						<td class="label"><span class="required">*</span>手机号：</td>
					</tr>
					<tr>
						<td class="error"><form:errors path="cellnum" /></td>
					</tr>
					<tr>
						<td><form:input class="inputtext" path="cellnum" /></td>
					</tr>
					<tr>
						<td class="label">城市：</td>
					</tr>
					<tr>
						<td>    
							<select id="cityid" name="cityid" onchange="getData('cityid','weixin/addr/select.html',updateDistrict)">
								<c:forEach var="city" items="${citys}">
								<option value="${city.id }" <c:if test="${city.id==defaultCityid }">selected="selected"</c:if> >
									${city.name }
								</option>
								</c:forEach>
							</select>           					
        				</td>
					</tr>
					<tr>
						<td class="label">区：</td>
					</tr>
					<tr>
						<td>    
							<select id="districtid" name="districtid" onchange="getData('districtid','weixin/addr/select.html',updateRegion)">
								<c:forEach var="district" items="${districts}">
								<option value="${district.id }" <c:if test="${district.id==defaultDistrictid }">selected="selected"</c:if> >
									${district.name }
								</option>
								</c:forEach>
							</select>           					
        				</td>
					</tr>
					<tr>
						<td class="label">小区：</td>
					</tr>
					<tr>
						<td>      
							<select id="regionid" name="regionid">
								<c:forEach var="region" items="${regions}">
								<option value="${region.id }" <c:if test="${region.id==defaultRegionid }">selected="selected"</c:if> >
									${region.name }
								</option>
								</c:forEach>
							</select>              					
        				</td>
					</tr>
					<tr>
						<td class="error"><form:errors path="detail" /></td>
					</tr>
					<tr>
						<td class="label">详细地址：</td>
					</tr>
					<tr>
						<td><form:input class="inputtext" path="detail"/></td>
					</tr>
					<tr>
						<td>
							<input class="submit" type="submit" value="添加地址" />
						</td>
					</tr>
				</table>
			</form:form>
		</td>
		</tr>
	</table>
</body>
</html>