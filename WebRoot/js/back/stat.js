function swicthStatState(){
	if(document.getElementById("id0").checked){
		disable(2);
		document.getElementById("id00").checked=true;
	}else{
		disable(1);
		document.getElementById("id10").checked=true;
	}
}

function restoreStatState(type0,type1,type2){
	if(type0==1){
		disable(2);
		document.getElementById("id0").checked=true;
		if(type1==0){
			document.getElementById("id00").checked=true;
		}else{
			document.getElementById("id01").checked=true;
		}
		document.getElementById("id02").value=type2
	}else if(type0==2){
		disable(1);
		document.getElementById("id1").checked=true;
		if(type1==0){
			document.getElementById("id10").checked=true;
		}else{
			document.getElementById("id11").checked=true;
		}
	}
}

function disable(type0){
	if(type0==1){
		document.getElementById("id00").disabled=true;
		document.getElementById("id01").disabled=true;
		document.getElementById("id02").disabled=true;
		document.getElementById("id10").disabled=false;
		document.getElementById("id11").disabled=false;
	}else if(type0==2){
		document.getElementById("id00").disabled=false;
		document.getElementById("id01").disabled=false;
		document.getElementById("id02").disabled=false;
		document.getElementById("id10").disabled=true;
		document.getElementById("id11").disabled=true;
	}
}