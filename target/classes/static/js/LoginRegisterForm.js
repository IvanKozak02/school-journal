let submitBtn = document.getElementById("submit-btn")



async function login() {
    if (document.getElementById("login-username").value === "" ||
        document.getElementById("login-password").value === ""){
        return
    }
    const formLogin = document.getElementById("form-login");
    const formData = new FormData(formLogin);
    const obj = {email: `${formData.get("email")}`, password: `${formData.get("password")}`}
    let userExists;


    let ifUserExists = await fetch('http://localhost:8080/isEnabled', {
        method:'POST',
        headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
        body : JSON.stringify(obj)
    });

    userExists = ifUserExists.ok;
    let obj2;
    if (!userExists){
        let result1 = await ifUserExists.json();
        obj2= {message:result1.message};
    }

    if (userExists) {
        let responses = await fetch('http://localhost:8080/api/auth/signin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(obj)
        })
        let result = await responses.json();
        document.cookie = `token=${result.token}`;
        // sessionStorage.setItem("token",`${result.token}`);

        let hasProfileResponses = await fetch('http://localhost:8080/hasProfile', {
            method: 'POST',
            headers: {'Content-Type': 'application/json', Accept: 'application/json'},
            body: JSON.stringify(obj)
        })

        let resultHasProfile= await hasProfileResponses.json();

        if (hasProfileResponses.ok) {
            document.cookie = `userId=${resultHasProfile.id}`;
            window.location.href = "http://localhost:8080/mainpage?userId=" + `${resultHasProfile.id}`;
        } else {
            window.location.href = "http://localhost:8080/myprofile"
        }
    }
    else if (userExists && obj2.message === "Неправильно введений логін або пароль") {
        document.cookie = 'token' +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
        document.body.innerHTML += `<div id='hideMe'><i class="fa-solid fa-triangle-exclamation"></i><p>${obj2.message}</p></div>`

    }
    else {
        document.cookie = 'token' +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
        document.querySelector(".message").innerHTML += `<div id='hideMe'><i class="fa-solid fa-triangle-exclamation"></i><p>${obj2.message}</p></div>`

    }

}


document.querySelector(".password").addEventListener("input", ()=>{
    if (document.getElementById("confirmPassword").hasAttribute("password-error")){
        document.getElementById("confirmPassword").removeAttribute("password-error");
    }
    else {
        document.getElementById("confirmPassword").removeAttribute("data-error");
    }
})

document.querySelector(".username").addEventListener("input", ()=>{
    document.getElementById("username").removeAttribute("password-error");
})
document.querySelector(".email").addEventListener("input", ()=>{
    document.getElementById("email").removeAttribute("password-error");
})
document.querySelector(".check-password").addEventListener("input", ()=>{
    document.getElementById("password").removeAttribute("password-error");
})



async function registration() {
    const formRegister = document.getElementById("form-registration");
    const formRegisterData = new FormData(formRegister);
    const inputUsername = document.getElementById("username");
    const inputEmail = document.getElementById("email");
    const inputPassword = document.getElementById("password");
    const inputConfirmPassword = document.getElementById("confirm");

    if (inputUsername.value==="" ){
        inputUsername.setAttribute("data-error","Поле є обов'язковим");
        return;
    }
    else if (inputEmail.value===""){
        inputEmail.setAttribute("data-error","Поле є обов'язковим");
        return;
    }
    else if (inputPassword.value===""){
        inputPassword.setAttribute("data-error","Поле є обов'язковим");
        return;
    }
    else if (inputConfirmPassword.value===""){
        inputConfirmPassword.setAttribute("data-error","Поле є обов'язковим");
        return;
    }
    if (formRegisterData.get("password") !== formRegisterData.get("confirmPassword")){
        document.getElementById("confirmPassword").setAttribute("password-error", "Паролі не співпадають");
        return;
    }
    /*====================СЕРВЕРНА ЧАСТИНА===========================*/
    const newUser = {username: `${formRegisterData.get("username")}`, email: `${formRegisterData.get("email")}`, password: `${formRegisterData.get("password")}`}
    let responses = await fetch('http://localhost:8080/api/auth/signup', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newUser)
    })
    let result = await responses.json();
     alert(result.message);
    window.location.href = "http://localhost:8080/loginform";


}