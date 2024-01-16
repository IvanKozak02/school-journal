<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="../static/css/journals.css">
	<link rel="stylesheet" href="../static/css/style(v6).css">
	<link rel="stylesheet" href="../static/css/icons.css">
	<link rel="stylesheet" href="../static/css/teacher-schedule.css">
	<link rel="stylesheet" href="../static/css/add_column_form.css">
	<link rel="stylesheet" href="../static/css/schedule-filters.css">
	<link rel="stylesheet" href="../static/css/addSchoolForm.css">
	<link rel="stylesheet" href="../static/css/delete-form.css">
	<link rel="stylesheet" href="../static/css/applications(v2).css">
	<link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

	<title>Smart school</title>
	<style>

		.numerator{
			position: relative;
		}
		.denominator{
			position: relative;
		}
		.dropdown {
			position: absolute;
			display: inline-block;
		}

		.dropdown-content {
			display: none;
			position: absolute;
			background-color: #f1f1f1;
			min-width: 215px;
			box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
			border-radius: 12px;
			width: 100%;
			left: -235px;
			z-index: 9999;

		}

		.dropdown-content button:first-child {
			color: black;
			padding: 12px 16px;
			text-decoration: none;
			display: block;
			width: 100%;
			border: none;
			border-radius: 12px;
			border-bottom: none;
			border-bottom-left-radius: 0;
			border-bottom-right-radius: 0;
			color: gray;
			/* background: inherit; */

		}

		.make-admin:active, .edit-subjects:active{
			transform: none;
		}

		.dropdown-content button:last-child{
			color: black;
			padding: 12px 16px;
			text-decoration: none;
			display: block;
			width: 100%;
			border: none;
			border-bottom: none;
			border-bottom-left-radius: 12px;
			border-bottom-right-radius: 12px;
			color: gray;
		}
		.delete{
			width: 30%;
			margin: 0 auto;
		}
		.dropdown-content button:hover {background-color: #ddd;}
		.dropdown-content.show{
			display: block;
		}
	</style>
</head>
<body style="overflow: hidden">
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
	<div id="content" style="padding-block: 1rem" subjects = '${subjects}'>
		<div class="header">
			<h1 style="padding-left: 40px; color: rgb(85,83,83);;">Розклад вчителів</h1>
			<div class="messages-container">
				<div class="messages" style="margin-right: 90px;">
					<div class="circle"></div>
					<i class="fa-solid fa-bell"></i>
				</div>
			</div>
		</div>
		<div class="input-field">
			<select id="schools__teacher"  style="width: 250px;margin-left: 40px;margin-top: 70px;" required>
				<option disabled selected>Оберіть вчителя</option>
			</select>
		</div>
					<div class="schedule-table-wrapper" style="display: block;">
			<div class="table-container">
				<table id="data" style="display: block;">
			<thead>
				<tr class="head-table">
					<th class="lessons">
						Уроки
				</th>
				<th class="date-inserted">
					Понеділок

				</th>
				<th class="date-inserted">
					Вівторок
				</th>
				<th class="date-inserted">
					Середа
				</th>
				<th class="date-inserted">
					Четвер
				</th>
				<th class="date-inserted">
					П'ятниця
				</th>
				<th class="date-inserted">
					Субота
				</th>
				</tr>
			</thead>
			<tbody>
				<tr class="teacher-row">
					<th class="day-of-week">Урок №1</th>
					<td class="schedule" oncontextmenu="openContMenu(0)"><div class="schedule-item"></div>
						<div class="numerator" >
						<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
						<span class="subject"></span>
						<div class="addition">
							<div class="dropdown">
								<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
									<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
								</div>
							</div>
						</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(1)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(2)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(3)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(4)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(5)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
				</tr>
				<tr class="teacher-row">
					<th class="day-of-week">Урок №2</th>
					<td class="schedule" oncontextmenu="openContMenu(6)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(7)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(8)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(9)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(10)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(11)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>

				</tr>
				<tr class="teacher-row">
					<th class="day-of-week">Урок №3</th>
					<td class="schedule" oncontextmenu="openContMenu(12)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(13)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(14)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(15)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(16)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(17)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>

				</tr>
				<tr class="teacher-row">
					<th class="day-of-week">Урок №4</th>
					<td class="schedule" oncontextmenu="openContMenu(18)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(19)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(20)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(21)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(22)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(23)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
				</tr>
				<tr class="teacher-row">
					<th class="day-of-week">Урок №5</th>
					<td class="schedule" oncontextmenu="openContMenu(24)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(25)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(26)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(27)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(28)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(29)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
				</tr>
				<tr class="teacher-row">
					<th class="day-of-week">Урок №6</th>
					<td class="schedule" oncontextmenu="openContMenu(30)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(31)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(32)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(33)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(34)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(35)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
<#--					<td class="schedule"><div class="schedule-item"></div></td>-->
				</tr>
				<tr class="teacher-row">
					<th class="day-of-week">Урок №7</th>
					<td class="schedule" oncontextmenu="openContMenu(36)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(37)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(38)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(39)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(40)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(41)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
				</tr>
				<tr class="teacher-row">
					<th class="day-of-week">Урок №8</th>
					<td class="schedule" oncontextmenu="openContMenu(42)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(43)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(44)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(45)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(46)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
					<td class="schedule" oncontextmenu="openContMenu(47)"><div class="schedule-item"></div>
						<div class="numerator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
						<div class="denominator">
							<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
							<span class="subject"></span>
							<div class="addition">
								<div class="dropdown">
									<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
										<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
									</div>
								</div>
							</div>
						</div>
					</td>
				</tr>

			</tbody>

		</table>
		
		
			</div>
			
		</div>
				</div>
	</div>
	<#if hasAccess>
		<div id="modalFour" class="modal">
			<div class="modal-content">
				<div class="login-root">
					<div class="formbg">
						<h2 class="teacher-name" style="text-align: center;padding-top: 20px;"></h2>
						<div class="formbg-inner padding-horizontal--48" style="overflow-y: auto; height:100%">
							<div class="input-field" style="padding-top: 30px">
								<label class="mandatory">Предмет</label>
								<select id="subject"  style="width: 280px;" required>
									<option disabled selected>Оберіть предмет</option>
								</select>
							</div>
							<div class="input-field period" style="padding-top: 30px;">
								<label class="mandatory">Періодичність занять</label>
								<select id="period" style="width: 280px;" required>
									<option value="Кожен тиждень" selected>Кожен тиждень</option>
									<option value="Чисельник">Чисельник</option>
									<option value="Знаменник">Знаменник</option>
								</select>
							</div>
							<div class="input-field school-class" style="padding-top: 30px;  display: none;">
								<label class="mandatory">Клас</label>
								<select id="school-class" style="width: 280px;" required>
									<option disabled selected>Оберіть клас</option>
								</select>
							</div>
							<div class="input-field school-class-group" style="padding-top: 30px; display: none;">
								<label class="mandatory">Група</label>
								<select id="school-class-group"  style="width: 280px;" required>
									<option disabled selected>Оберіть групу</option>
									<option>1</option>
									<option>2</option>
								</select>
							</div>

							<div class="save-schedule-button" style="padding-top: 30px">
								<button disabled type="button" class="save-schedule-plan save-column-name">Зберегти</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</#if>

	<#if hasAccess>
		<div id="delete-modal" class="modal" style="z-index: 999;">
			<div class="modal-content">
				<div class="login-root">
					<div class="formbg" style="max-height: 610px;    padding: 20px;">
						<form action="" id="delete-form" style="text-align: center">
							<div class="container-delete">
								<h1>Видалити запис</h1>
								<p style="font-size:16px;" class="are-you-sure">Ви впевнені, що хочете видалити введений розклад?</p>
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
	</#if>
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
  <script src="../static/js/menu-burger.js"></script>

<script>
    	$(document).ready(function() {    
    $("body").css("opacity", "1");
  });
  </script>

<script src="../static/js/form-schedule(v4).js"></script>
<script src="../static/js/messagesForm(v6).js"></script>

</html>