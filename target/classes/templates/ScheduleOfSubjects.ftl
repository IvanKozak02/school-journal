<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
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
  <link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

  <title>Smart school</title>

</head>
<body>
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
      <h3 class="journal">Розклад дзвінків</h3>
      <div class="messages-container">
        <div class="messages" style="margin-right: 90px;">
          <div class="circle"></div>
          <i style="margin-left: 0;" class="fa-solid fa-bell"></i>
        </div>
      </div>
    </div>
    <hr>
    <ul class="cards">
      <li class="cards__item" style="width: 35%;">
        <div class="card">
          <div class="card__image card__image--fence"></div>
          <div class="card__content">
            <div class="card__title">
              <h3 style="margin-right: 20px;">Розклад дзвінків</h3>
              <#if hasAccess>
              <div class="edit" style="display: flex; justify-content: end; align-items: baseline">
                <i class="fa fa-pen fa-xs edit-subject" style="font-size: 20px; cursor: pointer; top: 0; color: slateblue"></i>
                <i class="fa-solid fa-plus add-subject" style="font-size: 24px;cursor: pointer;color: slateblue;"></i>
              </div>
              </#if>

            </div>
            <table style="padding: 30px">
              <#if hasSchedule>
              <tr>
                <td class="profile-right-info" ><span class="numOfSubject" >${numberOfSubjects?split(",")[0]}</span></td>
                <td class="profile-right-info" style="display: flex"><span class="dateOfStart" style="display: table-cell;">${timeOfStart?split(',')[0]}</span> - <span class="dateOfFinish" style="display: table-cell;">${timeOfFinish?split(',')[0]}</span> </td>

              </tr>
                <tr>
                  <td class="profile-right-info" ><span class="numOfSubject" >${numberOfSubjects?split(",")[1]}</span></td>
                  <td class="profile-right-info" style="display: flex"><span class="dateOfStart" style="display: table-cell;">${timeOfStart?split(',')[1]}</span> - <span class="dateOfFinish" style="display: table-cell;">${timeOfFinish?split(',')[1]}</span> </td>

                </tr>
                <tr>
                  <td class="profile-right-info" ><span class="numOfSubject" >${numberOfSubjects?split(",")[2]}</span></td>
                  <td class="profile-right-info" style="display: flex"><span class="dateOfStart" style="display: table-cell;">${timeOfStart?split(',')[2]}</span> - <span class="dateOfFinish" style="display: table-cell;">${timeOfFinish?split(',')[2]}</span> </td>

                </tr>
                <tr>
                  <td class="profile-right-info" ><span class="numOfSubject" >${numberOfSubjects?split(",")[3]}</span></td>
                  <td class="profile-right-info" style="display: flex"><span class="dateOfStart" style="display: table-cell;">${timeOfStart?split(',')[3]}</span> - <span class="dateOfFinish" style="display: table-cell;">${timeOfFinish?split(',')[3]}</span> </td>

                </tr>
                <tr>
                  <td class="profile-right-info" ><span class="numOfSubject" >${numberOfSubjects?split(",")[4]}</span></td>
                  <td class="profile-right-info" style="display: flex"><span class="dateOfStart" style="display: table-cell;">${timeOfStart?split(',')[4]}</span> - <span class="dateOfFinish" style="display: table-cell;">${timeOfFinish?split(',')[4]}</span> </td>
                </tr>
                <tr>
                  <td class="profile-right-info" ><span class="numOfSubject" >${numberOfSubjects?split(",")[5]}</span></td>
                  <td class="profile-right-info" style="display: flex"><span class="dateOfStart" style="display: table-cell;">${timeOfStart?split(',')[5]}</span> - <span class="dateOfFinish" style="display: table-cell;">${timeOfFinish?split(',')[5]}</span> </td>
                </tr>
                <tr>
                  <td class="profile-right-info" ><span class="numOfSubject" >${numberOfSubjects?split(",")[6]}</span></td>
                  <td class="profile-right-info" style="display: flex"><span class="dateOfStart" style="display: table-cell;">${timeOfStart?split(',')[6]}</span> - <span class="dateOfFinish" style="display: table-cell;">${timeOfFinish?split(',')[6]}</span> </td>
                </tr>
                <tr>
                  <td class="profile-right-info" ><span class="numOfSubject" >${numberOfSubjects?split(",")[7]}</span></td>
                  <td class="profile-right-info" style="display: flex"><span class="dateOfStart" style="display: table-cell;">${timeOfStart?split(',')[7]}</span> - <span class="dateOfFinish" style="display: table-cell;">${timeOfFinish?split(',')[7]}</span> </td>
                </tr>
              </#if>
            </table>
          </div>
        </div>
      </li>
    </ul>
  </div>
</div>


<div id="addSchoolSubjectSchedule" class="modal" style="z-index: 999; overflow: auto">
  <div class="modal-content" style="background: #fff;
    padding: 0 40px 40px;
    border-radius: 8px; margin: 25px auto; width:565px;">
    <h1><span class="add-column-text" style="margin-right: 10px; font-size: 25px; letter-spacing: 2px; text-align: center; padding-top: 25px; padding-bottom: 25px">Розклад дзвінків</span></h1>
    <div class="content-container" >
      <div class="class-information">
        <form action="#"  id="form-connect-school" onsubmit="return false">
          <div class="data" style="display: block;
    max-height: 380px;
    overflow: auto;">
            <div class="field-container" style="display: flex; justify-content: space-around; flex-direction: column;
    padding: 5px;">
              <h4 class="subject-header">1-ий урок</h4>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час початку:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="start-time" >
              </div>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час завершення:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="finish-time" >
              </div>
            </div>
            <div class="field-container" style="display: flex; justify-content: space-around; flex-direction: column;
    padding: 5px;">
              <h4 class="subject-header">2-ий урок</h4>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час початку:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="start-time" >
              </div>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час завершення:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="finish-time" >
              </div>
            </div>
            <div class="field-container" style="display: flex; justify-content: space-around; flex-direction: column;
    padding: 5px;">
              <h4 class="subject-header">3-ий урок</h4>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час початку:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="start-time" >
              </div>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час завершення:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="finish-time" >
              </div>
            </div>
            <div class="field-container" style="display: flex; justify-content: space-around; flex-direction: column;
    padding: 5px;">
              <h4 class="subject-header">4-ий урок</h4>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час початку:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="start-time" >
              </div>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час завершення:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="finish-time" >
              </div>
            </div>
            <div class="field-container" style="display: flex; justify-content: space-around; flex-direction: column;
    padding: 5px;">
              <h4 class="subject-header">5-ий урок</h4>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час початку:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="start-time" >
              </div>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час завершення:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="finish-time" >
              </div>
            </div>
            <div class="field-container" style="display: flex; justify-content: space-around; flex-direction: column;
    padding: 5px;">
              <h4 class="subject-header">6-ий урок</h4>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час початку:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="start-time" >
              </div>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час завершення:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="finish-time" >
              </div>
            </div>
            <div class="field-container" style="display: flex; justify-content: space-around; flex-direction: column;
    padding: 5px;">
              <h4 class="subject-header">7-ий урок</h4>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час початку:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="start-time" >
              </div>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час завершення:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="finish-time" >
              </div>
            </div>
            <div class="field-container" style="display: flex; justify-content: space-around; flex-direction: column;
    padding: 5px;">
              <h4 class="subject-header">8-ий урок</h4>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час початку:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="start-time" >
              </div>
              <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                <label class="mandatory">Час завершення:</label>
                <input type="time" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="finish-time" >
              </div>
            </div>
          </div>
          <div class=" save-subject" style="padding-top: 30px;">
            <button type="button" class="save-school save-schedule">Зберегти</button>
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
<script src="../static/js/scheduleOfBells.js"></script>
<script src="../static/js/addSchoolForm(v4).js"></script>
<script src="../static/js/menu-burger.js"></script>
<script src="../static/js/SchoolProfile.js"></script>
<script src="../static/js/messagesForm(v6).js"></script>

</body>
</html>