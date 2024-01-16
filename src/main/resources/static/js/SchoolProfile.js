let schoolProfForm = document.getElementById("addSchoolProfile");

document.querySelector(".settings").addEventListener("click", ()=>{
    schoolProfForm.style.display = "block";
    document.body.style.overflow = "hidden";


})

document.querySelector(".add-admin").addEventListener("click", (ev)=>{
    ev.preventDefault();
    document.querySelector(".data").innerHTML += `<div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Назва посади</label>
                <input type="text" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="user-id" >
              </div>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Прізвище, ім'я, по-батькові</label>
                <input type="text" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="user-id" >
              </div>`;

})


document.querySelector(".save-school").addEventListener("click", ()=>{
    let allInputs = schoolProfForm.getElementsByTagName("input");
    let formData = new FormData();

    for (let i = 0; i < allInputs.length; i++) {
            formData.append("position",allInputs[i].value)

    }
    let listOfAdmins = [];
    for (let i = 0; i < allInputs.length; i+=2) {
           listOfAdmins.push({position:`${allInputs[i].value}`, name: `${allInputs[i+1].value}`})
    }
    console.log(listOfAdmins);
    let res = fetch("http://localhost:8080/api/auth/admin", {
        method:"POST",
        headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
        body: JSON.stringify(listOfAdmins)
    })
})


window.onclick = function (event) {
    if (event.target.className === "modal") {
        event.target.style.display = "none";
        document.body.style.overflow = "auto";
    }
};
