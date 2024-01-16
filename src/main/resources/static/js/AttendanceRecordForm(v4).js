
async function showForm(i) {
    let cellIndex = document.querySelectorAll(".inserted")[i].cellIndex;
    let date = document.querySelectorAll(".date-inserted")[cellIndex - 1].innerText;
    let tr = document.querySelectorAll(".inserted")[i].parentElement;
    let studentId = tr.querySelector(".student").getAttribute("id");
    let classId = document.querySelector(".table-wrapper").getAttribute("classId");
    document.querySelector(".add-column-text").innerText = tr.querySelector(".student").innerText.split(".")[1];
    document.querySelector(".add-column-text").setAttribute("studentId", studentId);
    document.querySelector(".add-column-text").setAttribute("index", i);
    document.getElementById("date-info").innerText = date;

    let subjects = await getSubjects(date, studentId, classId,i).then(res => {
        return res;
    });
    let N = await getMarks(date, studentId).then(res => {
        return res;
    });

    fillSubjectsAndMarksToForm(subjects, N);



    document.getElementById("attendanceRecordForm").style.display = "block";

}




function fillNToTable(Ns, student, date){
    let cellIndex = date.cellIndex-1;
    let tr = student.parentElement;
    let td = tr.querySelectorAll(".inserted")[cellIndex];
    if (Ns[0].mark === -1){
        td.innerText = 'Н/' + Ns.length;
    }
    else {
        td.innerText = 'ХВ/' + Ns.length;
    }
    if (document.querySelectorAll(".student-row")[0].getAttribute("head-teacher")==="YES"){
        td.innerHTML += `<div class="mark-item"></div>`;
    }
}


function fillSubjectsAndMarksToForm(subjects, N){
    let table = document.querySelector(".oblik-table");
    let tbody = table.querySelector("tbody");
    tbody.innerHTML = '';
    fillSubjectsToForm(subjects, tbody);
    let oblikRow = document.querySelectorAll(".oblik-row");
    fillMarksToForm(oblikRow,N);
}

function fillMarksToForm(oblikRow, marks){
    for (let i = 0; i < marks.length; i++) {
        let mark = marks[i];
        for (let j = 0; j < oblikRow.length; j++) {
            if (oblikRow[j].querySelector(".subject").innerText.substring(2).trim() === mark.classJournal.schedule.subject.name){
                if (!oblikRow[j].innerHTML.includes('<td class="inserted-oblik" style="text-align: center">Н</td>')
                    && mark.mark === -1){
                    oblikRow[j].innerHTML+= `<td class="inserted-oblik" style="text-align: center">Н</td>
                                                    <td class="inserted-oblik"><input type="radio" onchange="attendanceN()" checked class="attendance" name="${j}" style="width: 18px; height: 18px"></td>
                                                    <td class="inserted-oblik"><input type="radio" onchange="attendanceByIll()" class="attendance-by-ill" name="${j}" style="width: 18px; height: 18px;margin-left: 7px"></td>`
                    break;
                }
                else if (!oblikRow[j].innerHTML.includes('<td class="inserted-oblik" style="text-align: center">Н</td>')
                    && mark.mark === -2){
                    oblikRow[j].innerHTML+= `<td class="inserted-oblik" style="text-align: center">Н</td>
                                                    <td class="inserted-oblik"><input type="radio" onchange="attendanceN()"  class="attendance" name="${j}" style="width: 18px; height: 18px"></td>
                                                    <td class="inserted-oblik"><input type="radio" onchange="attendanceByIll()" checked class="attendance-by-ill" name="${j}" style="width: 18px; height: 18px;margin-left: 7px"></td>`
                    break;
                }
            }
        }
    }
    for (let i = 0; i < oblikRow.length; i++) {
        if (oblikRow[i].children.length === 1){
            oblikRow[i].innerHTML+= `<td class="inserted-oblik" style="text-align: center"></td>
                                                    <td class="inserted-oblik"><input disabled type="radio" class="HCheckBox" name="A" style="width: 18px; height: 18px"></td>
                                                    <td class="inserted-oblik"><input disabled type="radio" class="XBВCheckBox" name="A" style="width: 18px; height: 18px;margin-left: 7px"></td>`
        }
    }
}

function fillSubjectsToForm(subjects, tbody){
    for (let i = 0; i < subjects.length; i++) {
        tbody.innerHTML += `<tr class="oblik-row"><td class="inserted-oblik">
                                                     <div class="subject">${subjects[i]}</div>
                                                    </td>
                    </tr>`;
    }
}


async function getMarks(date, studentId){
    return fetch("http://localhost:8080/attendance-rec-mark/?date=" + date + "&studentId=" + studentId, {}).then(res => res.json())
        .then((json) => {
            return json
        });
}



async function getSubjects(date,studentId, classId, i){
    return fetch("http://localhost:8080/attendance-rec/?date=" + date + "&studentId=" + studentId + "&classId=" + classId + "&index=" + i, {}).then(res => res.json())
        .then((json) => {
            return json
        });
}



function attendanceByIll(){
    let studentId = document.querySelector(".add-column-text").getAttribute("studentId");
    let date = document.querySelector(".date-column").innerText;

    let allRadioIll = document.querySelectorAll(".attendance-by-ill");
    for (let i = 0; i < allRadioIll.length; i++) {
        allRadioIll[i].checked = true;
        allRadioIll[i].setAttribute("checked", "checked");
    }

    saveN(studentId,date, -2);

}


function attendanceN(){
    let studentId = document.querySelector(".add-column-text").getAttribute("studentId");
    let date = document.querySelector(".date-column").innerText;
    let allRadioN = document.querySelectorAll(".attendance");
    for (let i = 0; i < allRadioN.length; i++) {
        allRadioN[i].checked = true;
        allRadioN[i].setAttribute("checked", "checked");
    }
    saveN(studentId,date, -1);

}


async function saveN(studentId, date, mark) {
    const formData = new FormData();
    let obj = {studentId:studentId, date:date, mark:mark};
    formData.append("jsonData", JSON.stringify(obj));
    let res = await fetch("http://localhost:8080/change-type-n", {
        method: 'POST',
        headers: {
            Accept: 'application/json'
        },
        body: formData
    }).then(response=>{
        if (response.ok){
            changeCellText(mark);
        }
        else {
            alert("Помилка");
        }
    });

}

function changeCellText(mark){
    let index = document.querySelector(".add-column-text").getAttribute("index");
    let cell = document.querySelectorAll(".inserted")[index];
    if (mark === -1){
        cell.innerText = 'Н/' + document.querySelectorAll(".attendance").length;
    }
    else {
        cell.innerText = 'ХВ/' + document.querySelectorAll(".attendance-by-ill").length;
    }
}

window.onclick = function (event) {
    if (event.target.className === "modal") {
        event.target.style.display = "none";
    }
};