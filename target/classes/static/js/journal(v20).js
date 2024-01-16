window.onload = function () {
    initializeTable('I семестр');
};

function findLastCols(dates){
    for (let i = dates.length-1; i>0; i--) {
        if ((dates[i].innerText === "I с."  || dates[i].innerText === "II с.")){
            return dates[i].getAttribute("style") === 'background-color: #b357ff; text-align:center;';
        }
    }
    return true;
}

async function initializeTable(semester) {
    let dates = document.querySelectorAll(".date-inserted");
    let pat = new RegExp('\\d\\d\\.\\d\\d');
    let kstOfLastCells;
    if (!findLastCols(dates)){
        kstOfLastCells = addLastCells(semester,dates);
    }
    else {
        if (semester === 'I семестр'){
            kstOfLastCells = 2;
        }
        else {
            kstOfLastCells = 4;
        }
    }
    for (let i = 0; i < dates.length - kstOfLastCells; i++) {
        if (!pat.test(dates[i].innerText) && dates[i].innerText !== '') {
            dates[i].setAttribute("onclick", "editColumn(" + i + ")");
            dates[i].setAttribute('date', dates[i + 1].innerText);
            dates[i].setAttribute('style', "background-color: #b357ff;text-align:center;");
        }
        else {
            dates[i].setAttribute("onclick", `showForm(${i})`);
        }
    }
    let index = findCurrentDate();
    let kstOfCol = kstOfColThatNeedToAdd(dates.length);
    let endOfInterval = kstOfCol + dates.length;
    let startOfInterval = startInterval(index);
    addColumnToTable(dates.length, kstOfCol, startOfInterval, endOfInterval);
    if (index === -1) {
        displayPageOfJournal(0, document.querySelectorAll(".date-inserted"));
    }
    displayPageOfJournal(index, document.querySelectorAll(".date-inserted"));
    fillStudents();
    fillColNames();
    let marks = await getAllMark(document.querySelector(".table-wrapper").getAttribute("id")).then(res => {
        return res;
    });
    if (marks.length>0){
        insertMarksToJournal(marks);
    }
}

function fillColNames(){
    let d = document.querySelectorAll(".date-inserted");
    for (let i = 0; i < d.length; i++) {
        if (i+1 !== d.length){
            if (d[i].innerText === d[i+1].innerText) {
                let dates1 = d[i].innerText.trim() + '-' + i;
                let dates2 = d[i+1].innerText.trim() + '-' + (i+1);
                d[i].setAttribute("colName",dates1);
                d[i+1].setAttribute("colName",dates2);
            }
        }
    }

    for (let i = 0; i < d.length; i++){
        if (d[i].getAttribute("colName") === null){
            d[i].setAttribute("colName",d[i].innerText);
        }
    }
}



function addLastCells(semester,dates){
    if (semester === "I семестр"){
        for (let i = dates.length-2; i < dates.length; i++) {
            dates[i].setAttribute('style', "background-color: #b357ff;text-align:center;");
        }
        return 2;
    }
    else {
        for (let i = dates.length-4; i < dates.length; i++) {
            dates[i].setAttribute('style', "background-color: #b357ff;text-align:center;");
        }
        return 4;
    }
}

function insertMarksToJournal(marks){
    for (let i = 0; i < marks.length; i++) {
        let markObj = {student:marks[i].student.userPersonalData.id, date: marks[i].date, colName:marks[i].colName, mark: marks[i].mark, typeOfMark: marks[i].typeOfMark};
        fillTableByMarks(markObj);
    }
}


async function getAllMark(journalId){
    return fetch("http://localhost:8080/mark/" + journalId, {}).then(res => res.json())
        .then((json) => {
            return json
        });
}


function fillStudents(){
    let td = document.querySelectorAll(".inserted");
    for (let i = 0; i < td.length; i++) {
        td[i].setAttribute("onclick", "showmenu(" + i + ")");
        td[i].setAttribute("oncontextmenu", "getContextMenu(" + i + ")");
    }
}


function displayPageOfJournal(index, dates){
    let allTr = document.querySelectorAll(".student-row");
    let startOfInterval = startInterval(index, dates);
    let endOfInterval = endInterval(index, dates);
    fillAdditionsColumnCells(allTr,dates);
    let year = new Date().getFullYear();
    let headTable = document.querySelector(".head-table");
    let thFinish = headTable.querySelectorAll(".date-inserted")[endOfInterval-1].innerText;
    if (thFinish === ''){
        let pat = new RegExp('\\d\\d\\.\\d\\d');
        let i = endOfInterval-1
        while (true){
            if (headTable.querySelectorAll(".date-inserted")[i].innerText !== '' &&
                pat.test(headTable.querySelectorAll(".date-inserted")[i].innerText)){
                thFinish = headTable.querySelectorAll(".date-inserted")[i].innerText;
                break;
            }
            i--;
        }


    }

    let thStart = headTable.querySelectorAll(".date-inserted")[startOfInterval].innerText;
    document.querySelector(".dates").innerText = thStart.trim() + '-' + thFinish.trim();
    if (year + '-' + dates[index].innerText.substring(3) + '-' + dates[index].innerText.substring(0,2) === new Date().toJSON().slice(0, 10)){
        fillColumnWithCurrentDate(index, dates);
    }

    for (let i = 0; i < startOfInterval; i++) {
        dates[i].style.display = "none";
    }
    for (let i = endOfInterval; i < dates.length ; i++) {
        dates[i].style.display = "none";
    }

    for (let i = 0; i < allTr.length; i++) {
        let tr = allTr[i];
        for (let j = 0; j < startOfInterval; j++) {
            tr.querySelectorAll(".inserted")[j].style.display = "none";
        }
        for (let j = endOfInterval; j < dates.length; j++) {
            tr.querySelectorAll(".inserted")[j].style.display = "none";
        }
    }
}

function fillAdditionsColumnCells(allTr, dates){
    let pat = new RegExp('\\d\\d\\.\\d\\d');
    for (let i = 0; i < allTr.length; i++) {
        let tr = allTr[i];
        for (let j = 0; j < dates.length; j++) {
            if (!pat.test(dates[j].innerText) && dates[j].innerText !== '') {
                tr.querySelectorAll(".inserted")[j].style.backgroundColor = "#edd5ff";
            }
        }
    }
}



function fillColumnWithCurrentDate(index, dates){
    dates[index].style.backgroundColor = "#9dcbff";
}


function endInterval(index){
    let b = true;
    while (true){
        if (b && index % 19 === 0){
            b = false;
            index+=1;
        }
        b=false;
        if (index % 19 === 0 && index !== 0){
            break;
        }
        index++;
    }
    return index;
}

function startInterval(index){
    while (true){
        if (index % 19 === 0){
            break;
        }
        index--;
    }
    return index;
}

function findCurrentDate(){
    let date = new Date();
    let currentDay = (date.getDate() < 10) ? '0' + date.getDate(): date.getDate();
    let currentMonth = (date.getMonth()<10)? '0'+ (date.getMonth() + 1): date.getMonth()+1;
    let currentDate = currentDay + '.' + currentMonth;
    let dates = document.querySelectorAll(".date-inserted");
    let year = date.getFullYear();
    for (let i = 0; i < dates.length; i++) {
        let d = new Date(year + '-' + dates[i].innerText.substring(3) + '-' + dates[i].innerText.substring(0,2));
        if (d > date){
            return i;
        }
        let day = (d.getDate() < 10) ? '0' + d.getDate(): d.getDate();
        let month = (d.getMonth()<10)? '0'+ (d.getMonth() + 1): d.getMonth()+1;
        let pot_date = day +'.' +month;
        if(pot_date === currentDate) {
            return i;
        }
    }
    return -1;

}


function kstOfColThatNeedToAdd(datesLength){
    return 19 - (datesLength % 19);
}

function addColumnToTable(colLength,kstOfColNeedToAdd,startOfInterval,endOfInterval){

    let allTr = document.querySelectorAll(".student-row");
    let head_Table = document.querySelector(".head-table");
    for (let i = 0; i < kstOfColNeedToAdd; i++) {
        if (colLength > startOfInterval && colLength < endOfInterval){
            head_Table.innerHTML += `<th class="date-inserted disabled-col"></th>`;
        }
        else {
            head_Table.innerHTML += `<th style="display: none" class="date-inserted disabled-col"></th>`;

        }
    }
    for (let i = 0; i < allTr.length; i++) {
        let tr = allTr[i];
        for (let j = 0; j < kstOfColNeedToAdd; j++) {
            if (colLength > startOfInterval && colLength < endOfInterval) {
                tr.innerHTML += `<td  class="inserted disabled-col"><div class="mark-item"></div></td>`;
            }
            else {
                tr.innerHTML += `<td style="display: none" class="inserted disabled-col"><div class="mark-item"></div></td>`;

            }
        }
    }
}


document.querySelector(".next-page").addEventListener("click", ()=>{
    let lastElementIndex = findCurrentPageLastElementIndex();
    findNextPageElement(lastElementIndex);




})

function findCurrentPageLastElementIndex(){
    let tr = document.querySelectorAll(".student-row");
    let tds = tr[0].querySelectorAll(".inserted");
    let index;
    for (let i = 0; i < tds.length; i++) {
        if (window.getComputedStyle(tds[i]).display !== "none"){
            index = tds[i].cellIndex;
        }
    }
    return index;
}


function findNextPageElement(lastElementIndex){
    let tr = document.querySelectorAll(".student-row");
    let tds = tr[0].querySelectorAll(".inserted");
    if (lastElementIndex !== tds.length){
        let headTable = document.querySelector(".head-table");
        let thFinish = headTable.querySelectorAll(".date-inserted")[lastElementIndex+18].innerText;
        if (thFinish === ''){
            let pat = new RegExp('\\d\\d\\.\\d\\d');
            let i = lastElementIndex+18;
            while (true){
                if (headTable.querySelectorAll(".date-inserted")[i].innerText !== '' &&
                    pat.test(headTable.querySelectorAll(".date-inserted")[i].innerText)){
                    thFinish = headTable.querySelectorAll(".date-inserted")[i].innerText;
                    break;
                }
                i--;
            }
        }

        let thStart = headTable.querySelectorAll(".date-inserted")[lastElementIndex].innerText;
        document.querySelector(".dates").innerText = thStart.trim() + '-' + thFinish.trim();
        hidePage(lastElementIndex);
        showNextPage(lastElementIndex);

    }
}
function showNextPage(lastElementIndex){
    let allTr = document.querySelectorAll(".student-row");
    let head_Table = document.querySelector(".head-table");
    let allDates = head_Table.querySelectorAll(".date-inserted");
    for (let i = lastElementIndex; i < lastElementIndex+19; i++) {
        allDates[i].style.display = "table-cell";
        // head_Table.innerHTML += `<th style="dislay: none" class="date-inserted disabled-col"></th>`;
    }

    for (let i = 0; i < allTr.length; i++) {
        let tr = allTr[i];
        let tds = tr.querySelectorAll(".inserted");
        for (let j = lastElementIndex; j < lastElementIndex+19; j++) {
            tds[j].style.display = "table-cell";
        }
    }
}



function hidePage(lastElementIndex){
    let allTr = document.querySelectorAll(".student-row");
    let head_Table = document.querySelector(".head-table");
    let allDates = head_Table.querySelectorAll(".date-inserted");
    for (let i = lastElementIndex-19; i < lastElementIndex; i++) {
        allDates[i].style.display = "none";
    }

    for (let i = 0; i < allTr.length; i++) {
        let tr = allTr[i];
        let tds = tr.querySelectorAll(".inserted");
        for (let j = lastElementIndex-19; j < lastElementIndex; j++) {
            tds[j].style.display = "none";
        }
    }

}


document.querySelector(".previous-page").addEventListener("click", ()=>{
    let lastElementIndex = findCurrentPageLastElementIndex();
    findPreviousPage(lastElementIndex);
});

function findPreviousPage(lastElementIndex){
    if (lastElementIndex > 19){
        let headTable = document.querySelector(".head-table");
        let thStart = headTable.querySelectorAll(".date-inserted")[lastElementIndex-38].innerText;
        let thFinish = headTable.querySelectorAll(".date-inserted")[lastElementIndex-20].innerText;
        document.querySelector(".dates").innerText = thStart.trim() + '-' + thFinish.trim();
        hidePage(lastElementIndex);
        showPreviousPage(lastElementIndex);
    }
}




function showPreviousPage(lastElementIndex){
    let allTr = document.querySelectorAll(".student-row");
    let head_Table = document.querySelector(".head-table");
    let allDates = head_Table.querySelectorAll(".date-inserted");
    for (let i = lastElementIndex-19-19; i < lastElementIndex-19; i++) {
        allDates[i].style.display = "table-cell";
    }

    for (let i = 0; i < allTr.length; i++) {
        let tr = allTr[i];
        let tds = tr.querySelectorAll(".inserted");
        for (let j = lastElementIndex-19-19; j < lastElementIndex-19; j++) {
            tds[j].style.display = "table-cell";
        }
    }
}




async function editColumn(i) {
    let date = document.querySelectorAll(".date-inserted")[i].getAttribute("date");
    let journalId = document.querySelector(".table-wrapper").getAttribute("id");
    let colName = document.querySelectorAll(".date-inserted")[i].getAttribute("colName");
    document.querySelector(".save-column-name").setAttribute("oldColName", colName);
    let column = {columnName: colName, canAdd: true, date: date, classJournal: journalId};
    let col = await findColumnData(column).then(res => {
        return res;
    });
    fillEditForm(col);
}

function fillEditForm(col){
    document.querySelector(".add-column-text").innerText = "Редагувати стовпець";
    document.getElementById("date-info").innerText = col.date;
    document.querySelector(".col-name").value = col.columnName;
    document.querySelector(".canAdd").checked = col.canAdd === true;
    document.querySelector(".col-name").parentElement.removeAttribute("data-error");
    document.querySelector(".save-column-name").setAttribute("edit", "edit");
    document.querySelector(".save-column-name").setAttribute("colId", col.id);
    document.getElementById("modalOne").style.display = "block";

}


async function findColumnData(column){
    return fetch("http://localhost:8080/findcolumn/?columnName=" + column.columnName  + "&date=" + column.date + "&classJournal=" + column.classJournal, {}).then(res => res.json())
        .then((json) => {
            return json
        });
}

