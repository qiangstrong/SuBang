function isSelectValue(objSelect, tarValue) {
	var isExist = false;
	for ( var i = 0; i < objSelect.options.length; i++) {
		if (objSelect.options[i].value == tarValue) {
			isExist = true;
			break;
		}
	}
	return isExist;
}

function restoreSearchState(stateType, stateArg) {
	var objSelect = document.getElementById("type");
	var objInput = document.getElementById("arg");
	if (isSelectValue(objSelect, stateType)) {
		objSelect.value = stateType;
		objInput.value = stateArg;
	}
}

// 与订单相关的状态恢复
function switchOrderState() {
	var typeSelect = document.getElementById("type");
	var argSelect = document.getElementById("arg0");
	var argText = document.getElementById("arg1");
	if (typeSelect.value == 2) {
		argSelect.disabled = false;
		argText.disabled = true;
	} else {
		argSelect.disabled = true;
		argText.disabled = false;
	}
}

function restoreOrderState(stateType, stateArg) {
	var typeSelect = document.getElementById("type");
	var argSelect = document.getElementById("arg0");
	var argText = document.getElementById("arg1");
	if (isSelectValue(typeSelect, stateType)) {
		typeSelect.value = stateType;
		if (stateType == 2) {
			argSelect.disabled = false;
			argText.disabled = true;
			argSelect.value = stateArg;
		} else {
			argSelect.disabled = true;
			argText.disabled = false;
			argText.value = stateArg;
		}
	}
}
