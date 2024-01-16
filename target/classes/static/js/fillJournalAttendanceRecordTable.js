document.querySelector(".semester").addEventListener("change", async () => {
    fillJournalTable();
});

async function fillJournalTable() {
    let journalId = document.querySelector(".table-wrapper").getAttribute("id");
    let semester = document.querySelector(".semester");
    let dates = await getDates().then(res => {
        return res;
    });
    let students = await getStudentsList(journalId).then(res => {
        return res;
    });
    fillThead(semester, dates);
    fillTbody(semester, students, dates);
    fillStudents();
    initializeTable(semester.value);
    await getAllNByStudents();
}

function fillStudents(){
    let td = document.querySelectorAll(".inserted");
    for (let i = 0; i < td.length; i++) {
        td[i].setAttribute("onclick", "showForm(" + i + ")");
    }
}

function fillThead(semester,dates) {
    let th = document.querySelector(".head-table");
    th.innerHTML = `<th style="color: slateblue; font-style: bold; font-weight: 800;" class="student">Учні</th>`;
    for (let i = 0; i < dates.length; i++) {
        th.innerHTML += `<th class="date-inserted" onclick="showForm(${i})">${dates[i]}</th>`;
    }
}

function fillTbody(semester,students,dates) {
    let tbody = document.getElementsByTagName("tbody")[0];
    tbody.innerHTML = '';
    for (let i = 0; i < students.length; i++) {
        tbody.innerHTML += `<tr class="student-row">
                                <th class="student" id="${students[i].split("-")[0]}">${students[i].split("-")[1]}</th>
                            </tr>`
    }
    fillCells(dates);

}

function fillCells(dates){
    let students = document.querySelectorAll(".student-row");
    for (let i = 0; i < students.length; i++) {
        for (let j = 0; j < dates.length; j++) {
            students[i].innerHTML += `<td class="inserted">
                                    <div class="mark-item"></div>
                                </td>`;
        }
    }
}


function getDates() {
    let semester = document.querySelector(".semester").value;
    return fetch("http://localhost:8080/attendance-rec-date/?semester=" + semester, {}).then(res => res.json())
        .then((json) => {
            return json
        });
}

function getStudentsList(journalId) {
    return fetch("http://localhost:8080/attendance-rec-students/?journalId=" + journalId, {}).then(res => res.json())
        .then((json) => {
            return json
        });
}