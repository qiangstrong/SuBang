//切换显示隐藏
function toggle(targetid) {
	target = document.getElementById(targetid);
	if (target.style.display == "block") {
		target.style.display = "none";
	} else {
		target.style.display = "block";
	}
}

function getData(sourceid,url,callback){
	var sourceValue=document.getElementById(sourceid).value; 
	$.ajax({
		url:url,
		data:sourceid+"="+sourceValue,
		type:"post",
		cache:false,
		dateType:'application/json; charset=utf-8',
		error:function(){	
			alert("error occured!");
		},
		success:callback	
	});	
}