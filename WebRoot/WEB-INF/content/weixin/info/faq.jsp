<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta content="" name="pgv">
<base href="<%=basePath%>">
<title>常见问题</title>
<meta content="authenticity_token" name="csrf-param">
<meta content="krKExkIYG4g8hJ7y8vTCEvq4KA8517SIZOnt8n0tubI=" name="csrf-token">
<link href="css/weixin/ccsjsp/info/term.css" media="screen" rel="stylesheet">
<script src="css/weixin/ccsjsp/info/question-405066dfbcd21aa33ace9e50d18f96cc.js"></script>
</head>
<body scroll="no" style="zoom: 1;">
<div id="container">
  <div class="order_weixin" id="orderurl" style="display:none"><span class="arrow-left"></span><a class="btn-info fanhui" href="javascript:history.back(-1)"></a></div>
  <br>
  <img alt="Wenti" src="css/weixin/ccsjsp/info/wenti.png" style="width:100%">
  <table class="normal_question">
    <tbody>
      <tr>
        <td colspan="2"><h3><img alt="Guanyu" src="css/weixin/ccsjsp/info/guanyu.png"></h3></td>
      </tr>
      <c:forEach var="faq" items="${faqs}" varStatus="p_status">
      <tr>
        <td><span class="blue-color">问:</span></td>
        <td><p class="blue-color">${p_status.count}. ${faq.question}</p>
          <p>${faq.answer}</p>
          </td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
</body>
</html>