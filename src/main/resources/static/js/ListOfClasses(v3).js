let deleteBtnIcons = document.querySelectorAll(".delete-class-icons-container");
let deleteForm = document.getElementById("delete-modal");
let canselBtn = document.querySelector(".cancelbtn");
let deleteBtn = document.querySelector(".deletebtn");
let className = document.querySelectorAll(".class-name");
let classes = document.querySelectorAll(".class");

/*==================ВИДАЛЕННЯ КЛАСУ============================*/

for (let i = 0; i < deleteBtnIcons.length; i++) {
    deleteBtnIcons[i].addEventListener("click", ()=>{
        document.querySelector(".delete-class").innerText = className[i].innerText;
        document.body.style.overflow = "hidden";
        deleteForm.style.display = "block";
        deleteBtn.setAttribute("id", document.querySelectorAll(".class-span")[i].getAttribute("id"));
    })
}

/*==================ВИДАЛЕННЯ КЛАСУ============================*/



// deleteBtn.addEventListener("click", ()=>{
//     for (let i = 0; i < deleteBtnIcons.length; i++) {
//         if (className[i].innerText === deleteBtn.getAttribute("class-name")){
//             // const formData = new FormData();
//             // formData.append("jsonData", JSON.stringify(className[i].innerText));
//             deleteClassByName(className[i].innerText);
//             break;
//         }
//     }
// })

// async function deleteClassByName(className){
//     fetch("http://localhost:8080/class" + className,{
//         method:'DELETE'
//     })
//         .then(res => res.json())
//         .then((json) => {
//             return json
//         });
// }

canselBtn.addEventListener("click", ()=>{
    deleteForm.style.display = "none";
    document.body.style.overflowY = "auto";
})


document.querySelector(".add-new-class").addEventListener("click", async () => {

    let allTeachers = await getAllTeacherWhoHasNotClass().then(res => {
        return res;
    });
    console.log(allTeachers);
    fillAllOptions(allTeachers);
    document.getElementById("createClassModal").style.display = "block";
});

function fillAllOptions(allTeachers){
    let teacherNameSelect = document.querySelector(".class-teacher");
    teacherNameSelect.innerHTML = `<option disabled selected>Оберіть класного керівника</option>`;
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

document.querySelector(".save-class").addEventListener("click", ()=>{
    let value = checkAllFields();
    if (value.length === 4){
        if (document.querySelector(".classes-container").getAttribute("role")==="YES") {
            console.log(value);
            const nameOfClass = value[0] + "-" + value[1];
            const arr = [];
            arr.push(nameOfClass);
            arr.push(value[2]);
            arr.push(value[3]);
            console.log(arr);
            const formData = new FormData();
            const obj = {classData: arr};
            formData.append("jsonData", JSON.stringify(obj));
            saveNewClass(formData);
        }
        else {
            alert("Класи може створювати лише адміністратор!!!");
            document.getElementById("createClassModal").style.display = "none";
            document.body.style.overflowY = "auto";
        }
    }
})

async function saveNewClass(formData){
    let res = await fetch("http://localhost:8080/class", {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData,
    }).then(response=>{
        if (response.ok){
            window.location.href = "http://localhost:8080/classes";
        }
        else {
            alert("Помилка");
        }
    });

}

document.querySelector(".deletebtn").addEventListener("click", ()=>{
    deleteClass();
});

function deleteClass(){

    if (document.querySelector(".classes-container").getAttribute("role") === "YES"){
        let deleteClassId = deleteBtn.getAttribute("id");
        deleteClassById(deleteClassId);
    }
    else {
        alert("Ви не можете видаляти класи!!!");
    }

}

async function deleteClassById(classId){
    let res = await fetch("http://localhost:8080/deleteclass?classId=" + classId, {
        method: 'DELETE',
        headers: {
            Accept: 'application/json',
        }
    }).then(response=>{
        if (response.ok){
            window.location.href = "http://localhost:8080/classes";
        }
        else {
            alert("Помилка");
        }
    });

}


function findClass(classId) {
    getClassById(classId);

}

async function getClassById(classId){
    window.location.href = "http://localhost:8080/classprofile?classId=" + classId;
}

function checkAllFields(){
    let value = [];
    let selects = document.getElementsByTagName("select");
    let labels = document.querySelectorAll(".mandatory");
    for (let i = 0; i < selects.length; i++) {
        if (selects[i].value === selects[i].children[0].value){
            alert("Заповніть поле " + labels[i].innerText.toLowerCase());
            break;
        }
        else {
            value.push(selects[i].value);
        }
    }
    value.push(document.getElementById("cabinet").value);
    return value;
}
