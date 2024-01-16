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
            <h3 class="journal">Налаштування</h3>
            <div class="messages-container">
                <div class="messages" style="margin-right: 90px;">
                    <div class="circle"></div>
                    <i style="margin-left: 0;" class="fa-solid fa-bell"></i>
                </div>
            </div>
        </div>
        <hr>

        <ul class="cards">

            <li class="cards__item" style="width: 29%">
                <div class="card">
                    <div class="card__image card__image--fence"></div>
                    <div class="card__content">
                        <div class="card__title" style="align-items: start">
                            <h3 style="margin-right: 20px;">Структура навчального року</h3>
                            <i id="edit-profile" class="fa fa-pen fa-xs edit  edit-structure"></i>

                        </div>
                        <a href="#" type="button" class="view">Внести</a>
                    </div>
                </div>
            </li><br>



        </ul>
    </div>
</div>

<#if hasAccess>
<div id="addSchoolProfile" class="modal" style="z-index: 999; overflow: auto">
    <div class="modal-content" style="background: #fff;
    padding: 0 40px 40px;
    border-radius: 8px; margin: 25px auto; width:565px">
        <h1><span class="add-column-text" style="margin-right: 10px; font-size: 25px; letter-spacing: 2px; text-align: center; padding-top: 25px; padding-bottom: 25px">Структура навчального року</span></h1>
        <div class="content-container">
            <div class="class-information" style="padding-top: 15px">
                <form action="#" id="form-connect-school" onsubmit="return false">
                    <h3>Перший семестр</h3>
                    <div class="data">

                        <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                            <label class="mandatory">Початок семестру</label>
                            <input type="date" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="first-sem-start" >
                        </div>
                        <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                            <label class="mandatory">Завершення семестру</label>
                            <input type="date" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="first-sem-finish" >
                        </div>
                    </div>
                    <h3 style="padding-top: 10px">Другий семестр</h3>
                    <div class="data">

                        <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                            <label class="mandatory">Початок семестру</label>
                            <input type="date"  style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="second-sem-start" >
                        </div>
                        <div class="field padding-bottom--24 " id="user-id-container" style="padding-top: 20px; padding-bottom: 15px">
                            <label class="mandatory">Завершення семестру</label>
                            <input type="date" style=" border-radius: 8px; margin-top: 5px;" name="class-cabinet" class="subject-area" id="second-sem-finish" >
                        </div>
                    </div>
                    <div class=" save-subject" style="padding-top: 30px;">
                        <button type="button" class="save-school save-year-structure">Зберегти</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</#if>
<div class="note-form-wrap">
    <div class="note-title">
        Повідомлення
    </div>
    <div class="note-items">

    </div>
</div>
</body>
<script src="https://code.jquery.com/jquery-3.6.3.min.js"></script>
<script>
    $(document).ready(function() {
        $("body").css("opacity", "1");
    });
</script>
<script src="../static/js/menu-burger.js"></script>
<script src="../static/js/Settings.js"></script>
<script src="../static/js/messagesForm(v6).js"></script>

</html>