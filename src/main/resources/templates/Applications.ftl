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
  <link rel="stylesheet" href="../static/css/delete-form.css">
  <link rel="stylesheet" href="../static/css/applications(v2).css">
  <link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

  <title>Smart school</title>

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
      <h1 style="padding-left: 40px; color: rgb(85,83,83);;">Заявки</h1>
      <div class="messages-container">
        <div class="messages" style="margin-right: 90px;">
          <div class="circle"></div>
          <i class="fa-solid fa-bell"></i>
        </div>
      </div>
    </div>

    <div class="table-wrapper" style="max-height: calc(100vh - 130px);">

      <table id="example"  class="ui celled table" style="width:98%; ">
        <thead>
        <tr>
          <th>Тип заявки</th>
          <th>Дата створення</th>
          <th>Статус</th>
          <th>Відправник</th>
          <th>Змінити</th>
        </tr>
        </thead>
        <tbody>
        <#if isApplication == true>
          <#list applications as application>
            <tr>
              <td class="type-of-application">${application.typeOfApplication}</td>
              <td class="date-of-creation-app">${application.dateOfCreate}</td>
              <td class="status">
                <div <#if application.status == "Схвалено">
                  style="background-color: #9fec00;"
                </#if>
                        <#if application.status == "Відхилено">
                          style="background-color: #ec3500;"
                        </#if>
                        class="name-of-status">
                  ${application.status}
                </div>
              </td>
              <td class="sender">${application.userPersonalData.userName}</td>
              <td><div class="update-or-delete">
                  <div class="update">
                    <i class="fa-solid fa-eye"></i>
                  </div>
                  <div class="delete">
                    <i class="fa-solid fa-trash-arrow-up"></i>
                  </div>
                </div>
              </td>
            </tr>
          </#list>
        </#if>
        </tbody>
        <tfoot>
        <tr>
          <th>Тип заявки</th>
          <th>Дата створення</th>
          <th>Статус</th>
          <th>Відправник</th>
          <th>Змінити</th>
        </tr>
        </tfoot>
      </table>
    </div>

    <div id="application" class="modal" style="z-index: 999;">
      <div class="modal-content">
        <div class="login-root">
          <div class="formbg" style="max-height: 610px; padding: 30px; overflow-y: auto">
            <h1><span class="add-column-text" style="padding-top: 24px; font-size: 26px; padding-bottom: 20px; text-align: center;">Заявка</span></h1>
            <div class="application-body">
              <table class="app-table">
                <tr>
                  <td><span class="profile-header" >Від кого:</span></td>
                  <td><span class="user-name" style="display: table-cell;"></span></td>
                </tr>
                <tr>
                  <td><span class="profile-header">Email:</span></td>
                  <td><span class="user-email" style="display: table-cell;"></span></td>
                </tr>
                <tr>
                  <td><span class="profile-header">Дата створення:</span></td>
                  <td><span class="date-of-creation" style="display: table-cell;"></span></td>
                </tr>
                <tr>
                  <td colspan="2" class="request-message message-user" style="padding-top: 15px">
                    Прошу приєднати мене
                    <span class="name"></span>
                    до <span class="school-name"></span>, що у <span class="region"></span>,<span class="position"></span>
                    <div class="sub">

                    </div>

                  </td>
                </tr>
                <tr class="material-block ng-star-inserted" style="">
                  <td class="profile-header" style="padding-top: 20px; text-align: center">Вкладення:</td>
                  <td style="padding-top: 20px;">
                    <div class="attachments">
                      <div class="attachment-item">
                        <a class="document" href=""></a>
                      </div>
                    </div>
                  </td>
                </tr>
              </table>
            </div>
            <div class="buttons" style="display: flex; justify-content: space-evenly">
              <div class=" save-subject " style="padding-top: 60px;">
                <button type="button" class="save-subject-plan confirm-app">Прийняти заявку</button>
              </div>
              <div class=" save-subject" style="padding-top: 60px;">
                <button style="background: #e56363;box-shadow: rgb(84 105 212) 0 0;" type="button" class="save-subject-plan cancel-app">Відхилити заявку</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div id="deleteModal" class="modal" style="z-index: 999;">
      <div class="modal-content">
        <div class="login-root">
          <div class="formbg" style="max-height: 610px;     padding: 20px;">
              <div class="container">
                <h1>Видалити запис</h1>
                <p style="font-size:16px;" class="are-you-sure">Ви впевнені, що хочете видалити заявку</p>
<#--                <input type="hidden" class="deleteId">-->
                <div class="clearfix">
                  <button type="button" style="margin-right: 15px;"  class="cancelbtn">Скасувати</button>
                  <button type="button"  class="deletebtn">Видалити</button>
                </div>
              </div>
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

<script
        src="https://code.jquery.com/jquery-3.6.3.js"
        integrity="sha256-nQLuAZGRRcILA+6dMBOvcRh5Pe310sBpanc6+QBmyVM="
        crossorigin="anonymous"></script>
<script>
  $(document).ready(function() {
    $("body").css("opacity", "1");
  });
</script>

<script src="../static/js/menu-burger.js"></script>
<script src="../static/js/messagesForm(v6).js"></script>
<script src="../static/js/pagination-table.js"></script>
<script src="../static/js/confirmOrCancelApplicationForm(v1).js"></script>

<script>
  $(document).ready(function () {
    $('#example').DataTable({
      fixedHeader: true,
      ordering: false,
      pageLength:100,
      language: {
        url: "http://cdn.datatables.net/plug-ins/1.10.9/i18n/Ukranian.json"
      }
    });
  })
</script>

<script src="https://cdn.datatables.net/1.13.2/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.2/js/dataTables.semanticui.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fomantic-ui/2.8.8/semantic.min.js"></script>


<script>
  window.onclick = function (event) {
      if (event.target.className === "modal") {
        event.target.style.display = "none";
        document.body.style.overflow = "auto";
      }


  };

</script>

<script src="../static/js/delete-form(v1).js"></script>


</body>
</html>