<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Smart school</title>
  <link href="../static/css/mobiscroll.javascript.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="../static/css/style(v6).css">
    <link rel="stylesheet" href="../static/css/icons.css">
    <link rel="stylesheet" href="../static/css/educationYearStruct(v7).css">
    <link rel="stylesheet" href="../static/css/add_column_form.css">
    <link rel="stylesheet" href="../static/css/air-datepicker.css">
    <link rel="stylesheet" href="../static/css/delete-form.css">
    <link rel="stylesheet" href="../static/css/applications(v2).css">
    <link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

    <script src="../static/js/mobiscroll.javascript.min.js"></script>

</head>
<body>
<div class="wrap">
    <nav class="sidebar">
        <ul class="menu-list">
            <li><a class="logo" <#if role>href="/admin" <#else> href="/mainpage"</#if>>
                    <img class="logo-image" src="../static/images/Smart%20School.png" alt="">
                    <span class="menu-item">Smart school</span>

                </a>
                <i id="btn" class="fa-solid fa-bars"></i>
            </li>
            <li><a class="menu-ref" <#if role>href="/admin" <#else> href="/mainpage"</#if>>
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
            <#if role>
                <li><a class="menu-ref" href="/admin-cabinet">
                        <i class="fa fa-book"></i>
                        <span class="menu-item">Кабінет адміністратора</span>
                    </a></li>
            </#if>
            <li><a class="menu-ref" href="/users">
                    <i class="fa fa-users"></i>
                    <span class="menu-item">Учасники</span>
                </a></li>
            <#if role>
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
    <div id="content" <#if role>role='YES'</#if>>
        <div class="header">
            <h1 style="color: rgb(85,83,83); padding-left: 16px">Шкільний календар</h1>
            <div class="messages-container">
                <div class="messages" style="margin-right: 90px; position: absolute">
                    <div class="circle"></div>
                    <i class="fa-solid fa-bell"></i>
                </div>
            </div>
        </div>
        <div class="container">
            <div id="calendar"></div>
            <div class="events">
                <div class="holidays">
                    <span class="header">Канікули</span>
                    <#if hasHolidaysEvents>
                        <#list holidayEvents as event>
                            <div class="holidays-name" id="${event.id}">
                                <span class="holidays-dates-color"  style="background: ${event.eventColor}"></span>
                                <span class="holidays-dates-name">${event.eventName}</span>
                            </div>

                            <div class="holidays-dates-container">

                                <div class="holidays-dates">

                                    <div class="holidays-start-finish">
                                        <#if event.dateOfStartEvent != 'null'>
                                        <span class="holidays-dates-start">
                                                ${event.dateOfStartEvent}
                                        </span>
                                        </#if>
                                       <#if event.dateOfStartEvent != 'null' && event.dateOfFinishEvent != 'null'>
                                        <span>-</span>
                                        </#if>
                                          <#if event.dateOfFinishEvent != 'null'>
                                              <span class="holidays-dates-finish">${event.dateOfFinishEvent}</span>
                                          </#if>
                                    </div>
                                    <div class="update-or-delete">
                                        <div class="holidays-update">
                                            <i class="fa-solid fa-pen-fancy"></i>
                                        </div>
                                        <div class="holidays-delete">
                                            <i class="fa-regular fa-trash-can"></i>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </#list>
                    </#if>
                    <button type="button" class="add-event">Додати</button>
                </div>
                <div class="school-events">
                    <span class="header">Шкільні заходи</span>

                    <#if hasSchoolEvents>
                        <#list schoolEvents as event>
                            <div class="school-events-name" id="${event.id}">
                                <span class="school-events-dates-color" style="background: ${event.eventColor}"></span>
                                <span class="school-events-dates-name">${event.eventName}</span>
                            </div>

                            <div class="school-events-dates-container">

                                <div class="school-events-dates">

                                    <div class="school-events-start-finish">
                                        <#if event.dateOfStartEvent??>
                                            <span class="school-events-dates-start">
                                                ${event.dateOfStartEvent}
                                            </span>
                                        </#if>
                                        <#if event.dateOfStartEvent?? && event.dateOfFinishEvent??>
                                            <span>-</span>
                                        </#if>
                                        <#if event.dateOfFinishEvent??>
                                            <span class="school-events-dates-finish">${event.dateOfFinishEvent}</span>
                                        </#if>
                                    </div>
                                    <div class="school-events-update-or-delete">
                                        <div class="school-events-edit">
                                            <i class="fa-solid fa-pen-fancy"></i>
                                        </div>
                                        <div class="school-events-delete">
                                            <i class="fa-regular fa-trash-can"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </#if>
                    <button type="button" class="add-event">Додати</button>
                </div>

                <div class="public-holiday">
                    <span class="header">Державні вихідні</span>

                    <#if hasPublicEvents>
                        <#list publicEvents as event>
                            <div class="public-holiday-name" id="${event.id}">
                                <span class="public-holiday-dates-color" style="background: ${event.eventColor}"></span>
                                <span class="public-holiday-dates-name">${event.eventName}</span>
                            </div>

                            <div class="public-holiday-dates-container">

                                <div class="public-holiday-dates">

                                    <div class="public-holiday-start-finish">
                                        <#if event.dateOfStartEvent??>
                                            <span class="public-holiday-dates-start">
                                                ${event.dateOfStartEvent}
                                            </span>
                                        </#if>
                                        <#if event.dateOfStartEvent?? && event.dateOfFinishEvent??>
                                            <span>-</span>
                                        </#if>
                                        <#if event.dateOfFinishEvent??>
                                            <span class="public-holiday-dates-finish">${event.dateOfFinishEvent}</span>
                                        </#if>
                                    </div>
                                    <div class="public-holiday-update-or-delete">
                                        <div class="public-holiday-edit">
                                            <i class="fa-solid fa-pen-fancy"></i>
                                        </div>
                                        <div class="public-holiday-delete">
                                            <i class="fa-regular fa-trash-can"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </#if>
                    <button type="button" class="add-event">Додати</button>
                </div>
            </div>
        </div>
        <div id="modalFive" class="modal">
            <div class="modal-content">
                <div class="login-root">
                    <div class="formbg">
                        <div class="formbg-inner padding-horizontal--48">
                            <form id="stripe-login" class="new-column-form">
                                <div class="box-root padding-top--48 padding-bottom--24 flex-flex flex-justifyContent--center">
                                    <h1><span class="add-column-text"></span></h1>
                                </div>
                                <div class="date-column">
                                    <h2 style="text-align: center;" id="date-info"></h2>
                                </div>
                                <div class="field padding-bottom--24 input-event-name" >
                                    <label class="column-name">Назва</label>
                                    <input type="text" name="event-name" class="col-name name-ev" id="name-input">
                                </div>
                                <div class="field padding-bottom--24 input-event-date">
                                    <label class="column-name">Тривалість</label>
                                    <input type="text" readonly name="event-name" id="date-period" class="col-name date-ev">
                                </div>
                                <div class="color-gama">
                                    <div class="cont">
                                        <input type="radio" checked name="col" class="color color-1">
                                    </div>
                                    <div class="cont">
                                        <input type="radio" name="col" class="color color-2">

                                    </div>
                                    <div class="cont">
                                        <input type="radio" name="col" class="color color-3">

                                    </div>
                                    <div class="cont">
                                        <input type="radio" name="col" class="color color-4">

                                    </div>
                                    <div class="cont">
                                        <input type="radio" name="col" class="color color-5">

                                    </div>
                                    <div class="cont">
                                        <input type="radio" name="col" class="color color-6">

                                    </div>

                                </div>

                                <div class="save-subject" style="padding-top: 60px;">
                                    <button type="button" id="save-events" class="save-event save-column-name">Зберегти</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="modalSix" class="modal" style="z-index: 999;">
            <div class="modal-content">
                <div class="login-root">
                    <div class="formbg" style="max-height: 610px;     padding: 20px;">
                        <form action="" id="delete-form">

                            <div class="container" style="flex-direction: column;">
                                <h1>Видалити запис</h1>
                                <p style="font-size:16px;" class="are-you-sure">Ви впевнені, що хочете видалити запис?<br> <span class="delete-date"></span></p>
                                <input type="hidden" class="deleteId">
                                <div class="clearfix">
                                    <button type="button" style="margin-right: 15px;"  class="cancelbtn">Скасувати</button>
                                    <button type="button"  class="deletebtn" id="delete-event">Видалити</button>
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
<script
        src="https://code.jquery.com/jquery-3.6.3.js"
        integrity="sha256-nQLuAZGRRcILA+6dMBOvcRh5Pe310sBpanc6+QBmyVM="
        crossorigin="anonymous"></script>

<script src="https://unpkg.com/js-year-calendar@latest/dist/js-year-calendar.min.js"></script>

<script>
    $(document).ready(function() {
        $("body").css("opacity", "1");
    });
</script>
<script src="../static/js/air-datepicker.js"></script>
<script>
    const localeEs = {
        days: ['Неділя', 'Понеділок', 'Вівторок', 'Середа', 'Четвер', 'П`ятниця', 'Субота'],
        daysShort: ['НД', 'ПН', 'ВТ', 'СР', 'ЧТ', 'ПТ', 'СБ'],
        daysMin: ['НД', 'ПН', 'ВТ', 'СР', 'ЧТ', 'ПТ', 'СБ'],
        months: ['Січень', 'Лютий', 'Березень', 'Квітень', 'Травень', 'Червень', 'Липень', 'Серпень', 'Вересень', 'Жовтень', 'Листопад', 'Грудень'],
        monthsShort: ['Січ', 'Лют', 'Бер', 'Квіт', 'Трав', 'Черв', 'Лип', 'Серп', 'Вер', 'Жовт', 'Лист', 'Груд'],
        today: 'Сьогодні',
        clear: 'Очистити',
        timeFormat: 'hh:ii',
        firstDay: 1
    }

</script>

<script>
    window.onclick = function (event) {
        if (event.target.className === "modal") {
            event.target.style.display = "none";
            document.body.style.overflowY = "auto";
        }
    };
</script>
<script src="../static/js/menu-burger.js"></script>
<script src="../static/js/uaLanguage.js"></script>
<script src="../static/js/structureCalendar(v1).js"></script>
<script src="../static/js/messagesForm(v6).js"></script>

</html>