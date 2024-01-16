<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="../static/css/icons.css">
  <link rel="stylesheet" href="../static/css/style(v6).css">
  <link rel="stylesheet" type="text/css" href="../static/css/solid.css">
  <link rel="stylesheet" href="../static/css/delete-form.css">
  <link rel="stylesheet" href="../static/css/add_column_form.css">
  <link rel="stylesheet" href="../static/css/календарне%20планування.css">
  <link rel="stylesheet" href="../static/css/journal(v.7).css">
  <link rel="stylesheet" href="../static/css/addSchoolForm(v1).css">
  <link rel="stylesheet" href="../static/css/schoolProfile(v1).css">
  <link rel="stylesheet" href="../static/css/applications(v2).css">
  <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
  <link rel="stylesheet" href="../static/css/applications(v2).css">
  <link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

  <title>Smart school</title>
  <style>

  </style>
</head>
<body style="opacity: 1;">
<div class="wrap">


  <nav class="sidebar">
    <ul class="menu-list">
      <li><a class="logo" <#if hasAccess>href="/admin" <#else> href="/mainpage"</#if>>
          <img class="logo-image" src="../static/images/Smart%20School.png" alt="">
          <span class="menu-item">Smart school</span>

        </a>
        <i id="btn" class="fa-solid fa-bars"></i>
      </li>
      <li><a class="menu-ref" <#if hasAccess>href="/admin" <#else> href="/mainpage"</#if>>
          <i class="fa fa-home" aria-hidden="true"></i>
          <span class="menu-item">Головна</span>
        </a>
      </li>
      <li><a class="menu-ref" href="/school">
          <i class="fa fa-school"></i>
          <span class="menu-item">Школа</span>
        </a></li>
      <li><a class="menu-ref" href="/teacher-schedule">
          <i class="fa fa-clipboard-list"></i>
          <span class="menu-item">Розклад вчителя</span>
        </a></li>
      <li><a class="menu-ref" href="/classes">
          <i class="fa-solid fa-people-line"></i>
          <span class="menu-item">Класи</span>
        </a></li>
      <#if hasAccess>
        <li><a class="menu-ref" href="/admin-cabinet">
            <i class="fa fa-book"></i>
            <span class="menu-item">Кабінет адміністратора</span>
          </a></li>
      </#if>
      <li><a class="menu-ref" href="/users">
          <i class="fa fa-users"></i>
          <span class="menu-item">Учасники</span>
        </a></li>
      <#if hasAccess>
        <li><a class="menu-ref" href="/applications">
            <i style="position: relative;width: 70px;height: 40px;top: 14px;font-size: 20px;
						text-align: center;" class="fa-solid fa-envelope"></i>
            <span class="menu-item">Заявки</span>
          </a></li>
      </#if>
      <form action="/logout" method="post">
        <button style="display:flex; align-items: center" class="log-out" type="submit">
          <i style="margin-bottom: 7px; margin-right: 15px;" class="fa fa-arrow-right-from-bracket"></i>Вийти
        </button>
      </form>
    </ul>
  </nav>
  <div id="content">
    <div class="header">
      <h3 class="journal">Профіль школи</h3>
      <div class="messages-container">
        <div class="messages" style="margin-right: 90px;">
          <div class="circle"></div>
          <i style="margin-left: 0" class="fa-solid fa-bell"></i>
        </div>
      </div>
    </div>
    <hr>
    <div class="profile" style="padding-bottom: 0">

      <div id="profile-container">
        <div class="profile_row">
          <div class="profile-left">
            <div class ="circle-img"></div>
          </div>
          <div class="profile-right">

            <h1 class="school-name">${schoolName}</h1>

            <table >
              <tr>
                <td class="profile-right-info" ><span class="profile-header" >Тип школи:</span></td>
                <td class="profile-right-info"><span class="type-of-school" style="display: table-cell;">${typeOfSchool}</span></td>
              </tr>
              <tr>
                <td class="profile-right-info" ><span class="profile-header" >Веб-сайт:</span></td>
                <td class="profile-right-info"><a class="web-cite" href="${webCite}" style="display: table-cell;">${webCite}</a></td>
              </tr>
            </table>


          </div>
          <#if hasAccess>
          <a href="/settings"><i id="set" class="fa-solid fa-gear"></i></a>
          </#if>
        </div>
      </div>
    </div>


    <ul class="cards">
      <li class="cards__item">
        <div class="card">
          <div class="card__image card__image--fence"></div>
          <div class="card__content">
            <div class="card__title">
                <h3 style="margin-right: 20px;">Розклад дзвінків</h3>
                <i class="fa-solid fa-bell" style="font-size: 40px;
    margin-left: 5px;
    color: slateblue;"></i>
            </div>
            <a href="/schedule" type="button" class="view">Переглянути</a>
          </div>
        </div>
      </li>
      <li class="cards__item">
        <div class="card">
          <div class="card__image card__image--fence"></div>
          <div class="card__content">
            <div class="card__title">
                <h3 style="margin-right: 20px;">Оголошення</h3>
                <i class="fa-solid fa-comment-medical"></i>
              </div>
            <a href="#" type="button" class="view">Переглянути</a>
          </div>
        </div>
      </li>
      <li class="cards__item">
        <div class="card">
          <div class="card__image card__image--fence"></div>
          <div class="card__content">
            <div class="card__title">
                <h3 style="margin-right: 20px;">Шкільний календар</h3>
                <i class="fa-solid fa-calendar-days"></i>

            </div>
            <a href="/school-calendar" type="button" class="view">Переглянути</a>
          </div>
        </div>
      </li>
      <li class="cards__item">
        <div class="card">
          <div class="card__image card__image--fence"></div>
          <div class="card__content">
            <div class="card__title">
              <h3 style="margin-right: 20px;">Адреса</h3>
            </div>
            <table style="padding: 30px">
              <tr>
                <td class="profile-right-info" ><span class="profile-header" >Область:</span></td>
                <td class="profile-right-info"><span class="type-of-school" style="display: table-cell;">${region}</span></td>
              </tr>
              <tr>
                <td class="profile-right-info" ><span class="profile-header" >Місто:</span></td>
                <td class="profile-right-info"><span class="type-of-school" style="display: table-cell;">${city}</span></td>
              </tr>
              <tr>
                <td class="profile-right-info"  style="display: flex"><span class="profile-header" >Вулиця:</span></td>
                <td class="profile-right-info"><span class="type-of-school" style="display: table-cell;">${street}</span></td>
              </tr>
            </table>
          </div>
        </div>
      </li>
      <li class="cards__item">
        <div class="card">
          <div class="card__image card__image--fence"></div>
          <div style="min-height: 213px" class="card__content">
            <div class="card__title">
              <h3 style="margin-right: 20px;">Контакти</h3>
            </div>
            <table style="padding: 30px">
              <tr>
                <td class="profile-right-info" ><span class="profile-header" >Електронна пошта:</span></td>
                <td class="profile-right-info"><span class="type-of-school" style="display: table-cell;">${email}</span></td>
              </tr>
              <tr>
                <td class="profile-right-info" ><span class="profile-header" >Телефон:</span></td>
                <td class="profile-right-info"><span class="type-of-school" style="display: table-cell;">${phone}</span></td>
              </tr>
            </table>
          </div>
        </div>
      </li>
      <li class="cards__item">
        <div class="card">
          <div class="card__image card__image--fence"></div>
          <div  class="card__content">
            <div class="card__title" style="align-items: center">
              <h3 style="margin-right: 20px;">Адміністрація школи</h3>
            </div>
            <table style="padding: 30px">
              <#list administration as administrator>
                <tr>
                  <td class="profile-right-info" ><span class="position" >${administrator.position}</span></td>
                  <td class="profile-right-info"><span class="username" style="display: table-cell;">${administrator.name}</span></td>

                  <#if roles?contains('ROLE_ADMIN')>
                    <td class="profile-right-info">
                      <div class="edit" style="display: flex; justify-content: end">
                        <i class="fa fa-pen fa-xs edit-user" profileId = "${administrator.id}" style="font-size: 20px; cursor: pointer"></i>
                      </div>
                    </td>
                  </#if>
                </tr>
              </#list>
            </table>
          </div>
        </div>
      </li>
    </ul>
  </div>
</div>

<div class="note-form-wrap">
  <div class="note-title">
    Повідомлення
  </div>
  <div class="note-items">
  </div>
</div>


<div id="addAdministration" class="modal" style="z-index: 999; overflow: auto">
  <div class="modal-content" style="background: #fff;
    padding: 0 40px 40px;
    border-radius: 8px; margin: 25px auto; width:565px">
    <h1><span class="add-column-text" style="margin-right: 10px; font-size: 25px; letter-spacing: 2px; text-align: center; padding-top: 25px; padding-bottom: 25px">Адміністрація</span></h1>
    <div class="content-container">
      <div class="class-information">
        <form action="#" id="form-connect-school" onsubmit="return false">
          <div class="data">
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Назва посади</label>
                <input type="text" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="position" >
              </div>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Прізвище, ім'я, по-батькові</label>
                <input type="text" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="username" >
              </div>
            </div>
          <div class=" save-subject" style="padding-top: 30px;">
            <button type="button" class="save-school update-admin">Зберегти</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<div class="note-form-wrap">
  <div class="note-title">
    Повідомлення
  </div>
  <div class="note-items">

  </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.3.min.js"></script>
<script>
  $(document).ready(function() {
    $("body").css("opacity", "1");
  });
</script>
<script src="../static/js/menu-burger.js"></script>
<script src="../static/js/SchoolProfile.js"></script>
<script src="../static/js/editAdministrationOfSchool.js"></script>
<script>
  window.onclick = function (event) {
    if (event.target.className === "modal") {
      event.target.style.display = "none";
      document.body.style.overflow = "auto";
    }


  };

</script>
<script src="../static/js/messagesForm(v6).js"></script>


</body>



</html>