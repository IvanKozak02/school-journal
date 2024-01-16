let table;
let ID = document.querySelectorAll(".student-id");
let edit = document.querySelector(".edit");



document.getElementById("edit-class-profile").addEventListener("click", async () => {

    document.getElementById("cabinet").value = document.querySelector(".class-cabinet").innerText;

    if (document.querySelector(".teacher-name").getAttribute("role") === "YES"){
        let allTeachers = await getAllTeacherWhoHasNotClass().then(res => {
            return res;
        });
        console.log(allTeachers);
        fillAllOptions(allTeachers);
        document.getElementById("updateClassProfile").style.display = "block";
    }
})


function fillAllOptions(allTeachers){
    let teacherNameSelect = document.querySelector(".class-teacher");
    let teacherName = document.querySelector(".teacher-name").innerText;
    let teacherId = document.querySelector(".teacher-name").getAttribute("id");
    teacherNameSelect.innerHTML = `<option value="${teacherId}" disabled selected>${teacherName}</option>`;
    for (let i = 0; i < allTeachers.length; i++) {
        teacherNameSelect.innerHTML += `<option value="${allTeachers[i].userPersonalData.id}">${allTeachers[i].userPersonalData.userName}</option>`;
    }

}

async function getAllTeacherWhoHasNotClass(){
    let teachers;
    return teachers = fetch("http://localhost:8080/allteacherwithoutclass")
        .then(res => res.json())
        .then((json) => {
            return json
        });
}



document.querySelector(".update-class").addEventListener("click", ()=>{
    let values = checkAllFields();
        let classId = document.querySelector(".class-number").getAttribute("id");
        const obj = {classId: classId, cabinet: values[1], newHeadTeacherId: values[0]};
        const formData = new FormData();
        formData.append("jsonData", JSON.stringify(obj));
        updateClassProfile(formData, classId);

})


async function updateClassProfile(formData, classId){
    let res = await fetch("http://localhost:8080/updateclassprofile", {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData
    }).then(response=>{
        if (response.ok){
            window.location.href = "http://localhost:8080/classprofile?classId=" + classId;
        }
        else {
            alert("Помилка");
        }
    });

}

function checkAllFields(){
    let value = [];
    let select = document.querySelector(".class-teacher");
            value.push(select.value);
    value.push(document.getElementById("cabinet").value);
    return value;
}


/*==============================ВИВЕДЕННЯ УЧНІВ І ВЧИТЕЛІВ=================*/

document.querySelector(".teacher-btn").addEventListener("click", ()=>{
    document.querySelector(".student-btn").classList.remove("show");
    document.querySelector(".teacher-btn").classList.add("show");
    document.querySelector(".table-wrapper").innerHTML = '';
    if (table !== undefined){
        destroyTable();
    }
    getAllTeachers();
});


document.querySelector(".student-btn").addEventListener("click", ()=>{
    document.querySelector(".student-btn").classList.add("show");
    document.querySelector(".teacher-btn").classList.remove("show");
    document.querySelector(".table-wrapper").innerHTML = '';
    if (table !== undefined){
        destroyTable();
    }
    getAllStudents();
});


window.onload = async function (){
    await getAllStudents();
    await getViewedMes();
}


function getCookie(cookieName) {
    let cookie = {};
    document.cookie.split(';').forEach(function(el) {
        let [key,value] = el.split('=');
        cookie[key.trim()] = value;
    })
    return cookie[cookieName];
}

async function getAllStudents() {
    let classId = document.querySelector(".class-number").getAttribute("id");
    let students = await getUsers("classstudents", classId).then(res => {
        return res;
    });
    if (students.length > 0) {
        fillTableByStudents(students);
    }
    else {
        document.querySelector(".table-wrapper").innerHTML = `<div class="img-container">
          <img width="500px" height="300px" src="https://creazilla-store.fra1.digitaloceanspaces.com/cliparts/63211/school-clipart-md.png" alt="">
          <h1 class="class-user-name" style="padding-top: 30px; color: #bbbbbb;">Учнів не знайдено</h1>
        </div>`;
    }
}

function destroyTable(){
    table.destroy();
}

function fillTableByStudents(students){
    document.querySelector(".table-wrapper").innerHTML += `
        <table id="example"  class="ui celled table" style="width:98%;">
        <thead>
          <tr>
            <th>ID</th>
            <th style="min-width: 165px">ПІБ</th>
            <th style="min-width: 180px">Номер телефону</th>
            <th>Поштова скринька</th>
            <th>Дата народження</th>
            <th>Батьки</th>
            <th style="min-width: 130px">Номер телефону одного з батьків</th>
            <th>Видалити</th>
          </tr>
          </thead> 
          <tbody class="list-body"> </tbody>
          <tfoot>
          <tr>
            <th>ID</th>
            <th>ПІБ</th>
            <th>Номер телефону</th>
            <th>Поштова скринька</th>
            <th>Дата народження</th>
            <th>Батьки</th>
            <th>Номер телефону одного з батьків</th>
            <th>Видалити</th>
          </tr>
          </tfoot>
        </table>`;
    fillAllRowsByStudents(students);
    initializeTable();
}

function fillAllRowsByStudents(students){
    for (let i = 0; i < students.length; i++) {
        document.querySelector(".list-body").innerHTML += `<tr class="student" style="font-weight: initial;">
            <td class="id" id="${students[i].id}">${students[i].userPersonalData.id}</td>
            <td class="pib">${students[i].userPersonalData.userName}</td>
            <td class="phone-number">${students[i].userPersonalData.workPhone}</td>
            <td class="email">${students[i].userPersonalData.email}</td>
            <td class="class">${students[i].userPersonalData.dateOfBirth}</td>
            <td class="parents">${students[i].parentsName}</td>
            <td class="parents-phone">${students[i].parentsPhones}</td>
             <td>
             <div class="update-or-delete" onclick="deleteStudentFromClass(${i})">
              <div class="delete">
                <i class="fa-solid fa-trash-arrow-up" onclick="deleteStudentsFromClass(${students[i].id})"></i>
              </div>
            </div>
            </td>
            </tr>`;
    }


}

async function getAllTeachers() {
    let classId = document.querySelector(".class-number").getAttribute("id");
    let teachers = await getUsers("teachersforclass", classId).then(res => {
        return res;
    });
    if (teachers.length > 0) {
        fillTableByTeachers(teachers);
    }
    else {
        document.querySelector(".table-wrapper").innerHTML = `<div class="img-container">
          <img width="500px" height="300px" src="https://creazilla-store.fra1.digitaloceanspaces.com/cliparts/63211/school-clipart-md.png" alt="">
          <h1 class="class-user-name" style="padding-top: 30px; color: #bbbbbb;">Вчителів не знайдено</h1>
        </div>`;
    }
}

function fillTableByTeachers(teachers){
    document.querySelector(".table-wrapper").innerHTML += `
        <table id="example"  class="ui celled table" style="width:98%;">
        <thead>
          <tr>
            <th>ID</th>
            <th>ПІБ</th>
            <th>Номер телефону</th>
            <th>Поштова скринька</th>
            <th>Предмети</th>
          </tr>
          </thead> 
          <tbody class="list-body"> </tbody>
          <tfoot>
          <tr>
            <th>ID</th>
            <th>ПІБ</th>
            <th>Номер телефону</th>
            <th>Поштова скринька</th>
            <th>Предмети</th>
          </tr>
          </tfoot>
        </table>`

    fillAllRowsByTeacher(teachers)
    initializeTable();

}



function initializeTable(){
    table = new DataTable('#example',{
        fixedHeader: true,
        ordering: false,
        responsive:true,
        pageLength:100,
        language: {
            url: "//cdn.datatables.net/plug-ins/1.13.4/i18n/uk.json"
        }
    });
}

async function fillAllRowsByTeacher(teachers) {
    for (let i = 0; i < teachers.length; i++) {
        let rolesArr = [];
        for (let j = 0; j < teachers[i].userPersonalData.userAuthData.roles.length; j++) {
            rolesArr.push(teachers[i].userPersonalData.userAuthData.roles[j].name);
        }
        let roleStr = rolesArr.join(',');
        document.querySelector(".list-body").innerHTML += `<tr>
            <td class="id">${teachers[i].userPersonalData.id}</td>
            <td class="pib">${teachers[i].userPersonalData.userName}</td>
            <td class="phone-number">${teachers[i].userPersonalData.workPhone}</td>
            <td class="email">${teachers[i].userPersonalData.email}</td>
            <td class = "subjects"></td>
            </tr>`;

        fillSubjects(teachers[i].subjects, i);
    }

}

function fillSubjects(subjects,j){
    for (let i = 0; i < subjects.length; i++) {
        document.querySelectorAll(".subjects")[j].innerHTML += `<span class="subject">${subjects[i].name}</span><br>`
    }
}

async function getUsers(userCategory, classId){
    return fetch("http://localhost:8080/all" + userCategory + "/" +"?classId=" + classId).then(
        res => res.json())
        .then((json) => {
            return json
        });
}




/*==============================ВИВЕДЕННЯ УЧНІВ І ВЧИТЕЛІВ=================*/


/*--------------ФОРМА ДЛЯ ДОДАВАННЯ КОРИСТУВАЧА------------------*/

let saveUserBtn = document.querySelector(".save-user");
let addUserBtn = document.querySelector(".add-user-btn");
let addUserForm = document.getElementById("modalNine");
let userID = document.getElementById("user-id");
let optionsUser = document.querySelectorAll(".class-option-user");

let selectMenuUser = document.querySelector(".select-menu-user");


addUserBtn.addEventListener("click", async () => {
    let ul = document.querySelector(".options");
    ul.innerHTML = '';
    ul.innerHTML += `<li class="class-option-user not-found" style="display: none;">
                <span class="option-text">Студентів не знайдено</span>
              </li>`
    addUserForm.style.display = "block";
    document.body.style.overflow = "hidden";
    document.getElementById("user-id-container").removeAttribute("data-error");
    userID.value = "";
    selectMenuUser.classList.remove("active");
    let students = await getStudents().then(res => {
        return res;
    });
    fillAllLi(students);

})



function fillAllLi(students){
    let ul = document.querySelector(".options");
    let allSt = [];
    for (let i = 0; i < document.querySelectorAll(".id").length; i++) {
        allSt.push(document.querySelectorAll(".id")[i].innerText);
    }

    for (let i = 0; i < students.length; i++) {
        if (!allSt.includes(students[i].userPersonalData.id)){
            ul.innerHTML += `<li class="class-option-user" onClick="fillInput(${i+1})">
                <span class="option-text" >${students[i].userPersonalData.id}</span>
              </li>`
        }
    }
}

function fillInput(i){
    userID.value = document.querySelectorAll(".option-text")[i].innerText;
    selectMenuUser.classList.remove("active");
    document.querySelector(".save-user").removeAttribute("disabled");
}


userID.addEventListener("input", async () => {
    /*Серверна частина якщо є ID в базі то витягуємо його*/
    if (userID.value === "") {
        selectMenuUser.classList.remove("active");
    } else {
        selectMenuUser.classList.add("active");
    }
    document.getElementById("user-id-container").removeAttribute("data-error");

})

function filterByLetter() {
    let input, filter, ul, li, span, i, txtValue, kst=0;
    input = document.getElementById("user-id");
    filter = input.value.toUpperCase();
    ul = document.querySelector(".options");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        span = li[i].getElementsByTagName("span")[0];
        txtValue = span.textContent || span.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            document.querySelector(".not-found").style.display = "none";
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";
            kst+=1;
        }
    }
    if (kst === li.length){
        document.querySelector(".not-found").style.display = "flex";
        document.querySelector(".save-user").setAttribute("disabled", "disabled");
    }
}


async function getStudents(){
    let users;
    return users = fetch("http://localhost:8080/allstudentswithoutclass").then(
        res => res.json())
        .then((json) => {
            return json
        });
}


optionsUser.forEach(option =>{
    option.addEventListener("click", ()=>{
        userID.value = option.querySelector(".option-text").innerText;
        selectMenuUser.classList.remove("active");
    })
})


saveUserBtn.addEventListener("click", ()=>{
    let userId = getCookie('userId');

    if (document.querySelector(".teacher-name").getAttribute("role") === "YES" || userId === document.querySelector(".teacher-name").getAttribute("id")) {
        if (userID.value === "") {
            document.getElementById("user-id-container").setAttribute("data-error", "Оберіть ID користувача");
        }
        let classId = document.querySelector(".class-number").getAttribute("id");
        let studentId = userID.value;
        const obj = {userPersonalDataId: studentId, classId: classId};
        const formData = new FormData();
        formData.append("jsonData", JSON.stringify(obj));
        addStudentToClass(formData, classId);
    }
    else {
        alert("Ви не можете додавати учнів до даного класу!!!");
        document.getElementById("modalNine").style.display = "none";
        document.body.style.overflowY = "auto";
    }
});


async function addStudentToClass(formData, classId){
    let res = await fetch("http://localhost:8080/addstudenttoclass", {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData,
    }).then(response=>{
        if (response.ok){
            window.location.href = "http://localhost:8080/classprofile?classId=" + classId;
        }
        else {
            alert("Помилка");
        }
    });

}


/*--------------ФОРМА ДЛЯ ДОДАВАННЯ КОРИСТУВАЧА------------------*/


/*--------------ФОРМА ДЛЯ ВИДАЛЕННЯ СТУДЕНТА------------------*/

function deleteStudentFromClass(i){
    document.querySelector(".delete-student").innerText = document.querySelectorAll(".pib")[i].innerText;
    document.querySelector(".deletebtn").setAttribute("stId", document.querySelectorAll(".id")[i].getAttribute("id"));
    document.getElementById("delete-student-from-class").style.display = "block";
}

/*--------------ФОРМА ДЛЯ ВИДАЛЕННЯ СТУДЕНТА------------------*/

document.querySelector(".deletebtn").addEventListener("click",()=>{
   let studentId = document.querySelector(".deletebtn").getAttribute("stId");
   let classId = document.querySelector(".class-number").getAttribute("id");

   const obj = {studentId: studentId, classId: classId};
   const formData = new FormData();
   formData.append("jsonData", JSON.stringify(obj));
   deleteStudent(formData);
});


async function deleteStudent(formData){
    fetch("http://localhost:8080/deletestudentfromclass",{
        method: 'POST',
        body:formData
    })
        .then(res =>{
            if (res.ok){
                window.location.href = "http://localhost:8080/classprofile?classId=" + document.querySelector(".class-number").getAttribute("id");
            }
            else {
                return res.json()
            }
        }).then(data=>{
            alert(data.message);
    })
}

function getClassGroup(classId){
    window.location.href = "http://localhost:8080/class-groups?classId=" + classId;
}


window.onclick = function (event) {
    if (event.target.className === "modal") {
        event.target.style.display = "none";
        document.body.style.overflowY = "auto";
    }

};

document.querySelector(".cancelbtn").addEventListener("click", ()=>{
    document.getElementById("delete-student-from-class").style.display = "none";
})