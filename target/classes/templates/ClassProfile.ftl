<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="../static/css/style(v6).css">
  <link rel="stylesheet" href="../static/css/icons.css">
  <link rel="stylesheet" href="../static/css/classProfile.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fomantic-ui/2.8.8/semantic.min.css">
  <link rel="stylesheet" href="https://cdn.datatables.net/1.13.2/css/dataTables.semanticui.min.css">
  <link rel="stylesheet" href="../static/css/календарне планування(v1).css">
  <link rel="stylesheet" href="../static/css/journal(v.7).css">
  <link rel="stylesheet" href="../static/css/delete-form.css">
  <link rel="stylesheet" href="../static/css/add_column_form.css">
  <link rel="stylesheet" href="../static/css/schedule-filters.css">
  <link rel="stylesheet" href="../static/css/journals.css">
  <link rel="stylesheet" href="../static/css/addSchoolForm.css">
  <link rel="stylesheet" href="../static/css/applications(v2).css">
  <link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

  <title>Smart school</title>

  <style>
    .img-container{
      display: flex;
      justify-content: center;
      padding-top: 60px;
      flex-direction: column;
      align-items: center;
    }
    .save-user[disabled]{
      transform: none;
    }
    a:hover{
      color: #f1f1f1;
    }
  </style>
</head>
<body style="background: #dfe9f5">
<div class="wrap">
  <nav class="sidebar">
    <ul class="menu-list">
      <li><a class="logo" <#if isAdmin>href="/admin" <#else> href="/mainpage"</#if>>
          <img class="logo-image" src="../static/images/Smart%20School.png" alt="">
          <span class="menu-item">Smart school</span>

        </a>
        <i id="btn" class="fa-solid fa-bars"></i>
      </li>
      <li><a class="menu-ref" <#if isAdmin>href="/admin" <#else> href="/mainpage"</#if>>
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
      <#if isAdmin>
        <li><a class="menu-ref" href="/admin-cabinet">
            <i class="fa fa-book"></i>
            <span class="menu-item">Кабінет адміністратора</span>
          </a></li>
      </#if>
      <li><a class="menu-ref" href="/users">
          <i class="fa fa-users"></i>
          <span class="menu-item">Учасники</span>
        </a></li>
      <#if isAdmin>
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
      <h1 style="color: rgb(85,83,83); padding-left: 16px">Профайл класу</h1>
      <div class="messages-container">
        <div class="messages" style="margin-right: 20px; position: relative">
          <div class="circle"></div>
          <i class="fa-solid fa-bell"></i>
        </div>
      </div>

    </div>

    <div class="main">
      <div class="card">
        <div class="card-body">
          <h2 style="font-size: 28px" class="class-number" id="${classId}">${className} клас</h2>
          <img src="../static/images/pngwing.com%20(2).png" alt="">
          <i id="edit-class-profile" class="fa fa-pen fa-xs edit"></i>
          <table style="font-weight: bold; padding-top: 20px ">
            <tbody>
            <tr>
              <td class="tab-header">Класний керівник</td>
              <td class="teacher-name" <#if role == "YES">role="${"YES"}"</#if> id="${teacherProfileId}">${teacherName}</td>
            </tr>
            <tr>
              <td class="tab-header">Кількість учнів у класі</td>

              <td>${kstOfStudents}</td>
            </tr>
            <tr>
              <td class="tab-header">Номер кабінету</td>
              <td class="class-cabinet">${cabinet}</td>
            </tr>
            <tr>
              <td class="tab-header">Поштова скринька керівника</td>

              <td class="teacher-email">${email}</td>
            </tr>
            </tbody>
          </table>
        </div>

          <div class="class-card-body">
            <h2 style="font-size: 28px; padding-bottom: 40px;">Матеріали класу</h2>
            <div class="class-info">

              <a href="/attendance-record/${journalId}" class="class-schedule">
                <span class="schedule-text">Журнал класу</span>
                <i class="fa-solid fa-book"></i>
              </a>
              <div class="class-schedule" onclick="getClassGroup(${classId})">
                <span class="schedule-text" >Групи класу</span>
                <i class="fa-solid fa-users-line" ></i>
              </div>
                <a href="/instructions/${classId}" class="class-schedule">
                  <span class="schedule-text">Інструктажі</span>
                  <i style="font-size: 1.5rem" class="fa-solid fa-person-chalkboard"></i>
                </a>
                <a href="/motion-table/${classId}" class="class-schedule">
                  <span class="schedule-text">Рух учнів</span>
                  <i style="font-size: 1.5rem" class="fa-solid fa-users-between-lines"></i>
                </a>
      </div>
    </div>

        </div>
      </div>


    <div class="students-container" style="padding-left: 10px">
      <div class="students">
        <div class="students-teacher-parents-menu">
          <button style="border: none;" class="student-btn show">Учні</button>
          <button style="border: none;" class="teacher-btn">Вчителі</button>
        </div>
        <div class="add-user">
          <button type="button" class="add-user-btn">Додати користувача
            <i class="fa-solid fa-user-plus"></i>
          </button>
        </div>
      </div>

      <div class="table-wrapper" style="margin-left: 0; height: calc(121vh - 280px); max-height: none">

      </div>
    </div>

  </div>
</div>
</div>

<div id="updateClassProfile" class="modal" style="z-index: 999;">
  <div class="modal-content" style="background: #fff;
    padding: 0 40px 40px;
    border-radius: 8px;">
    <h1><span class="add-column-text" style=" letter-spacing: 2px; text-align: center; padding-top: 25px; padding-bottom: 25px">Редагування класу</span></h1>
    <div class="content-container">
      <div class="class-information">
        <div id="class-teacher-name" class="input-field" style="padding-top: 15px;">
          <label class="mandatory">Класний керівник</label>
          <select class="class-teacher" style="width: 96%" name="sex" required>
            <option disabled selected>Оберіть класного керівника</option>
          </select>
        </div>
      <div class="field padding-bottom--24 " style="padding-top: 20px">
        <h4>Кабінет</h4>
        <input type="text" style="width: 96%; border-radius: 8px" name="class-cabinet" class="subject-area" id="cabinet">
      </div>
      <div class=" save-subject update-class" style="padding-top: 60px;">
        <button type="button" class="save-class">Зберегти</button>
      </div>
    </div>
  </div>
</div>
</div>

<div id="modalNine" class="modal" style="z-index: 999;">
  <div class="modal-content" style="background: #fff;
    padding: 0 40px 40px;
    border-radius: 8px;">
    <h1><span class="add-column-text" style=" letter-spacing: 2px; text-align: center; padding-top: 25px; padding-bottom: 25px">Додати користувача</span></h1>
    <div class="content-container">
      <div class="class-information">
        <div class="class-filter" id="class-close-filter-user" style="margin-right: 0; padding-top: 20px;">
          <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
            <h4>ID користувача</h4>
            <input oninput="filterByLetter()" type="text" style="border-radius: 8px" name="class-cabinet" class="subject-area" id="user-id" >
          </div>
          <div class="select-menu-user" id="class-menu-user" style="width: 100%; padding-top: 0;">
            <div class="select-btn" id="class-user" style="font-size: 16px">
            <ul class="options" style="background:rgb(247 246 255); z-index: 9; margin-top: 0">
              <li class="class-option-user not-found" style="display: none;">
                <span class="option-text">Студентів не знайдено</span>
              </li>
            </ul>
            </div>
          </div>
        <div class=" save-subject" style="padding-top: 60px;">
          <button type="button" class="save-user">Зберегти</button>
        </div>
      </div>
    </div>
  </div>
</div>



</div>
<div id="delete-student-from-class" class="modal" style="z-index: 999;">
  <div class="modal-content">
    <div class="login-root" style="box-shadow: none">
        <form action="" id="delete-form">
          <div class="container">
            <h1>Видалити учня</h1>
            <p style="font-size:16px;" class="are-you-sure">Ви впевнені, що хочете вилучити учня <span class="delete-student"> </span> з класу?</p>
            <input type="hidden" class="deleteId">
            <div class="clearfix">
              <button type="button" style="margin-right: 15px;"  class="cancelbtn">Скасувати</button>
              <button type="button"  class="deletebtn">Видалити</button>
            </div>
          </div>
        </form>
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


</body>
<script
        src="https://code.jquery.com/jquery-3.6.3.js"
        integrity="sha256-nQLuAZGRRcILA+6dMBOvcRh5Pe310sBpanc6+QBmyVM="
        crossorigin="anonymous"></script>


<script src="https://cdn.datatables.net/1.13.2/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.2/js/dataTables.semanticui.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fomantic-ui/2.8.8/semantic.min.js"></script>
<script src="../static/js/pagination-table.js"></script>

<script src="//cdn.datatables.net/plug-ins/1.13.3/i18n/uk.json"></script>


<script>

  $(document).ready(function() {
    $("body").css("opacity", "1");
  });
</script>
<script src="../static/js/ClassProfile(v9).js"></script>
<script src="../static/js/menu-burger.js"></script>
<script src="../static/js/messagesForm(v6).js"></script>
</html>