let addInstructionBtn = document.querySelector(".add-instruction-btn");
let addInstructionForm = document.getElementById("modalTwo");
let inputSub = document.querySelector(".subject-area");
let saveEventBtn = document.querySelector(".save-instruction");
let periodInstr = document.querySelector(".homework-done-date");



addInstructionBtn.addEventListener("click",()=>{

    inputSub.value = "";
    periodInstr.value = ""
    inputSub.parentElement.removeAttribute("data-error");
    periodInstr.parentElement.removeAttribute("date-error");
    document.body.style.overflow = "hidden";
    document.getElementById("modalTwo").style.display = "block";
})



inputSub.addEventListener("input", ()=>{
    inputSub.parentElement.removeAttribute("data-error");
    saveEventBtn.removeAttribute("disabled");
})


periodInstr.addEventListener("click", ()=>{
    periodInstr.parentElement.removeAttribute("date-error");
    saveEventBtn.removeAttribute("disabled");
})


saveEventBtn.addEventListener("click", ()=>{
    if (inputSub.value === ""){
        inputSub.parentElement.setAttribute("data-error", "Поле не може бути порожнім");
        saveEventBtn.setAttribute("disabled","disabled");
    }
    else if (periodInstr.value === ""){
        periodInstr.parentElement.setAttribute("date-error", "Оберіть дату");
    }
    else{
        let instructionSubject = document.querySelector(".subject").value;
        let instructionDate = document.querySelector(".instruction-date").value;
        let classId = document.querySelector(".instruction-row").getAttribute("classId");
        let obj = {instructionSubject:instructionSubject, instructionDate:instructionDate, classId:classId};
        const formData = new FormData();
        formData.append("jsonData", JSON.stringify(obj));
        saveInstruction(formData, classId);
    }
})


async function saveInstruction(formData,classId){
    let res = await fetch("http://localhost:8080/instruction", {
        method: 'POST',
        headers: {
            Accept: 'application/json'
        },
        body: formData
    }).then(response=>{
        if (response.ok){
            window.location.href = "http://localhost:8080/instructions/" + classId;
        }
        else {
            alert("Помилка");
        }
    });
}


function openDeleteForm(instructionId){
    event.preventDefault();
    document.getElementById("deleteForm").style.display = "block";
    document.querySelector(".deletebtn").setAttribute("instructionId", instructionId);
}


document.querySelector(".deletebtn").addEventListener("click", ()=>{
    let classId = document.querySelector(".instruction-row").getAttribute("classId");
    let instructionId = document.querySelector(".deletebtn").getAttribute("instructionId");
    deleteInstruction(instructionId, classId);
});

document.querySelector(".cancelbtn").addEventListener("click", ()=>{
    document.getElementById("deleteForm").style.display = "none";
})



async function deleteInstruction(instructionId, classId){
    return fetch("http://localhost:8080/instruction/?instructionId=" + instructionId, {
        method:'DELETE'
    }).then(res =>{
        if (res.ok){
            window.location.href = "http://localhost:8080/instructions/" + classId;
        }
        else {
            alert('Помилка');
        }
    });
}


window.onclick = function (event) {
    if (event.target.className === "modal") {
        event.target.style.display = "none";
        document.body.style.overflowY = "auto";
    }
};