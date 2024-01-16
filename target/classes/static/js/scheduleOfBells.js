let kstOfTr = document.getElementsByTagName("tr").length;

let start = true;
let kst;

let fields =[];
document.querySelector(".add-subject").addEventListener("click", ()=>{
    // document.querySelector(".data").innerHTML = '';
    // addNextInputField(kstOfTr);
    document.getElementById("addSchoolSubjectSchedule").style.display = "block";
    document.querySelector(".save-subject").removeAttribute("request");
    document.querySelector(".save-subject").setAttribute("request", "addschedule");
    document.querySelector(".add-subjects").classList.remove("next");
    document.querySelector(".add-subjects").classList.add("add");
    document.querySelector(".add-subjects").innerText = "Додати урок"
    document.querySelector(".previous").style.display = "block";

})

// document.querySelector(".add-subjects").addEventListener("click", ()=>{
//
//     if (document.querySelector(".add-subjects").classList.contains("add")){
//         let kstOfContainers = hidePreviousInputField();
//         let kst = kstOfTr + document.querySelector('.data').children.length;
//         let allInputs = document.getElementsByTagName("input");
//         if (allInputs[allInputs.length-1].value !== '' && allInputs[allInputs.length-2].value !== ''){
//             fields.push(allInputs[length-1].value);
//             fields.push(allInputs[length-2].value);
//         }
//         addNextInputField(kst);
//     }
//     else {
//         hidePreviousInputField();
//     }
//
// });

// function hidePreviousInputField(){
//     let kstOfContainers = document.querySelectorAll(".field-container").length;
//     // let kstOfTr = document.getElementsByTagName("tr").length;
//     let allInputs = document.getElementsByTagName("input");
//     document.querySelectorAll(".field-container")[kstOfContainers-1].style.display = "none";
//     // document.querySelectorAll(".subject-header")[kstOfContainers-1].style.display = "none";
//     console.log(allInputs[allInputs.length-1].value);
//     console.log(allInputs[allInputs.length-2].value);
//     return kstOfContainers;
// }

// function showPreviousInputField(lastInput){
//     // let kstOfContainers = document.querySelectorAll(".field-container").length;
//     document.querySelectorAll(".field-container")[lastInput].style.display = "flex";
// }

// function addNextInputField(kstOfSubjects){
//     document.querySelector(".data").innerHTML += `
//             <div class="field-container" style="display: flex; justify-content: space-around; flex-direction: column;
//     padding: 5px;">
//             <h4 class="subject-header">${kstOfSubjects+1}-ий урок</h4>
//               <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
//                 <label class="mandatory">Час початку:</label>
//                 <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="start-time" >
//               </div>
//               <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
//                 <label class="mandatory">Час завершення:</label>
//                 <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="finish-time" >
//               </div>
//             </div>`
// }


function getFillInputs(){
    let fillInputs = [];
    let allInputs = document.getElementsByTagName("input");
    for (let i = 0; i < allInputs.length; i++) {
        if (allInputs[i].value !== ''){
            fillInputs.push(allInputs[i].value);
        }
    }
    return fillInputs;
}

document.querySelector(".edit-subject").addEventListener("click", ()=>{
   document.querySelector(".data").innerHTML = '';
    let kstOfTr = document.getElementsByTagName("tr").length;
    for (let i = 0; i < kstOfTr; i++) {
        document.querySelector(".data").innerHTML += `
            <div class="field-container" style="display: flex; justify-content: space-around;">
            <h4 class="subject-header">${document.querySelectorAll(".numOfSubject")[i].innerText}</h4>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час початку:</label>
                <input type="time" value="${document.querySelectorAll('.dateOfStart')[i].innerText}" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="start-time" >
              </div>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час завершення:</label>
                <input type="time" value="${document.querySelectorAll('.dateOfFinish')[i].innerText}" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="finish-time" >
              </div>
            </div>`
    }
    console.log(document.querySelector(".content-container").offsetHeight);
    document.getElementById("addSchoolSubjectSchedule").style.display = "block";
    document.querySelector(".save-subject").removeAttribute("request");
    document.querySelector(".save-schedule").setAttribute("request", "updateschedule");

    document.querySelector(".add-subjects").style.display = "none";
    document.querySelector(".previous").style.display = "none";



});

// document.querySelector(".previous").addEventListener("click", (ev)=>{
//     ev.preventDefault();
//         let data = document.querySelector(".data");
//         if (document.querySelector(".data").children.length ===1){
//
//         }
//         else {
//             let length = document.querySelectorAll(".field-container").length;
//             let field = document.querySelectorAll(".field-container")[length-1];
//             data.removeChild(field);
//             showPreviousInputField(document.querySelector(".data").children.length-1)
//
//             // addNextInputField(kst -= 1);
//         }
//     // else {
//     //     let data = document.querySelector(".data");
//     //     data.removeChild(data.lastChild);
//     //     addNextInputField(kst-=1);
//     //
//     // }
// });



document.querySelector(".save-schedule").addEventListener("click", ()=>{
    if (checkAllInputs()){

            let scheduls = [];
            let fillInputs = getFillInputs();
            let fillLabels = getLabels();
            const formData = new FormData();
            console.log(JSON.stringify(fillInputs));
            let timeOfStart = [];
            let timeOfFinish = [];

            for (let i = 0; i < fillInputs.length; i++) {
                if (i % 2 === 0){
                    timeOfStart.push(fillInputs[i]);
                }
                else {
                    timeOfFinish.push(fillInputs[i]);
                }

            }
            let schedule = {numberOfSubject:fillLabels, timeOfStartSubject: timeOfStart,timeOfFinishSubject: timeOfFinish}
            formData.append("jsonData", JSON.stringify(schedule));
            saveSchedule(formData, 'addschedule');

    }

});


async function saveSchedule(formData, request){
    let res = await fetch("http://localhost:8080/" + request, {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData,
    }).then(response=>{
        if (response.ok){
            window.location.href = "http://localhost:8080/schedule";
        }
    });

}

function getLabels(){
    let fillLabels = [];
    let allLabels = document.getElementsByTagName("h4");
    for (let i = 0; i < allLabels.length; i++) {
            fillLabels.push(allLabels[i].innerText);
        }
    return fillLabels;
}

function checkAllInputs(){
    let allInputs = document.getElementsByTagName("input");
    for (let i = 0; i < allInputs.length; i++) {
        if (allInputs[i].value === ''){
            alert("Не заповнені всі поля у " + document.getElementsByTagName("h4")[i].innerText);
            return false;
        }
    }
    return true;
}

window.onclick = function (event) {
    if (event.target.className === "modal") {
        event.target.style.display = "none";
        document.body.style.overflow = "auto";
    }
};