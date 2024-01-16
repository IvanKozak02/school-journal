<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="../static/css/MyProfileForm.css">
  <link rel="stylesheet" href="../static/css/style(v6).css">
  <link rel="stylesheet" href="../static/css/icons.css">
  <link rel="stylesheet" href="https://unicons.iconscout.com/release/v4.0.0/css/line.css">
  <link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

  <title>Smart school</title>
</head>
<body>
<nav class="sidebar">
  <ul class="menu-list">
    <li><a  class="logo" href="#">
      <img class="logo-image" src="../static/images/Smart%20School.png" alt="">
      <span class="menu-item">Smart school</span>

    </a>
      <i id="btn" class="fa-solid fa-bars"></i>
    </li>
    <li><a class="menu-ref" href="/myprofile">
      <i class="fa fa-home" aria-hidden="true"></i>
      <span class="menu-item">Мій профіль</span>
    </a>
    </li>
    <form action="/logout" method="post">
      <button style="display:flex; align-items: center" class="log-out" type="submit">
        <i style="margin-bottom: 7px; margin-right: 15px;" class="fa fa-arrow-right-from-bracket"></i>Вийти
      </button>
    </form>
  </ul>
</nav>
<div class="container">
  <header>Мій профіль</header>

  <form action="#" onsubmit="return false">
    <div class="form first">
      <div class="details personal">
        <span class="title">Особисті дані</span>

        <div class="fields">
          <div class="input-field">
            <label class="mandatory">Прізвище, ім'я, по-батькові</label>
            <input name="userName" id="username" type="text" placeholder="ПІБ" required>
          </div>


          <div class="input-field">
            <label class="mandatory">Ви реєструєтесь як</label>
            <select id="role" name="role" required>
              <option disabled selected>Оберіть статус</option>
              <option>Працівник закладу освіти</option>
              <option>Учень</option>
            </select>
          </div>
          <div class="input-field">
            <label class="mandatory">Дата народження</label>
            <input name="dateOfBirth" id="dateOfBirth" type="date" placeholder="Введіть дату народження" required>
          </div>

          <div class="input-field">
            <label class="mandatory">Робочий номер телефону</label>
            <input type="tel"  name="workPhone" minlength="10" class="form-control" id="tel"  placeholder="+38 (___) ___-__-__" required>
          </div>

          <div class="input-field">
            <label class="mandatory">Стать</label>
            <select id="sex" name="sex" required>
              <option disabled selected>Оберіть стать</option>
              <option>Чоловік</option>
              <option>Жінка</option>
              <option>Інше</option>
            </select>
          </div>

          <div class="input-field">
            <label>Посада</label>
            <input id="position" name="position" type="text" placeholder="Введіть " required>
          </div>
        </div>
      </div>

      <div class="address-details">
        <span class="title">Інформація про місце проживання</span>

        <div class="fields">
          <div class="input-field">
            <label>Область</label>
            <input name="strict" id="strict" type="text" >
          </div>

          <div class="input-field">
            <label>Місто</label>
            <input name="city" id="city" type="text">
          </div>

          <div class="input-field">
            <label>Вулиця</label>
            <input name="street" type="text">
          </div>

          <div class="input-field">
            <label>Номер квартири / будинку</label>
            <input name="numberOfHouse" id="numberOfHouse" type="text">
          </div>
        </div>
        <div class="fill-profile-btn">
          <button class="nextBtn" type="submit">
            <span class="btnText">Зберегти</span>
          </button>
        </div>

      </div>
    </div>
  </form>
</div>

<!--<script src="script.js"></script>-->
</body>
<script src="../static/js/menu-burger.js"></script>
<script
        src="https://code.jquery.com/jquery-3.6.3.js"
        integrity="sha256-nQLuAZGRRcILA+6dMBOvcRh5Pe310sBpanc6+QBmyVM="
        crossorigin="anonymous"></script>
<script>
  $(document).ready(function() {
    $("body").css("opacity", "1");
  });
</script>

<script src="../static/js/myProfileForm(v1).js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js"></script>
<script>
  $("#tel").mask("+38 (999) 999-99-99");
</script>
</html>
