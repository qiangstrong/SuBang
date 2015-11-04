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
<title>速帮</title>
<meta content="authenticity_token" name="csrf-param">
<meta content="EQCNGAJK8SF43zM23Iugrhu8oB2c2w5mU+/TZx90S6A=" name="csrf-token">
<link href="css/weixin/ccsjsp/info/term.css" media="screen" rel="stylesheet">
<script src="css/weixin/ccsjsp/info/question-405066dfbcd21aa33ace9e50d18f96cc.js"></script>
</head>
<body scroll="no" style="zoom: 1;">
<div id="container">
  <div class="order_weixin" id="orderurl" style="display:none"><span class="arrow-left"></span><a class="btn-info fanhui" href="javascript:history.back(-1)"></a></div>
  <div class="introduce_text"><img alt="Solgan service" src="css/weixin/ccsjsp/info/solgan_service.png">
    <p>速帮作为中国互联网领先洗衣品牌，致力为用户提供专业、便捷、高品质的洗涤服务。</p>
    <p>我们的品控团队来自北京奥运指定洗衣服务商荣昌集团，拥有超过20年的洗衣管理经验，为您的爱衣提供最专业的洗涤品质保障。我们创新的手机预约上门取送、72小时送回、超值按袋洗衣、优质的按件清洗，致力为您的生活提供更多便利。</p>
    <img alt="Service 1" src="css/weixin/ccsjsp/info/service_1.png" style="margin: 0 auto;width:100%;height: 100%;margin-bottom:20px">
    <h3>洗涤，专业品质</h3>
    <p>速帮团队拥有超过20年的洗衣管理经验，深知最好的洗衣加工店在哪里以及如何让他们提供最好的服务。除了基本的资质、品牌、店面面积外，速帮对加工商使用的设备，技师的等级水平、工作年限，店内员工数量，清洗流程等均有着严格的要求。我们给加工商制定了“123”洗衣法和质量把控的“365”标准，保证了我们的清洗品质高于市面上洗衣店的洗衣质量。</p>
    <img alt="Service 2" src="css/weixin/ccsjsp/info/service_2.png" style="margin: 0 auto;width:100%;height: 100%;margin-bottom:20px">
    <h3>取送，快速安全</h3>
    <p>速帮的衣物取送工作都交给社区里有固定住所的“速帮管家”。他们可能就是你家楼下的张大姐，隔壁楼的小刘。他们可能在您下单后几分钟内就能过来取衣服了。把衣服交给“速帮管家”，放心、踏实，亲切！我们通过各种流程控制保障衣物的快速取送和洗涤，减少您的等待时间。</p>
    <h3>便宜，性价比高</h3>
    <p>强悍的议价能力和去门店化运作帮助速帮为您提供最为贴心的衣物清洗价格，仅有传统洗衣店50%价格甚至更低。更有超值的“按袋计费”99元/袋，装多少件衣服都是99元。一袋可装下33件衬衫以及124条丝巾。</p>
    <h3>保障，先行赔付</h3>
    <p>速帮深知洗衣加工行业的投诉率是不可回避的问题，面对投诉，速帮一直抱着积极面对、快速解决的态度去处理。为了确保用户的体验和权益，速帮提供了远高于行业标准的赔付政策（最高赔偿5000元）。执行“先行赔付”来提高服务满意度，即速帮先出钱赔付给用户，解决后再和洗衣店确认责任；“7天内解决“是确保我们在7天内给到用户满意的答复；“100%耐心服务”是对我们服务态度的要求。</p>
    <p>希望我们的用心服务能让您的生活更美好！感谢您对速帮的支持。</p>
  </div>
</div>
</body>
</html>