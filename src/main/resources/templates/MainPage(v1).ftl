<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="../static/css/icons.css">
	<link rel="stylesheet" href="../static/css/style(v6).css">
	<link rel="stylesheet" href="../static/css/slider(v19).css">
	<link rel="stylesheet" type="text/css" href="../static/css/solid.css">
	<link rel="stylesheet" type="text/css" href="../static/css/delete-form.css">
	<link rel="stylesheet" href="../static/css/календарне%20планування(v1).css">
	<link rel="stylesheet" href="../static/css/addToSchoolForms(v1).css">
	<link rel="stylesheet" href="../static/css/addSchoolForm(v1).css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/gh/habibmhamadi/multi-select-tag/dist/css/multi-select-tag.css">
	<link href='https://unpkg.com/boxicons@2.0.9/css/boxicons.min.css' rel='stylesheet'>
	<link rel="stylesheet" href="../static/css/applications(v2).css">
	<link rel="icon" type="images/x-icon" href="../static/images/Smart%20School.png" />

	<title>Smart school</title>
	<style>
		.circle-img {
			width: 180px;
			height: 180px;
			background: #DFF4F6;
			-moz-border-radius: 50px;
			-webkit-border-radius: 50px;
			border-radius: 50px;
		}

	</style>
</head>
<body>
<div class="wrap">
	<nav class="sidebar">
		<ul class="menu-list">
			<li><a  class="logo" href="#">
				<img class="logo-image" src="../static/images/Smart%20School.png" alt="">
				<span class="menu-item">Smart school</span>
				
			   </a>
			   <i id="btn" class="fa-solid fa-bars"></i>
		    </li>
			<li><a class="menu-ref" href="/mainpage">
				<i class="fa fa-home" aria-hidden="true"></i>
				<span class="menu-item">Головна</span>
				</a>
			</li>
			<#if hasSchool==true && hasRoleTeacher==true>
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
			<li><a class="menu-ref" href="/users">
				<i class="fa fa-users"></i>
				<span class="menu-item">Учасники</span>
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
		<div class="header" style="justify-content: flex-end">
			<div class="messages-container">
				<div class="messages" style="margin-right: 90px;">
					<div class="circle"></div>
					<i class="fa-solid fa-bell"></i>
				</div>
			</div>
		</div>
		<h3 class="journal">Ваш профіль</h3>
		<hr>
		<div class="profile">
			<div id="profile-container" style="display: flex; justify-content: space-between">
				<div class="profile_row">
					<div class="profile-left">
						<div class ="circle-img">
						</div>
						<div class="profile-id">
							<div class="profile-id-text">
								<span>ID - ${id} </span>
							</div>
							<div class="profile-id-image">
								<i class="fa-solid fa-copy"></i>
							</div>
							
						</div>

						
					</div>
					<div class="profile-right">

						<h1 id="teacher-name">${teacherName}</h1>

						<table >
							<tr>
								<td class="profile-right-info" ><span class="profile-header" >Номер телефону:</span></td>
								<td class="profile-right-info"><span id="work-phone-profile" style="display: table-cell;">${telNumber}</span></td>
							</tr>
							<tr>
								<td class="profile-right-info" ><span class="profile-header" >Посада:</span></td>
								<td class="profile-right-info"><span id="position-profile" style="display: table-cell;">${position}</span></td>
							</tr>
							<tr>
								<td class="profile-right-info"><span class="profile-header">Email:</span></td>
								<td class="profile-right-info"><span id="email-profile" style="display: table-cell;">${email}</span></td>
							</tr>
							<tr>
								<td class="profile-right-info"><span class="profile-header">Дата народження:</span></td>
								<td class="profile-right-info"><span style="display: table-cell;">${dateOfBirth}</span></td>
							</tr>
						</table>
						
						
					</div>
					

				</div>
				<i id="edit-profile" class="fa fa-pen fa-xs edit"></i>
			</div>

		</div>

		<h3 class="journal"><#if hasClassJournal>Класні журнали<#elseif hasSchool==false>Підключення до системи</#if></h3>
		<hr>
		<#if hasSchool == true && hasRoleTeacher==true>
		<div class="cards" style="padding-left: 0.5rem;">
			<#list journals as journal>
				<a style="width: 21%" class="single-card" href="/journal/${journal.id}">
					<div>
						<h1>${journal.schedule.schoolClass.nameOfClass}</h1>
						<h4>${journal.schedule.subject.name}</h4>
						<h4><#if journal.schedule.classGroup?has_content>підгрупа №${journal.schedule.classGroup.numberOfGroup}</#if></h4>

					</div>
					<div>
						<i class="fa-solid fa-users"></i>
					</div>
				</a>
			</#list>
		</div>
		</#if>

		<#if isClassTeacher>
		<h3 class="journal">Класне керівництво</h3>
		<hr>
			<div class="cards" style="padding-left: 0.5rem;">
					<a class="single-card" <#if hasStudent>href="/attendance-record/${attendanceJournal}" <#else>href="${attendanceJournal}</#if>">
						<div>
							<h1>${class}</h1>
						</div>
						<div>
							<i class="fa-solid fa-book"></i>
						</div>
					</a>
			</div>
		</#if>

		<div style="padding-left: 1.5rem" class="are-you-teacher-or-admin">
			<#if hasSchool == false && isStudent == false>
			<div class="are-you-teacher-form">
				<div class="teacher-form-container">
					<div class="teacher-form_raw">
						<h3 class="are-you-teacher-h3">Ваш навчальний заклад вже зареєстровано?</h3>
						<p class="are-you-teacher-p">Для приєднання до навчального закладу вам необхідно подати заявку</p>
						<button type="button" class="add-to-school">Приєднатися</button>
					</div>

				</div>
			</div>
			<div class="are-you-admin-form">
				<div class="admin-form-container">
					<h3 class="are-you-admin-h3">Ваш навчальний заклад ще не зареєстровано?</h3>
					<p class="are-you-admin-p">Якщо ви адміністратор навчального закладу, то вам необхідно його підключити</p>
					<button type="button" class="register-school">Підключити</button>
				</div>
			</div>
			</#if>
<#--			<#if hasSchool == false && isStudent == true>-->
<#--				<div class="are-you-teacher-form">-->
<#--					<div class="teacher-form-container">-->
<#--						<div class="teacher-form_raw">-->
<#--							<p class="are-you-teacher-p">Для приєднання до навчального закладу вам необхідно подати заявку</p>-->
<#--							<button type="button" class="add-to-school">Приєднатися</button>-->
<#--						</div>-->
<#--					</div>-->
<#--				</div>-->
<#--			</#if>-->
			<#if hasSchool == true && hasRoleTeacher == true>
				<div class="are-you-admin-form" style="width: 55%;">
					<div class="admin-form-container" style="background-color: slateblue; " >
						<h3 style="color: #fff; padding-bottom: 20px" class="are-you-admin-h3">${schoolName}</h3>
						<table>
							<tr style="display: flex;align-items: baseline">
								<td class="profile-right-info" ><span class="profile-header" style="color: #d6d6d6" >Предмети:</span></td>
								<td class="profile-right-info">
									<span id="work-phone-profile" style="display: table-cell; color: #fff; font-weight: 600;">${subjects}</span>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</#if>
		</div>


		<h3 class="personal-achievement">Особисті досягнення</h3>
		<hr>

		<div id="slider" class="sliders" style="margin: 1.5rem">
			<div class="sertificate-buttons">
				<form  action="#" id="add-certificate-form" enctype="multipart/form-data" onsubmit="return false">
					<input name="add-cert" type="file" id="add-certificate" multiple hidden />
					<label class="add-sert" for="add-certificate" >Додати</label>
					<button type="submit" id="save-certificate">Зберегти</button>
				</form>

				<button type="submit" id="remove-sertificate"
						<#if isCertificates>style="display: block" </#if>>
				Видалити</button>
			</div>

			<div class="sliderbig">
				<#if isCertificates>
					<#list certificates as certificate>
						<div db_id="${certificate.id}" id="big" class="sliderbig__items"><img src="img/${certificate.imgUrl}" alt=""></div>
					</#list>
				</#if>
			</div>
			<div class="slider">
				<#if isCertificates>
					<#list certificates as certificate>
						<div db_id="${certificate.id}" class="slider__items"><img src="img/${certificate.imgUrl}" alt=""></div>
					</#list>
				</#if>
			</div>

		</div>

	</div>
</div>
<#if hasSchool == false>
	<div id="addSchoolModal" class="modal" style="z-index: 999;">
		<div class="modal-content" style="background: #fff;
    padding: 0 40px 40px;
    border-radius: 8px; margin: 25px auto; width:565px">
			<h1><span class="add-column-text" style="margin-right: 10px; font-size: 25px; letter-spacing: 2px; text-align: center; padding-top: 25px; padding-bottom: 25px">Підключення школи до системи</span></h1>
			<div class="content-container">
				<div class="class-information">
					<form action="#" id="form-connect-school" onsubmit="return false">
						<div class="data" >
							<div id="oblast" class="input-field">
								<label class="mandatory">Область</label>
								<select id="oblast" class="select-element" style="width: 210px" name="oblast" required>
									<option  disabled selected>Оберіть область</option>
									<option region = "01">Автономна Республіка Крим
									</option>
									<option region = "05">Вінницька область
									</option>
									<option region = "07">Волинська область
									</option>
									<option region = "12">Дніпропетровська область
									</option>
									<option region = "14">Донецька область</option>
									<option region = "18">Житомирська область</option>
									<option region = "21">Закарпатська область</option>
									<option region = "23">Запорізька область</option>
									<option region = "26">Івано-Франківська область</option>
									<option region = "32">Київська область</option>
									<option region = "35">Кіровоградська область</option>
									<option region = "44">Луганська область</option>
									<option region = "46">Львівська область</option>
									<option region = "48">Миколаївська область</option>
									<option region = "51">Одеська область</option>
									<option region = "53">Полтавська область</option>
									<option region = "56">Рівненська область</option>
									<option region = "59">Сумська область</option>
									<option region = "61">Тернопільська область</option>
									<option region = "63">Харківська область</option>
									<option region = "65">Херсонська область</option>
									<option region = "68">Хмельницька область</option>
									<option region = "71">Черкаська область</option>
									<option region = "73">Чернівецька область</option>
									<option region = "74">Чернігівська область</option>
									<option region = "80">КИЇВ</option>
									<option region = "85">м.Севастополь</option>
								</select>
							</div>
							<div id="type" style=" width: 44%;" class="input-field">
								<label class="mandatory">Тип населеного пункту</label>
								<select disabled onchange="typeOfCitySelect(event)" id="typeOfCity" name="type" required>
									<option disabled selected>Оберіть тип</option>
									<option>Місто</option>
									<option>Село</option>
									<option>Селище міського типу</option>
								</select>
							</div>
							<div id="reg" style=" display: none;padding-top:35px;width: 44%;" class="input-field">
								<label class="mandatory">Район</label>
								<select id="region" name="region" required>
									<option disabled selected>Оберіть район</option>

								</select>
							</div>
							<div style="width: 44%;" class="input-field">
								<label class="mandatory">Населений пункт</label>
								<select disabled  id="city" name="city" required>
									<option disabled selected>Оберіть населений пункт</option>
								</select>
							</div>
							<div style="padding: 35px 0;" class="input-field">
								<label class="mandatory">Відділ освіти</label>
								<select disabled id="viddil" style="width: 210px" name="district" required>
									<option disabled selected>Оберіть відділ</option>
								</select>
							</div>
							<div class="input-field">
								<label class="mandatory">Школа</label>
								<select disabled id="schools"  style="width: 212px" name="name" required>
									<option disabled selected>Оберіть школу</option>
								</select>
							</div>

						</div>
						<div style="display: flex" class="input-cont">
							<label class="mandatory">Предмети, які викладаються у школі</label>
							<select class="mandatory" name="subjects" id="countries" multiple>
								<option value="1">Українська мова і літературне читання</option>
								<option value="2">Англійська мова</option>
								<option value="3">Математика</option>
								<option value="4">Я досліджую світ (інтегрований курс)</option>
								<option value="5">Музичне мистецтво*</option>
								<option value="6">Образотворче мистецтво*</option>
								<option value="7">Інформатика</option>
								<option value="8">Українська мова</option>
								<option value="9">Українська література</option>
								<option value="10">Зарубіжна література</option>
								<option value="11">Історія (Вступ до історії)</option>
								<option value="12">Природознавство</option>
								<option value="13">Основи здоров’я</option>
								<option value="14">Історія України та Всесвітня історія (інтегрований курс)</option>
								<option value="15">Біологія</option>
								<option value="16">Географія</option>
								<option value="17">Історія України</option>
								<option value="18">Всесвітня історія</option>
								<option value="19">Алгебра</option>
								<option value="20">Геометрія</option>
								<option value="21">Фізика</option>
								<option value="22">Хімія</option>
								<option value="23">Польська мова</option>
								<option value="24">Громадянська освіта</option>
								<option value="25">Математика (Алгебра і початки аналізу та геометрія)</option>
								<option value="26">Біологія і екологія</option>
								<option value="27">Захист України</option>
								<option value="28">Фізика і астрономія</option>
								<option value="29">Мистецтво*</option>
								<option value="30">Технології*</option>
								<option value="31">Інформатика</option>
								<option value="32">Фінансова грамотність</option>
							</select>
						</div>
						<div class="next-prev-btn">
							<div class="btns">
								<button class="previous">Назад</button>
								<button class="next">Далі</button>
							</div>
						</div>
						<!--					<div style="display:none;background: #f4e7ff;" class="container">-->
						<!--						<input type="file" id="file" accept="image/*" hidden>-->
						<!--						<div class="img-area" style="background: #fff" data-img="">-->
						<!--							<i class='bx bxs-cloud-upload icon'></i>-->
						<!--							<h3>Завантажити фото паспорта</h3>-->
						<!--							<p>Розмір фото повинен бути не більше <span>2MB</span></p>-->
						<!--						</div>-->
						<!--						<div class="select-image-container">-->
						<!--							<button class="select-image">Оберіть фото</button>-->
						<!--						</div>-->
						<!--					</div>-->
						<div class="save-subject" style="padding-top: 30px;">
							<button type="submit"  class="save-school">Зберегти</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div id="addTeacherToSchool" class="modal" style="z-index: 999;">
		<div class="modal-content" style="background: #fff;
    padding: 0 40px 40px;
    border-radius: 8px; margin: 25px auto; width:565px">
			<h1><span class="add-column-text" style="margin-right: 10px; font-size: 25px; letter-spacing: 2px; text-align: center; padding-top: 25px; padding-bottom: 25px">Подання заявки на приєднання</span></h1>
			<div class="content-container">
				<div class="class-information">
					<form action="#" id="form-connect-teacher" onsubmit="return false">
						<div style="display: flex; align-items: baseline" class="data-t">
							<div id="oblast" class="input-field">
								<label class="mandatory">Область</label>
								<select disabled class="select-element__teacher" style="width: 210px" name="sex" required>
									<option  disabled selected>Оберіть область</option>
									<!--								<option region = "01">Автономна Республіка Крим-->
									<!--								</option>-->
									<!--								<option region = "05">Вінницька область-->
									<!--								</option>-->
									<!--								<option region = "07">Волинська область-->
									<!--								</option>-->
									<!--								<option region = "12">Дніпропетровська область-->
									<!--								</option>-->
									<!--								<option region = "14">Донецька область</option>-->
									<!--								<option region = "18">Житомирська область</option>-->
									<!--								<option region = "21">Закарпатська область</option>-->
									<!--								<option region = "23">Запорізька область</option>-->
									<!--								<option region = "26">Івано-Франківська область</option>-->
									<!--								<option region = "32">Київська область</option>-->
									<!--								<option region = "35">Кіровоградська область</option>-->
									<!--								<option region = "44">Луганська область</option>-->
									<!--								<option region = "46">Львівська область</option>-->
									<!--								<option region = "48">Миколаївська область</option>-->
									<!--								<option region = "51">Одеська область</option>-->
									<!--								<option region = "53">Полтавська область</option>-->
									<!--								<option region = "56">Рівненська область</option>-->
									<!--								<option region = "59">Сумська область</option>-->
									<!--								<option region = "61">Тернопільська область</option>-->
									<!--								<option region = "63">Харківська область</option>-->
									<!--								<option region = "65">Херсонська область</option>-->
									<!--								<option region = "68">Хмельницька область</option>-->
									<!--								<option region = "71">Черкаська область</option>-->
									<!--								<option region = "73">Чернівецька область</option>-->
									<!--								<option region = "74">Чернігівська область</option>-->
									<!--								<option region = "80">КИЇВ</option>-->
									<!--								<option region = "85">м.Севастополь</option>-->
								</select>
							</div>
							<#--						<div id="type" style=" width: 44%;" class="input-field">-->
							<#--							<label class="mandatory">Тип населеного пункту</label>-->
							<#--							<select disabled onchange="typeOfCitySelect(event)" id="typeOfCity__teacher" name="region__teacher" required>-->
							<#--								<option disabled selected>Оберіть тип</option>-->
							<#--								<option>Місто</option>-->
							<#--								<option>Село</option>-->
							<#--								<option>Селище міського типу</option>-->
							<#--							</select>-->
							<#--						</div>-->
							<#--						<div id="regions" style=" display: none;padding-top:35px;width: 44%;" class="input-field">-->
							<#--							<label class="mandatory">Район</label>-->
							<#--							<select id="region__teacher" name="region__teacher" required>-->
							<#--								<option disabled selected>Оберіть регіон</option>-->

							<#--							</select>-->
							<#--						</div>-->
							<div style="width: 44%;" class="input-field">
								<label class="mandatory">Населений пункт</label>
								<select disabled  id="city__teacher" name="city" required>
									<option disabled selected>Оберіть населений пункт</option>
								</select>
							</div>
							<div style="padding: 35px 0;" class="input-field">
								<label class="mandatory">Відділ освіти</label>
								<select disabled id="viddil__teacher" style="width: 210px" name="district" required>
									<option disabled selected>Оберіть відділ</option>
								</select>
							</div>
							<div class="input-field">
								<label class="mandatory">Школа</label>
								<select disabled id="schools__teacher"  style="width: 212px" name="sex" required>
									<option disabled selected>Оберіть школу</option>
								</select>
							</div>

						</div>
						<div style="display: flex" class="input-cont-teacher">
							<label class="mandatory">Предмети, які викладаються у школі</label>
							<select class="mandatory" name="countries" id="subjects" multiple>
								<#--							<option value="1">Українська мова і літературне читання</option>-->
								<#--							<option value="2">Англійська мова</option>-->
								<#--							<option value="3">Математика</option>-->
								<#--							<option value="4">Я досліджую світ (інтегрований курс)</option>-->
								<#--							<option value="5">Музичне мистецтво*</option>-->
								<#--							<option value="6">Образотворче мистецтво*</option>-->
								<#--							<option value="7">Інформатика</option>-->
								<#--							<option value="8">Українська мова</option>-->
								<#--							<option value="9">Українська література</option>-->
								<#--							<option value="10">Зарубіжна література</option>-->
								<#--							<option value="11">Історія (Вступ до історії)</option>-->
								<#--							<option value="12">Природознавство</option>-->
								<#--							<option value="13">Основи здоров’я</option>-->
								<#--							<option value="14">Історія України та Всесвітня історія (інтегрований курс)</option>-->
								<#--							<option value="15">Біологія</option>-->
								<#--							<option value="16">Географія</option>-->
								<#--							<option value="17">Історія України</option>-->
								<#--							<option value="18">Всесвітня історія</option>-->
								<#--							<option value="19">Алгебра</option>-->
								<#--							<option value="20">Геометрія</option>-->
								<#--							<option value="21">Фізика</option>-->
								<#--							<option value="22">Хімія</option>-->
								<#--							<option value="23">Польська мова</option>-->
								<#--							<option value="24">Громадянська освіта</option>-->
								<#--							<option value="25">Математика (Алгебра і початки аналізу та геометрія)</option>-->
								<#--							<option value="26">Біологія і екологія</option>-->
								<#--							<option value="27">Захист України</option>-->
								<#--							<option value="28">Фізика і астрономія</option>-->
								<#--							<option value="29">Мистецтво*</option>-->
								<#--							<option value="30">Технології*</option>-->
								<#--							<option value="31">Інформатика</option>-->
								<#--							<option value="32">Фінансова грамотність</option>-->
							</select>
						</div>

						<div style="display:none;background: #f4e7ff;" class="container">
							<input type="file" name="file" id="file" accept="image/*" hidden>
							<div class="img-area" style="background: #fff" data-img="">
								<i class='bx bxs-cloud-upload icon'></i>
								<h3>Завантажити фото паспорта</h3>
								<p>Розмір фото повинен бути не більше <span>2MB</span></p>
							</div>
							<div class="select-image-container">
								<button class="select-image">Оберіть фото</button>
							</div>
						</div>
						<div class="next-prev-btn-teacher">
							<div class="btns">
								<button class="previous-t">Назад</button>
								<button disabled class="next-t">Далі</button>
							</div>
						</div>
						<div class="save-subject" style="padding-top: 30px;">
							<button type="submit"  class="save-teacher">Зберегти</button>
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

<div id="profileModal" class="modal" style="z-index: 999;">
	<div class="modal-content" style="background: #fff;
    padding: 0 40px 40px;
    border-radius: 8px; margin: 25px auto;">
		<h1><span class="add-column-text" style=" font-size: 25px; letter-spacing: 2px; text-align: center; padding-top: 25px; padding-bottom: 25px">Редагування профілю</span></h1>
		<div class="content-container">
			<div class="class-information">
				<form action="/mainpage" id="form-profile">
					<div class="field padding-bottom--24 " id="user-name" >
						<h5>Прізвище, ім'я, по-батькові</h5>
						<input type="text" name="username" class="subject-area username" id="username">
					</div>
					<div class="field padding-bottom--24 "  id="work-phone">
						<h5>Робочий номер телефону</h5>
						<input type="text" name="workPhone" minlength="10" class="form-control" id="tel"  placeholder="+38 (___) ___-__-__" required>
					</div>
					<div class="field padding-bottom--24 "  id="email-container">
						<h5>Поштова скринька</h5>
						<input required type="email" style="border-radius: 8px" name="email" class="subject-area" id="email">
					</div>
					<div class="field padding-bottom--24 "  id="position-container">
						<h5>Посада</h5>
						<input  type="text" style="border-radius: 8px" name="position" class="subject-area" id="position">
					</div>
					<div class=" save-subject" style="padding-top: 30px;">
						<button type="button"  class="save-profile">Зберегти</button>
					</div>
				</form>
			</div>


		</div>
	</div>
</div>


<script src="https://code.jquery.com/jquery-3.6.3.min.js"></script>
<script>
	$(document).ready(function() {
		$("body").css("opacity", "1");
	});
</script>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js"></script>
<script>
	$("#tel").mask("+38 (999) 999-99-99");
</script>

<script src="../static/js/slick.min.js"></script>
<#if isCertificates>
	<script src="../static/js/script(v2).js"></script>
</#if>
<script src="../static/js/addSertificate(v6).js"></script>

<script>
	let burger = document.querySelector("#btn");
	let sidebar = document.querySelector(".sidebar");
	burger.onclick = function () {
		sidebar.classList.toggle("active");
		setTimeout(function(){
			$(".slider").slick('setPosition');
			$(".sliderbig").slick('setPosition')
		}, 400);

	}

</script>
<#if hasSchool == false>
<script src="https://cdn.jsdelivr.net/gh/habibmhamadi/multi-select-tag/dist/js/multi-select-tag.js"></script>
<script>
	new MultiSelectTag('countries')
</script>

<script src="../static/js/addTeacherToSchool(v4).js"></script>
<script src="../static/js/selectDoc.js"></script>
<script src="../static/js/addSchoolForm(v4).js"></script>

</#if>
<script src="../static/js/editProfileForm.js"></script>
<script src="../static/js/messagesForm(v6).js"></script>
<script src="../static/js/main-page1.js"></script>
</body>



</html>