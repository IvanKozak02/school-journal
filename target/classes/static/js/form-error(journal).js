let newColForm = document.querySelector(".new-column-form");





savePlanBut.addEventListener("click", ()=>{
	if (colName.value === "") {
	    colName.parentElement.setAttribute("data-error", "Поле не може бути порожнім");
		savePlanBut.setAttribute("disabled","disabled");

	}
	else{
		newColForm.submit();
	}
})


colName.addEventListener("input", ()=>{

	colName.parentElement.removeAttribute("data-error");
	savePlanBut.removeAttribute("disabled");
})