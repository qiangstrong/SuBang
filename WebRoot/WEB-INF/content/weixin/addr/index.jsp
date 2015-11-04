<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta
	content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=no"
	name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<meta content="email=no" name="format-detection">
<meta content="" name="pgv">
<base href="<%=basePath%>">
<title>常用地址</title>
<link href="/css/weixin/ccsjsp/addr/index.css" rel="stylesheet"
	type="text/css">
<link href="/css/weixin/ccsjsp/addr/base.css" rel="stylesheet"
	type="text/css">
<link href="/css/weixin/ccsjsp/addr/footer.css" rel="stylesheet"
	type="text/css">
<link href="/css/weixin/ccsjsp/addr/order.css" rel="stylesheet"
	type="text/css" media="all">

<script src="/css/weixin/ccsjsp/addr/hm.js"></script>
<script type="text/javascript"
	src="/css/weixin/ccsjsp/addr/jquery-1.8.2.min.js"></script>
</head>

<body style="padding-bottom: 60px; zoom: 1;">
	<!--目前没有地址时  -->
	<c:if test="${addrDetails.size()==0}">

		<div class="p5"></div>
		<div id="addr_wrap">
			<div>
				<div class="cardexplain no-coupon">
					<img src="/css/weixin/ccsjsp/addr/no_adress.png">
					<p>您还没有添加地址</p>
				</div>
			</div>
		</div>

	</c:if>
	<!-- 目前有地址时 -->
	<c:if test="${addrDetails.size()>0}">
		<div class="p5"></div>
		<div id="addr_wrap">
			<div id="all_address">
				<div data-role="content">
					<table class="order-place-table swipe-delete" cellpadding="0"
						cellspacing="0" data-role="listview">
						<tbody>
							<c:forEach var="addrDetail" items="${addrDetails}">
								<tr id="address_list_2478947" class="address_list">
									<td style="width:20px;background:#fff"></td>
									<td style="padding:0 0px">
										<div class="ui-btn ui-icon-carat-r">
											<div>
												<span ontouchstart="" class="bigf address_text">${addrDetail.name
													}</span> <span>${addrDetail.cellnum }</span><span>${addrDetail.cityname
													}</span><span>${addrDetail.districtname }</span>

											</div>
											<p ontouchstart="" class="address_text p_address">
												${addrDetail.regionname } ${addrDetail.detail }</p>


											<img src="/css/weixin/ccsjsp/addr/order_arrow.png"
												class="order_arrow">
										</div> <!-- 删除按钮 -->
										<div class="behind">
											<a class="delete-btn ui-btn "
												onclick="show_tip(&#39;2478947&#39;)"> <img
												src="/css/weixin/ccsjsp/addr/address_delete.png"> </a>
										</div> <input type="hidden" value="" id="order_address_id">
										<input type="hidden" value="" id="delete_id">
										<div class="borderD"></div></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<div
						style="height:90px;color:#8a8a8a;text-align:right;padding:14px">左划列表可删除地址</div>
				</div>
			</div>
		</div>
	</c:if>









	<!-- 确定删除 start -->
	<div id="confirm_win" style="display:none" class="cod">
		<div class="wx_mask"></div>
		<div class="wx_confirm">
			<div class="wx_confirm_inner" style="padding:0% 0 0 0;width:100%">
				<div class="wx_confirm_hd bigfont p30">确定删除吗？</div>
				<input type="hidden" id="delete_id">
				<div class="borderD"></div>
				<div class="wx_confirm_btns" style="position:relative">
					<button type="cancel" onclick="close_tip();" id="codGoPay">取消</button>
					<button type="submit"
						onclick="javascript:window.location.href='weixin/addr/delete.html?addrid=${addrDetail.id}'"
						id="gotodelete">确定</button>
					<div class="borderL"></div>
				</div>
			</div>

		</div>
	</div>
	<!-- 确定删除 end -->

	<div id="delete_address_show" style="display:none" class="cod">
		<div class="wx_confirm ">
			<div class="wx_confirm_inner" id="wx_confirm_float">
				<div class="wx_confirm_hd">
					<div class="wx_confirm_tit" id="show_mes"></div>
				</div>
			</div>
		</div>
	</div>

	<div class="fixed-bottom white-bg">
		<a href="weixin/addr/showadd.html" target="_blank">
			<div class="borderD2"></div>
			<div class="choose-time ">
				<img src="/css/weixin/ccsjsp/addr/icon_add.png"> <span
					class="am-button"> 添加地址</span>
			</div> </a>
	</div>

	<script type="text/javascript">
	  
	  
	  $(function() {
	      var x;
	      $('.swipe-delete tr div.ui-btn')
	          .on('touchstart', function(e) {
	
	              $('.swipe-delete tr div.ui-btn').css('left', '0px') 
	              $(e.currentTarget).addClass('open')
	              x = e.originalEvent.targetTouches[0].pageX 
	          })
	          .on('touchmove', function(e) {
	              var change = e.originalEvent.targetTouches[0].pageX - x
	              change = Math.min(Math.max(-76, change), 0) 
	              e.currentTarget.style.left = change + 'px'
	              if (change < -4) disable_scroll() 
	          })
	          .on('touchend', function(e) {
	              var left = parseInt(e.currentTarget.style.left)
	              var new_left;
	              if (left < -5) {
	                  new_left = '-76px'
	              } else {
	                  
	                  new_left = '0px'
	              }
	              // e.currentTarget.style.left = new_left
	              $(e.currentTarget).animate({left: new_left}, 200)
	              enable_scroll()
	          });
	
	  });
	  
	  function prevent_default(e) {
	      e.preventDefault();
	  }
	
	  function disable_scroll() {
	      $(document).on('touchmove', prevent_default);
	  }
	
	  function enable_scroll() {
	      $(document).unbind('touchmove', prevent_default)
	  }
	 /*
	  function toDeleteAddress(){
	      $("#confirm_win").hide();
		  var delete_id = $("#delete_id").val();
		  var sign = "delete";
		  var url = "mobile.php?m=wap&act=address&do=manage_address&city_id=1&mark=66d5e0da-65b8-11e5-a990-5cb901892a54";
	      var submit_data = {address_id:delete_id,sign:sign};
          $.post(url, submit_data, function(data){
        	    var len = $('#addr_wrap tr.address_list:visible').length;
        		if(len <= 1){
                	// alter_to_url('地址删除成功', "mobile.php?m=wap&act=address&do=address_list&city_id=1&mark=66d5e0da-65b8-11e5-a990-5cb901892a54");
                  window.location = "mobile.php?m=wap&act=address&do=address_list&city_id=1&mark=66d5e0da-65b8-11e5-a990-5cb901892a54";
            	}else{
                 	$('#address_list_' + delete_id).fadeOut(500, function(){
                   		$(this).remove();
                	});
            	}
          });
	   }
	   */
	   
	   function show_tip($delete_id){
			$("#confirm_win").show();
			$("#delete_id").val($delete_id);
	    
			return false;
		}
	   
	   function close_tip(){
			$("#confirm_win").hide();
			return false;
		}
	   
	  function alert_1(title) {
	    	$("#delete_address_show #show_mes").html(title);
	    	$("#delete_address_show").show().delay(1500).hide(0);
	  }
	  
	  function alter_to_url(title,url) {
	      alert_1(title);
	      setTimeout("window.location.href='" + url + "'", 500);
	  }
	  //跳转地址编辑页面
	  /*function editAddress(o){
		  var url = $(o).attr('href');
		  window.location = url;
	  }*/
	</script>
</body>
</html>