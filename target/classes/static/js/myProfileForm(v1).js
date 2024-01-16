document.querySelector(".nextBtn").addEventListener("click", ()=>{

    if (document.getElementById("role").value === 'Оберіть статус'){
        alert("Оберіть ваш статус");
    }
    else if (document.getElementById("role").value === 'Працівник закладу освіти' &&
            document.getElementById("position").value === ''){
        alert("Вкажіть назву вашої посади!!!")
    }

    else {
        let user = {userName: document.getElementById("username").value,
            email: "",
            role: document.getElementById("role").value,
            dateOfBirth: document.getElementById("dateOfBirth").value,
            workPhone:document.getElementById("tel").value,
            sex: document.getElementById("sex").value,
            position: document.getElementById("position").value,
            strict: document.getElementById("strict").value,
            city:document.getElementById("city").value,
            numberOfHouse: document.getElementById("numberOfHouse").value};
        saveNewProfile(user);
    }
})

async function saveNewProfile(user){
    let res = await fetch("http://localhost:8080/addNewUserProfile",{
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', Accept: 'application/json'
        },
        body:JSON.stringify(user)
    })
    let result= await res.json();

    if (res.ok) {
        document.cookie = `userId=${result.id}`;
        window.location.href = "http://localhost:8080/mainpage";
    } else {
        window.location.href = "http://localhost:8080/myprofile"
    }


}