document.querySelector(".add-student-to-school").addEventListener("click", async () => {
    document.body.style.overflow = "hidden";
    document.querySelector(".save-student").style.display = "none";
    document.querySelector(".next-prev-btn-teacher").style.display = "block";
    document.querySelector(".previous-t").style.display = "none";
    document.querySelector(".data-t").style.display = "flex";
    document.querySelector(".next-t").style.display = "block";


    clearValueOfInput__teacher();
    document.querySelector(".data-t").style.display = "flex";

    document.getElementById("addStudentToSchool").style.display = "block";
    document.getElementById("addStudentToSchool").style.overflowY = "auto";

    checkImgArea();
    institutions__teacher = await getAllSchools().then(res => {
        return res;
    });

    // const uniqueRegion = [...new Set(institutions__teacher.map((item) => item.oblast))]
    //
    // for (let i = 0; i < uniqueRegion.length; i++) {
    //     document.querySelector(".select-element__teacher").innerHTML += `<option>${uniqueRegion[i]}</option>`
    // }
    // document.querySelector(".select-element__teacher").removeAttribute("disabled");
    fillUniqueRegion();


});


document.querySelector(".next-t").addEventListener("click", ()=>{
    document.querySelector(".data-t").style.display = "none";
    document.querySelector(".next-t").style.display = "none";
    document.querySelector(".previous-t").style.marginRight = "0";
    document.querySelector(".save-student").style.display = "block";
    document.querySelector(".previous-t").style.display = "block";
    document.querySelector(".parents-container").style.display = "flex";
    document.querySelector(".container").style.display = "none";
})


document.querySelector(".previous-t").addEventListener("click", ()=>{
    document.querySelector(".data-t").style.display = "flex";
    document.querySelector(".next-t").style.display = "block";
    document.querySelector(".previous-t").style.marginRight = "15px";
    document.querySelector(".save-student").style.display = "none";
    document.querySelector(".previous-t").style.display = "none";
    document.querySelector(".parents-container").style.display = "none";
    document.querySelector(".container").style.display = "block";

})



function clearValueOfInput__teacher(){

    inputFile.value = '';
    document.querySelector(".select-element__teacher").innerHTML = '';
    document.querySelector(".select-element__teacher").innerHTML += `<option  disabled selected>Оберіть область</option>`;
    document.querySelector(".select-element__teacher").value = document.querySelector(".select-element__teacher").children[0].value;;
    document.getElementById("city__teacher").innerHTML = '';
    document.getElementById("city__teacher").innerHTML+=`<option disabled selected>Оберіть населений пункт</option>`
    document.getElementById("city__teacher").value = document.getElementById("city__teacher").children[0].value;
    document.getElementById("viddil__teacher").innerHTML = '';
    document.getElementById("viddil__teacher").innerHTML +=`<option disabled selected>Оберіть відділ</option>`;
    document.getElementById("viddil__teacher").value = document.getElementById("viddil__teacher").children[0].value;
    document.getElementById("schools__teacher").innerHTML = '';
    document.getElementById("schools__teacher").innerHTML +=`<option disabled selected>Оберіть школу</option>`;
    document.getElementById("schools__teacher").value = document.getElementById("schools__teacher").children[0].value
    document.getElementById("city__teacher").setAttribute("disabled", "disabled");
    document.getElementById("schools__teacher").setAttribute("disabled", "disabled");
    document.getElementById("viddil__teacher").setAttribute("disabled", "disabled");
}




const region = document.querySelector(".select-element__teacher");


region.addEventListener("change",  () => {

    // document.getElementById("city__teacher").innerHTML = '';
    // document.getElementById("city__teacher").innerHTML+=`<option disabled selected>Оберіть населений пункт</option>`
    // document.getElementById("viddil__teacher").innerHTML = '';
    // document.getElementById("schools__teacher").innerHTML = '';
    // document.getElementById("viddil__teacher").innerHTML +=`<option disabled selected>Оберіть відділ</option>`
    // document.getElementById("schools__teacher").innerHTML +=`<option disabled selected>Оберіть школу</option>`
    checkRegion();
    fillCity();


});
const studentCityName = document.getElementById("city__teacher");

studentCityName.addEventListener("change", (event)=>{
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


const viddil = document.getElementById("viddil__teacher");

viddil.addEventListener("change", (event)=>{
    const viddil = `${event.target.value}`;
    // const nas_punkt = document.getElementById("city__teacher").value;
    // const schools = getSchool__teacher(viddil, nas_punkt, institutions__teacher);
    // fillSchools(schools, "schools__teacher");
    // document.getElementById("schools__teacher").removeAttribute("disabled");
    findSchoolsByViddils(viddil)

})

const school = document.getElementById("schools__teacher");

school.addEventListener("change", ()=>{
    document.querySelector(".next-t").removeAttribute("disabled");
})

const saveStudent = document.querySelector(".save-student");
saveStudent.addEventListener("click", ()=>{
    let allSelectIsCorrect = checkAllSelectsTeacher(false);
    let allInputCorrect = checkInputs();

    let inputMultipartFiles = document.getElementById("file");
    if (allSelectIsCorrect && allInputCorrect){
        const subjectStr = '';
        const typeOfApp = 'Приєднання до школи як учень';

        let application = {typeOfApplication: typeOfApp, schoolName: document.getElementById("schools__teacher").value,
        subjects:subjectStr, parentsName: document.querySelector(".parents-name").value,
        parentPhone: document.querySelector(".parents-phone").value};
        const formData = new FormData();
        formData.append("jsonData", JSON.stringify(application));
        formData.append("file", inputMultipartFiles.files[0])
        saveApplication(formData);
    }
})


function checkInputs(){
    if (document.querySelector(".parents-name").value === ''){
        alert("Заповніть поле батьки!!!")
        return false;
    }
    else if (document.querySelector(".parents-phone").value === ''){
        alert("Заповніть поле номер телефону батьків!!!")
        return false;
    }
    return true;

}