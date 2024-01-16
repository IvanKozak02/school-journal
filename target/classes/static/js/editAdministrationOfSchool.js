let edit = document.querySelectorAll(".edit");

for (let i = 0; i < edit.length; i++) {
    edit[i].addEventListener("click", () => {
        document.getElementById("position").value = document.querySelectorAll(".position")[i].innerText;
        document.getElementById("username").value = document.querySelectorAll(".username")[i].innerText;
        document.querySelector(".update-admin").setAttribute("user",i);
        document.getElementById("addAdministration").style.display = "block";
    });
}


function checkInputs(){
    if (document.getElementById("position").value === ''){
        alert("Заповніть поле назва посади!!!");
        return false;
    }
    else if (document.getElementById("username").value === ''){
        alert("Заповніть поле прізвище, ім'я, по-батькові!!!");
        return false;
    }
    return true;
}


document.querySelector(".update-admin").addEventListener("click", ()=>{
    if (checkInputs()){
        let editUser = document.querySelector(".update-admin").getAttribute("user");
        let id = parseInt(editUser);
        let profileId = document.querySelectorAll(".edit-user")[id].getAttribute("profileId");
        const formData = new FormData();
        let updateData = {position: document.getElementById("position").value, name:document.getElementById("username").value,
        id:profileId};
        formData.append("jsonData", JSON.stringify(updateData));
        // formData.append("id",profileId);
        updateAdministration(formData, id, updateData);
    }

})

async function updateAdministration(formData, id, updateData){

        let res = fetch("http://localhost:8080/updateadmin", {
            method:'POST',
            headers: {
                Accept: 'application/json'
            },
            body : formData
        });

        if ((await res).ok){
            document.querySelectorAll(".position")[id].innerText = updateData.position;
            document.querySelectorAll(".username")[id].innerText = updateData.name;
            document.getElementById("addAdministration").style.display = "none";
        }

}