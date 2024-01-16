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
  <link rel="stylesheet" href="../static/css/календарне планування(v1).css">
  <link rel="stylesheet" href="../static/css/OblikStatystyka.css">
  <link rel="stylesheet" href="../static/css/applications(v2).css">
  <link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

  <title>Smart school</title>

  <style>
    .inserted{
      height: 80px;

    }
    .table-wrapper{
      width: -webkit-fit-content;
      overflow-x: hidden;
    }
    .date-inserted{
      height: 55px;
      min-width: 175px;
    }
    .student{
      min-width: 230px;
    }
  </style>
</head>
<body style="background: rgb(223, 233, 245);">
<div class="wrap" >
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
  <main id="swup" class="transition-fade">

  </main>
  <div id="content" >
    <div class="header">
      <h1 style="padding-left: 40px; color: rgb(85,83,83);">Статистика відвідування</h1>
      <div class="messages-container">
        <div class="messages" style="margin-right: 90px;">
          <div class="circle"></div>
          <i class="fa-solid fa-bell"></i>
        </div>
      </div>
    </div>
    <h2 style="padding-left: 40px; color: rgb(85,83,83);; padding-top: 15px;">Клас: <span>${class}</span></h2>
    <div class="journal-filters">
      <div class="journal-marks-and-lessons-plan">
        <a class="mark-link journal-btn" href="/attendance-record/${journalId}">Облік відвідувань</a>
        <a class="mark-link subject-plan" id="active"  href="/visit-statistics/?journalId=${journalId}&time=1">Статистика відвідування</a>
      </div>


      <div class="journal-teacher" style="margin-right: 20px;">
        <span>Вчитель:</span>
        <span style="color:slateblue; font-weight:bold">${teacherName}</span>
      </div>

    </div>

    <div class="semesters" journal=${journalId}>
      <label for="first-semester" class="sem-label">
        <input checked type="radio" class="semester" onchange="findAllN(1)" id="first-semester" name="sem">
        I - ий семестр
      </label>
      <label for="secondSemester" class="sem-label">
        <input type="radio" class="semester" onchange="findAllN(2)" id="secondSemester" name="sem">
        II - ий семестр
      </label>
      <label for="year" class="sem-label">
        <input type="radio" class="semester" onchange="findAllN(3)" id="year" name="sem">
        Рік
      </label>
    </div>



    <div class="table-wrapper" style="display: block;max-height: calc(92vh - 280px);">
      <div class="table-container">
        <table id="data" style="display: block;">
          <thead>
          <tr class="head-table">
            <th style="color: slateblue; font-style: bold; font-weight: 800; " class="student">
              Учні
            </th>
            <th class="date-inserted">
              Пропущено уроків без поважної причини
            </th>
            <th class="date-inserted">
              Пропущено уроків через хворобу
            </th>
          </tr>
          </thead>
          <tbody>

          <#list studentList as student>
            <tr class="student-row">
              <th class="student">${student?split("-")[3]}</th>
              <td class="inserted">${student?split("-")[1]}</td>
              <td class="inserted">${student?split("-")[2]}</td>
            </tr>
          </#list>

          </tbody>

        </table>


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

<script src="../static/js/menu-burger.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="../static/js/VisitStatistics(v1).js"></script>
<script>
  let links = document.getElementsByClassName('mark-link');

  links[0].addEventListener("click", function(){
    links[0].setAttribute('id','active');
    links[1].removeAttribute('id',"active");
  });

  links[1].addEventListener("click", function(){
    links[1].setAttribute('id','active');
    links[0].removeAttribute('id', "active");
  })

</script>
<script>
  $(document).ready(function() {
    $("body").css("opacity", "1");
  });
</script>

<script src="../static/js/messagesForm(v6).js"></script>


</body>
</html>