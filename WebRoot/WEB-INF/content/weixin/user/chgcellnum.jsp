<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta content="" name="pgv">
<base href="<%=basePath%>">
<title>更改注册手机号码</title>
<link href="css/weixin/ccsjsp/user/index/welcome.css" rel="stylesheet" type="text/css">
<link href="css/weixin/ccsjsp/user/index/base.css" rel="stylesheet" type="text/css">
<script src="css/weixin/ccsjsp/user/index/hm.js"></script><script type="text/javascript" src="css/weixin/ccsjsp/user/index/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="css/weixin/ccsjsp/user/index/jscommon.js"></script>
</head>

<body>
<div class="welcome-index" id="wx_mask">
  <form action="javascript:void(0)" method="POST">
    <table>
      <tbody>
        <tr>
          <td width="65%"><label class="input_wrap" for="tel"> <img src="css/weixin/ccsjsp/user/index/welcome_phone.png">
              <input name="cellnum" id="cellnum" type="tel" maxlength="11" placeholder="请输入手机号" value="" onkeyup="value=value.replace(/[^\d]/g,&#39;&#39;)" onbeforepaste="clipboardData.setData(&#39;text&#39;,clipboardData.getData(&#39;text&#39;).replace(/[^\d]/g,&#39;&#39;))">
              <a href="javascript:void(0)" id="get_vcode_btn" class="btn-public verification_code">验证</a> </label></td>
        </tr>
        <tr>
          <td><label class="input_wrap" for="code"> <img src="css/weixin/ccsjsp/user/index/welcome_pas.png">
              <input name="authcode" id="authcode" type="tel" maxlength="4" placeholder="请输入验证码" onkeyup="value=value.replace(/[^\d]/g,&#39;&#39;)" onbeforepaste="clipboardData.setData(&#39;text&#39;,clipboardData.getData(&#39;text&#39;).replace(/[^\d]/g,&#39;&#39;))">
            </label></td>
        </tr>
        <tr>
          
          <td class="p10"><button id="confirmbn" class="btn-public" style="opacity:0.8" onclick="this.form.submit()">确定</button></td>
        </tr>
      </tbody>
      
    </table>
  </form>
</div>


 
<div id="codFloat" style="display:none" class="cod">
  <div class="wx_mask"></div>
  <div class="wx_confirm ">
    <div class="wx_confirm_inner" id="wx_confirm_float">
      <div class="wx_confirm_hd">
        <div class="wx_confirm_tit" id="show_mes"> </div>
      </div>
    </div>
  </div>
</div>



<div id="add"></div>

 

<script type="text/javascript">
  var user_type = "20";
  var back_url = "weixin/user/cellnum.html";///???
  var InterValObj; 	//timer变量，控制时间  
  var count = 30; 	//间隔函数，1秒执行  
  var curCount;		//当前剩余秒数 
  var flag = 1, tel_valid = false, vcode_valid = false;
  
  $(document).ready(function (){
	 // 获取验证码
     $("#get_vcode_btn").click(function(){
    	if(! chkMobile()){
    		return false;
    	}
        var submit_data = {cellnum : $("#cellnum").val()};
        if(flag == 1){
          flag = 0;
          curCount = count; 
          $.post("weixin/user/cellnum.html", submit_data, function (data){
              if(data.code=="ok"){
                setVCodeBtn();
              }else if (data.code=="err") {
                flag = 1;
                showTip(data.msg);
              };
            },"json");
        }
      });
      
	  // 校验提交
	   /*
	   $("#binding").click(function(){
    	  if(! chkMobile()){
    		  return false;
    	  }
          if($("#authcode").val() == ''){
            showTip("请输入验证码");
            return false;
          }
          if(user_type == '20' || user_type == '19'){
        	// 浏览器用户直接绑定
            confirmBinding();
          }else{
        	// 微信等用户弹出提示
            $("#confirm_tip").show();        
          }
      });
      */
      
      $("#confirmbn").click(function(){
    	  if(! chkMobile()){
    		  return false;
    	  }
          if($("#authcode").val() == ''){
            showTip("请输入验证码");
            return false;
          }
          confirmBinding();
          
      });
      
	  
	  // 监听手机号输入，设置输入法状态
  	  
  	  $('#cellnum').on('input propertychange', function(){
  		setConfirmBtn();
  	   	if (tel_valid) {
  	   		$(this).blur();
  	  	}
	  });
	  
	  // 监听验证码输入，设置输入法状态
  	  $('#authcode').on('input propertychange', function(){
  		setConfirmBtn();
  	   	if (vcode_valid) {
  	   		$(this).blur();
  	  	}
  	  });
  	  
  });
  function closeConfirmTip(){
     flag = 1;
     $("#confirm_tip").hide();
     return false;
  }
 
  function setVCodeBtn(){
	$("#get_vcode_btn").html("" + curCount + "s");  
  	InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次  
    $("#get_vcode_btn").addClass('gray_bg');
  }
  
  function SetRemainTime() {
	curCount--;  
    if (curCount == 0) {  
        flag = 1;               
        window.clearInterval(InterValObj);//停止计时器  
        $("#get_vcode_btn").removeAttr("disabled");//启用按钮  
        $("#get_vcode_btn").html("重新获取");  
        $("#get_vcode_btn").removeClass('gray_bg'); 
    }  
    else {  
        $("#get_vcode_btn").html("" + curCount+"s" );  
    }  
  }  
    
  function showTip(msg) {
  	$("#show_mes").html(msg);
  	$("#codFloat").show().delay(2000).hide(0);
  }
    
  // 验证手机号
  function chkMobile(){
      var tel = $("#cellnum").val();
      if(tel == ''){
        showTip("请输入手机号");
        return false;
      }
      var re = /^1\d{10}$/;
      if (!re.test(tel)) {
        showTip("请正确填写手机号");
        return false;
      };
      return true;
  }
  
  // 设置“立即绑定”按钮显示状态
  function setConfirmBtn(){
  	var tel = $.trim($("#cellnum").val()), tel_patt = /^1\d{10}$/;
  	tel_valid = (tel && tel_patt.test(tel)) ? true : false;
  	var vcode = $.trim($("#authcode").val()), vcode_patt = /^[0-9]{4}$/;
   	vcode_valid = (vcode && vcode_patt.test(vcode)) ? true : false;
  	if(tel_valid && vcode_valid){
   		$('#confirmbn').prop('disabled', false).css({'opacity':'1'});
   		return true;
  	}else{
   		$('#confirmbn').prop('disabled', true).css({'opacity':'0.8'});
   		return false;
  	}
  }
  
  // 确认绑定
  
  function confirmBinding(){
	  //$("#binding").prop("disabled", true);  
	  
      $("#confirm_tip").hide();
      var submit_data = {  authcode : $("#authcode").val()};
      $("#confirmbn").attr("class","btn-public select"); 
      $.post("weixin/user/chgauthcode.html", submit_data, function (data){
         if (data.code == "err") {
           flag = 1;
           $("#confirmbn").attr("class", "btn-public");
           $('#confirmbn').prop('disabled', false);           
           showTip(data.msg);
         }
          else
         {
         flag = 1;
         window.location.href = "weixin/user/index.html";}
      },"json");
  }
</script> 
</body>
</html>