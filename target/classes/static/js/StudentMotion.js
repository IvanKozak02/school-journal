document.body.style.background = "#dfe9f5";
let cellStudentCount = document.querySelectorAll(".cell-student-count");
let saveOnChanged = document.querySelectorAll(".save-on-changed");
let countColumns = document.querySelectorAll(".inserted");
for (let i = 0; i < cellStudentCount.length; i++) {
    cellStudentCount[i].addEventListener("click", ()=>{
        for (let j = 0; j < saveOnChanged.length; j++) {
            saveOnChanged[j].style.display = "none";
            countColumns[j].style.borderColor = "";
        }
        saveOnChanged[i].style.display = "block";
        countColumns[i].style.borderColor = "burlywood";
    })
}


// window.onclick = function (event) {
//
//     if (!event.target.matches('.cell-student-count')) {		// якщо не була натиснута кнопка
//         for (let i = 0; i < saveOnChanged.length; i++) {
//             saveOnChanged[i].style.display = "none";
//             if (cellStudentCount[i].value===""){
//                 cellStudentCount[i].value = "0";
//             }
//         }
//     }
// };

function saveMotion(i,characteristic,period,classId){
    let row = document.querySelectorAll(".student-row")[i];
    let count = findCount(row,period);
    let obj = {characteristic:characteristic, period:period, countOfStudents:count,classId};
    const formData = new FormData();
    formData.append("jsonData", JSON.stringify(obj));
    saveMotionToDB(formData,row,period);
}

async function saveMotionToDB(formData, row, period) {
    let res = await fetch("http://localhost:8080/motion-table", {
        method: 'POST',
        headers: {
            Accept: 'application/json'
        },
        body: formData
    }).then(response => {
        if (response.ok) {
            // window.location.href = "http://localhost:8080/motion-table/" + classId;
            let td = findTd(row,period);
            td.querySelector('.save-on-changed').style.display = "none";
            td.style.borderColor = '';
        } else {
            alert("Помилка");
        }
    });
}

function fillTd(row, period){


}

function findTd(row, period){
    let index = getIndex(period);
    return row.querySelectorAll('.inserted')[index];
}

function findCount(row,period){
  return findTd(row,period).querySelector('.cell-student-count').value;
}


function getIndex(period){
    switch (period){
        case "I семестр":
            return 0;
        case "II семестр":
            return 1;
        case "Рік":
            return 2;
    }
}
