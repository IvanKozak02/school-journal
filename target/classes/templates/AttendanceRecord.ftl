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

</head>
<body style="background: rgb(223, 233, 245);">
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
    <main id="swup" class="transition-fade">

    </main>
    <div id="content">

        <div class="header">
            <h1 style="padding-left: 40px; color: rgb(85,83,83);">Журнал <span>${className}</span> класу</h1>
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
                <label>
                    <select class="class-teacher semester" style="width: 96%" required>
                        <option <#if isFirstSem>selected</#if>>I семестр</option>
                        <option <#if isFirstSem == false>selected</#if>>II семестр</option>
                    </select>
                </label>
            </div>
        </div>

        <div class="journal-filters">
            <div class="journal-marks-and-lessons-plan">
                <a class="mark-link journal-btn" id="active" href="${journalId}">Облік відвідування</a>
                <a class="mark-link subject-plan" href="/visit-statistics/?journalId=${journalId}&time=1">Статистика відвідувань</a>
            </div>

            <div class="journal-teacher">
                <span>Вчитель:</span>
                <span style="color:slateblue; font-weight:bold">${teacherName}</span>
            </div>

            <div class="lessons-date">
                <i style="margin-right: 5px;" class="fa-solid fa-chevron-left previous-page"></i>
                <span class="dates"></span>
                <i style="margin-left: 5px;" class="fa-solid fa-chevron-right next-page" id="right-arrow"></i>
            </div>
        </div>
        <div class="table-wrapper" style="display: block;" id="${journalId}" classId="${classId}">
            <div class="table-container">
                <table id="data" style="display: block;">
                    <thead>
                    <tr class="head-table">
                        <th style="color: slateblue; font-style: bold; font-weight: 800;" class="student">
                            Учні
                        </th>

                        <#list days as day>
                            <th class="date-inserted">
                                ${day}
                            </th>
                        </#list>
                    </tr>
                    </thead>
                    <tbody>
                    <#list students as student>
                        <tr class="student-row" <#if hasAccess>head-teacher="YES" <#else>head-teacher="NO" </#if>>
                            <th class="student" id="${student?split("-")[0]}">${student?split("-")[1]}</th>
                            <#list days as day>
                                <td class="inserted">
                                    <#if hasAccess>
                                    <div class="mark-item"></div>
                                    </#if>
                                </td>
                            </#list>
                        </tr>
                    </#list>
                    </tbody>

                </table>


            </div>

        </div>

        <div id="attendanceRecordForm" class="modal">
            <div class="modal-content">
                <div class="modal-content" style="width: 525px">
                    <div class="login-root">
                        <div class="formbg" style="max-width: 525px">
                            <div class="formbg-inner padding-horizontal--48">
                                <form id="stripe-login" class="new-column-form">
                                    <div class="box-root padding-top--48 padding-bottom--24 flex-flex flex-justifyContent--center">
                                        <h1><span class="add-column-text"></span></h1>
                                    </div>
                                    <div class="date-column">
                                        <h2 style="text-align: center;" id="date-info"></h2>
                                    </div>
                                    <div class="table-wrap">
                                        <div class="table-container">
                                            <table class="oblik-table">
                                                <thead style="color: lightslategray">
                                                <th class="oblik-table-header">
                                                    Предмети
                                                </th>
                                                <th class="oblik-table-header">
                                                    Позначка в журналі
                                                </th>
                                                <th class="oblik-table-header" style="padding-left: 10px">
                                                    Н
                                                </th>
                                                <th class="oblik-table-header">
                                                    ХВ
                                                </th>
                                                </thead>
                                                <tbody>
                                                <tr class="oblik-row">
                                                    <td class="inserted-oblik">
                                                        <div class="subject">Математика</div>
                                                    </td>
                                                    <td class="inserted-oblik" style="text-align: center">
                                                        Н
                                                    </td>
                                                    <td class="inserted-oblik"><input type="radio" class="HCheckBox" name="A" style="width: 18px; height: 18px"></td>
                                                    <td class="inserted-oblik"><input type="radio" class="XBВCheckBox" name="A" style="width: 18px; height: 18px;margin-left: 7px"></td>
                                                </tr>
                                                <tr class="oblik-row">
                                                    <td class="inserted-oblik">
                                                        <div class="subject">Українська</div>

                                                    </td>
                                                    <td class="inserted-oblik" style="text-align: center">
                                                        <div class="oblik-mark">Н</div>
                                                    </td>
                                                    <td class="inserted-oblik"><input type="radio" class="HCheckBox" name="B" style="width: 18px; height: 18px"></td>
                                                    <td class="inserted-oblik"><input type="radio" class="XBВChekBox" name="B" style="width: 18px; height: 18px; margin-left: 7px"></td>
                                                </tr>
                                                <tr class="oblik-row">
                                                    <td class="inserted-oblik">
                                                        <div class="subject">Англійська</div>

                                                    </td>
                                                    <td class="inserted-oblik" style="text-align: center">
                                                        <div class="oblik-mark">Н</div>
                                                    </td>
                                                    <td class="inserted-oblik-radio"><input type="radio" class="HCheckBox" name="C" style="width: 18px; height: 18px"></td>
                                                    <td class="inserted-oblik-radio"><input type="radio" class="XBВChekBox" name="C" style="width: 18px; height: 18px; margin-left: 7px"></td>
                                                </tr>
                                                <tr class="oblik-row">
                                                    <td class="inserted-oblik">
                                                        <div class="subject">Німецька</div>
                                                    </td>
                                                    <td class="inserted-oblik" style="text-align: center">
                                                        <div class="oblik-mark">Н</div>
                                                    </td>
                                                    <td class="inserted-oblik"><input type="radio" class="HCheckBox" name="D" style="width: 18px; height: 18px"></td>
                                                    <td class="inserted-oblik"><input type="radio" class="XBВChekBox" name="D" style="width: 18px; height: 18px; margin-left: 7px"></td>
                                                </tr>
                                                <tr class="oblik-row">
                                                    <td class="inserted-oblik">
                                                        <div class="subject">Я і світ</div>

                                                    </td>
                                                    <td class="inserted-oblik" style="text-align: center">
                                                        <div class="oblik-mark" >Н</div>
                                                    </td>
                                                    <td class="inserted-oblik"><input type="radio" class="HCheckBox" name="E" style="width: 18px; height: 18px"></td>
                                                    <td class="inserted-oblik"><input type="radio" class="XBВChekBox" name="E" style="width: 18px; height: 18px; margin-left: 7px"></td>
                                                </tr>


                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </form>
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
<script src="https://code.jquery.com/jquery-3.5.1.js"></script>
<script src="../static/js/menu-burger.js"></script>

<script>
    $(document).ready(function () {
        $("body").css("opacity", "1");
    });
</script>
<script>
    let links = document.getElementsByClassName('mark-link');

    links[0].addEventListener("click", function () {
        links[0].setAttribute('id', 'active');
        links[1].removeAttribute('id', "active");
    });

    links[1].addEventListener("click", function () {
        links[1].setAttribute('id', 'active');
        links[0].removeAttribute('id', "active");
    })

</script>
<script src="../static/js/AttendanceRecordForm(v4).js"></script>
<script src="../static/js/attendanceRecord(v5).js"></script>
<script src="../static/js/fillJournalAttendanceRecordTable.js"></script>
<script src="../static/js/messagesForm(v6).js"></script>
</body>


</html>