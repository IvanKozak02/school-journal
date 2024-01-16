let inputField = document.querySelector(".input-subject .subject-area");
let saveSubPlan = document.querySelector(".save-subject-plan");

let nextLesson = document.querySelector(".next-lesson");
let homeworkDone = document.querySelector(".homework-done");
let setUpDate = document.querySelector(".set-up-date");

let saveSub = document.querySelector(".save-subject");

let homeworkDoneDate = document.querySelector(".homework-done-date");
let subjectArea = document.querySelector(".subject");
let homeworkArea = document.querySelector(".homework");

let table;

initializeTable()

document.querySelector(".save-subject-plan").addEventListener("click", ()=>{
	if (document.querySelector(".input-subject .subject-area").value === "") {
		document.querySelector(".input-subject .subject-area").parentElement.setAttribute("data-error", "Поле не може бути порожнім");
		document.querySelector(".save-subject-plan").setAttribute("disabled","disabled");

	}
	else if (document.querySelector(".set-up-date").hasAttribute("checked") && document.querySelector(".homework-done-date").value === "") {
		document.querySelector(".homework-done-date").parentElement.setAttribute("date-error", "Оберіть дату");
		document.querySelector(".homework-done-date").value = "Оберіть дату";
		document.querySelector(".save-subject-plan").setAttribute("disabled","disabled");
	}
	else{
		saveCalendarPlan();
	}
})

inputField.addEventListener("input", ()=>{

	inputField.parentElement.removeAttribute("data-error");
	document.querySelector(".save-subject-plan").removeAttribute("disabled");
})

homeworkDoneDate.addEventListener("input", ()=>{
	homeworkDoneDate.removeAttribute("date-error");
	document.querySelector(".save-subject-plan").removeAttribute("disabled");
})



let bt = document.querySelectorAll(".air-datepicker-cell");

bt.forEach(el =>{
	el.addEventListener("click", ()=>{
		homeworkDone.removeAttribute("date-error");
		document.querySelector(".save-subject-plan").removeAttribute("disabled");
	});
})


setUpDate.addEventListener("change",()=>{
	document.querySelector(".set-up-date").setAttribute("checked", "checked");
	nextLesson.removeAttribute("checked");
	homeworkDone.style.display = "block";
	saveSub.style.paddingTop = "60px";
})

nextLesson.addEventListener("change",()=>{
	setUpDate.removeAttribute("checked");
	nextLesson.setAttribute("checked", "checked");
	homeworkDone.style.display = "none";
	saveSub.style.paddingTop = "20px";
	document.querySelector(".save-subject-plan").removeAttribute("disabled","disabled");
})


let update = document.querySelectorAll(".update");
let tableRecordDate = document.querySelectorAll(".record");
// let tableRecordSubject = document.querySelectorAll(".record-subject");
// let tableRecordHomework = document.querySelectorAll(".record-house-work");
// let tableRecordHomeworkDate = document.querySelectorAll(".record-house-work-date");
// let lessonDateHeader = document.querySelector(".lesson-date-header");

function showCalendarPlan(i){
	document.getElementById("scroll").scrollTop = 0;
	document.querySelector(".lesson-date-header").innerText = document.querySelectorAll(".record")[i].innerText;
	document.querySelector(".subject-lesson").value = document.querySelectorAll(".record-subject")[i].innerText;
	document.querySelector(".homework").value = document.querySelectorAll(".record-house-work")[i].innerText;
	document.querySelector(".input-subject .subject-area").parentElement.removeAttribute("data-error");
	document.querySelector(".save-subject-plan").removeAttribute("disabled");
	document.querySelector(".homework-done-date").parentElement.removeAttribute("date-error", "Оберіть дату");
	document.querySelector(".homework-done-date").value = document.querySelectorAll(".record-house-work-date")[i].innerText;
	document.querySelector('.save-subject-plan').setAttribute("edit", "edit");
	getOldObject(i);
	if (document.querySelector(".homework-done-date").value !== "") {
		document.querySelector(".set-up-date").setAttribute("checked", "checked");
		document.querySelector(".next-lesson").removeAttribute("checked");
		document.querySelector(".next-lesson").checked = false;
		document.querySelector(".set-up-date").checked = true;
		document.querySelector(".homework-done").style.display = "block";
	}
	else{
		document.querySelector(".next-lesson").checked = true;
		document.querySelector(".set-up-date").checked = false;
		document.querySelector(".homework-done").style.display = "none";

	}
	document.querySelector("#modalTwo").style.display = "block";
}

// for (let i = 0; i < document.querySelectorAll(".update").length; i++) {
// 	document.querySelectorAll(".update")[i].addEventListener("click", ()=>{
// 		document.getElementById("scroll").scrollTop = 0;
// 		document.querySelector(".lesson-date-header").innerText = document.querySelectorAll(".record")[i].innerText;
// 		document.querySelector(".subject-lesson").value = document.querySelectorAll(".record-subject")[i].innerText;
// 		document.querySelector(".homework").value = document.querySelectorAll(".record-house-work")[i].innerText;
// 		document.querySelector(".input-subject .subject-area").parentElement.removeAttribute("data-error");
// 		document.querySelector(".save-subject-plan").removeAttribute("disabled");
// 		document.querySelector(".homework-done-date").parentElement.removeAttribute("date-error", "Оберіть дату");
// 		document.querySelector(".homework-done-date").value = document.querySelectorAll(".record-house-work-date")[i].innerText;
// 		document.querySelector('.save-subject-plan').setAttribute("edit", "edit");
// 		getOldObject(i);
// 		if (document.querySelector(".homework-done-date").value !== "") {
// 			document.querySelector(".set-up-date").setAttribute("checked", "checked");
// 			document.querySelector(".next-lesson").removeAttribute("checked");
// 			document.querySelector(".next-lesson").checked = false;
// 			document.querySelector(".set-up-date").checked = true;
// 			document.querySelector(".homework-done").style.display = "block";
// 		}
// 		else{
// 			document.querySelector(".next-lesson").checked = true;
// 			document.querySelector(".set-up-date").checked = false;
// 			document.querySelector(".homework-done").style.display = "none";
//
// 		}
// 	})
// }

function getOldObject(i){
	let journalId = document.querySelector(".table-wrapper").getAttribute("journalId");
	let dateOfLesson = document.querySelectorAll(".record")[i].innerText;
	let lessonTopic = document.querySelectorAll(".record-subject")[i].innerText;
	let homework = document.querySelectorAll(".record-house-work")[i].innerText;
	let dateOfHomework = document.querySelectorAll(".record-house-work-date")[i].innerText;


	let calObj = journalId + "," + dateOfLesson + "," + lessonTopic + "," + homework + "," + dateOfHomework;

	document.querySelector(".save-subject-plan").setAttribute("oldObj", calObj);


}

function saveCalendarPlan(){
	let journalId = document.querySelector(".table-wrapper").getAttribute("journalId");
	let table = document.querySelector(".table-wrapper");
	let tbody = table.querySelector("tbody");
	let tr = tbody.querySelectorAll("tr");
	let dateOfLesson;
	if (document.querySelector(".save-subject-plan").getAttribute("edit") === "edit"){

		dateOfLesson = document.querySelector(".lesson-date-header").innerText;
	}
	else {
		if (tr.length === 1 && tr[0].classList.contains("insert")){
			dateOfLesson = 1;
		}
		else if (tr.length === 1 && !tr[0].classList.contains("insert")) {
			dateOfLesson = 0;
		}
		else {
			dateOfLesson = tr.length;
		}
	}

	let lessonTopic = document.querySelector(".subject-lesson").value;
	let homework = document.querySelector(".homework").value;

	let dateOfHomework = document.querySelector(".homework-done-date").value;
	if (dateOfHomework === '' || document.querySelector(".next-lesson").checked === true){
		dateOfHomework = 'n';
	}


	let semester = document.querySelector(".semester").value;

	let obj = {journalId:journalId,dateOfLesson:dateOfLesson,lessonTopic:lessonTopic,homework:homework,dateOfHomework:dateOfHomework, semester:semester};
	const formData = new FormData();
	if (document.querySelector(".save-subject-plan").getAttribute("edit") === "edit"){
		let o = document.querySelector(".save-subject-plan").getAttribute("oldObj");
		let oldObj = {journalId:o.split(",")[0], dateOfLesson: o.split(",")[1],lessonTopic: o.split(",")[2],
			homework: o.split(",")[3], dateOfHomework: o.split(",")[4]};
		formData.append("newObj", JSON.stringify(obj));
		formData.append("oldObj", JSON.stringify(oldObj));

		editCalendarPlan(formData,journalId);

	}
	else {
		formData.append("jsonData", JSON.stringify(obj));
		addNewPlanToDB(formData, journalId);
	}
}

async function editCalendarPlan(formData,journalId){
	let res = await fetch("http://localhost:8080/edit-calendar-planning", {
		method: 'POST',
		headers: {
			Accept: 'application/json'
		},
		body: formData
	}).then(response=>{
		if (response.ok){
			window.location.href = "http://localhost:8080/calendar-planning/?journalId=" + journalId;
		}
		else {
			alert("Помилка");
		}
	});
}


async function addNewPlanToDB(formData,journalId){
	let res = await fetch("http://localhost:8080/calendar-planning", {
		method: 'POST',
		headers: {
			Accept: 'application/json'
		},
		body: formData
	}).then(response=>{
		if (response.ok){
			window.location.href = "http://localhost:8080/calendar-planning/?journalId=" + journalId;
		}
		else {
			alert("Помилка");
		}
	});
}


document.querySelector(".semester").addEventListener("change", async () => {
	table.destroy();
	let semester = document.querySelector(".semester").value;
	let journalId = document.querySelector(".table-wrapper").getAttribute("journalId");
	let calRecord = await getAllCalRecord(parseInt(journalId), semester).then(res => {
		return res;
	});
	if (calRecord.length > 0){
		fillTableByCalRecords(calRecord);
	}
	else {
		let table = document.getElementById("example");
		let tbody = table.querySelector("tbody");
		tbody.innerHTML = '';
	}
	initializeTable();

});

function fillTableByCalRecords(calRecord){
	let table = document.getElementById("example");
	let tbody = table.querySelector("tbody");
	tbody.innerHTML = '';

	for (let i = 0; i < calRecord.length; i++) {
		tbody.innerHTML += `<tr class="insert">
                <td class="record">${calRecord[i].dateOfLesson}</td>
                <td class="record-subject">${calRecord[i].lessonTopic}</td>
                <td class="record-house-work">${calRecord[i].homework}</td>
                <td class="record-house-work-date">${calRecord[i].dateOfHomework}</td>
                <td><div class="update-or-delete">
                        <div class="update">
                            <i class="fa-solid fa-pen-to-square"></i>
                        </div>
                        <div class="delete">
                            <i class="fa-solid fa-trash-arrow-up"></i>
                        </div>
                    </div>
                </td>
            </tr>`;
	}
}


function getSemester(semester){
	switch (semester){
		case "I семестр":
			return 1;
		case "II семестр":
			return 2;
	}
}


async function getAllCalRecord(journalId, semester){
	return fetch("http://localhost:8080/all-calendar-records/?journalId=" + journalId + "&semester=" + semester, {}).then(res => res.json())
		.then((json) => {
			return json
		});
}


function initializeTable(){
	table = new DataTable('#example',{
		fixedHeader: true,
		ordering:false,
		pageLength:100,
		language: {
			url: "//cdn.datatables.net/plug-ins/1.13.4/i18n/uk.json"
		}
	});
}