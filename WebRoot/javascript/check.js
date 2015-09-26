function checkSame(){
	password = document.getElementById("password").value;
	password1 = document.getElementById("password1").value;
	if(password==password1){
		return true;
	}		
	else{
		pswerr = document.getElementById("pswerr");
		pswerr.innerText="两次输入密码不一致。";
		return false;
	}		
}