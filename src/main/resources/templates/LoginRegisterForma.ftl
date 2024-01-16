<#import "/spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
  <link rel="stylesheet" href="../static/css/LoginRegisterForm(v7).css">
  <link rel="stylesheet" href="../static/css/icons.css">
  <link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

  <title>Smart school</title>
</head>
<body>

<div class="background-image">
  <img id="back-image" src="../static/images/My%20project%20(1).png" alt="">
</div>
<div class="logo">
    <img id="logo" src="../static/images/Smart%20School.png" alt="Smart School" style="background: inherit">
    <span class="menu-item">Smart school</span>

</div>
<div class="container" id="container">

  <div class="form-container sign-up-container">
    <form action="#" id="form-registration" onsubmit="return false">
      <h1>Реєстрація</h1>
      <label for="username" id="username">
        <input type="text" required name="username" class="username" placeholder="ПІБ" />
      </label>
      <label for="email" id="email">
        <input type="email" required name="email" class="email"  placeholder="Поштова скринька" />
      </label>
      <label for="password" id="password">
        <input type="password" required minlength="8" class="check-password" name="password" placeholder="Пароль" />
      </label>
      <label for="confirmPassword" id="confirmPassword">
        <input type="password" required name="confirmPassword" id="confirm" class="password"  placeholder="Підтвердити пароль" />
      </label>
      <button style="margin-top: 20px" class="signup-btn" onclick="registration()">Зареєструватися</button>
    </form>
  </div>
  <div class="form-container sign-in-container">
    <form action="#" id="form-login" onsubmit="return false">
      <h1>Увійти в систему</h1>
      <input required type="text" name="email" id="login-username" placeholder="Поштова скринька" />
      <input required type="password" name="password" id="login-password" placeholder="Пароль" />
      <a href="#">Забули пароль?</a>
      <button id="submit-btn" onclick="login()">Увійти</button>
    </form>
  </div>
  <div class="overlay-container">
    <div class="overlay">
      <div class="overlay-panel overlay-left">
        <h1>Ви вже зареєстровані?</h1>
        <p>Введіть поштову скриньку і пароль та увійдіть в систему</p>
        <button class="ghost" onclick="clickBtnSignin()" id="signIn">УВІЙТИ</button>
      </div>
      <div class="overlay-panel overlay-right">
        <h1>Ви новий користувач?</h1>
        <p>Зареєструйтесь та користуйтесь системою</p>
        <button class="ghost" onclick="clickBtnSignup()" id="signUp">Зареєструватися</button>
      </div>
    </div>
  </div>
</div>
<div class="message">

</div>
</body>
<script src="js/LoginRegisterForm.js"></script>

<script>
  const signUpButton = document.getElementById('signUp');
  const signInButton = document.getElementById('signIn');
  const container = document.getElementById('container');

  function clickBtnSignup(){
    container.classList.add("right-panel-active");
  }

  function clickBtnSignin(){
    container.classList.remove("right-panel-active");
  }
</script>

</html>