

/*--------Add column Form------------------------------------------*/


const date = document.querySelectorAll(".date-inserted");
let dateIns = document.getElementById("date-info");
let thead = document.querySelector(".head-table");
let colName = document.querySelector(".col-name");
let savePlanBut = document.querySelector(".save-column-name");


function showForm(i) {
    if (document.querySelectorAll(".table-wrapper")[0].getAttribute("teacher") === "YES"){
        document.querySelector(".add-column-text").innerText = 'Додати стовпець';
        document.querySelector(".col-name").value = '';
        document.querySelector(".canAdd").checked = false;
        colName.parentElement.removeAttribute("data-error");
        savePlanBut.removeAttribute("disabled");
        dateIns.innerText = document.querySelectorAll(".date-inserted")[i].innerText;
        document.querySelector(".save-mark-btn").setAttribute("dateColIndex", i);
        document.querySelector(".save-column-name").removeAttribute("edit");
        document.getElementById("modalOne").style.display = "block";
    }
}



window.onclick = function (event) {
    if (event.target.className === "modal") {
        event.target.style.display = "none";
    }
};

document.querySelector(".save-column-name").addEventListener("click", ()=>{
    if (document.querySelector(".save-column-name").getAttribute("edit") === "edit"){
        editColumns();
    }
    else {
        saveNewColumn("add");
    }
});


function saveNewColumn(request){
    let colName = document.querySelector(".col-name").value;
    if (colName.length <= 4){
        let date = document.getElementById("date-info").innerText;
        let journalId = document.querySelector(".table-wrapper").getAttribute("id");
        if (document.querySelector(".col-name").value !== '') {
            let column;
            if (document.querySelector(".canAdd").checked === true) {
                column = {columnName: colName, canAdd: true, date: date, classJournal: journalId};
            } else {
                column = {columnName: colName, canAdd: false, date: date, classJournal: journalId};
            }
            const formData = new FormData();
            formData.append("jsonData", JSON.stringify(column));
            saveColumn(formData, request, date,colName);
        }
    }
    else {
        alert("Назва стовпця завелика!!!")
    }
}

function editColumns(){
    let colName = document.querySelector(".col-name").value;
    if (colName.length <= 4) {
        let date = document.getElementById("date-info").innerText;
        let journalId = document.querySelector(".table-wrapper").getAttribute("id");
        let colId = document.querySelector(".save-column-name").getAttribute("colId");
        if (document.querySelector(".col-name").value !== '') {
            let column;
            if (document.querySelector(".canAdd").checked === true) {
                column = {columnName: colName, canAdd: true, date: date, classJournal: journalId, colId: colId};
            } else {
                column = {columnName: colName, canAdd: false, date: date, classJournal: journalId, colId: colId};
            }
            const formData = new FormData();
            formData.append("jsonData", JSON.stringify(column));
            saveColumn(formData, "edit", date, colName);
        }
    }
    else {
        alert("Назва стовпця завелика!!!")
    }
}

async function saveColumn(formData,request, date, colName) {
    await fetch("http://localhost:8080/" + request + "column", {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData,
    }).then(response=>{
        if (response.ok){
            if (request !== 'edit'){
                addCell(date,colName);
            }
            else {
                editCell(date,colName);
            }
            // window.location.href = "http://localhost:8080/journal/" + document.querySelector(".table-wrapper").getAttribute("id");
        }
        else {
            alert("Помилка");
        }
    });
}

async function addCell(date,colName){
    let index = getIndexByDate(date);
    insertDate(index+1,colName, date);
    insertColumn(index+1);
    document.getElementById("modalOne").style.display = "none";
    fillJournalTable();
    // initializeTable(document.querySelector(".semester").value);
    // changeIndexOfAnotherCells(index);
}

async function fillJournalTable() {
    let journalId = document.querySelector(".table-wrapper").getAttribute("id");
    let semester = document.querySelector(".semester");
    let dates = await getDates(journalId).then(res => {
        return res;
    });
    let students = await getStudentsList(journalId).then(res => {
        return res;
    });
     fillThead(semester, dates);
     fillTbody(semester, students, dates);
    fillStudents();
    initializeTable(semester.value);
}


function getIndexByDate(date){
    let dates = document.querySelectorAll(".date-inserted");
    for (let i = 0; i < dates.length; i++) {
        if (dates[i].innerText === date){
            return i;
        }
    }
}

function updateDates(date){
    let pat = new RegExp('\\d\\d\\.\\d\\d');
    let dates = document.querySelectorAll(".date-inserted");
    for (let i = 0; i < dates.length; i++) {
        if (!pat.test(dates[i].innerText) && dates[i].innerText !== ''){
            dates[i].setAttribute("onclick", "editColumn(" + i + ")");
            dates[i].setAttribute("date", date);
            dates[i].setAttribute("style", "background-color: #b357ff; text-align:center;");
        }
        else {
            dates[i].setAttribute("onclick", `showForm(${i})`);
        }
    }
}

function insertDate(index, colName, date){
    let row = document.querySelector(".head-table");
    let insertDate = row.insertCell(index);
    insertDate.classList.add("date-inserted");
    insertDate.innerText = colName.trim();
    updateDates(date);
}


function insertColumn(index){
    let row = document.querySelectorAll(".student-row");
    for (let i = 0; i < row.length; i++) {
        let newCell = row[i].insertCell(index);
        newCell.classList.add("inserted");
        newCell.innerHTML = `<div class="mark-item"></div>`;
        newCell.setAttribute("style", "background-color: rgb(237, 213, 255);");
    }
    fillStudents();
}

function editCell(date,colName){
    let index = getIndexByDate(date);
    renameCell(index,colName);
}

function renameCell(index){
    let dates = document.querySelectorAll(".date-inserted");
    for (let i = index; i > 0 ; i--) {
        if (dates[i].innerText === document.querySelector(".save-column-name").getAttribute("oldColName")){
            dates[i].innerText = document.querySelector(".col-name").value;
            document.getElementById("modalOne").style.display = "none";
            break;
        }
    }
}

// function changeDates(index){
//     let dates = document.querySelectorAll('.date-inserted');
//     for (let i = index; i < dates.length; i++) {
//         dates[index].
//     }
// }