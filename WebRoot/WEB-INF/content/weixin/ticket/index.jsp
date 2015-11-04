<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta charset="utf-8">
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no" name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta content="" name="pgv">
<base href="<%=basePath%>">
<title>我的优惠券</title>
<link href="css/weixin/ccsjsp/ticket/index/footer.css" rel="stylesheet" type="text/css">
<link href="css/weixin/ccsjsp/ticket/index/base.css" rel="stylesheet" type="text/css">
<link href="css/weixin/ccsjsp/ticket/index/card.css" rel="stylesheet" type="text/css" media="all">
<link href="css/weixin/ccsjsp/ticket/index/wap.css" rel="stylesheet" type="text/css">

<script src="css/weixin/ccsjsp/ticket/index/hm.js"></script><script type="text/javascript" src="css/weixin/ccsjsp/ticket/index/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="css/weixin/ccsjsp/ticket/index/jscommon.js"></script>
</head>
<body style="padding-bottom: 68px; zoom: 1;">
<div id="second" style="margin-top:0px;">
  <div class="headerPlace header-address" style="margin-bottom:0px">
      <div class="choose-time">
        <span class="">我的优惠券</span>
        <span class="pull-right"> 
            <a href="weixin/ticket/intro.html" style="padding:2px 6px"> 使用说明</a>
        </span>
      </div>
  </div>
  <div class="borderD"></div>
    <div class="coupon-btn-long">
        <div id="coupon-btn-style" class="coupon-btn-style">
          <div class="borderD2"></div>
          <img src="css/weixin/ccsjsp/ticket/index/icon_add.png">
          <span>兑换优惠券</span>
          <div class="borderD2"></div>
        </div>
    </div>
    <div class="cardexplain white_bg" style="margin:0;display:none" id="cardExplain">
      <div class="cod">
        <div class="wx_mask"></div>
        <div class="wx_confirm ">
          <div class="wx_confirm_inner" id="wx_confirm_coupon">
            <div class="wx_confirm_hd" style="margin-bottom:0;padding-top:10px">
                <div class="wx_confirm_tit" id="ump_color_gray">兑换优惠券</div>
                <form action="javascript:;" method="POST">
                  <div class="inputbox">
                    <input id="coupon_sn" name="bind_sn_coupon" type="text" placeholder="输入优惠码" class="bind_sn_coupon #coupon_sn">
                  </div>
                  <div id="coupon_tip" class="coupon_tip" style="display:none"></div>
                  <div class="floatBorder floatbox">
                     <a id="codGoPayCancel" class="cancel single_input2">取消</a>
                     <a id="exchange" class="single_input2" href="javascript:void(0);" ontouchstart="">兑换</a>
                  </div>
                </form>
                <div class="clearBoth"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
</div>
<div style="border-top:1px solid #eee;"></div>



<div id="coupon_list">
  <div class="kaixi" style="text-align: left; padding: 0 0 10px 20px; color:  #8a8a8a">快洗不支持优惠券哦~</div>
  <div class="ump-coupon-wrap">
    <ul class="ump-coupon-list ">
      <li class="ump-coupon-item coupon-style-3" id="li_0" jump="" func=""> 
        <table width="100%">
          <tbody>
            <tr>
              <td width="36%" align="center"><div class="ump-coupon-value color-gaoduan">
                  <p>高端服饰券</p>
                  <span>￥</span><i>70</i> </div></td>
              <td align="left"><p class="coupon-title"> 换季洗衣大礼包</p>
                <p class="limit-font"> 满199可用  
                  无支付限制 </p>
                <p class="limit-font"> </p>
                <p class="limit-font"> 2015-10-21-2015-10-31 </p></td>
            </tr>
          </tbody>
        </table>
        <img src="css/weixin/ccsjsp/ticket/index/choose_shechi.png" class="select-icon" id="select-icon_27382690" style="display:none"> </li>
      <li class="ump-coupon-item coupon-style-1" id="li_1" jump="" func=""> 
        <table width="100%">
          <tbody>
            <tr>
              <td width="36%" align="center"><div class="ump-coupon-value color-chuanglian">
                  <p>窗帘券</p>
                  <span>￥</span><i>60</i> </div></td>
              <td align="left"><p class="coupon-title"> 换季洗衣大礼包</p>
                <p class="limit-font"> 满169可用  
                  无支付限制 </p>
                <p class="limit-font"> </p>
                <p class="limit-font"> 2015-10-21-2015-10-31 </p></td>
            </tr>
          </tbody>
        </table>
        <img src="css/weixin/ccsjsp/ticket/index/choose_chuanglian.png" class="select-icon" id="select-icon_27382689" style="display:none"> </li>
      <li class="ump-coupon-item coupon-style-0" id="li_2" jump="" func=""> 
        <table width="100%">
          <tbody>
            <tr>
              <td width="36%" align="center"><div class="ump-coupon-value color-cloth">
                  <p>洗衣券</p>
                  <span>￥</span><i>20</i> </div></td>
              <td align="left"><p class="coupon-title"> 换季洗衣大礼包 </p>
                <p class="limit-font"> 满69可用  
                  无支付限制 </p>
                <p class="limit-font"> </p>
                <p class="limit-font"> 2015-10-21-2015-10-31 </p></td>
            </tr>
          </tbody>
        </table>
        <img src="css/weixin/ccsjsp/ticket/index/choose_clothes.png" class="select-icon" id="select-icon_27382688" style="display:none"> </li>
      <li class="ump-coupon-item coupon-style-5" id="li_3" jump="" func=""> 
        <table width="100%">
          <tbody>
            <tr>
              <td width="36%" align="center"><div class="ump-coupon-value color-tongy">
                  <p>不限品类券</p>
                  <span>￥</span><i>10</i> </div></td>
              <td align="left"><p class="coupon-title"> 换季洗衣大礼包 </p>
                <p class="limit-font"> 满25可用  
                  无支付限制 </p>
                <p class="limit-font"> </p>
                <p class="limit-font"> 2015-10-21-2015-10-31 </p></td>
            </tr>
          </tbody>
        </table>
        <img src="css/weixin/ccsjsp/ticket/index/choose_07.png" class="select-icon" id="select-icon_27382680" style="display:none"> </li>
      <li class="ump-coupon-item coupon-style-0" id="li_4" jump="" func=""> 
        <table width="100%">
          <tbody>
            <tr>
              <td width="36%" align="center"><div class="ump-coupon-value color-cloth">
                  <p>洗衣券</p>
                  <span>￥</span><i>40</i> </div></td>
              <td align="left"><p class="coupon-title"> 换季洗衣大礼包 </p>
                <p class="limit-font"> 满99可用  
                  无支付限制 </p>
                <p class="limit-font"> </p>
                <p class="limit-font"> 2015-10-21-2015-10-31 </p></td>
            </tr>
          </tbody>
        </table>
        <img src="css/weixin/ccsjsp/ticket/index/choose_clothes.png" class="select-icon" id="select-icon_27382681" style="display:none"> </li>
      <li class="ump-coupon-item coupon-style-2" id="li_5" jump="" func=""> 
        <table width="100%">
          <tbody>
            <tr>
              <td width="36%" align="center"><div class="ump-coupon-value color-shoes">
                  <p>洗鞋券</p>
                  <span>￥</span><i>10</i> </div></td>
              <td align="left"><p class="coupon-title"> 洗鞋专享红包</p>
                <p class="limit-font"> 满38可用  
                  无支付限制 </p>
                <p class="limit-font"> </p>
                <p class="limit-font"> <span style="color:#fd4747">2015-10-29过期(后天)</span> </p></td>
            </tr>
          </tbody>
        </table>
        <img src="css/weixin/ccsjsp/ticket/index/choose_shoes.png" class="select-icon" id="select-icon_27367638" style="display:none"> </li>
    </ul>
  </div>
</div>





<div id="coupon_list">
      <div class="empty_Coupons cardexplain no-coupon">
      <img src="css/weixin/ccsjsp/ticket/index/no_coupon.png">
      <p>无可用优惠券</p>
    </div>
  </div>   
<div class="back-home">
  <a href="weixin/region/index.html">
    <img src="css/weixin/ccsjsp/ticket/index/btn_home.png">
  </a>
</div>

<!-- 使用优惠券 -->
<script type="text/javascript">
var flag = 1;
$(document).ready(function (){
    $("#exchange").click(function(){
	    var submitData = {
	        bind_sn_coupon : $.trim($("#coupon_sn").val())
	    };
	    if($.trim($("#coupon_sn").val()) == ""){
	        $("#coupon_tip").text("请输入优惠码").slideDown(500).delay(500).slideUp(500);
	        return false;
	    }
	    if(flag == 1){
	        flag = 2;
	        $.post("mobile.php?m=wap&act=payment&do=bind_coupon&city_id=22&mark=null-mark", submitData, function (data){
	            if(data.message.state == 1){
	              $("#coupon_tip").text(data.message.msg).slideDown(500);
	              alertToUrl("mobile.php?m=wap&act=payment&do=coupon_list&city_id=22&mark=null-mark", data.message.msg);
	            }else{
	              flag = 1;
	              $("#coupon_tip").text(data.message.msg).slideDown(500).delay(500).slideUp(500);
	            }                       
	        },"json");
	    }
    });
});
    
$("#coupon-btn-style").click(function(){
  $("#cardExplain").show();
  $('#coupon_sn').val('');
})
 
$('#codGoPayCancel').click(function() {
  $("#cardExplain").hide();
  $("#coupon_tip").text('');
  return false;
});
 
$("#windowclosebutton").click(function() {
  $("#windowcenter").slideUp(500);
});

function alertToUrl(url,title) {
    setTimeout("window.location.href='" + url + "'", 500);
}
</script>

</body></html>