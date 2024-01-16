let institutions;


document.querySelector(".register-school").addEventListener("click", ()=>{
    document.body.style.overflow = "hidden";
    document.querySelector(".save-subject").style.display = "none";
    document.querySelector(".input-cont").style.display = "none";
    document.querySelector(".next-prev-btn").style.display = "block";
    document.querySelector(".previous").style.display = "none";
    document.querySelector(".data").style.display = "flex";
    document.querySelector(".next").style.display = "block";

    clearValueOfInput();
    document.getElementById("addSchoolModal").style.display = "block";
    document.getElementById("addSchoolModal").style.overflowY = "auto";
})



document.querySelector(".next").addEventListener("click", ()=>{
    document.querySelector(".data").style.display = "none";
    document.querySelector(".input-cont").style.display = "flex";
    document.querySelector(".next").style.display = "none";
    document.querySelector(".previous").style.marginRight = "0";
    document.querySelector(".save-subject").style.display = "block";
    document.querySelector(".previous").style.display = "block";
})


document.querySelector(".previous").addEventListener("click", ()=>{
    document.querySelector(".data").style.display = "flex";
    document.querySelector(".input-cont").style.display = "none";
    document.querySelector(".next").style.display = "block";
    document.querySelector(".previous").style.marginRight = "15px";
    document.querySelector(".save-subject").style.display = "none";
    // document.querySelector(".previous").style.display = "block";
    document.querySelector(".previous").style.display = "none";
})


function clearValueOfInput(){

    inputFile.value = '';
    document.querySelector(".input-cont").value = '';
    document.querySelector(".select-element").value = document.querySelector(".select-element").children[0].value;
    document.getElementById("typeOfCity").value = document.getElementById("typeOfCity").children[0].value;
    document.getElementById("region").value = document.getElementById("region").children[0].value;
    document.getElementById("city").innerHTML = '';
    document.getElementById("city").innerHTML+=`<option disabled selected>Оберіть населений пункт</option>`
    document.getElementById("city").value = document.getElementById("city").children[0].value;
    document.getElementById("viddil").innerHTML = '';
    document.getElementById("viddil").innerHTML +=`<option disabled selected>Оберіть відділ</option>`;
    document.getElementById("viddil").value = document.getElementById("viddil").children[0].value;
    document.getElementById("schools").innerHTML = '';
    document.getElementById("schools").innerHTML +=`<option disabled selected>Оберіть школу</option>`;
    document.getElementById("schools").value = document.getElementById("schools").children[0].value
    document.getElementById("typeOfCity").setAttribute("disabled", "disabled");
    document.getElementById("city").setAttribute("disabled", "disabled");
    document.getElementById("region").setAttribute("disabled", "disabled");
    document.getElementById("schools").setAttribute("disabled", "disabled");
    document.getElementById("viddil").setAttribute("disabled", "disabled");
}

const selectElement = document.querySelector(".select-element");

selectElement.addEventListener("change", async (event) => {

    document.getElementById("typeOfCity").setAttribute("disabled", "disabled")
    document.getElementById("typeOfCity").value = document.getElementById("typeOfCity").children[0].value;
    document.getElementById("region").value = document.getElementById("region").children[0].value;
    document.getElementById("reg").style.display = "none";
    document.getElementById("city").innerHTML = '';
    document.getElementById("city").innerHTML+=`<option disabled selected>Оберіть населений пункт</option>`
    document.getElementById("viddil").innerHTML = '';
    document.getElementById("schools").innerHTML = '';
    document.getElementById("viddil").innerHTML +=`<option disabled selected>Оберіть відділ</option>`
    document.getElementById("schools").innerHTML +=`<option disabled selected>Оберіть школу</option>`
    const typeOfCity = `${event.target.value}`;
    const result = `${event.target.value}`;
    let regionCode;
    console.log(result);
    const options = document.querySelectorAll('[region]');
    for (const a of options) {
        if (a.innerText.includes(result)) {
            regionCode = a.getAttribute("region");
            break;
        }
    }

    institutions = await getSchools(regionCode).then(res => {
        return res;
    });

    document.getElementById("typeOfCity").removeAttribute("disabled")

});




 async function getSchools(regionCode){
     let schools;
     return schools = fetch("https://registry.edbo.gov.ua/api/institutions/?ut=3&lc=" + `${regionCode}` + "&exp=json", {}).then(res => res.json())
         .then((json) => {
             return json
         });

}




async function typeOfCitySelect(event){
    document.getElementById("region").value = document.getElementById("region").children[0].value;
    document.getElementById("reg").style.display = "none";
    document.getElementById("city").innerHTML = '';
    document.getElementById("city").innerHTML+=`<option disabled selected>Оберіть населений пункт</option>`
    const typeOfCity = `${event.target.value}`;
    if (typeOfCity === "Село" || typeOfCity === "Селище міського типу"){
        document.getElementById("reg").style.display = "block";
        const region = getRegion(institutions);
        fillRegionSelect(region, "region");
        document.getElementById("region").removeAttribute("disabled");
    }
    else {
        const city = getCity(institutions);
        fillCitySelect(city, "city");
        document.getElementById("city").removeAttribute("disabled");

    }
}

document.getElementById("region").addEventListener("change", (event)=>{
    if (document.getElementById("typeOfCity").value === "Село"){
        const  land = getLand(`${event.target.value}`,institutions);
        fillCitySelect(land,"city");
        document.getElementById("city").removeAttribute("disabled");
        document.getElementById("viddil").innerHTML = '';
        document.getElementById("schools").innerHTML = '';
        document.getElementById("viddil").innerHTML +=`<option disabled selected>Оберіть відділ</option>`
        document.getElementById("schools").innerHTML +=`<option disabled selected>Оберіть школу</option>`
    }
    else{
        const  smt = getSMT(`${event.target.value}`, institutions);
        fillCitySelect(smt,"city");

        document.getElementById("city").removeAttribute("disabled");

    }

})

document.getElementById("city").addEventListener("change", (event)=>{
    document.getElementById("viddil").value = document.getElementById("viddil").children[0].value;
    document.getElementById("schools").value = document.getElementById("viddil").children[0].value;
    document.getElementById("viddil").innerHTML = '';
    document.getElementById("schools").innerHTML = '';
    document.getElementById("viddil").innerHTML +=`<option disabled selected>Оберіть відділ</option>`
    document.getElementById("schools").innerHTML +=`<option disabled selected>Оберіть школу</option>`

    const nas_punkt = `${event.target.value}`;
    const viddils = getViddil(nas_punkt, institutions);
    fillViddils(viddils, "viddil");
    document.getElementById("viddil").removeAttribute("disabled");
})



document.getElementById("viddil").addEventListener("change", (event)=>{
    const viddil = `${event.target.value}`;
    const nas_punkt = document.getElementById("city").value;
    const schools = getSchool(viddil, nas_punkt, institutions);
    fillSchools(schools, "schools");
    document.getElementById("schools").removeAttribute("disabled");

})

function fillSchools(schools, schools_id){
    let schoolSelect = document.getElementById(schools_id);
    removeElements(schoolSelect);
    schoolSelect.innerHTML += `<option disabled selected>Оберіть школу</option`;
    for (let i = 0; i < schools.length; i++) {
        schoolSelect.innerHTML += `<option>${schools[i]}</option>`;
    }
}

function getSchool(viddil, nas_punkt, institution){
    return [...new Set(institution.filter(item => item.governance_name === viddil && item.koatuu_name.includes(nas_punkt)).map(item => item.short_name))];
}

function getViddil(nas_punkt, institution){
   if (nas_punkt==="Львів"){
        return [...new Set(institution.filter(item => item.koatuu_name.includes("Львів, "))
            .map(item => item.governance_name))];
    }
    else {
        return [...new Set(institution.filter(item => item.koatuu_name.includes(`${nas_punkt}`))
            .map(item => item.governance_name))];
    }
}

function getLand(region, institution){
    return [...new Set(institution.filter(item => item.koatuu_name.includes("с.") &&
        item.koatuu_name.includes(`${region}`)).map(item => item.koatuu_name.split(",")[0]))];
}
function getSMT(region, institution){
    return [...new Set(institution.filter(item => item.koatuu_name.includes("смт") &&
        item.koatuu_name.includes(`${region}`)).map(item => item.koatuu_name.split(",")[0]))];
}
function getCity(institution){
    return [...new Set(institution.filter(item => !item.koatuu_name.includes("с.") && !item.koatuu_name.includes("смт")).map(item =>
        item.koatuu_name.split(",")[0]))];
}

function fillRegionSelect(region, reg_id){
    let regionSelect = document.getElementById(reg_id);
    removeElements(regionSelect);
    regionSelect.innerHTML += `<option disabled selected>Оберіть район</option`;
    for (let i = 0; i < region.length; i++) {
        regionSelect.innerHTML += `<option>${region[i]}</option>`;
    }
}
function fillViddils(viddils, viddil_id){
    let viddilSelect = document.getElementById(viddil_id);
    removeElements(viddilSelect);
    viddilSelect.innerHTML += `<option disabled selected>Оберіть відділ освіти</option`;
    for (let i = 0; i < viddils.length; i++) {
        viddilSelect.innerHTML += `<option>${viddils[i]}</option>`;
    }
}

function fillCitySelect(city, city_class){
    let citySelect = document.getElementById(city_class);
    removeElements(citySelect);
    citySelect.innerHTML += `<option disabled selected>Оберіть населений пункт</option`;
    for (let i = 0; i < city.length; i++) {
        citySelect.innerHTML += `<option>${city[i]}</option>`;
    }
}

function removeElements(select){
    select.innerHTML = '';

}
function getRegion(institution){
    return [...new Set(institution.filter(item => item.koatuu_name.includes("район")).map(item =>
        item.koatuu_name.split(",")[1]))];

}


document.querySelector(".save-school").addEventListener("click", ()=>{
    let allSubjects = document.querySelectorAll(".item-container");
    let allDataIsCorrect = checkAllSelects()

    if (allDataIsCorrect){
        let schoolInfo = getSchoolInfo(document.getElementById("schools").value);
        let subjects = [];

        for (let i = 0; i < allSubjects.length; i++) {
            subjects.push(allSubjects[i].innerText);
        }

        let schoolObject = {city: schoolInfo.koatuu_name, contactPhone: schoolInfo.phone,
            email: schoolInfo.email, name: schoolInfo.short_name, oblast: schoolInfo.region_name,
            street: schoolInfo.address, subjects: {subjects}, typeOfSchool: schoolInfo.university_financing_type_name,
            webCite:schoolInfo.website, viddil:schoolInfo.governance_name};


        saveSchool(schoolObject);
    }
})

function getSchoolInfo(schools){
    let school = [...new Set(institutions.filter(item => item.short_name.includes(schools)).map(item => item))];
    const setIter = school.values();
    return setIter.next().value;
}



function checkAllSelects(){
    let allSelects = document.querySelectorAll("select");
    let allSelectsLabel = document.querySelectorAll(".mandatory");
    let k;
    if (document.querySelector('[name="region"]').value === 'Оберіть район'){
        k = 5;
    }
    else {
        k=6;
    }
    for (let i = 0; i <= k; i++) {
        if (allSelects[i].value === allSelects[i].children[0].value){
            if (allSelects[i].value === "Оберіть район" && allSelects[i-1].value === "Місто"){
                continue;
            }
            alert("Заповніть колонку " + allSelectsLabel[i].innerText.toLowerCase())
            return false;
        }
        // if (allSelects[i].value === ''){
        //     alert("Заповніть колонку " + allSelectsLabel[i].innerText.toLowerCase())
        //     return false;
        // }
    }
    if (allSelects[6].value === ''){
        alert("Заповніть колонку предмети, які викладаються у школі")
        return false;
    }

    return true;

}


async function saveSchool(formData){
    let res = await fetch("http://localhost:8080/school", {
        method: 'POST',
        headers: {
            Accept: 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
    }).then(response=>response.json())
        .then(data=>{
            alert(data.message)
            window.location.href = "http://localhost:8080/mainpage";
        })

}

window.onclick = function (event) {
    if (event.target.className === "modal") {
        event.target.style.display = "none";
        document.body.style.overflow = "auto";
    }
};