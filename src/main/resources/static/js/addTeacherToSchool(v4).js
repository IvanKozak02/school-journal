
let institutions__teacher;



document.querySelector(".next-t").addEventListener("click", ()=>{
    document.querySelector(".data-t").style.display = "none";
    document.querySelector(".previous-t").style.display = "block";
    document.querySelector(".previous-t").style.marginRight = "0";
    document.querySelector(".next-t").style.display = "none";
    document.querySelector(".input-cont-teacher").style.display = "flex";
    document.querySelector(".container").style.display = "none";
    document.querySelector(".save-teacher").style.display = "block";

})

document.querySelector(".previous-t").addEventListener("click", ()=>{
    document.querySelector(".data-t").style.display = "flex";
    document.querySelector(".previous-t").style.display = "none";
    document.querySelector(".next-t").style.display = "block";
    document.querySelector(".input-cont-teacher").style.display = "none";
    document.querySelector(".container").style.display = "block";
    document.querySelector(".save-teacher").style.display = "none";

})


document.querySelector(".add-to-school").addEventListener("click", async () => {
    document.body.style.overflow = "hidden";

    document.querySelector(".next-t").setAttribute("disabled", "disabled");
    if (document.querySelectorAll(".mult-select-tag")[1] !== undefined){
        document.querySelectorAll(".mult-select-tag")[1].innerHTML = '';
        document.querySelectorAll(".mult-select-tag")[1].remove();
    }


    document.querySelector(".input-cont-teacher").style.display = "none";
    document.querySelector(".container").style.display = "block";
    document.querySelector(".previous-t").style.display = "none";
    document.querySelector(".save-teacher").style.display = "none";

    clearValueOfInput__teacher();
    document.querySelector(".previous-t").style.display = "none";
    document.querySelector(".data-t").style.display = "flex";
    document.querySelector(".next-t").style.display = "block";
    document.getElementById("addTeacherToSchool").style.display = "block";
    document.getElementById("addTeacherToSchool").style.overflowY = "auto";
    // if (document.querySelector(".img-area").children.length === 4) {
    //     document.querySelector(".img-area").children[3].remove();
    //     document.querySelector(".img-area").removeAttribute("data-img");
    // }

    checkImgArea();
    institutions__teacher = await getAllSchools().then(res => {
        return res;
    });
    fillUniqueRegion();

    // const uniqueRegion = [...new Set(institutions__teacher.map((item) => item.oblast))]
    //
    // for (let i = 0; i < uniqueRegion.length; i++) {
    //     document.querySelector(".select-element__teacher").innerHTML += `<option>${uniqueRegion[i]}</option>`
    // }
    // document.querySelector(".select-element__teacher").removeAttribute("disabled");


})

function checkImgArea(){
    if (document.querySelector(".img-area").children.length === 4) {
        document.querySelector(".img-area").children[3].remove();
        document.querySelector(".img-area").removeAttribute("data-img");
    }
}


function clearValueOfInput__teacher(){

    inputFile.value = '';
    document.querySelector(".select-element__teacher").innerHTML = '';
    document.querySelector(".select-element__teacher").innerHTML += `<option  disabled selected>Оберіть область</option>`;
    document.querySelector(".select-element__teacher").value = document.querySelector(".select-element__teacher").children[0].value;;
    document.querySelector(".input-cont").value = '';
    document.getElementById("city__teacher").innerHTML = '';
    document.getElementById("city__teacher").innerHTML+=`<option disabled selected>Оберіть населений пункт</option>`
    document.getElementById("city__teacher").value = document.getElementById("city").children[0].value;
    document.getElementById("viddil__teacher").innerHTML = '';
    document.getElementById("viddil__teacher").innerHTML +=`<option disabled selected>Оберіть відділ</option>`;
    document.getElementById("viddil__teacher").value = document.getElementById("viddil").children[0].value;
    document.getElementById("schools__teacher").innerHTML = '';
    document.getElementById("schools__teacher").innerHTML +=`<option disabled selected>Оберіть школу</option>`;
    document.getElementById("schools__teacher").value = document.getElementById("schools").children[0].value
    document.getElementById("city__teacher").setAttribute("disabled", "disabled");
    document.getElementById("schools__teacher").setAttribute("disabled", "disabled");
    document.getElementById("viddil__teacher").setAttribute("disabled", "disabled");
}



// function checkInputs(){
//     document.getElementById("addTeacherToSchool").style.display = "block";
//     document.querySelector(".oblast").value = document.querySelector(".select-element").children[0].value;
//     document.getElementById("typeOfCity__teacher").value = document.getElementById("typeOfCity").children[0].value;
//     document.getElementById("region__teacher").value = document.getElementById("region__teacher").children[0].value;
//     document.getElementById("city__teacher").innerHTML = '';
//     document.getElementById("city__teacher").innerHTML+=`<option disabled selected>Оберіть населений пункт</option>`
//     document.getElementById("city__teacher").value = document.getElementById("city__teacher").children[0].value;
//     document.getElementById("viddil__teacher").innerHTML = '';
//     document.getElementById("viddil__teacher").innerHTML +=`<option disabled selected>Оберіть відділ</option>`;
//     document.getElementById("viddil__teacher").value = document.getElementById("viddil__teacher").children[0].value;
//     document.getElementById("schools__teacher").innerHTML = '';
//     document.getElementById("schools__teacher").innerHTML +=`<option disabled selected>Оберіть школу</option>`;
//     document.getElementById("schools__teacher").value = document.getElementById("schools__teacher").children[0].value
//     document.getElementById("typeOfCity__teacher").setAttribute("disabled", "disabled");
//     document.getElementById("city__teacher").setAttribute("disabled", "disabled");
//     document.getElementById("region__teacher").setAttribute("disabled", "disabled");
//     document.getElementById("schools__teacher").setAttribute("disabled", "disabled");
//     document.getElementById("viddil__teacher").setAttribute("disabled", "disabled");
// }


const oblast = document.querySelector(".select-element__teacher");


oblast.addEventListener("change",  () => {

    // document.getElementById("city__teacher").innerHTML = '';
    // document.getElementById("city__teacher").innerHTML+=`<option disabled selected>Оберіть населений пункт</option>`
    // document.getElementById("viddil__teacher").innerHTML = '';
    // document.getElementById("schools__teacher").innerHTML = '';
    // document.getElementById("viddil__teacher").innerHTML +=`<option disabled selected>Оберіть відділ</option>`
    // document.getElementById("schools__teacher").innerHTML +=`<option disabled selected>Оберіть школу</option>`
    checkRegion();
    fillCity();

});

function checkRegion(){
    document.getElementById("city__teacher").innerHTML = '';
    document.getElementById("city__teacher").innerHTML+=`<option disabled selected>Оберіть населений пункт</option>`
    document.getElementById("viddil__teacher").innerHTML = '';
    document.getElementById("schools__teacher").innerHTML = '';
    document.getElementById("viddil__teacher").innerHTML +=`<option disabled selected>Оберіть відділ</option>`
    document.getElementById("schools__teacher").innerHTML +=`<option disabled selected>Оберіть школу</option>`
}

function fillCity(){
    const city = getCity__teacher(document.querySelector(".select-element__teacher").value);
    fillCitySelect(city, "city__teacher");
    document.getElementById("city__teacher").removeAttribute("disabled");
}

//
// async function typeOfCitySelect__teacher(event){
//     // document.getElementById("region__teacher").value = document.getElementById("region").children[0].value;
//     // document.getElementById("regions").style.display = "none";
//     document.getElementById("city__teacher").innerHTML = '';
//     document.getElementById("city__teacher").innerHTML+=`<option disabled selected>Оберіть населений пункт</option>`
//     const typeOfCity = `${event.target.value}`;
//     // if (typeOfCity === "Село" || typeOfCity === "Селище міського типу"){
//     //     document.getElementById("regions").style.display = "block";
//     //     const region = getRegion(institutions__teacher);
//     //     fillRegionSelect(region, "region__teacher");
//     //     document.getElementById("region__teacher").removeAttribute("disabled");
//     //
//     // }
//     // else {
//
//
//     // }
// }

function getCity__teacher(region){
    return [...new Set(institutions__teacher.filter(item => item.oblast.includes(region)).map(item =>
        item.city.split(",")[0]))];
}


// document.getElementById("region__teacher").addEventListener("change", (event)=>{
//     if (document.getElementById("typeOfCity__teacher").value === "Село"){
//         const  land = getLand(`${event.target.value}`, institutions__teacher);
//         fillCitySelect(land,"city__teacher");
//         document.getElementById("city__teacher").removeAttribute("disabled");
//         document.getElementById("viddil__teacher").innerHTML = '';
//         document.getElementById("schools__teacher").innerHTML = '';
//         document.getElementById("viddil__teacher").innerHTML +=`<option disabled selected>Оберіть відділ</option>`
//         document.getElementById("schools__teacher").innerHTML +=`<option disabled selected>Оберіть школу</option>`
//     }
//     else{
//         const  smt = getSMT(`${event.target.value}`, institutions__teacher);
//         fillCitySelect(smt,"city__teacher");
//
//         document.getElementById("city__teacher").removeAttribute("disabled");
//
//     }
// })


document.getElementById("city__teacher").addEventListener("change", (event)=>{
    // document.getElementById("viddil__teacher").value = document.getElementById("viddil__teacher").children[0].value;
    // document.getElementById("schools__teacher").value = document.getElementById("viddil__teacher").children[0].value;
    // document.getElementById("viddil__teacher").innerHTML = '';
    // document.getElementById("schools__teacher").innerHTML = '';
    // document.getElementById("viddil__teacher").innerHTML +=`<option disabled selected>Оберіть відділ</option>`
    // document.getElementById("schools__teacher").innerHTML +=`<option disabled selected>Оберіть школу</option>`

    clearFieldsAfterCity();

    const nas_punkt = `${event.target.value}`;
    // const viddils = getViddils__teacher(nas_punkt);
    // fillViddils(viddils, "viddil__teacher");
    // document.getElementById("viddil__teacher").removeAttribute("disabled");

    findViddilsByCityName(nas_punkt)
})





function findViddilsByCityName(nas_punkt){
    const viddils = getViddils__teacher(nas_punkt);
    fillViddils(viddils, "viddil__teacher");
    document.getElementById("viddil__teacher").removeAttribute("disabled");
}

function clearFieldsAfterCity(){
    document.getElementById("viddil__teacher").value = document.getElementById("viddil__teacher").children[0].value;
    document.getElementById("schools__teacher").value = document.getElementById("viddil__teacher").children[0].value;
    document.getElementById("viddil__teacher").innerHTML = '';
    document.getElementById("schools__teacher").innerHTML = '';
    document.getElementById("viddil__teacher").innerHTML +=`<option disabled selected>Оберіть відділ</option>`
    document.getElementById("schools__teacher").innerHTML +=`<option disabled selected>Оберіть школу</option>`
}


function getViddils__teacher(nas_punkt){
    if (nas_punkt==="Львів"){
        return [...new Set(institutions__teacher.filter(item => item.city.includes("Львів, "))
            .map(item => item.viddil))];
    }
    else {
        return [...new Set(institutions__teacher.filter(item => item.city.includes(`${nas_punkt}`))
            .map(item => item.viddil))];
    }
}


document.getElementById("viddil__teacher").addEventListener("change", (event)=>{
    const viddil = `${event.target.value}`;
    // const nas_punkt = document.getElementById("city__teacher").value;
    // const schools = getSchool__teacher(viddil, nas_punkt, institutions__teacher);
    // fillSchools(schools, "schools__teacher");
    // document.getElementById("schools__teacher").removeAttribute("disabled");
    findSchoolsByViddils(viddil)

})

function findSchoolsByViddils(viddil){
    const nas_punkt = document.getElementById("city__teacher").value;
    const schools = getSchool__teacher(viddil, nas_punkt, institutions__teacher);
    fillSchools(schools, "schools__teacher");
    document.getElementById("schools__teacher").removeAttribute("disabled");
}


document.getElementById("schools__teacher").addEventListener("change", (event)=>{
    const school = `${event.target.value}`;
    const subjects = getAllSubjectsOfSchool(school);
    if (document.querySelectorAll(".mult-select-tag")[1] !== undefined){
        document.querySelectorAll(".mult-select-tag")[1].innerHTML = '';
        document.querySelectorAll(".mult-select-tag")[1].remove()
    }
    fillSubjects(subjects);
    document.querySelector(".next-t").removeAttribute("disabled");

})

function fillSubjects(subjects){
    let subjectSelect = document.getElementById("subjects");
    removeElements(subjectSelect);
    for (let i = 0; i < subjects[0].length; i++) {
        subjectSelect.innerHTML += `<option>${subjects[0][i].name}</option>`;
    }
    new MultiSelectTag('subjects');

}

function getAllSubjectsOfSchool(school){
    return [...new Set(institutions__teacher.filter(item => item.name === school).map(item => item.subjects))];
}


function getSchool__teacher(viddil, nas_punkt){
    return [...new Set(institutions__teacher.filter(item => item.viddil === viddil && item.city.includes(nas_punkt)).map(item => item.name))];

}



document.querySelector(".save-teacher").addEventListener("click", ()=>{
    let allSubjects = document.querySelectorAll(".item-container");
    let allDataIsCorrect = checkAllSelectsTeacher(true)

    let inputMultipartFiles = document.getElementById("file");
    if (allDataIsCorrect){
        let listOfSubjects = [];
        for (let i = 0; i < allSubjects.length; i++) {
            listOfSubjects.push(allSubjects[i].innerText);
        }

        const subjectStr = listOfSubjects.join(',');
        const typeOfApp = 'Приєднання до школи на посаду вчителя';

        let application = {typeOfApplication: typeOfApp, schoolName: document.getElementById("schools__teacher").value,
            subjects: subjectStr};
        const formData = new FormData();
        formData.append("jsonData", JSON.stringify(application));
        formData.append("file", inputMultipartFiles.files[0])
         saveApplication(formData);
    }
})


async function saveApplication(formData){
    let res = await fetch("http://localhost:8080/newApplications", {
        method: 'POST',
        enctype: 'multipart/form-data',
        headers: {
            Accept: 'application/json',
        },
        body: formData,
    }).then(response=>response.json())
        .then(data=>{
            alert(data.message)
            window.location.href = "http://localhost:8080/mainpage";
        })

}


function checkAllSelectsTeacher(isSubject){
    let allSelects = document.querySelectorAll("select");
    let allSelectsLabel = document.querySelectorAll(".mandatory");
    let k;

    for (let i = 7; i < allSelects.length-1; i++) {
        if (allSelects[i].value === allSelects[i].children[0].value){
            alert("Заповніть колонку " + allSelectsLabel[i].innerText.toLowerCase())
            return false;
        }
    }
    if (isSubject){
        if (allSelects[11].value === ''){
            alert("Заповніть колонку предмети, які викладаються у школі")
            return false;
        }
    }
    return true;

}


async function getAllSchools(){
    let schools;
    return schools = fetch("http://localhost:8080/schools").then(res => res.json())
        .then((json) => {
            return json
        });
}

function fillUniqueRegion(){
    const uniqueRegion = [...new Set(institutions__teacher.map((item) => item.oblast))]
    for (let i = 0; i < uniqueRegion.length; i++) {
        document.querySelector(".select-element__teacher").innerHTML += `<option>${uniqueRegion[i]}</option>`
    }
    document.querySelector(".select-element__teacher").removeAttribute("disabled");
}
