let editPencil = document.getElementById("edit-profile");

editPencil.addEventListener("click", ()=>{
    document.getElementById("profileModal").style.display = "block";

    document.body.style.overflow = "hidden";

    document.getElementById("username").parentElement.removeAttribute("data-error");
    // document.getElementById("tel").parentElement.removeAttribute("data-error");
    document.getElementById("email").parentElement.removeAttribute("data-error");
    document.getElementById("position").parentElement.removeAttribute("data-error");

    document.getElementById("username").style.border = "none";
    // document.getElementById("tel").style.border = "none";
    document.getElementById("email").style.border = "none";
    document.getElementById("position").style.border = "none";


    document.getElementById("username").value = document.getElementById("teacher-name").innerText;
    document.getElementById("tel").value = document.getElementById("work-phone-profile").innerText;
    document.getElementById("email").value = document.getElementById("email-profile").innerText;
    document.getElementById("position").value = document.getElementById("position-profile").innerText;
})

document.querySelector(".save-profile").addEventListener("click",()=>{
    if (checkData()){
        const formProfile = document.getElementById("form-profile");
        const formData = new FormData(formProfile);
        const updateProfile = {username: `${formData.get("username")}`, phoneNumber: `${formData.get("workPhone")}`,
            email: `${formData.get("email")}`,position:`${formData.get("position")}`}
        saveUpdateProfile(updateProfile);

    }
})


async function saveUpdateProfile(updateProfile){
    let res = fetch("http://localhost:8080/updateprofile", {
        method:'POST',
        headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
        body : JSON.stringify(updateProfile)
    });

    if ((await res).ok){
        document.getElementById("form-profile").submit();
    }

}



function checkData(){
    if (document.getElementById("username").value === ""){
        document.getElementById("username").parentElement.setAttribute("data-error", "Поле не може бути порожнім");
        document.getElementById("username").style.border = "1px solid red"
        return false;

    }
    else if (document.getElementById("email").value === ""){
        document.getElementById("email").parentElement.setAttribute("data-error", "Поле не може бути порожнім");
        document.getElementById("email").style.border = "1px solid red"
        return false;
    }
    else if (document.getElementById("position").value === ""){
        document.getElementById("position").parentElement.setAttribute("data-error", "Поле не може бути порожнім");
        document.getElementById("position").style.border = "1px solid red"
        return false;
    }
    return true;
}

window.onclick = function(event) {
    if (event.target.className === "modal") {
        event.target.style.display = "none";
        document.body.style.overflowY = "auto";
    }

}

document.getElementById("username").addEventListener("input", ()=>{
    document.getElementById("username").parentElement.removeAttribute("data-error");
    document.getElementById("username").style.border = "none"
})

document.getElementById("email").addEventListener("input", ()=>{
    document.getElementById("email").parentElement.removeAttribute("data-error");
    document.getElementById("email").style.border = "none"
})
document.getElementById("position").addEventListener("input", ()=>{
    document.getElementById("position").parentElement.removeAttribute("data-error");
    document.getElementById("position").style.border = "none"
})