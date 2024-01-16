let deleteTab = document.querySelectorAll(".delete");
let popUpDelete = document.getElementById("modalThree");
let deleteBtn = document.querySelector(".deletebtn");
let deleteForm = document.getElementById("delete-form");
let deleteInputId = document.querySelector(".deleteId");
let cancelBtn = document.querySelector(".cancelbtn");
let sureMessage = document.querySelector(".are-you-sure");
let deleteDate = document.querySelector(".delete-date")

for (let i = 0; i < deleteTab.length; i++) {
	deleteTab[i].addEventListener("click", ()=>{
		deleteInputId.value = i;
		deleteDate.innerText = tableRecordDate[i].innerText;
		showPopUpDelete(i);
	})
}



function showPopUpDelete(i){
	popUpDelete.style.display = "block";
	document.querySelector(".deletebtn").setAttribute("subject", document.querySelectorAll(".record-subject")[i].innerText);
}



deleteBtn.addEventListener("click", ()=>{
	let journalId = document.querySelector(".table-wrapper").getAttribute("journalId");
	let date = document.querySelector(".delete-date").innerText;
	let subject = document.querySelector(".deletebtn").getAttribute("subject");
	deleteCalendarPlanRecord(journalId, date, subject);
});

async function deleteCalendarPlanRecord(journalId, date, subject) {
	let res = await fetch("http://localhost:8080/calendar-planning/?journalId=" + journalId + "&date=" + date +"&subject=" + subject, {
		method: 'DELETE',
		headers: {
			Accept: 'application/json',
		}
	}).then(response => {
		if (response.ok) {
			window.location.href = "http://localhost:8080/calendar-planning/?journalId=" + journalId;
		} else {
			alert("Помилка");
		}
	});

}




cancelBtn.addEventListener("click", ()=>{
	popUpDelete.style.display = "none";
})