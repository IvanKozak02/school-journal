<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" type="text/css" href="../static/css/style(v6).css">
  <link rel="stylesheet" href="../static/css/icons.css">
  <link rel="stylesheet" type="text/css" href="../static/css/solid.css">
  <link rel="stylesheet" href="../static/css/journal(v.7).css">
  <link rel="stylesheet" href="../static/css/add_column_form.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fomantic-ui/2.8.8/semantic.min.css">
  <link rel="stylesheet" href="https://cdn.datatables.net/1.13.2/css/dataTables.semanticui.min.css">
  <link rel="stylesheet" href="../static/css/календарне%20планування(v1).css">
  <link rel="stylesheet" href="../static/css/addSchoolForm.css">
  <link rel="stylesheet" href="../static/css/OblikVidviduvannja(v1).css">
  <link rel="stylesheet" href="../static/css/applications(v2).css">
  <link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />



  <title>Smart school</title>
  <style>
    .option .option-text{
      font-size: 16px
    }

  </style>
</head>
<body style="opacity:0;
	transition:.5s;
	background: #dfe9f5;">
<div class="transition-fade" id="swup">
  <nav class="sidebar">
    <ul class="menu-list">
      <li><a  class="logo" href="/admin">
          <img class="logo-image" src="../static/images/Smart%20School.png" alt="">
          <span class="menu-item">Smart school</span>

        </a>
        <i id="btn" class="fa-solid fa-bars"></i>
      </li>
      <li><a class="menu-ref" href="/admin">
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
      <li><a class="menu-ref" href="/admin-cabinet">
          <i class="fa fa-book"></i>
          <span class="menu-item">Кабінет адміністратора</span>
        </a></li>
      <li><a class="menu-ref" href="/users">
          <i class="fa fa-users"></i>
          <span class="menu-item">Учасники</span>
        </a></li>
      <li><a class="menu-ref" href="/applications">
          <i style="position: relative;width: 70px;height: 40px;top: 14px;font-size: 20px;
						text-align: center;" class="fa-solid fa-envelope"></i>
          <span class="menu-item">Заявки</span>
        </a></li>
      <form action="/logout" method="post">
        <button style="display:flex; align-items: center" class="log-out" type="submit">
          <i style="margin-bottom: 7px; margin-right: 15px;" class="fa fa-arrow-right-from-bracket"></i>Вийти
        </button>
      </form>
    </ul>
  </nav>

  <div id="content" >
    <div class="header">
      <h1 style="padding-left: 40px; color: rgb(85,83,83);;">Облік відвідування</h1>
      <div class="messages-container">
        <div class="messages" style="margin-right: 20px; position: relative">
          <div class="circle"></div>
          <i class="fa-solid fa-bell"></i>
        </div>
      </div>

    </div>
    <div class="sem-and-subject" style="padding-top: 20px">
      <div class="input-field class-name" style="padding-top: 15px; padding-left: 40px; width: 15%">
        <label> Клас
          <select onchange="getJournal()" class="class-teacher" style="width: 96%" name="sex" required>
            <option selected disabled>Оберіть клас</option>
            <#list classes as class>
              <option value="${class}">${class?split(".")[0]}</option>
            </#list>
          </select>
        </label>
      </div>

  </div>
    <div class="img-container" style="align-items: center; flex-direction: column; display:flex;">
      <img width="500px" height="300px" src="https://creazilla-store.fra1.digitaloceanspaces.com/cliparts/63211/school-clipart-md.png" alt="">
      <h1 class="class-user-name" style="padding-top: 30px;">Для відображення Обліку відвідування, оберіть Клас</h1>
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
<script
        src="https://code.jquery.com/jquery-3.6.3.js"
        integrity="sha256-nQLuAZGRRcILA+6dMBOvcRh5Pe310sBpanc6+QBmyVM="
        crossorigin="anonymous"></script>
<script src="../static/js/menu-burger.js"></script>


<script src="../static/js/AdminCabinet(v.2).js"></script>
<script src="https://cdn.datatables.net/1.13.2/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.2/js/dataTables.semanticui.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fomantic-ui/2.8.8/semantic.min.js"></script>
<script>
  $(document).ready(function() {
    $("body").css("opacity", "1");
  });
</script>
<script src="../static/js/messagesForm(v6).js"></script>
\
</body>
</html>