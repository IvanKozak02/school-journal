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
	<link rel="stylesheet" href="../static/css/air-datepicker.css">
    <link rel="stylesheet" href="../static/css/addSchoolForm.css">
	<link rel="stylesheet" href="../static/css/applications(v2).css">
	<link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

    <title>Smart school</title>

    <style>
        .single-card{
            max-width: 305px;
            min-height: 150px;
            width: 100%;
            justify-content: center;
            text-align: center;
            align-items: center;
            margin-right: 30px;
        }
    </style>

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
			<h1 style="padding-left: 40px; color: rgb(85,83,83);;">Кабінет адміністратора</h1>
			<div class="messages-container">
				<div class="messages" style="margin-right: 90px;">
					<div class="circle"></div>
					<i class="fa-solid fa-bell"></i>
				</div>
			</div>
		</div>
        <div class="cards" style="padding-left: 40px; padding-top: 30px;">
            <a class="single-card" href="/admin-cabinet-oblik" style="border-radius: 10px; padding: 3rem; box-shadow: 3px -2px 30px 0px rgba(184,184,184,0.63);">
                <div>
                    <h3>Облік відвідування</h3>
                </div>
            </a>
            <a class="single-card" href="/admin-cabinet-journal" style="border-radius: 10px; padding: 3rem; box-shadow: 3px -2px 30px 0px rgba(184,184,184,0.63);">
                <div>
                    <h3>Облік навчальних досягнень</h3>
                </div>
            </a>
            <a class="single-card" href="/admin-cabinet-instruction" style="border-radius: 10px; padding: 3rem; box-shadow: 3px -2px 30px 0px rgba(184,184,184,0.63);">
                <div>
                    <h3>Інструктажі</h3>
                </div>
            </a>
            <a class="single-card" href="/admin-cabinet-motion-table" style="border-radius: 10px; padding: 3rem; box-shadow: 3px -2px 30px 0px rgba(184,184,184,0.63);">
                <div>
                    <h3>Таблиця руху учнів</h3>
                </div>
            </a>
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
	<script src="../static/js/menu-burger.js"></script>

  <script src="https://cdnjs.cloudflare.com/ajax/libs/fomantic-ui/2.8.8/semantic.min.js"></script>
  <script>
    	$(document).ready(function() {
    $("body").css("opacity", "1");
  });
  </script>
	<script src="../static/js/messagesForm(v6).js"></script>

  <script>
  	window.onclick = function (event) {
        if (event.target.className === "modal") {
          event.target.style.display = "none";
        }
      };
  </script>



</body>
</html>