let addGroupBtn = document.querySelector(".add-group-btn");
let tableWrapper = document.querySelector(".schedule-table-wrapper")
let choseStudent = document.querySelectorAll(".chose-student");
let addStudentToGroupBtn = document.querySelector(".add-student-to-group");
let addGroupCheckBox = document.querySelector(".add-group-checkbox");


addGroupBtn.addEventListener("click", ()=>{
    if (document.getElementById("content").getAttribute("role") === "YES"){
        document.querySelector(".student-add-remove-form").style.display = "none";
        addGroupsToAddRemoveStudentsForm();
        let width = tableWrapper.offsetWidth += 94.3;
        tableWrapper.style.width = `${width}px`
        let headTab = document.querySelector(".head-table");
        headTab.innerHTML += `<th class="date-inserted" onclick="myFunc2(${document.querySelectorAll(".date-inserted").length})">Групa №${document.querySelectorAll(".date-inserted").length + 1}</th>`
        addCells();
        for (let j = 0; j < choseStudent.length; j++) {
            choseStudent[j].setAttribute("checked", "checked");
        }
    }
})


function addGroupsToAddRemoveStudentsForm(){
    let checkLabel = document.querySelectorAll(".check-label");
    for (let i = 0; i < checkLabel.length; i++) {
        document.querySelector(".add-group-checkbox").removeChild(checkLabel[i]);
    }
    for (let i = 0; i < document.querySelector(".head-table").children.length-1; i++) {
        addGroupCheckBox.innerHTML += `<label class="check-label"><input type="radio" name="add-check" class="add-checkbox">${i + 1}</label>`;
    }
}

function myFunc(){
    let k=0;
    let chStudents = document.querySelectorAll(".chose-student");
    for (let j = 0; j < chStudents.length; j++) {
        if (document.querySelectorAll(".chose-student")[j].checked === true){
            break;
        }
        else {
            k+=1;
        }

    }
    if (k === (chStudents.length)){
        let checkedGroup = document.querySelectorAll(".add-checkbox:checked");
        for (let i = 0; i < checkedGroup.length; i++) {
            checkedGroup[i].checked = false;

        }
        document.querySelector(".student-add-remove-form").style.display = "none";

    }
    else {

        addGroupsToAddRemoveStudentsForm();
        document.querySelector(".student-add-remove-form").style.display = "flex";
        document.querySelector(".count-of-change").innerText = document.querySelectorAll(".chose-student:checked").length;

    }
}



function addCells(){
    let groupRow = document.querySelectorAll(".teacher-row");
    for (let i = 0; i < groupRow.length; i++) {
        groupRow[i].innerHTML += `<td class="schedule"></td>`;
    }
}





addStudentToGroupBtn.addEventListener("click", ()=>{
    if (document.getElementById("content").getAttribute("role") === "YES") {
        let students = [];
        let changeGroup;
        for (let i = 0; i < document.querySelectorAll(".add-checkbox").length; i++) {
            if (document.querySelectorAll(".add-checkbox")[i].checked === true) {
                changeGroup = i + 1;
            }

        }
        let choseSt = document.querySelectorAll(".chose-student");
        let studentName = document.querySelectorAll(".student-name");
        for (let i = 0; i < choseSt.length; i++) {
            if (document.querySelectorAll(".chose-student")[i].checked === true) {
                if (checkIfStudentIsInAnotherGroup(document.querySelectorAll(".teacher-row")[i].children)) {
                    if (document.querySelectorAll(".teacher-row")[i].children[changeGroup].children[0] === undefined) {
                        addCheckMark(i);
                    }
                    students.push(studentName[i].getAttribute("id"));
                }
            }
        }
        if (students.length > 0) {
            let sc_class = document.querySelector(".class-group").getAttribute("id");
            let subject;

            let groupBtns = document.querySelectorAll(".group-btn");
            for (let i = 0; i < groupBtns.length; i++) {
                if (groupBtns[i].classList.contains("show")) {
                    subject = groupBtns[i].innerText;
                }
            }

            const obj = {numberOfGroup: changeGroup, schoolClassId: sc_class, subject: subject, students: students};
            const formData = new FormData();
            formData.append("jsonData", JSON.stringify(obj));
            saveGroupOfClass(formData);
        }
    }
})

function checkIfStudentIsInAnotherGroup(arr){
    let sc=[];
    for (let i = 1; i < arr.length; i++) {
        sc[i-1] = arr[i];
    }
    for (let i = 0; i < sc.length; i++) {
        if (sc[i].innerHTML.indexOf('<i class="fa-solid fa-square-check"></i>') !== -1){
            return false;
        }
    }
    return true;
}

document.getElementById("subjects").addEventListener("change", async () => {
    let classId = document.querySelector(".class-group").getAttribute("id");
    let students = await getStudentsByClass(classId).then(res => {
        return res;
    });
    if (students.length > 0){
        deleteShowClassFromBtn();
        let btns = document.querySelectorAll(".group-btn");
        document.querySelector(".group-btn-container").innerHTML += `<div style="display: flex" class="btn-groups"><button style="border: none; margin-left: 10px" class="group-btn show" onclick="getGroup(${btns.length+1})" >${document.getElementById("subjects").value}
            </button><i style="margin-left: -10px; position: static" onclick="deleteGroup(${document.querySelectorAll(".group-btn").length + 1})" class="fa-solid fa-circle-xmark delete-icon"></i></div>`;
        let selectGroupBtn = document.querySelectorAll(".group-btn");
        for (let i = 0; i < selectGroupBtn.length; i++) {
            if (selectGroupBtn[i].classList.contains("show")){
                removeOptionFromSelect(selectGroupBtn[i]);
                break;
            }
        }
        fillTableByListOfStudents(students);
    }
    else {
        alert("Приєднайте учнів до класу!!!");
    }


});


function fillTableByListOfStudents(students){
    createTableStructure();
    let tableWrap = document.querySelector(".schedule-table-wrapper");
    tableWrap.style.width = '0px';
    tableWrap.style.width = '678px';

    fillStudents(students,0,0,false);
}

async function getStudentsByClass(classId){
    let students;
    return students = fetch("http://localhost:8080/allstudentsbyclass?classId=" + classId)
        .then(res => res.json())
        .then((json) => {
            return json
        });
}


function deleteShowClassFromBtn(){
    let btns = document.querySelectorAll(".group-btn");
    for (let i = 0; i < btns.length; i++) {
        btns[i].classList.remove("show");
    }
}


async function saveGroupOfClass(formData){
    let res = await fetch("http://localhost:8080/newgroup", {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData,
    }).then(response=>{
        if (response.ok){
            let checkedGroup = document.querySelectorAll(".add-checkbox:checked");
            for (let i = 0; i < checkedGroup.length; i++) {
                checkedGroup[i].checked = false;
            }
            let selectStudent = document.querySelectorAll(".chose-student:checked");
            for (let j = 0; j < selectStudent.length; j++) {
                selectStudent[j].checked = false;
            }
            document.querySelector(".student-add-remove-form").style.display = "none";


            // window.location.href = "http://localhost:8080/classprofile?classId=" + classId;
        }
        else {
            alert("Помилка");
        }
    });

}



function addCheckMark(i){
    for (let j = 0; j < document.querySelectorAll(".add-checkbox").length; j++) {
        let child;
        if (document.querySelectorAll(".add-checkbox")[j].checked === true) {
            child = document.querySelectorAll(".teacher-row")[i].children;
            child[j + 1].innerHTML += `<i class="fa-solid fa-square-check"></i>`;
        }
    }
}






document.querySelector(".remove-student-from-group").addEventListener("click", ()=>{
    if (document.getElementById("content").getAttribute("role") === "YES") {

        let chStudent = document.querySelectorAll(".chose-student");
        let st = [];
        let studentPos;
        for (let i = 0; i < chStudent.length; i++) {
            if (document.querySelectorAll(".chose-student")[i].checked === true) {
                for (let j = 1; j < document.querySelectorAll(".teacher-row")[i].children.length; j++) {
                    if (document.querySelectorAll(".teacher-row")[i].children[j].children.length > 0) {
                        const node = document.querySelectorAll(".teacher-row")[i].children[j].children[0];
                        document.querySelectorAll(".teacher-row")[i].children[j].removeChild(node);
                        st.push({
                            studentId: document.querySelectorAll(".student-name")[i].getAttribute("id"),
                            numberOfPidgroup: document.querySelectorAll(".student-name")[i].getAttribute("numberOfPidgroup")
                        });
                    }
                }
            }
        }
        deleteStudentFromGroup(studentPos, st);
        let checkedGroup = document.querySelectorAll(".add-checkbox:checked");
        for (let i = 0; i < checkedGroup.length; i++) {
            checkedGroup[i].checked = false;
        }
        let selectStudent = document.querySelectorAll(".chose-student:checked");
        for (let j = 0; j < selectStudent.length; j++) {
            selectStudent[j].checked = false;
        }
        document.querySelector(".student-add-remove-form").style.display = "none";
    }
})

function deleteStudentFromGroup(studentPos, chStudent){
    const classId = document.querySelector(".class-group").getAttribute("id");
    let subName;
    const subject = document.querySelectorAll('.group-btn');
    for (let i = 0; i < subject.length; i++) {
        if (subject[i].classList.contains("show")){
            subName = subject[i].innerText;
        }
    }
    const obj = {classId: classId, subjectName: subName, students:chStudent};
    console.log(obj);
    const formData = new FormData();
    formData.append("jsonData", JSON.stringify(obj));
    deleteStudents(formData);


    // for (let i = 0; i < chStudent.length; i++) {
    //     let obj = {classId: classId,studentId:chStudent, pidgroup: }
    // }


}

async function deleteStudents(formData){
    let res;
    return res = await fetch("http://localhost:8080/deletestudentfromgroup", {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData
    }).then(res => res.json())
        .then((json) => {
            console.log(json);
            return json
        });

}



async function getGroup(i) {
    // let groups = document.querySelectorAll(".group-btn");
    deleteShowClassFromBtn();
    createTableStructure();
    let selectGroupBtn = document.querySelectorAll(".group-btn")[i-1];
    selectGroupBtn.classList.add("show");
    removeOptionFromSelect(selectGroupBtn);
    let subject = document.querySelector(".group-btn-container").children[i - 1].innerText;
    let classId = document.querySelector(".class-group").getAttribute("id");
    const obj = {subject: subject, classId: classId};
    const formData = new FormData();
    formData.append("jsonData", JSON.stringify(obj));
    let students = await getStudentsByGroup(formData).then(res => {
        return res;
    });
    if (students.length > 0){
        fillTable(students);
    }
    else {
        fillTableByListOfStudents(students);
    }
}

function removeOptionFromSelect(selectGroupBtn){
    let select = document.getElementsByTagName("select")[0];
    for (let i=0; i<select.length; i++) {
        if (select.options[i].value === selectGroupBtn.innerText)
            select.remove(i);
    }
    select.value = select.children[0].value;
}

function createTableStructure(){
    document.querySelector(".table-container").innerHTML = '';
    document.querySelector(".table-container").innerHTML += ` <table id="data" style="display: block;">
          <thead>
          <tr class="head-table">
            <th class="lessons" style="min-width: 265px">
              Учні
            </th>
          </tr>
          </thead>
          <tbody>
          </tbody>
            </table>`;

    let tableWrap = document.querySelector(".schedule-table-wrapper");
    tableWrap.style.width = "27.2%";
    tableWrap.style.borderBottomLeftRadius = "5px";
    tableWrap.style.borderTopLeftRadius = "20px";
    tableWrap.style.display = "block";

}

window.onload = async function () {
    let subjects = document.getElementById("content").getAttribute("subjects");
    if (subjects !== null){
        let subArr = subjects.split(',');
        fillSubjects(subArr);
    }
    let subject;
    let classId;
    if (document.querySelector(".group-btn-container").children.length !== 0) {
        subject = document.querySelector(".group-btn-container").children[0].innerText;
        classId = document.querySelector(".class-group").getAttribute("id");
        const obj = {subject: subject, classId: classId};
        const formData = new FormData();
        formData.append("jsonData", JSON.stringify(obj));
        let students = await getStudentsByGroup(formData).then(res => {
            return res;
        });
        fillTable(students);
    }
}




function fillTable(students){

    let kstOfGroup = fillThead(students);
    let tableWrap = document.querySelector(".schedule-table-wrapper");
    tableWrap.style.width = '0px';
    let width = 94.3*kstOfGroup;
    tableWrap.style.width = `${678 + width}px`
    fillRaw(students, kstOfGroup);
}

function fillThead(students){
    let studentWithGroup = [];

    for (let i = 0; i < students.length; i++) {
        if (students[i].numberOfGroup !== -1){
            studentWithGroup[i] = students[i];
        }
    }

    for (let i = 0; i < studentWithGroup.length; i++) {
        document.querySelector('.head-table').innerHTML += `<th class="date-inserted" onclick="myFunc2(${document.querySelectorAll(".date-inserted").length})">Групa №${students[i].numberOfGroup}</th>`;
    }
    return studentWithGroup.length;
}

function fillRaw(students, kstOfGroup){
    let st = [];
    let stud = [];
    let pidgroup;

    for (let i = 0; i < students.length; i++) {
        stud.push(students[i].students);
    }

    if (students.length > 0){
        for (let i = 0; i < students.length; i++) {
            st = students[i].students;
            pidgroup = students[i].numberOfGroup;
            fillStudents(st, kstOfGroup, students[i].numberOfGroup, true)
        }
    }

}

function fillStudents(st, kstOfGroups, numberOfGroup, newList){
    let tbody = document.getElementsByTagName("tbody");
    for (let i = 0; i < st.length; i++) {
        tbody[0].innerHTML += `<tr class="teacher-row">
        <th class="day-of-week" style="padding-left: 0">
            <div class="student">
                <input type="checkbox" class="chose-student" style="margin-left: 10px" onChange="myFunc()">
                    <span class="student-name" style="margin-left: 10px"
                          id="${st[i].userPersonalData.id}" >${st[i].userPersonalData.userName}</span>
            </div>
        </th>
    </tr>`;
    }
    if (newList){
        let raws = document.querySelectorAll(".teacher-row");
        for (let i = raws.length - st.length; i < raws.length; i++) {
            fillTd(kstOfGroups, numberOfGroup, raws[i]);
        }
    }
}


function fillTd(kstOfGroups,numberOfGroup, raw){
    for (let i = 0; i < kstOfGroups; i++) {
        if (i+1 === numberOfGroup){
            raw.innerHTML +=`<td class="schedule"><i class="fa-solid fa-square-check"></i></td>`
        }
        else {
            raw.innerHTML += `<td class="schedule"></td>`;
        }
    }
}



async function getStudentsByGroup(formData){
    let res;
    return res = await fetch("http://localhost:8080/groups", {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData
    }).then(res => res.json())
        .then((json) => {
            console.log(json);
            return json
        });

}




function fillSubjects(subArr){
    let subjectSelect = document.getElementById("subjects");
    for (let i = 0; i < subArr.length; i++) {
        subjectSelect.innerHTML += `<option value='${subArr[i]}'>${subArr[i]}</option>`;
    }
}



function deleteGroup(i){
    if (document.getElementById("content").getAttribute("role") === "YES") {

        let subject = document.querySelectorAll(".group-btn")[i - 1].innerText;
        let classId = document.querySelector(".class-group").getAttribute("id");
        const obj = {classId: classId, subjectName: subject};
        const formData = new FormData();
        formData.append("jsonData", JSON.stringify(obj));
        deleteGrFromDB(formData, classId);
    }
}

async function deleteGrFromDB(formData, classId){
fetch("http://localhost:8080/deletegroup", {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData
    }).then(res => {
        if (res.ok){
            window.location.href =  "http://localhost:8080/class-groups?classId=" + classId;
        }
        else {
            alert("Розклад вже введено!!!");
        }
    });
}



window.onclick = function (event) {
    if (event.target.className === "modal") {
        event.target.style.display = "none";
        document.body.style.overflowY = "auto";
    }
    if (!event.target.matches('.select-btn')) {		// якщо не була натиснута кнопка

        let menu_items =
            document.getElementsByClassName("select-menu");
        let i;
        for (i = 0; i < menu_items.length; i++) {
            let openMenu = menu_items[i];
            if (openMenu.classList.contains('active')) {
                openMenu.classList.remove('active');
            }
        }
    }

};


function myFunc2(k){
    document.querySelector(".delete-class").innerText = k+1;
    document.getElementById("delete-modal").style.display = "block";

}

document.querySelector(".deletebtn").addEventListener("click", ()=>{
    let classSubGroup = document.querySelector(".delete-class").innerText;
    let classId = document.querySelector(".class-group").getAttribute("id");
    let subjectName;

    for (let i = 0; i < document.querySelectorAll(".group-btn").length; i++) {
        if (document.querySelectorAll(".group-btn")[i].classList.contains("show")){
            subjectName = document.querySelectorAll(".group-btn")[i].innerText;
        }
    }

    const obj = {classSubGroup:classSubGroup, classId: classId, subjectName:subjectName};
    const formData = new FormData();
    formData.append("jsonData", JSON.stringify(obj));
    deleteSubGroup(formData, classSubGroup);
});


function deleteSubGroup(formData, classSubGroup){
        fetch("http://localhost:8080/deletesubgroup", {
            method: 'POST',
            headers: {
                Accept: 'application/json',
            },
            body: formData
        }).then(res => {
            if (res.ok){
                eraseGroupFromTable(classSubGroup)
            }
            else {
                alert("Розклад вже введено!!!");
            }
        })
}

function eraseGroupFromTable(numberOfGroup){
    let tr = document.querySelectorAll(".teacher-row");
    let node = document.querySelector(".head-table").children[numberOfGroup];
    document.querySelector(".head-table").removeChild(node);
    for (let i = 0; i < tr.length; i++) {
        tr[i].removeChild(tr[i].children[numberOfGroup]);
    }
    let tableWr = document.querySelector(".schedule-table-wrapper");
    let width = tableWr.offsetWidth -= 94.3;
    tableWr.style.width = `${width}px`
    document.getElementById("delete-modal").style.display = "none";
}


document.querySelector(".cancelbtn").addEventListener("click", ()=>{
    document.getElementById("delete-modal").style.display = "none";
})