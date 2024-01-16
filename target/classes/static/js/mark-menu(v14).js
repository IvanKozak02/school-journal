document.body.style.background = "#dfe9f5";

let inputMark = document.getElementById("input-mark");




let markCheckbox = document.querySelectorAll(".mark");
let checkPresence = document.querySelector(".check");


function showmenu(i){
	let ev = event;
	let td = document.querySelectorAll(".inserted");
	document.getElementById("contMenu").style.display = "none";
	if (td[i].innerText !== ''){
		document.querySelector(".check").checked = false;
	}
	document.getElementById("mark-btn").setAttribute("index", i);
	for (let i = 0; i < markCheckbox.length; i++) {
		markCheckbox[i].checked = false;
	}
	if (td[i].innerText !== "") {
		if (Number.isInteger(parseInt(td[i].innerText,10))) {
			inputMark.value = td[i].innerText;
			document.getElementById("option-1").setAttribute("checked", "checked");
			document.getElementById("option-2").removeAttribute("checked");
			document.getElementById("option-2").checked = false;
			document.getElementById("option-1").checked = true;
			document.getElementById("numbers").style.display = "block";
			document.getElementById("levels").style.display = "none";
		}
		else if (td[i].innerText !== "Н") {
			document.getElementById("option-1").removeAttribute("checked");
			document.getElementById("option-1").checked = false;
			document.getElementById("option-2").checked = true;
			document.getElementById("option-2").setAttribute("checked", "checked");
			document.getElementById("levels").style.display = "flex";
			document.getElementById("numbers").style.display = "none";

			switch (td[i].innerText) {

				case "В":
					markCheckbox[0].checked = true;

					break;
				case "Д":
					markCheckbox[1].checked = true;
					break;
				case "С":
					markCheckbox[2].checked = true;
					break;
				case "П":
					markCheckbox[3].checked = true;
					break;
				case "Н":
					checkPresence.checked = true;
					break;
			}
		}
		else {
			document.getElementById("option-1").setAttribute("checked", "checked");
			document.getElementById("option-2").removeAttribute("checked");
			document.getElementById("numbers").style.display = "block";
			document.getElementById("levels").style.display = "none";
			document.querySelector(".check").checked = true;
			document.getElementById("option-1").checked = true;
			document.getElementById("option-2").checked = false;
			inputMark.value = "";

		}
				
	}
	else{
		inputMark.value = "";
		checkPresence.checked = false;
	}
	let menu = document.querySelector(".mark-menu");
	menu.classList.remove("off");
	const{pageX:mouseX, pageY:mouseY} = ev;
		menu.style.left = `${mouseX+30}px`; 
		menu.style.top = `${mouseY-10}px`;
		let offsetTop = `${mouseY-10}`;
		let offsetLeft = `${mouseX+30}`;
		if (offsetTop < 280) {
			menu.style.top = "280px";
		}
		if(offsetTop > 500){
			menu.style.top = "510px";
		}
		if (offsetLeft> 1152  && document.getElementById("levels").style.display === "none"){
			menu.style.left = `${mouseX-150}px`;
		}
		else if (offsetLeft> 1152  && document.getElementById("levels").style.display === "flex") {
			menu.style.left = `${mouseX-300}px`;
		}
}


function getForm(estimate){
	if (!isNaN(parseInt(estimate))){
		return ""
	}
}

document.addEventListener('click', function handleClickOutsideMark(event) {
  const mark = document.getElementById('menu');

  if(!event.target.classList.contains("mark-item") && !event.target.classList.contains("number-mark") && !event.target.classList.contains("save-mark-btn") && 
  	!event.target.classList.contains("mark-input") && !event.target.classList.contains("mark-label") &&
  	!event.target.classList.contains("checkbox-horizontal")  && !event.target.classList.contains("small-block")
  	 && !event.target.classList.contains("check") && !event.target.classList.contains("presence")
  	 && !event.target.classList.contains("mark-numbers") && !event.target.classList.contains("mark")
  	 && !event.target.classList.contains("level-label")) {
  	mark.classList.add("off");
  }

});

let tableWrap = document.querySelectorAll(".table-wrapper");
tableWrap[0].addEventListener('scroll', function handleClickOutsideMark(event) {
  let mark = document.getElementById('menu');
    mark.classList.add("off");

});

let numbers = document.getElementById("numbers");
let levels = document.getElementById("levels");


document.querySelector(".option-1").addEventListener("click",function(){
	levels.style.display = "none";
	numbers.style.display = "block";
	document.getElementById("option-1").setAttribute("checked", "checked");
	document.getElementById("option-2").removeAttribute("checked");
	document.getElementById("option-1").checked = true;
	document.getElementById("option-2").checked = false;

})


document.querySelector(".option-2").addEventListener("click",function(){

	levels.style.display = "flex";
	numbers.style.display = "none";
	document.getElementById("option-2").setAttribute("checked", "checked");
	document.getElementById("option-1").removeAttribute("checked");
	document.getElementById("option-2").checked = true;
	document.getElementById("option-1").checked = false;

})

document.getElementById("mark-level-btn").addEventListener("click", ()=>{

});


document.getElementById("mark-level-btn").addEventListener("click", ()=>{
	if (checkMark()){
		getMarkData("Рівнева");
	}
})

document.getElementById("mark-btn").addEventListener("click", ()=>{
	if (checkMark()){
		getMarkData("Бальна");
	}

})

function checkMark() {
	if(document.querySelector(".check").checked && (document.getElementById("input-mark").value !== '' ||
		document.querySelectorAll(".mark:checked").length !== 0)){
		return false;
	}
	else if (document.querySelectorAll(".mark:checked").length > 1){
		return false;
	}
	return true;


}
function getMarkData(scale){
	let mark = getMark(scale);
	let journalId = document.querySelector(".table-wrapper").getAttribute("id");
	let student = getStudent();
	let colName = getColName();
	let date;
	if (colName === 'I с.' || colName === 'Ск.' || colName === 'II с.' || colName === 'Річ.'){
		if (colName === 'Ск.'){
			let ind = document.getElementById("mark-btn").getAttribute("index");
			let index = parseInt(ind);
			let cellIndex = document.querySelectorAll(".inserted")[index].cellIndex;
			date = document.querySelectorAll(".date-inserted")[cellIndex-2].innerText;
		}
		else {
			date = colName;
		}
	}
	else {
		date = getDate();
	}
	let presence = document.querySelector(".check").checked;
	if (mark === '' && presence){
		let markObj = {mark:-1,student:student,typeOfMark: scale, classJournal: journalId,date:date, colName:colName};
		const formData = new FormData();
		formData.append("jsonData", JSON.stringify(markObj));
		saveMark(formData, markObj);
	}
	else if (isNaN(parseInt(mark))){
		alert("Введений бал не є числом!!!");
	}
	else{
		let markObj = {mark:mark,student:student,typeOfMark: scale, classJournal: journalId,date:date, colName:colName};
		const formData = new FormData();
		formData.append("jsonData", JSON.stringify(markObj));
		saveMark(formData, markObj);
	}
}


async function saveMark(formData, markObj){
	await fetch("http://localhost:8080/mark", {
		method: 'POST',
		headers: {
			Accept: 'application/json',
		},
		body: formData,
	}).then(response=>{
		if (response.ok){
			fillTableByMarks(markObj);
		}
		else {
			alert("Помилка");
		}
	});
}

function fillTableByMarks(markObj) {
	let mark = markObj.mark;
	let th = markObj.date;
	let colName = markObj.colName;
	let tr = document.getElementById(markObj.student).parentElement;
	let index = findTh(th, colName);
	if (mark === -1 || mark === -2){
		mark = 'Н';
		fillMark(tr,index, mark);
	}
	else {
		if (markObj.typeOfMark === "Бальна"){
			fillMark(tr,index, mark);
		}
		else {
			let levMark = getLevelMark(mark);
			fillMark(tr, index, levMark);
		}

	}

	document.querySelector(".mark-menu").classList.add("off");
}

function getLevelMark(mark){
	switch (parseInt(mark)){
		case 4:
			return "В";
		case 3:
			return "Д";
		case 2:
			return "С";
		case 1:
			return "П";
	}
}

function fillMark(tr,index, mark){
	let td = tr.querySelectorAll(".inserted");
	for (let i = 0; i < td.length; i++) {
		if (i === index){
			td[i].innerText = mark;
			if (document.querySelectorAll(".table-wrapper")[0].getAttribute("teacher") === "YES") {
				td[i].innerHTML += `<div class="mark-item"></div>`;
			}
			break;
		}
	}
}

function findTh(th, colName){
	let head = document.querySelectorAll(".date-inserted");
	for (let i = 0; i < head.length; i++) {
		if (head[i].innerText.trim() === th){
			if (th === 'I с.'=== colName || th === 'II с.'===colName || th === 'Річ.' === colName){
				return i;
			}
			else if(colName === 'Ск.'){
				return i+1;
			}



			for (let j = i; j >= 0 ; j--) {
				if (head[j].getAttribute("colName").trim() === colName){
					return j;
				}
			}
		}
	}
}

function getColName(){
	let ind = document.getElementById("mark-btn").getAttribute("index");
	let index = parseInt(ind);
	let cellIndex = document.querySelectorAll(".inserted")[index].cellIndex;
	return document.querySelectorAll(".date-inserted")[cellIndex-1].getAttribute("colName");
}

function getDate(){
	let ind = document.getElementById("mark-btn").getAttribute("index");
	let index = parseInt(ind);
	let cellIndex = document.querySelectorAll(".inserted")[index].cellIndex;
	return findDate(cellIndex-1);
}

function getStudent(){
	let ind = document.getElementById("mark-btn").getAttribute("index");
	let index = parseInt(ind);
	let rowIndex = document.querySelectorAll(".inserted")[index].parentElement.rowIndex;
	return document.querySelectorAll(".student")[rowIndex].getAttribute("id");
}

function getMark(scale){
	if (scale === "Бальна"){
		return document.getElementById("input-mark").value;
	}
	if (document.querySelectorAll(".mark:checked").length === 1){
		return document.querySelectorAll(".mark:checked")[0].value;
	}
	else {
		return '';
	}
}



document.querySelector(".check").addEventListener("change", ()=>{
	deleteMark('Н', "mark-btn");
});

function deleteMark(scale, button){
	let td = document.querySelectorAll(".inserted")[document.getElementById(button).getAttribute("index")];
	if (scale === 'Н' && td.innerText !== 'Н'){
		return;
	}
	let tr = td.parentElement;
	let cellIndex = td.cellIndex;
	let rowIndex = tr.rowIndex;

	let colName = document.querySelectorAll(".date-inserted")[cellIndex-1].getAttribute("colName");
	let journalId = document.querySelector(".table-wrapper").getAttribute("id");
	let student = document.querySelectorAll(".student")[rowIndex].getAttribute("id");
	let date;
	if (colName === 'I с.' || colName === 'II с.' || colName === 'Річ.'){
		date = colName;

	}
	else if(colName === 'Ск.'){
		date = document.querySelectorAll(".date-inserted")[cellIndex-2].innerText.trim();
	}
	else {
		date = findDate(cellIndex-1);
	}

	let delMark = {date: date, colName: colName, classJournal:journalId, student:student};
	const formData = new FormData();
	formData.append("jsonData", JSON.stringify(delMark));
	if (document.querySelector(".check").checked === false){
		deleteMarkFromDB(formData, td);
	}
}

function findDate(index){
	let pat = new RegExp('\\d\\d\\.\\d\\d');
	let dates = document.querySelectorAll(".date-inserted");
	while (true){
		if (pat.test(dates[index].innerText)){
			return dates[index].innerText;
		}
		index++;
	}

}

async function deleteMarkFromDB(delMark, td){
	await fetch("http://localhost:8080/mark", {
		method: 'DELETE',
		headers: {
			Accept: 'application/json',
		},
		body: delMark,
	}).then(response => {
		if (response.ok) {
			clearCell(td);
			document.getElementById("contMenu").style.display = 'none';
		} else {
			alert("Помилка");
		}
	});
}

function clearCell(td){
	td.innerText = '';
	if (document.querySelectorAll(".table-wrapper")[0].getAttribute("teacher") === "YES") {
		td.innerHTML += `<div class="mark-item"></div>`;
	}
}

function getContextMenu(i){
	document.querySelector(".mark-menu").classList.add("off");
	let td = document.querySelectorAll(".inserted")[i];
	if (td.innerText !== '' && td.innerText !== 'Н'){
		let contMenu = document.getElementById("contMenu");
		const{pageX:mouseX, pageY:mouseY} = event;
		contMenu.style.left = `${mouseX+20}px`;
		contMenu.style.top = `${mouseY-10}px`;
		contMenu.style.display = "block";
		document.getElementById("delete-mark").setAttribute("index", i);
		event.preventDefault();
	}
}

document.addEventListener('click', function(e){
	let inside = (e.target.closest('.table-wrapper'));
	if((!inside || e.target.classList.contains("student")) && !e.target.classList.contains("delete-mark")){
		let contextMenu = document.getElementById('contMenu');
		contextMenu.setAttribute('style', 'display:none');
	}
});

document.querySelector(".delete-mark").addEventListener("click", ()=>{
	deleteMark('', "delete-mark");
});