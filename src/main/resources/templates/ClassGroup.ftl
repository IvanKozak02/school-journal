<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="../static/css/journals.css">
  <link rel="stylesheet" href="../static/css/style(v6).css">
  <link rel="stylesheet" href="../static/css/icons.css">
  <link rel="stylesheet" href="../static/css/teacher-schedule.css">
  <link rel="stylesheet" href="../static/css/add_column_form.css">
  <link rel="stylesheet" href="../static/css/schedule-filters.css">
  <link rel="stylesheet" href="../static/css/ClassGroup(v1).css">
  <link rel="stylesheet" href="../static/css/delete-form.css">
  <link rel="stylesheet" href="../static/css/addSchoolForm.css">
  <link rel="stylesheet" href="../static/css/applications(v2).css">
  <link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

  <title>Smart school</title>

  <style>
    .schedule-item:hover{
      opacity: 0;
    }
    .schedule-item{
      cursor: default;
    }
.group-btn{
      background: #fff;
      border-radius: 50px;
      display: flex;
      justify-content: center;
      padding: 7px 30px;
      color: grey;
      margin-right: 15px;
      font-size: 15px;
      cursor: pointer;
    }

    .fa-solid.fa-circle-xmark{
      position: absolute;
      width: 9px;
      height: 9px;
      right: -9px;
      top: -4px;
      background: rgba(156,182,201,.5);
      border-radius: 50%;
      transition: .4s;
      cursor: pointer;
      margin-right: 0;
      font-size: 12px;
    }
    .group-btn{
      position: relative;
    }
    .group-btn.show{
      color: #fff;
      background-color: slateblue;
    }

  </style>
</head>
<body >
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
  <div id="content" style="padding-block: 1rem" <#if approve>role="${canCreateGroup}"</#if><#if existSubjectsWithoutGroup>subjects = '${subjects}'</#if>>
    <div class="header">
      <h1 style="padding-left: 40px; color: rgb(85,83,83);" class="class-group" id="${classId}">Групи класу</h1>
      <div class="messages-container">
        <div class="messages" style="margin-right: 90px; position:absolute;">
          <div class="circle"></div>
          <i class="fa-solid fa-bell"></i>
        </div>
      </div>
    </div>
    <div class="group-btn-container" style="display: flex;margin-left: 40px;margin-top: 50px;">
      <#if hasGroup>
          <#list subjectBtn as subject>
            <div style="display: flex" class="btn-groups">
              <button style="border: none; margin-left: 10px" class="group-btn <#if subject?counter==1> show</#if>" onclick="getGroup(${subject?counter})" >${subject.name}
              </button>
              <i style="margin-left: -10px; position: static" onclick="deleteGroup(${subject?counter})" class="fa-solid fa-circle-xmark delete-icon"></i>
            </div>
          </#list>
        </#if>

    </div>
    <div class="add-group" >
      <div class="class-filter" id="subject-close-filter" style="margin-left: 40px; margin-top:50px">
        <h4>Предмет</h4>
        <div id="type" class="input-field">
          <select <#if approve==false>disabled</#if> id="subjects" name="subject" required>
            <option disabled selected>Оберіть предмет</option>
          </select>
        </div>
      </div>
      <button type="button" class="add-group-btn">Додати групу</button>
    </div>


        <#if hasGroup==false>
        <div class="schedule-table-wrapper" style="display: flex; justify-content: center; width: 100%; border-bottom-left-radius: 0; border-top-left-radius: 0;">
          <div class="table-container">
          <div class="img-container">
            <img width="500px" height="300px" src="https://creazilla-store.fra1.digitaloceanspaces.com/cliparts/63211/school-clipart-md.png" alt="">
            <h1 class="class-user-name" style="padding-top: 30px; color: #bbbbbb; text-align: center">Груп не створено</h1>
          </div>
          </div>
        </div>
        <#else>
          <div class="schedule-table-wrapper" style="display: block; width: 27.9%;  max-height: calc(90vh - 106px);">
            <div class="table-container">
              <table id="data" style="display: block;">
                <thead>
                <tr class="head-table">
                  <th class="lessons" style="min-width: 265px">
                    Учні
                  </th>
                </tr>
                </thead>
                <tbody>
                </tbody>
              </table>
            </div>
          </div>
        </#if>

    <div class="student-add-remove-form"  >
      <div class="add-group-checkbox">
        <span>Вибрано: <span class="count-of-change"></span></span>
        <span>Оберіть групу: </span>

      </div>
      <div class="add-remove-btn">
        <button class="add-student-to-group">Залучити до групи</button>
        <button class="remove-student-from-group">Видалити з групи</button>
      </div>


    </div>
  </div>

</div>

<div id="delete-modal" class="modal" style="z-index: 999;">
  <div class="modal-content">
    <div class="login-root">
      <div class="formbg" style="max-height: 610px;padding: 20px;">
        <form action="" id="delete-form">
          <div class="container" style="border-radius: 0; box-shadow: none;">
            <h1>Видалити групу</h1>
            <p style="font-size:16px;" class="are-you-sure">Ви впевнені, що хочете видалити групу№<span class="delete-class"></span>?</p>
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
<script src="../static/js/menu-burger.js"></script>

<script>
  $(document).ready(function() {
    $("body").css("opacity", "1");
  });
</script>
<script src="../static/js/messagesForm(v6).js"></script>

<script src="../static/js/form-schedule(v4).js"></script>
<script src="../static/js/ClassGroup(v3).js"></script>
</html>