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
            <h1 style="padding-left: 40px; color: rgb(85,83,83);">Журнал <span>${className}</span> класу
                <#if classGroup != 0><span>${classGroup}</span> група</#if></h1>
            <div class="messages-container">
                <div class="messages" style="margin-right: 90px;">
                    <div class="circle"></div>
                    <i class="fa-solid fa-bell"></i>
                </div>
            </div>
        </div>

        <div class="sem-and-subject">
            <h2 style="padding-left: 40px; color: rgb(85,83,83);; padding-top: 15px;">Предмет: <span>${subject}</span>
            </h2>
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
                <a class="mark-link journal-btn" id="active" href="/journal/${journalId}">Журнал оцінок</a>
                <a class="mark-link subject-plan" href="/calendar-planning/?journalId=${journalId}">План уроку</a>
            </div>

            <div class="mark-scale">
                <span>Шкала: </span>
                <input type="radio" name="select" id="option-1" checked>
                <input type="radio" name="select-1" id="option-2">
                <label for="option-1" class="option option-1">
                    <div class="dot"></div>
                    <span>Бальна</span>
                </label>
                <label for="option-2" class="option option-2">
                    <div class="dot"></div>
                    <span>Рівнева</span>
                </label>
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
        <div class="table-wrapper" style="display: block;" id="${journalId}" <#if hasAccess ==false>teacher = "NO"</#if> <#if hasAccess>teacher = "YES"</#if>>
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
                        <tr class="student-row" >
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

        <div id="modalOne" class="modal">
            <div class="modal-content">
                <div class="login-root">
                    <div class="formbg">
                        <div class="formbg-inner padding-horizontal--48">
                            <div class="box-root padding-top--48 padding-bottom--24 flex-flex flex-justifyContent--center">
                                <h1><span class="add-column-text"></span></h1>
                            </div>
                            <div class="date-column">
                                <h2 style="text-align: center;" id="date-info"></h2>
                            </div>
                            <div class="field padding-bottom--24 input-column-name"
                                 data-error="Поле не може бути порожнім">
                                <label class="column-name" for="email">Назва стовпця</label>
                                <input type="text" name="columnName" class="col-name">
                            </div>

                            <div style="display: none" class="field field-checkbox  flex-flex align-center">
                                <label style="margin-bottom: 10px; color: #878787" for="checkbox">
                                    <input type="checkbox" class="canAdd" name="canAdd"> Враховувати при розрахунку
                                    середнього балу
                                </label>
                            </div>
                            <div class="save-subject" style="padding-top: 60px;">
                                <button type="submit" class="save-subject-plan save-column-name">Зберегти</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="mark-menu off" id="menu">
            <div class="small-block">
                <label for="pres" class="checkbox-horizontal">
                    <input type="checkbox" name="pres" class="check">
                    <span class="presence">Н</span>
                </label>
            </div>
            <div class="scales">
                <div class="number-mark" id="numbers">
                    <label class="mark-label" for="mark">Бал</label>
                    <input type="text" name="mark" class="mark-input" id="input-mark">
                    <input type="submit" id="mark-btn" class="save-mark-btn" value="Зберегти">
                </div>
                <input type="hidden" id="chose-cell" value="">
                <ul class="scale" style="display: none;" id="levels">
                    <li class="mark-numbers"><input type="checkbox" name="mark" value="4" class="mark"><label for="mark"
                                                                                                           class="level-label">В</label>
                    </li>
                    <li class="mark-numbers"><input type="checkbox" name="mark" value="3" onchange="delMark()" class="mark"><label
                                class="level-label" for="mark">Д</label></li>
                    <li class="mark-numbers"><input type="checkbox" name="mark" value="2" class="mark"><label
                                class="level-label" for="mark">С</label></li>
                    <li class="mark-numbers"><input type="checkbox" name="mark" value="1" class="mark"><label
                                class="level-label" for="mark">П</label></li>
                    <li class="mark-numbers btn"><input type="submit" id="mark-level-btn" class="save-mark-btn"
                                                        value="Зберегти"></li>
                </ul>

            </div>


        </div>

    </div>
    <div id="contMenu" class="contMenu" style="display: none">
        <button id="delete-mark" class="delete-mark">Видалити</button>
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
<script src="../static/js/journal(v20).js"></script>
<script src="../static/js/fillJournalTable(v4).js"></script>
<script src="../static/js/addColumnForm(v11).js"></script>
<script src="../static/js/mark-menu(v14).js"></script>
<script src="../static/js/form-error(journal).js"></script>
<script src="../static/js/studentAssesment.js"></script>
<script src="../static/js/Instruction.js"></script>
<script src="../static/js/messagesForm(v6).js"></script>

</body>
</html>