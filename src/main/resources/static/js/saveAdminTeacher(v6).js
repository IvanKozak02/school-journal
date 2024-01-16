document.querySelector(".add-to-school").addEventListener("click", ()=>{
    document.querySelector(".input").placeholder = 'Пошук...'
    document.getElementById("addTeacherToSchool").style.display = "block";
})

document.querySelector(".save-teacher").addEventListener("click", ()=>{
    let allSubjects = document.querySelectorAll(".item-container");
    let listOfSubjects = [];
    if (allSubjects.length !== 0){
        for (let i = 0; i < allSubjects.length; i++) {
            listOfSubjects.push(allSubjects[i].innerText);
        }
        const subjectStr = listOfSubjects.join(',');

        const formData = new FormData();
        formData.append("jsonData", JSON.stringify(subjectStr));

        saveAdminAsTeacher(formData);
    }
    else {
        alert("Заповніть колонку предмети, які викладаються у школі");
    }

})

async function saveAdminAsTeacher (formData){
    let res = await fetch("http://localhost:8080/saveAdminAsTeacher", {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData,
    }).then(response=>response.json())
        .then(data=>{
            alert(data.message)
            window.location.href = "http://localhost:8080/admin";
        })
}