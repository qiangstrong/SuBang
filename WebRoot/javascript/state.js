function isSelectValue(objSelect,tarValue){
	var isExist=false;
    for (var i = 0; i < objSelect.options.length; i++) {        
        if (objSelect.options[i].value == tarValue) {        
        	isExist = true;        
            break;        
        }        
    }  
    return isExist;
}

function restoreSearchState(stateType,stateArg){
	var objSelect=document.getElementById("type");
	var objInput=document.getElementById("arg");
	if(isSelectValue(objSelect,stateType)){
		objSelect.value=stateType;
		objInput.value=stateArg;
	}
}