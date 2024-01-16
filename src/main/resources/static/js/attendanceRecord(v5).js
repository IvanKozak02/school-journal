window.onload = async function () {
    await initializeTable('I семестр');
    getAllNByStudents();
};

async function getAllNByStudents() {
    let N = await getNByStudent(document.querySelectorAll(".student")[1].getAttribute("id")).then(res => {
        return res;
    });
    let students = document.querySelectorAll(".student");
    let dates = document.querySelectorAll(".date-inserted");
    for (let i = 0; i < dates.length; i++) {
        let date = dates[i];
        for (let i = 1; i < students.length; i++) {
            let Ns = N.filter(n=>n.student.userPersonalData.id === students[i].getAttribute("id") && date.innerText === n.date);
            if (Ns.length !== 0){
                fillN(Ns)
            }
        }
    }

}

async function getNByStudent(studentId){
    return fetch("http://localhost:8080/attendance-rec-n/?studentId=" + studentId, {}).then(res => res.json())
        .then((json) => {
            return json
        });
}

function fillN(Ns) {
    let student = document.getElementById(Ns[0].student.userPersonalData.id);
    let date = findDate(Ns[0].date);
    fillNToTable(Ns, student, date);
}

function findDate(date){
    let dates = document.querySelectorAll(".date-inserted");
    for (let i = 0; i < dates.length; i++) {
        if (dates[i].innerText === date){
            return dates[i];
        }
    }
}

async function initializeTable(semester) {
    let dates = document.querySelectorAll(".date-inserted");
    let index = findCurrentDate();
    let kstOfCol = kstOfColThatNeedToAdd(dates.length);
    let endOfInterval = kstOfCol + dates.length;
    let startOfInterval = startInterval(index);
    addColumnToTable(dates.length, kstOfCol, startOfInterval, endOfInterval);
    if (index === -1) {
        displayPageOfJournal(0, document.querySelectorAll(".date-inserted"));
    }
    displayPageOfJournal(index, document.querySelectorAll(".date-inserted"));
    fillStudentsCells();
}

function fillStudentsCells(){
    let td = document.querySelectorAll(".inserted");
    if (document.querySelectorAll(".student-row")[0].getAttribute("head-teacher") === "YES") {
        for (let i = 0; i < td.length; i++) {
            td[i].setAttribute("onclick", "showForm(" + i + ")");
        }
    }
}


function displayPageOfJournal(index, dates){
    let allTr = document.querySelectorAll(".student-row");
    let startOfInterval = startInterval(index, dates);
    let endOfInterval = endInterval(index, dates);
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



