function switchCheckboxs(name) {
	var checkboxs = document.all(name);
	if (!checkboxs)
		return;
	if (typeof (checkboxs.length) == "undefined") {
		checkboxs.checked = event.srcElement.checked;
	} else {
		for ( var i = 0; i < checkboxs.length; i++) {
			checkboxs[i].checked = event.srcElement.checked;
		}
	}
}

function getCheckedIds(name) {
	var ids = "";
	var split = ",";
	var checkboxs = document.all(name);
	if (!checkboxs)
		return null;
	if (typeof (checkboxs.length) == "undefined" && checkboxs.checked) {
		ids = checkboxs.value + split;
	} else {
		for ( var i = 0; i < checkboxs.length; i++) {
			if (checkboxs[i].checked) {
				ids += checkboxs[i].value + split;
			}
		}
	}
	return ids;
}

function submit(name, url, data) {
	var form = document.createElement("form");

	var input = document.createElement("input");
	input.setAttribute("name", name);
	input.setAttribute("type", "hidden");
	input.setAttribute("value", data);

	form.appendChild(input);
	form.method = "POST";
	form.action = url;

	document.body.appendChild(form);
	form.submit();
};