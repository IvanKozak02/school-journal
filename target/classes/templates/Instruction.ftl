<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="../static/css/style(v6).css">
  <link rel="stylesheet" href="../static/css/icons.css">
  <link rel="stylesheet" href="../static/css/календарне планування(v1).css">
  <link rel="stylesheet" href="../static/css/air-datepicker.css">
  <link rel="stylesheet" href="../static/css/journal(v.7).css">
  <link rel="stylesheet" href="../static/css/add_column_form.css">
  <link rel="stylesheet" href="../static/css/delete-form.css">
  <link rel="stylesheet" href="../static/css/applications(v2).css">
  <link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

  <title>Smart school</title>
  <style>
    .instructions-container{
      padding-left: 40px;
    }
    .instruction-row{
      display: flex;
      flex-wrap: wrap;
    }
    .instruction {
      display: flex;
      flex-direction: column;
      margin-top: 20px;
      margin-right: 32px;
      padding: 4px 16px;
      min-width: 290px;
      max-width: 305px;
      width: 100%;
      height: 200px;
      background: #f0e9ff;
      border-radius: 25px;
      box-shadow: 1px 1px 10px #cac6ff;
    }

      .date-header{
        text-align: right;
        margin-bottom: 15px;
        font-size: 12px;
        color: #989898;
      }
      .instruction-name{
        font-weight: bold;
        font-size: 18px;
        word-wrap: break-word;
    }
      .date{
        color: #8400ff;
      }
      .add-instruction{
        display: flex;
        justify-content: flex-end;
      }
      .add-instruction-btn{
        padding: 10px 20px;
        border: none;
        border-radius: 8px;
        background: slateblue;
        color: #fff;
        font-size: 16px;
        cursor: pointer;
      }
  </style>

</head>
<body>
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
  <div id="content">
    <div class="header">
      <h1 style="padding-left: 40px; color: rgb(85,83,83);">Бесіди, інструктажі, заходи з БЖ</h1>
    <div class="messages-container">
      <div class="messages" style="margin-right: 90px;position:absolute;">
        <div class="circle"></div>
        <i class="fa-solid fa-bell"></i>
      </div>
    </div>
  </div>
    <h2 style="padding-left: 40px; color: rgb(85,83,83);; padding-top: 15px;">Клас: <span>${class.nameOfClass}</span></h2>

    <div class="instructions">

      <div class="instructions-container">
        <#if isHeadTeacher>
          <div class="add-instruction">
            <button class="add-instruction-btn">Додати інструктаж</button>
          </div>
        </#if>
        <div class="instruction-row" <#if instructionEmpty>style="flex-direction: column" </#if> classId = ${class.id}>
          <#if instructionEmpty == false>
            <#list instructions as instruction>
              <div <#if isHeadTeacher>oncontextmenu="openDeleteForm(${instruction.id})"</#if> class="instruction">
                <span class="date-header">Дата проведення: <span class="date">${instruction.instructionDate}</span></span>
                <span class="instruction-name">${instruction.instructionSubject}</span>
              </div>
            </#list>
          </#if>
          <#if instructionEmpty>
          <img width="900" height="400" style="margin: auto" src="https://ezavuch.expertus.com.ua/storage/summernote/644f7deb9f79f4345.png" alt="">
          <h2 style="text-align: center">Інструктажів не знайдено</h2>
          </#if>
        </div>
      </div>
    </div>
  <div id="modalTwo" class="modal" style="z-index: 999;">
    <div class="modal-content">
      <div class="login-root">
        <div class="formbg" style="max-height: 610px;">
          <div class="box-root padding-top--48 padding-bottom--24 flex-flex flex-justifyContent--center" style="display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;">
            <h1><span class="add-column-text" style="padding-top: 24px">Інструктаж</span></h1>
            <h2 class="lesson-date-header" style="margin-top: 0;"><span class="add-column-text"></span></h2>
          </div>
          <div class="formbg-inner padding-horizontal--48" id="scroll" style="max-height: 85%;
    position: relative; padding: 0 40px 60px 40px;">

            <div class="add-form">
              <form action="#" method="post" id="sub-plan-form">

                <div class="date-column">
                  <h2 style="text-align: center;" id="date-info"></h2>
                </div>
                <div class="field padding-bottom--24 input-subject" data-error = "Поле не може бути порожнім" >
                  <label class="column-name" style="padding-top: 0" >Тема інструктажу</label>
                  <textarea name="subject" class="subject-area subject" cols="30" rows="5"></textarea>
                </div>
                <div class="homework-done field"  date-error = "Оберіть дату">
                  <label class="column-name">Дата проведення</label>
                  <input type="text" value=""  data-language='en' readonly="readonly" id="airdatepicker" class="homework-done-date instruction-date">
                  <i class="fa-solid fa-calendar-days "></i>
                </div>
                <div class=" save-subject" style="padding-top: 60px;">
                  <button type="button" class="save-subject-plan save-instruction">Зберегти</button>
                </div>
              </form>
            </div>

          </div>
        </div>
      </div>
    </div>
  </div>
    <div id="deleteForm" class="modal" style="z-index: 999;">
      <div class="modal-content">
        <div class="login-root">
          <div class="formbg" style="max-height: 610px;">
            <form action="" id="delete-form">
              <div class="container" style="border-radius: 0">
                <h1>Видалити запис</h1>
                <p style="font-size:16px;" class="are-you-sure">Ви впевнені, що хочете видалити запис</p>
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
<script src="../static/js/menu-burger.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script>
  $(document).ready(function() {
    $("body").css("opacity", "1");
  });
</script>
<script
        src="https://code.jquery.com/jquery-3.6.3.js"
        integrity="sha256-nQLuAZGRRcILA+6dMBOvcRh5Pe310sBpanc6+QBmyVM="
        crossorigin="anonymous"></script>
<script src="../static/js/air-datepicker.js"></script>
<script>
  const localeEs = {
    days: ['Понеділок', 'Вівторок', 'Середа', 'Четвер', 'П`ятниця', 'Субота', 'Неділя'],
    daysShort: ['ПН', 'ВТ', 'СР', 'ЧТ', 'ПТ', 'СБ', 'НД'],
    daysMin: ['ПН', 'ВТ', 'СР', 'ЧТ', 'ПТ', 'СБ', 'НД'],
    months: ['Січень', 'Лютий', 'Березень', 'Квітень', 'Травень', 'Червень', 'Липень', 'Серпень', 'Вересень', 'Жовтень', 'Листопад', 'Грудень'],
    monthsShort: ['Січ', 'Лют', 'Бер', 'Квіт', 'Трав', 'Черв', 'Лип', 'Серп', 'Вер', 'Жовт', 'Лист', 'Груд'],
    today: 'Сьогодні',
    clear: 'Очистити',

    timeFormat: 'hh:ii',
    firstDay: 0
  }
  new AirDatepicker('#airdatepicker',{
    position:'top center',
    locale: localeEs
  })

</script>
<script src="../static/js/Instruction.js"></script>
<script src="../static/js/messagesForm(v6).js"></script>
</html>