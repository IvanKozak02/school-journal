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
    <link rel="stylesheet" href="../static/css/air-datepicker.css">
    <link rel="stylesheet" href="../static/css/delete-form.css">
    <link rel="stylesheet" href="../static/css/addSchoolForm(v1).css">
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
            <h2 style="padding-left: 40px; color: rgb(85,83,83);; padding-top: 15px;">Предмет: <span class="subject">${subject}</span>
            </h2>
            <div class="messages-container">
                <div class="messages" style="margin-right: 90px;">
                    <div class="circle"></div>
                    <i class="fa-solid fa-bell"></i>
                </div>
            </div>
        </div>
        <div class="sem-and-subject">
            <div id="class-teacher-name" class="input-field semester-name"
                 style="padding-top: 15px; padding-left: 40px; width: 15%">
                <select class="semester" style="width: 96%" name="sex" required>
                    <option <#if isFirstSem>selected</#if>>I семестр</option>
                    <option <#if isFirstSem == false>selected</#if>>II семестр</option>
                </select>
            </div>
        </div>
        <div class="journal-filters">
            <div class="journal-marks-and-lessons-plan">
                <a class="mark-link journal-btn" href="/journal/${journalId}">Журнал оцінок</a>
                <a class="mark-link subject-plan" id="active" href="/calendar-planning/?journalId=${journalId}">План
                    уроку</a>
            </div>
            <#if hasAccess>
                <div class="add-record" onclick="document.getElementById('scroll').scrollTop=0;"
                     style=" margin-right: 10px;">
                    <a <#if disabled>disabled</#if>class="mark-link" id="add-record-btn">Додати запис</a>
                </div>
            </#if>
        </div>

        <div class="table-wrapper" journalId= ${journalId}>
            <table id="example" class="ui celled table" style="width:98%; ">
                <thead>
                <tr>
                    <th>Дата</th>
                    <th>Тема уроку</th>
                    <th>Завдання додому</th>
                    <th>Виконати до</th>
                    <#if hasAccess>
                        <th>Змінити</th>
                    </#if>

                </tr>
                </thead>
                <tbody>
                <#if listIsNotEmpty>
                    <#list calendarPlanningList as calendarPlanning>
                        <tr class="insert">
                            <td class="record">${calendarPlanning.dateOfLesson}</td>
                            <td class="record-subject">${calendarPlanning.lessonTopic}</td>
                            <td class="record-house-work">${calendarPlanning.homework}</td>
                            <td class="record-house-work-date">${calendarPlanning.dateOfHomework}</td>
                            <#if hasAccess>
                                <td>
                                    <div class="update-or-delete" onclick="showCalendarPlan(${calendarPlanning?counter-1})">
                                        <div class="update">
                                            <i class="fa-solid fa-pen-to-square"></i>
                                        </div>
                                        <div class="delete">
                                            <i class="fa-solid fa-trash-arrow-up"></i>
                                        </div>

                                    </div>
                                </td>
                            </#if>
                        </tr>
                    </#list>
                </#if>
                </tbody>
                <tfoot>
                <tr>
                    <th>Дата</th>
                    <th>Тема уроку</th>
                    <th>Завдання додому</th>
                    <th>Виконати до</th>
                    <#if hasAccess>
                        <th>Змінити</th>
                    </#if>
                </tr>
                </tfoot>
            </table>
        </div>

        <div id="modalTwo" class="modal" style="z-index: 999;">
            <div class="modal-content">
                <div class="login-root">
                    <div class="formbg" style="max-height: 610px;">
                        <div class="box-root padding-top--48 padding-bottom--24 flex-flex flex-justifyContent--center"
                             style="display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;">
                            <h1><span class="add-column-text" style="padding-top: 24px"></span></h1>
                            <h2 class="lesson-date-header" style="margin-top: 0;"><span class="add-column-text"></span>
                            </h2>
                        </div>
                        <div class="formbg-inner padding-horizontal--48" id="scroll" style="max-height: 85%;
    overflow: auto;
    position: relative; padding: 0 40px 60px 40px;">

                            <div class="add-form">
                                <form action="#" method="post" id="sub-plan-form">

                                    <div class="date-column">
                                        <h2 style="text-align: center;" id="date-info"></h2>
                                    </div>
                                    <div class="field padding-bottom--24 input-subject"
                                         data-error="Поле не може бути порожнім">
                                        <label class="column-name" style="padding-top: 0" for="subject">Тема
                                            уроку</label>
                                        <textarea name="subject" class="subject-area subject-lesson" cols="30"
                                                  rows="5"></textarea>
                                    </div>
                                    <div class="field padding-bottom--24">
                                        <label class="column-name-home" for="homework">Домашнє завдання</label>
                                        <textarea name="homework" class="subject-area homework" cols="30"
                                                  rows="5"></textarea>
                                    </div>


                                    <div class="homework-radio ">
                                        <label style="margin-bottom: 10px; color: #878787" for="radio">
                                            <input type="radio" name="radio" checked="checked" class="next-lesson">
                                            Домашнє на наступний урок
                                        </label>
                                        <label style="margin-bottom: 10px; color: #878787" for="radio">
                                            <input type="radio" name="radio" class="set-up-date"> Встановити дату здачі
                                        </label>
                                    </div>
                                    <div class="homework-done field" style="display: none;" date-error="Оберіть дату">
                                        <label class="column-name " for="homework">Виконати до</label>
                                        <input type="text" value="" data-language='en' readonly="readonly"
                                               id="airdatepicker" class="homework-done-date">
                                        <i class="fa-solid fa-calendar-days "></i>
                                    </div>

                                    <div class=" save-subject" style="padding-top: 60px;">
                                        <button type="button" class="save-subject-plan">Зберегти</button>
                                    </div>
                                </form>
                            </div>

                        </div>
                    </div>


                </div>
            </div>
        </div>

        <div id="modalThree" class="modal" style="z-index: 999;">
            <div class="modal-content">
                <div class="login-root">
                    <div class="formbg" style="max-height: 610px;">
                        <form action="" id="delete-form">

                            <div class="container" style="border-radius: 0">
                                <h1>Видалити запис</h1>
                                <p style="font-size:16px;" class="are-you-sure">Ви впевнені, що хочете видалити запис за
                                    <br> <span class="delete-date"></span></p>
                                <input type="hidden" class="deleteId">
                                <div class="clearfix">
                                    <button type="button" style="margin-right: 15px;" class="cancelbtn">Скасувати
                                    </button>
                                    <button type="button" class="deletebtn">Видалити</button>
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
<script
        src="https://code.jquery.com/jquery-3.6.3.js"
        integrity="sha256-nQLuAZGRRcILA+6dMBOvcRh5Pe310sBpanc6+QBmyVM="
        crossorigin="anonymous"></script>
<script src="//cdn.datatables.net/plug-ins/1.13.3/i18n/uk.json"></script>
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
    new AirDatepicker('#airdatepicker', {
        inline: true,
        position: 'top center',
        locale: localeEs
    })

</script>
<script src="../static/js/menu-burger.js"></script>
<script src="../static/js/pagination-table.js"></script>
<script src="../static/js/messagesForm(v6).js"></script>


<script src="https://cdn.datatables.net/1.13.2/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.2/js/dataTables.semanticui.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/fomantic-ui/2.8.8/semantic.min.js"></script>
<script>
    $(document).ready(function () {
        $("body").css("opacity", "1");
    });
</script>

<script>
    window.onclick = function (event) {
        if (event.target.className === "modal") {
            event.target.style.display = "none";
        }
    };
</script>
<script src="../static/js/subject-plan(v.4).js">
</script>
<script src="../static/js/form-error(subject-plan)(v6).js"></script>
<script src="../static/js/delete-form(v1).js"></script>


</body>
</html>