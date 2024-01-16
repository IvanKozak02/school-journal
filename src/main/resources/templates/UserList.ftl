<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="../static/css/style(v6).css">
  <link rel="stylesheet" href="../static/css/icons.css">
  <link rel="stylesheet" href="../static/css/classProfile.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fomantic-ui/2.8.8/semantic.min.css">
  <link rel="stylesheet" href="https://cdn.datatables.net/1.13.2/css/dataTables.semanticui.min.css">
  <link rel="stylesheet" href="../static/css/календарне%20планування(v1).css">
  <link rel="stylesheet" href="../static/css/journal(v.7).css">
  <link rel="stylesheet" href="../static/css/delete-form.css">
  <link rel="stylesheet" href="../static/css/add_column_form.css">
  <link rel="stylesheet" href="../static/css/schedule-filters.css">
  <link rel="stylesheet" href="../static/css/journals.css">
  <link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/habibmhamadi/multi-select-tag/dist/css/multi-select-tag.css">
  <link rel="stylesheet" href="../static/css/addSchoolForm.css">
  <link rel="stylesheet" href="../static/css/applications(v2).css">
  <link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

  <title>Smart school</title>

  <style>
    .addition{
      text-align: center;
    }
    .fa-solid.fa-ellipsis{
      font-size: 30px;
      color: gray;
    }

    .fa-solid.fa-ellipsis:hover{
      color: slateblue;
    }




    .dropdown {
      position: relative;
      display: inline-block;
    }

    .dropdown-content {
      display: none;
      position: absolute;
      background-color: #f1f1f1;
      min-width: 215px;
      box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
      border-radius: 12px;
      width: 100%;
      left: -235px;
      z-index: 9999;

    }

    .dropdown-content button:first-child {
      color: black;
      padding: 12px 16px;
      text-decoration: none;
      display: block;
      width: 100%;
      border: none;
      border-radius: 12px;
      border-bottom: none;
      border-bottom-left-radius: 0;
      border-bottom-right-radius: 0;
      color: gray;
    /* background: inherit; */

    }

    .make-admin:active, .edit-subjects:active{
      transform: none;
    }

    .dropdown-content button:last-child{
      color: black;
      padding: 12px 16px;
      text-decoration: none;
      display: block;
      width: 100%;
      border: none;
      border-bottom: none;
      border-bottom-left-radius: 12px;
      border-bottom-right-radius: 12px;
      color: gray;
    }
    .delete{
      width: 30%;
      margin: 0 auto;
    }
    .save-teacher{
      background-color: rgb(84, 105, 212);
      box-shadow: rgb(0 0 0 / 0%) 0px 0px 0px 0px, rgb(0 0 0 / 0%) 0px 0px 0px 0px, rgb(0 0 0 / 12%) 0px 1px 1px 0px, rgb(84 105 212) 0px 0px 0px 1px, rgb(0 0 0 / 0%) 0px 0px 0px 0px, rgb(0 0 0 / 0%) 0px 0px 0px 0px, rgb(60 66 87 / 8%) 0px 2px 5px 0px;
      color: #fff;
      font-weight: 600;
      cursor: pointer;
      font-size: 16px;
      line-height: 28px;
      padding: 8px 16px;
      width: 100%;
      min-height: 44px;
      border: unset;
      border-radius: 4px;
      outline-color: rgb(84 105 212 / 0.5);
      transition: transform 80ms ease-in;
    }
    .dropdown-content button:hover {background-color: #ddd;}


    .dropdown-content.show{
      display: block;
    }

    .img-container{
      display: flex;
      justify-content: center;
      padding-top: 60px;
      flex-direction: column;
      align-items: center;
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
      <h1 style="color: rgb(85,83,83); padding-left: 16px">Список учасників</h1>
      <div class="messages-container">
        <div class="messages" style="margin-right: 90px;">
          <div class="circle"></div>
          <i class="fa-solid fa-bell"></i>
        </div>
      </div>

    </div>

    <div class="students-container" style="padding-left: 10px">
      <div class="students">
        <div class="students-teacher-parents-menu">
            <button style="border: none;" class="teacher-btn show">Вчителі</button>
            <button style="border: none;" class="student-btn">Учні</button>
            <button style="border: none;" class="admin-btn">Адміністратори</button>
        </div>

      </div>

      <div class="table-wrapper" style="margin-left: 0; margin-top: 10px; height: calc(110vh - 280px); max-height: none">

      </div>
    </div>

  </div>
</div>


<div id="delete-modal" class="modal" style="z-index: 999;">
  <div class="modal-content">
    <div class="login-root">
      <div class="formbg" style="max-height: 610px;padding: 20px;">
        <form action="" id="delete-form">
          <div class="container">
            <h1>Видалити клас</h1>
            <p style="font-size:16px;" class="are-you-sure">Ви впевнені, що хочете видалити <span class="delete-class"> </span>?</p>
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
</div>

<div id="editTeacherSubjects" class="modal" style="z-index: 999;">
  <div class="modal-content" style="background: #fff;
    padding: 0 40px 40px;
    border-radius: 8px; margin: 25px auto; width:565px">
    <h1><span class="add-column-text" style="margin-right: 10px; font-size: 25px; letter-spacing: 2px; text-align: center; padding-top: 25px;">Редагування предметів</span></h1>
    <h3 style="text-align: center"><span class="add-teacher-name" style="margin-right: 10px;  letter-spacing: 2px; text-align: center; padding-top: 25px; padding-bottom: 25px"></span></h3>
    <div class="content-container" style="display: block; padding-top: 20px;">
      <div class="class-information" style="display: block">
        <form action="#" id="form-connect-teacher" onsubmit="return false">
          <div style="display: flex; align-items: baseline; flex-direction: column" class="data-t">
          <div style="display: flex;flex-direction: column;width: 100%;" class="input-cont-teacher">
            <label class="mandatory" style="padding-bottom: 5px">Предмети, які викладаються у школі</label>
            <select class="mandatory sub" name="countries" id="subjects" multiple>
            </select>
          </div>
          <div class="save-subject" style="padding-top: 30px; margin-left: auto">
            <button type="submit"  class="save-teacher save-subjects">Зберегти</button>
          </div>
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






<script src="https://cdn.jsdelivr.net/gh/habibmhamadi/multi-select-tag/dist/js/multi-select-tag.js"></script>
<script src="../static/js/menu-burger.js"></script>
<script src="../static/js/userList(v4).js"></script>
<script src="../static/js/messagesForm(v6).js"></script>

</html>