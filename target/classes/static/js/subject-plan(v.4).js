let subjectPlanBtn = document.querySelector('.subject-plan');
let journalBtn = document.querySelector(".journal-btn");
let scales = document.querySelector(".mark-scale");
let journalTeacher = document.querySelector(".journal-teacher");
let lessonsDate = document.querySelector(".lessons-date");
let tableWrapper = document.querySelectorAll(".table-wrapper");
let markLinks = document.querySelectorAll(".mark-link");
let addRecord = document.querySelector(".add-record");


subjectPlanBtn.addEventListener("click", function () {
	scales.style.display = "none";
	journalTeacher.style.display = "none";
	lessonsDate.style.display = "none";

	tableWrapper[0].style.display = "none";
	tableWrapper[1].style.display = "block";
	addRecord.style.display = "block";


})


journalBtn.addEventListener("click", function () {
	scales.style.display = "flex";
	journalTeacher.style.display = "block";
	lessonsDate.style.display = "block";
	tableWrapper[1].style.display = "none";
	tableWrapper[0].style.display = "block";
	addRecord.style.display = "none";



})

if (markLinks[0].id === "active") {
	tableWrapper[1].style.display = "none";
	addRecord.style.display = "none";
}



let addRec = document.getElementById("add-record-btn");
let popupAddRecWindow = document.getElementById("modalTwo");
let updateRec = document.querySelectorAll(".update");

let subject = document.getElementById("subject");

let record = document.querySelectorAll(".record");
let inputRec = document.querySelector("#subject");


let form = document.getElementById("scroll");

addRec.addEventListener("click", function(){
	document.getElementById("scroll").scrollTop = 0;
	document.querySelector(".lesson-date-header").innerText = "";
	document.querySelector(".add-column-text").innerText = document.querySelector(".subject").innerText;
	document.getElementById("modalTwo").style.display = "block";
	document.querySelector(".save-subject").style.paddingTop = "20px";
	document.querySelector(".input-subject .subject-area").parentElement.removeAttribute("data-error");
	document.querySelector(".save-subject-plan").removeAttribute("disabled");
	document.querySelector(".homework-done-date").parentElement.removeAttribute("date-error", "Оберіть дату");
	document.querySelector(".next-lesson").checked = true;
	document.querySelector(".set-up-date").checked = false;
	document.querySelector(".homework-done").style.display = "none";
	document.querySelector(".homework-done-date").value = "";
	document.querySelector(".homework").value = "";
	document.querySelector(".subject-lesson").value = "";
	document.querySelector('.save-subject-plan').removeAttribute("edit");
})

// for (let i = 0; i < document.querySelectorAll(".update").length; i++) {
// 	document.querySelectorAll(".update")[i].addEventListener("click", function(){
// 		showPopUp(i);
//
// 	})
// }
//
//
// function showPopUp(i){
// 	document.querySelector("#modalTwo").style.display = "block";
// 	document.querySelector(".add-column-text").value = document.querySelectorAll(".record")[i].innerText;
//
// }

