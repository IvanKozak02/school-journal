<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../static/css/style(v6).css">
    <link rel="stylesheet" href="../static/css/icons.css">
    <link rel="stylesheet" type="text/css" href="../static/css/solid.css">
    <link rel="stylesheet" href="../static/css/add_column_form.css">
    <link rel="stylesheet" href="../static/css/delete-form.css">
    <link rel="stylesheet" href="../static/css/listOfClasses(v1).css">
    <link rel="stylesheet" href="../static/css/schedule-filters.css">
    <link rel="stylesheet" href="../static/css/journals.css">
    <link rel="stylesheet" href="../static/css/addSchoolForm.css">
    <link rel="stylesheet" href="../static/css/applications(v2).css">
    <link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

    <title>Smart school</title>
</head>
<body style="opacity:0;
	transition:.5s;
	background: #dfe9f5;">
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
            <h1 style="padding-left: 40px; color: rgb(85,83,83);">Класи : </h1>
            <div class="messages-container">
                <div class="messages" style="margin-right: 90px; position: absolute">
                    <div class="circle"></div>
                    <i class="fa-solid fa-bell"></i>
                </div>
            </div>
        </div>
        <div style="justify-content: flex-end; padding-top: 30px;" class="classes-header">
            <#if hasAccess>
            <button type="button" class="add-new-class">Додати новий клас</button>
            </#if>
        </div>

        <div class="classes-container" <#if role == "YES">role="${"YES"}"</#if>>
            <#if isFirstClass>
           <div class="list-of-classes">
               <#list firstClasses as firstClass>
                <div class="class">
                    <#if hasAccess>
                    <div class="delete-class-icons-container">
                        <span class="delete-class-icon">
                            <i class="fa-solid fa-trash-can" style="color: #fff; font-size: 16px"></i>
                        </span>
                    </div>
                    </#if>

                    <div class="class-span" id="${firstClass.id}" onclick="findClass(${firstClass.id})">
                        <span class="class-name">${firstClass.nameOfClass}</span>
                    </div>
                </div>

               </#list>
            </div>
            </#if>
            <#if isSecondClass>
                <div class="list-of-classes">
                    <#list secondClasses as secondClass>
                        <div class="class" >
                            <#if hasAccess>
                                <div class="delete-class-icons-container">
                        <span class="delete-class-icon">
                            <i class="fa-solid fa-trash-can" style="color: #fff; font-size: 16px"></i>
                        </span>
                                </div>
                            </#if>
                            <div class="class-span" id="${secondClass.id}" onclick="findClass(${secondClass.id})">
                                <span class="class-name">${secondClass.nameOfClass}</span>
                            </div>
                        </div>
                    </#list>
                </div>
            </#if>
            <#if isThirdClass>
                <div class="list-of-classes">
                    <#list thirdClasses as thirdClass>
                        <div class="class">

                            <#if hasAccess>
                                <div class="delete-class-icons-container">
                        <span class="delete-class-icon">
                            <i class="fa-solid fa-trash-can" style="color: #fff; font-size: 16px"></i>
                        </span>
                                </div>
                            </#if>
                            <div class="class-span" id="${thirdClass.id}" onclick="findClass(${thirdClass.id})">
                                <span class="class-name">${thirdClass.nameOfClass}</span>
                            </div>
                        </div>
                    </#list>
                </div>
            </#if>
            <#if isfourthClass>
                <div class="list-of-classes">
                    <#list fourthClasses as fourthClass>
                        <div class="class">

                            <#if hasAccess>
                                <div class="delete-class-icons-container">
                        <span class="delete-class-icon">
                            <i class="fa-solid fa-trash-can" style="color: #fff; font-size: 16px"></i>
                        </span>
                                </div>
                            </#if>
                            <div class="class-span"  id="${fourthClass.id}" onclick="findClass(${fourthClass.id})">
                                <span class="class-name">${fourthClass.nameOfClass}</span>
                            </div>
                        </div>
                    </#list>
                </div>
            </#if>
            <#if isfifthClass>
                <div class="list-of-classes">
                    <#list fifthClasses as fifthClass>
                        <div class="class" >
                            <#if hasAccess>
                                <div class="delete-class-icons-container">
                        <span class="delete-class-icon">
                            <i class="fa-solid fa-trash-can" style="color: #fff; font-size: 16px"></i>
                        </span>
                                </div>
                            </#if>
                            <div class="class-span" id="${fifthClass.id}" onclick="findClass(${fifthClass.id})">
                                <span class="class-name">${fifthClass.nameOfClass}</span>
                            </div>
                        </div>
                    </#list>
                </div>
            </#if>
            <#if isSixthClass>
                <div class="list-of-classes">
                    <#list sixthClasses as sixthClass>
                        <div class="class" >
                            <#if hasAccess>
                                <div class="delete-class-icons-container">
                        <span class="delete-class-icon">
                            <i class="fa-solid fa-trash-can" style="color: #fff; font-size: 16px"></i>
                        </span>
                                </div>
                            </#if>
                            <div class="class-span" id="${sixthClass.id}" onclick="findClass(${sixthClass.id})">
                                <span class="class-name">${sixthClass.nameOfClass}</span>
                            </div>
                        </div>
                    </#list>
                </div>
            </#if>
            <#if isSeventhClass>
                <div class="list-of-classes">
                    <#list seventhClasses as seventhClass>
                        <div class="class" >

                            <#if hasAccess>
                                <div class="delete-class-icons-container">
                        <span class="delete-class-icon">
                            <i class="fa-solid fa-trash-can" style="color: #fff; font-size: 16px"></i>
                        </span>
                                </div>
                            </#if>
                            <div class="class-span" id="${seventhClass.id}" onclick="findClass(${seventhClass.id})">
                                <span class="class-name">${seventhClass.nameOfClass}</span>

                            </div>
                        </div>
                    </#list>
                </div>
            </#if>
            <#if isEighthClass>
                <div class="list-of-classes">
                    <#list eighthClasses as eighthClass>
                        <div class="class" >

                            <#if hasAccess>
                                <div class="delete-class-icons-container">
                        <span class="delete-class-icon">
                            <i class="fa-solid fa-trash-can" style="color: #fff; font-size: 16px"></i>
                        </span>
                                </div>
                            </#if>
                            <div class="class-span" id="${eighthClass.id}" onclick="findClass(${eighthClass.id})">
                                <span class="class-name">${eighthClass.nameOfClass}</span>

                            </div>
                        </div>
                    </#list>
                </div>
            </#if>
            <#if isNinthClass>
                <div class="list-of-classes">
                    <#list ninthClasses as ninthClass>
                        <div class="class" >

                            <#if hasAccess>
                                <div class="delete-class-icons-container">
                        <span class="delete-class-icon">
                            <i class="fa-solid fa-trash-can" style="color: #fff; font-size: 16px"></i>
                        </span>
                                </div>
                            </#if>
                            <div class="class-span" id="${ninthClass.id}" onclick="findClass(${ninthClass.id})">
                                <span class="class-name">${ninthClass.nameOfClass}</span>
                            </div>
                        </div>
                    </#list>
                </div>
            </#if>
            <#if isTenthlass>
                <div class="list-of-classes">
                    <#list tenthClasses as tenthClass>
                        <div class="class">

                            <#if hasAccess>
                                <div class="delete-class-icons-container">
                        <span class="delete-class-icon">
                            <i class="fa-solid fa-trash-can" style="color: #fff; font-size: 16px"></i>
                        </span>
                                </div>
                            </#if>
                            <div class="class-span" id="${tenthClass.id}" onclick="findClass(${tenthClass.id})">
                                <span class="class-name">${tenthClass.nameOfClass}</span>
                            </div>
                        </div>
                    </#list>
                </div>
            </#if>
            <#if isEleventhlass>
                <div class="list-of-classes">
                    <#list eleventhClasses as eleventhClass>
                        <div class="class">

                            <#if hasAccess>
                                <div class="delete-class-icons-container">
                        <span class="delete-class-icon">
                            <i class="fa-solid fa-trash-can" style="color: #fff; font-size: 16px"></i>
                        </span>
                                </div>
                            </#if>
                            <div class="class-span" id="${eleventhClass.id}" onclick="findClass(${eleventhClass.id})">
                                <span class="class-name">${eleventhClass.nameOfClass}</span>
                            </div>
                        </div>
                    </#list>
                </div>
            </#if>
            <#if classNotFound>
                <div class="img-container" style="display: flex;flex-direction: column;align-items: center;">
                    <img width="500px" height="300px" src="https://creazilla-store.fra1.digitaloceanspaces.com/cliparts/63211/school-clipart-md.png" alt="">
                    <h1 class="class-user-name" style="padding-top: 30px; color: #bbbbbb;">Класів не знайдено</h1>
                </div>
            </#if>
        </div>
    </div>


    <div id="createClassModal" class="modal" style="z-index: 999;">
        <div class="modal-content" style="background: #fff;
    padding: 0 40px 40px;
    border-radius: 8px;">

            <h1><span class="add-column-text"
                      style=" letter-spacing: 2px; text-align: center; padding-top: 25px; padding-bottom: 25px">Новий клас</span>
            </h1>
            <div class="content-container">
                <div class="class-information" style="display: flex">
                    <div id="class" class="input-field" style="margin-right: 15px;">
                        <label class="mandatory">Номер класу</label>
                        <select class="class-number" style="width: 96%" name="class-number" required>
                            <option class="dis-value" disabled selected>Оберіть номер класу</option>
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                            <option>6</option>
                            <option>7</option>
                            <option>8</option>
                            <option>9</option>
                            <option>10</option>
                            <option>11</option>
                        </select>
                    </div>
                    <div id="class-name-cont" class="input-field">
                        <label class="mandatory">Назва класу</label>
                        <select class="class-name-select" style="width: 96%" name="sex" required>
                            <option class="dis-value" disabled selected>Оберіть назву класу</option>
                            <option>А</option>
                            <option>Б</option>
                            <option>В</option>
                            <option>Г</option>
                            <option>Д</option>
                        </select>
                    </div>
                </div>

                <div id="class-teacher-name" class="input-field" style="padding-top: 15px;">
                    <label class="mandatory">Класний керівник</label>
                    <select class="class-teacher" style="width: 96%" name="sex" required>
                        <option disabled selected>Оберіть класного керівника</option>
                        <option>А</option>
                        <option>Б</option>
                        <option>В</option>
                        <option>Г</option>
                        <option>Д</option>
                    </select>
                </div>
            </div>
            <div class="field padding-bottom--24" style="padding-top: 20px">
                <h4>Кабінет</h4>
                <input type="text" style="margin-top: 5px; border-radius: 8px" name="class-cabinet" class="subject-area"
                       id="cabinet">
            </div>
            <div class="save-subject" style="padding-top: 60px;">
                <button type="button" class="save-class">Створити</button>
            </div>
        </div>
    </div>
</div>

<div id="delete-modal" class="modal" style="z-index: 999;">
    <div class="modal-content">
        <div class="login-root">
            <div class="formbg" style="max-height: 610px;    padding: 20px;">
                <form action="" id="delete-form" style="text-align: center">
                    <div class="container-delete">
                        <h1>Видалити клас</h1>
                        <p style="font-size:16px;" class="are-you-sure">Ви впевнені, що хочете видалити <span
                                    class="delete-class"></span> клас?</p>
                        <input type="hidden" class="deleteId">
                        <div class="clearfix">
                            <button type="button" style="margin-right: 15px;" class="cancelbtn">Скасувати</button>
                            <button type="button" class="deletebtn">Видалити</button>
                        </div>
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
<script
        src="https://code.jquery.com/jquery-3.6.3.js"
        integrity="sha256-nQLuAZGRRcILA+6dMBOvcRh5Pe310sBpanc6+QBmyVM="
        crossorigin="anonymous"></script>

<script src="../static/js/menu-burger.js"></script>

<script src="../static/js/ListOfClasses(v3).js"></script>
<script>
    $(document).ready(function () {
        $("body").css("opacity", "1");
    });
</script>

<script>
    window.onclick = function (event) {
        if (event.target.className === "modal") {
            event.target.style.display = "none";
            document.body.style.overflowY = "auto";
        }
        if (!event.target.matches('.select-btn')) {		// якщо не була натиснута кнопка

            let menu_items =
                document.getElementsByClassName("select-menu");
            let i;
            for (i = 0; i < menu_items.length; i++) {
                let openMenu = menu_items[i];
                if (openMenu.classList.contains('active')) {
                    openMenu.classList.remove('active');
                }
            }
        }

    };
</script>

<script src="../static/js/messagesForm(v6).js"></script>

</body>
</html>