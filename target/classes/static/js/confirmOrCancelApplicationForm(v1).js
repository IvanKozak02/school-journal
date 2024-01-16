let openApplication = document.querySelectorAll(".update");

for (let i = 0; i < openApplication.length; i++) {
    openApplication[i].addEventListener("click",async () => {
        if (document.querySelector(".sub").innerHTML !==''){
            document.querySelector(".sub").innerHTML = '';
        }

        const formData = getUserNameAndDateOfCreateApplication(i);

        let application = await getApplication(formData).then(res => {
            return res;
        });

        if (application.status === "Нова"){
            document.querySelector(".confirm-app").removeAttribute("username");
            document.querySelector(".confirm-app").removeAttribute("dateOfCreate");
            document.querySelector(".confirm-app").setAttribute("username", document.querySelectorAll(".sender")[i].innerText);
            document.querySelector(".confirm-app").setAttribute("dateOfCreate", document.querySelectorAll(".date-of-creation-app")[i].innerText);
            document.querySelector(".material-block").style.visibility = "visible";
            document.querySelector(".buttons").style.visibility = "visible";
            document.querySelector(".formbg").style.overflowY = "auto";
            fillApplicationForm(application,false);
        }
        else {
            document.querySelector(".material-block").style.visibility = "hidden";
            document.querySelector(".buttons").style.visibility = "hidden";
            document.querySelector(".formbg").style.overflow = "hidden";
            fillApplicationForm(application,true);
        }


        document.getElementById("application").style.display = "block";

    })
}

function getUserNameAndDateOfCreateApplication(i){
    let date_of_creation = document.querySelectorAll(".date-of-creation-app")[i].innerText;
    let username = document.querySelectorAll(".sender")[i].innerText;
    const formData = new FormData();
    let appData = {dateOfCreation: date_of_creation, userName: username};
    formData.append("jsonData", JSON.stringify(appData));
    return formData;

}

function fillApplicationForm(application, isApproved){
    document.querySelector(".user-name").innerText = application.userPersonalData.userName;
    document.querySelector(".user-email").innerText = application.userPersonalData.email;
    document.querySelector(".date-of-creation").innerText = application.dateOfCreate;
    document.querySelector(".name").innerText = application.userPersonalData.userName;
    document.querySelector(".school-name").innerText = application.school.name;
    document.querySelector(".region").innerText = application.school.city;

    if (application.typeOfApplication === "Приєднання до школи на посаду вчителя"){
        document.querySelector(".position").innerText = "на посаду вчителя";
        fillSubjects(application)
    }
    else {
        document.querySelector(".position").innerText = "як учня";
    }
    if (!isApproved){
        document.querySelector(".document").innerText = application.docURL;
        document.querySelector(".document").href = "/downloadFile/" + application.docURL;
    }

}

function fillSubjects(application){
    let subjects = application.subjects.split(',');

    document.querySelector(".sub").innerHTML += `<span class="subjects-s">з предмету:</span><br>
                <div class="subjects" style="margin-top: 40px;text-align: center;overflow-y: auto; max-height: 62px"> </div>`
    for (let i = 0; i < subjects.length; i++) {
        document.querySelector(".subjects").innerHTML += addSubjects(subjects[i]);
    }

}

function addSubjects(subject){
    return `<span class="subject">${subject}</span><br>`
}

async function getApplication(formData){
    return fetch("http://localhost:8080/application", {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData
    })
        .then(res => res.json())
        .then((json) => {
            return json
        });
}

/*=================================ВИДАЛЕННЯ ЗАЯВКИ=====================================*/

let allDel = document.querySelectorAll(".delete")

for (let i = 0; i < allDel.length; i++) {
    allDel[i].addEventListener("click", ()=>{
        document.getElementById("deleteModal").style.display = "block";
        document.querySelector(".deletebtn").removeAttribute("username");
        document.querySelector(".deletebtn").removeAttribute("dateOfCreate");
        document.querySelector(".deletebtn").setAttribute("username", document.querySelectorAll(".sender")[i].innerText);
        document.querySelector(".deletebtn").setAttribute("dateOfCreate", document.querySelectorAll(".date-of-creation-app")[i].innerText);
    })

}

document.querySelector(".cancelbtn").addEventListener("click", ()=>{
    document.getElementById("deleteModal").style.display = "none";
})

document.querySelector(".deletebtn").addEventListener("click", async () => {
    let date_of_creation = document.querySelector(".deletebtn").getAttribute("dateOfCreate");
    let username = document.querySelector(".deletebtn").getAttribute("username");
    const formData = new FormData();
    let appData = {dateOfCreation: date_of_creation, userName: username};
    formData.append("jsonData", JSON.stringify(appData));
    deleteApplication(formData);
})

async function deleteApplication(formData){
    let message;
    return message = fetch("http://localhost:8080/application", {
        method:'DELETE',
        headers: {
            Accept: 'application/json',
        },
        body: formData
    })
        .then(res => {
            if (res.ok){
                alert("Заявку успішно видалено!!!");
                window.location.href = "http://localhost:8080/applications";
            }
            else {
                alert("Заявку не вдалось видалити!!!")
                window.location.href = "http://localhost:8080/applications";
            }
        })

}

/*================================ПІДТВЕРДЖЕННЯ ЗАЯВКИ======================================*/
document.querySelector(".confirm-app").addEventListener("click", async () => {
       saveOrRejectApplication("approveapplication");
});


/*================================ВІДХИЛЕННЯ ЗАЯВКИ======================================*/
document.querySelector(".cancel-app").addEventListener("click", async () => {
    saveOrRejectApplication("rejectapplication");
});


async function saveOrRejectApplication(request) {
    let date_of_creation = document.querySelector(".confirm-app").getAttribute("dateOfCreate");
    let username = document.querySelector(".confirm-app").getAttribute("username");
    const formData = new FormData();
    let appData = {dateOfCreation: date_of_creation, userName: username};
    formData.append("jsonData", JSON.stringify(appData));
    let result = await application(formData, request, "POST").then(res => {
        return res;
    });
    alert(result.message)
    window.location.href = "http://localhost:8080/applications";
}

async function application(formData, request, method){
    return fetch("http://localhost:8080/" + request, {
        method:`${method}`,
        headers: {
            Accept: 'application/json',
        },
        body: formData
    })
        .then(res => res.json())
        .then((json) => {
            return json
        });
}