let table;
let user;
window.onclick = function (event) {
    if (event.target.className === "modal" || event.target.className === "dropdown") {
        event.target.style.display = "none";
        document.body.style.overflow = "auto";
    }

    if (!event.target.classList.contains("fa-ellipsis") && !event.target.classList.contains("make-admin") &&
        !event.target.classList.contains("edit-subjects")) {
        for (let i = 0; i < document.querySelectorAll(".dropdown-content").length; i++) {
            document.querySelectorAll(".dropdown-content")[i].style.display = "none";
        }
    }

};

function editTeacherSubject(i){
    if (document.querySelector(".mult-select-tag") !== null){
        document.querySelector(".mult-select-tag").remove();
    }
    document.querySelector(".sub").value = '';
    fillOptions(i);
    document.querySelectorAll(".dropdown-content")[i].style.display = "none";

}



async function fillOptions(i) {
    let teacherSchool;
    let schoolId = document.querySelectorAll(".edit-subjects")[i].getAttribute("schoolId");
    teacherSchool = await getSchoolById(schoolId).then(res => {
        return res;
    });

    let subjects = teacherSchool.subjects;
    fillSubjectsOptions(subjects);
    fillSubjectsSelect(i);
    new MultiSelectTag('subjects')
    document.querySelector(".add-teacher-name").innerText = document.querySelectorAll(".pib")[i].innerText;
    document.querySelector(".save-subjects").removeAttribute("id");
    let teacherId = document.querySelectorAll(".edit-subjects")[i].getAttribute("id");
    document.querySelector(".save-subjects").setAttribute("teacherId", teacherId);
    document.getElementById("editTeacherSubjects").style.display = "block";

}


function fillSubjectsOptions(subjects){
    let subjectSelect = document.getElementById("subjects");
    removeElements(subjectSelect);
    for (let i = 0; i < subjects.length; i++) {
        subjectSelect.innerHTML += `<option>${subjects[i].name}</option>`;
    }
}

function removeElements(select){
    select.innerHTML = '';

}



async function getSchoolById(schoolId){
    let school;
    return school = fetch("http://localhost:8080/school/" + schoolId).then(res => res.json())
        .then((json) => {
            return json
        });
}


function fillSubjectsSelect(i){
    let subjects = document.querySelectorAll(".subjects")[i];
    clearAllSelectOptions();
    for (let j = 0; j < subjects.children.length; j+=2) {
        addOptionWithSubject(subjects.children[j].innerText)
    }
}

function clearAllSelectOptions(){
    let options = document.getElementsByTagName("option");
    for (let i = 0; i < options.length; i++) {
        options[i].removeAttribute("selected");
    }
}

function addOptionWithSubject(text){
    let options = document.getElementsByTagName("option");
    for (let i = 4; i < options.length; i++) {
        if (options[i].innerText === text){
            options[i].setAttribute("selected", "selected");
            break;
        }
    }
}


document.querySelector(".teacher-btn").addEventListener("click", ()=>{
    document.querySelector(".student-btn").classList.remove("show");
    document.querySelector(".admin-btn").classList.remove("show");
    document.querySelector(".teacher-btn").classList.add("show");
    document.querySelector(".table-wrapper").innerHTML = '';
    if (table !== undefined){
        destroyTable();
    }
    getAllTeacher();
});


document.querySelector(".student-btn").addEventListener("click", ()=>{
    document.querySelector(".student-btn").classList.add("show");
    document.querySelector(".admin-btn").classList.remove("show");
    document.querySelector(".teacher-btn").classList.remove("show");
    document.querySelector(".table-wrapper").innerHTML = '';
    if (table !== undefined){
        destroyTable();
    }
    getAllStudents();
});


document.querySelector(".admin-btn").addEventListener("click", ()=>{
    document.querySelector(".student-btn").classList.remove("show");
    document.querySelector(".admin-btn").classList.add("show");
    document.querySelector(".teacher-btn").classList.remove("show");
    document.querySelector(".table-wrapper").innerHTML = '';
    if (table !== undefined){
        destroyTable();
    }
    getAllAdmins()

});


window.onload = async function (){
    document.querySelector(".student-btn").classList.remove("show");
    document.querySelector(".admin-btn").classList.remove("show");
    document.querySelector(".teacher-btn").classList.add("show");
    document.querySelector(".table-wrapper").innerHTML = '';
    if (table !== undefined){
        destroyTable();
    }
   await getAllTeacher();

}




async function getAllTeacher() {
    let teachers = await getUsers("teachers").then(res => {
        return res;
    });
    if (teachers.length>0){
        fillTableByTeacher(teachers);
    }
    else {
        document.querySelector(".table-wrapper").innerHTML = `<div class="img-container">
          <img width="500px" height="300px" src="https://creazilla-store.fra1.digitaloceanspaces.com/cliparts/63211/school-clipart-md.png" alt="">
          <h1 class="class-user-name" style="padding-top: 30px; color: #bbbbbb;">Вчителів не знайдено</h1>
        </div>`;
    }
}

async function getAllStudents() {
    let students = await getUsers("students").then(res => {
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

async function getAllAdmins() {
    let admins = await getUsers("admins").then(res => {
        return res;
    });
    if (admins.length > 0) {
        fillTableByAdmins(admins);
    }
    else {
        document.querySelector(".table-wrapper").innerHTML = `<div class="img-container">
          <img width="500px" height="300px" src="https://creazilla-store.fra1.digitaloceanspaces.com/cliparts/63211/school-clipart-md.png" alt="">
          <h1 class="class-user-name" style="padding-top: 30px; color: #bbbbbb;">Адміністраторів не знайдено</h1>
        </div>`;
    }
}

function fillTableByAdmins(admins){
    document.querySelector(".table-wrapper").innerHTML += `
        <table id="example"  class="ui celled table" style="width:98%;">
        <thead>
          <tr>
            <th>ID</th>
            <th>ПІБ</th>
            <th>Номер телефону</th>
            <th>Поштова скринька</th>
            <th>Додатково</th>
          </tr>
          </thead> 
          <tbody> </tbody>
          <tfoot>
          <tr>
            <th>ID</th>
            <th>ПІБ</th>
            <th>Номер телефону</th>
            <th>Поштова скринька</th>
            <th>Додатково</th>
          </tr>
          </tfoot>
        </table>`;


    fillAllRowsByAdmins(admins);
    initializeTable();
}

async function fillAllRowsByAdmins(admins) {
    for (let i = 0; i < admins.length; i++) {
        let rolesArr = [];
        for (let j = 0; j < admins[i].userPersonalData.userAuthData.roles.length; j++) {
            rolesArr.push(admins[i].userPersonalData.userAuthData.roles[j].name);
        }
        let roleStr = rolesArr.join(',');
        document.querySelector("tbody").innerHTML += `<tr>
            <td class="id">${admins[i].userPersonalData.id}</td>
            <td class="pib">${admins[i].userPersonalData.userName}</td>
            <td class="phone-number">${admins[i].userPersonalData.workPhone}</td>
            <td class="email">${admins[i].userPersonalData.email}</td>
            <td>
                <div class="addition">
                <div class="dropdown">
                  <i class="fa-solid fa-ellipsis" onclick="blockDropdownMenu(${i})"></i>
                  <div class="dropdown-content">
                  </div> 
                </div>
              </div>
            </td>
            </tr>`;

        let userId = getCookie('userId');
        let user = await getUserById(`${userId}`).then(res => {
            return res;
        });
        let rolesUser = [];
        for (let j = 0; j < user.userAuthData.roles.length; j++) {
            rolesUser.push(user.userAuthData.roles[j].name);
        }
        if (rolesUser.includes("ROLE_ADMIN")) {
            checkRolesAdmin(admins, roleStr, i);

        } else {
            document.querySelectorAll(".addition")[i].innerHTML = '';
        }
    }

}


function fillTableByStudents(students){
    document.querySelector(".table-wrapper").innerHTML += `
        <table id="example"  class="ui celled table" style="width:98%;">
        <thead>
          <tr>
            <th>ID</th>
            <th>ПІБ</th>
            <th>Номер телефону</th>
            <th>Поштова скринька</th>
            <th>Клас</th>
            <th>Батьки</th>
            <th>Номер телефону одного з батьків</th>
          </tr>
          </thead> 
          <tbody> </tbody>
          <tfoot>
          <tr>
            <th>ID</th>
            <th>ПІБ</th>
            <th>Номер телефону</th>
            <th>Поштова скринька</th>
            <th>Клас</th>
            <th>Батьки</th>
            <th>Номер телефону одного з батьків</th>
          </tr>
          </tfoot>
        </table>`;
    fillAllRowsByStudents(students);
    initializeTable();
}

function fillAllRowsByStudents(students){
    for (let i = 0; i < students.length; i++) {
        document.querySelector("tbody").innerHTML += `<tr>
            <td class="id">${students[i].split("/")[0]}</td>
            <td class="pib">${students[i].split("/")[1]}</td>
            <td style="min-width: 160px" class="phone-number">${students[i].split("/")[2]}</td>
            <td class="email">${students[i].split("/")[3]}</td>
            <td class="class">${students[i].split("/")[4]}</td>
            <td class="parents">${students[i].split("/")[5]}</td>
            <td style="min-width: 160px" class="parents-phone">${students[i].split("/")[6]}</td>
            </tr>`;
    }

}



async function fillTableByTeacher(teachers){
    document.querySelector(".table-wrapper").innerHTML += `
        <table id="example"  class="ui celled table" style="width:98%;">
        <thead>
          <tr>
            <th>ID</th>
            <th>ПІБ</th>
            <th>Номер телефону</th>
            <th>Поштова скринька</th>
            <th>Предмети</th>
            <th>Додатково</th>
          </tr>
          </thead> 
          <tbody> </tbody>
          <tfoot>
          <tr>
            <th>ID</th>
            <th>ПІБ</th>
            <th>Номер телефону</th>
            <th>Поштова скринька</th>
            <th>Предмети</th>
            <th>Додатково</th>
          </tr>
          </tfoot>
        </table>`
   await fillAllRowsByTeacher(teachers)

    initializeTable();

}

function destroyTable(){
    table.destroy();
}


function initializeTable(){
    table = new DataTable('#example',{
        fixedHeader: true,
        ordering: false,
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
        document.querySelector("tbody").innerHTML += `<tr>
            <td class="id">${teachers[i].userPersonalData.id}</td>
            <td class="pib">${teachers[i].userPersonalData.userName}</td>
            <td class="phone-number">${teachers[i].userPersonalData.workPhone}</td>
            <td class="email">${teachers[i].userPersonalData.email}</td>
            <td class = "subjects"></td>
            <td>
                <div class="addition">
                <div class="dropdown">
                  <i class="fa-solid fa-ellipsis menu" onclick="blockDropdownMenu(${i})"></i>
                  <div class="dropdown-content">

                  </div> 
                </div>
              </div>
            </td>
            </tr>`;

        let userId = getCookie('userId');
        let user = await getUserById(`${userId}`).then(res => {
            return res;
        });
        let rolesUser = [];
        for (let j = 0; j < user.userAuthData.roles.length; j++) {
            rolesUser.push(user.userAuthData.roles[j].name);
        }

        if (rolesUser.includes("ROLE_ADMIN")){
            checkRoles(teachers, roleStr, i);

        }
        else {
            document.querySelectorAll(".addition")[i].innerHTML='';
        }

        fillSubjects(teachers[i].subjects, i);
    }

}

function getCookie(cookieName) {
    let cookie = {};
    document.cookie.split(';').forEach(function(el) {
        let [key,value] = el.split('=');
        cookie[key.trim()] = value;
    })
    return cookie[cookieName];
}

function getUserById(id){
    let user;
    return user = fetch("http://localhost:8080/user?id=" + id).then(res => res.json())
        .then((json) => {
            return json
        });
}



function checkRolesAdmin(admins, roles, i){
    if (roles.includes("ROLE_ADMIN") && roles.includes("ROLE_TEACHER")){
        document.querySelectorAll(".dropdown-content")[i].innerHTML += `
        <button class="make-admin" userData="${admins[i].userPersonalData.id}" id="${admins[i].id}" onClick="deleteAdmin(${i})">Видалити роль адміністратора</button>`;

    }
    else if(roles.includes("ROLE_ADMIN") && !roles.includes("ROLE_TEACHER")){
        document.querySelectorAll(".dropdown-content")[i].innerHTML +=`
        <button class="make-admin" userData="${admins[i].userPersonalData.id}" onClick="createNewTeacher(${i})">Зробити
            вчителем</button>`;
    }
}


function checkRoles(teachers,roles, i){
    if (roles.includes("ROLE_ADMIN") && roles.includes("ROLE_TEACHER")){
        document.querySelectorAll(".dropdown-content")[i].innerHTML += `
        <button class="make-admin" userData="${teachers[i].userPersonalData.id}" id="${teachers[i].id}" onClick="deleteAdmin(${i})">Видалити роль адміністратора</button>
        <button class="edit-subjects" schoolId="${teachers[i].userPersonalData.school.id}" id="${teachers[i].id}"
                onClick="editTeacherSubject(${i})">Редагувати список предметів</button>`;
    }
    else if(roles.includes("ROLE_ADMIN") && !roles.includes("ROLE_TEACHER")){
        document.querySelectorAll(".dropdown-content")[i].innerHTML +=`
        <button class="make-admin" id="${teachers[i].id}" onClick="createNewTeacher(${i})">Зробити
            вчителем</button>
        <button class="edit-subjects" schoolId="${teachers[i].userPersonalData.school.id}" id="${teachers[i].id}"
                onClick="editTeacherSubject(${i})">Редагувати список предметів</button>`;
    }
    else if(!roles.includes("ROLE_ADMIN") && roles.includes("ROLE_TEACHER")){
        document.querySelectorAll(".dropdown-content")[i].innerHTML +=`
        <button class="make-admin"  id="${teachers[i].id}" onClick="createNewAdmin(${i})">Зробити
            адміністратором</button>
        <button class="edit-subjects" schoolId="${teachers[i].userPersonalData.school.id}" id="${teachers[i].id}"
                onClick="editTeacherSubject(${i})">Редагувати список предметів</button>`;
    }

}


function blockDropdownMenu(i){
        for (let j = 0; j < document.querySelectorAll(".dropdown-content").length; j++) {
            document.querySelectorAll(".dropdown-content")[j].style.display = "none";
    }
        document.querySelectorAll(".dropdown-content")[i].style.display = "block";
}

function fillSubjects(subjects,j){
    for (let i = 0; i < subjects.length; i++) {
        document.querySelectorAll(".subjects")[j].innerHTML += `<span class="subject">${subjects[i].name}</span><br>`
    }
}



async function getUsers(userCategory){
     let users;
        return users = fetch("http://localhost:8080/all" + userCategory).then(
            res => res.json())
            .then((json) => {
                return json
            });
}


document.querySelector(".save-subjects").addEventListener("click", ()=>{
    let teacherId = document.querySelector(".save-subjects").getAttribute("teacherId");
    let allSubjects = document.querySelectorAll(".item-container");
    if (document.getElementById("subjects").value !==''){
        let listOfSubjects = [];
        for (let i = 0; i < allSubjects.length; i++) {
            listOfSubjects.push(allSubjects[i].innerText);
        }
        const subjectStr = listOfSubjects.join(',');
        let object = {id:teacherId,subjects:subjectStr}
        const formData = new FormData();
        formData.append("jsonData", JSON.stringify(object));
        saveUpdates(formData, "updatesubjects");
    }

})


function createNewAdmin(i){
    let teacherId = document.querySelectorAll(".make-admin")[i].getAttribute("id");
    let object = {id:teacherId};
    const formData = new FormData();
    formData.append("jsonData", JSON.stringify(object));
    saveUpdates(formData, "createadmin");

}

function deleteAdmin(i){
    let userData = document.querySelectorAll(".make-admin")[i].getAttribute("userData");
    let object = {id:userData};
    const formData = new FormData();
    formData.append("jsonData", JSON.stringify(object));
    saveUpdates(formData, "deleteadmin");

}


function createNewTeacher(i){
    let userData = document.querySelectorAll(".make-admin")[i].getAttribute("userData");
    let object = {id:userData};
    const formData = new FormData();
    formData.append("jsonData", JSON.stringify(object));
    saveUpdates(formData, "addadminroleteacher");

}


async function saveUpdates(formData, request){
    let res = await fetch("http://localhost:8080/" + request, {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData,
    }).then(response=>{
        if (response.ok){
            window.location.href = "http://localhost:8080/users";
        }
        else {
            alert("Помилка");
        }
    });

}