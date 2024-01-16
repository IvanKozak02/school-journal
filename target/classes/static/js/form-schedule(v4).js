let scheduleCell = document.querySelectorAll('.schedule');
let popUpSchedule = document.getElementById('modalFour');


window.onload = async function () {
    let teachers = await getAllTeacher().then(res => {
        return res;
    });

    for (let i = 0; i < teachers.length; i++) {
        document.getElementById("schools__teacher").innerHTML += `<option value="${teachers[i].userPersonalData.id}" teacher_name = "${teachers[i].userPersonalData.userName}">${teachers[i].userPersonalData.userName}</option>`
    }

}
/*=================================Витягування Розкладу============================================================*/

document.getElementById("schools__teacher").addEventListener("change", ()=>{
    clearTable();
    getTeacherSchedule(document.getElementById("schools__teacher").value);
})


function clearTable(){
    let tr = document.querySelectorAll(".teacher-row");
    for (let i = 0; i < tr.length; i++) {
        let td = tr[i].querySelectorAll(".schedule");
        for (let j = 0; j < td.length; j++) {
            td[j].innerHTML = `<div class="numerator">
<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
<span class="subject"></span>
<div class="addition">
<div class="dropdown">
<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
</div>
</div>
</div>
</div><div class="denominator">
<span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
<span class="subject"></span>
<div class="addition">
<div class="dropdown">
<div class="dropdown-content" style="top: -50px; left: -20px; min-width: 110px">
<button onclick="deleteSchedule(event)" class="make-admin">Видалити</button>
</div>
</div>
</div>
</div>`;
        }
    }
}


async function getTeacherSchedule(teacherId) {
    let teacherSc = await getAllScheduleRecordForTeacher(teacherId).then(res => {
        return res;
    });
    if (teacherSc.length > 0){
        fillTableBySchedule(teacherSc);
    }
}

async function getAllScheduleRecordForTeacher(teacherId){
    return fetch("http://localhost:8080/schedulerecord?teacherId=" + teacherId).then(res => res.json())
        .then((json) => {
            return json
        });
}

function fillTableBySchedule(teacherSc){
    for (let i = 0; i < 6; i++) {
        switch (i){
            case 0:
                getDaySchedule("Понеділок", teacherSc,0);
                break;
            case 1:
                getDaySchedule("Вівторок", teacherSc, 1);
                break;
            case 2:
                getDaySchedule("Середа", teacherSc,2);
                break;
            case 3:
                getDaySchedule("Четвер", teacherSc, 3);
                break;
            case 4:
                getDaySchedule("П'ятниця", teacherSc, 4);
                break;
            case 5:
                getDaySchedule("Субота", teacherSc, 5);
                break;
        }
    }
}

function getDaySchedule(day, teacherSc, i){
    let schedule = new Set(teacherSc.filter(e=>e.dayOfWeek === day));
    let sortArr = Array.from(schedule).sort(function (a,b){
        return a.lessonNumber - b.lessonNumber;
    })

    fillDaySchedule(i, sortArr, teacherSc);
}


function fillDaySchedule(dayNumber, schedule, teacherSc) {
    let teacherRow = document.querySelectorAll(".teacher-row");
    // for (let i = 0; i < teacherRow.length; i++) {
    //     let td = teacherRow[i].querySelectorAll(".schedule")[dayNumber];
    for (let j = 0; j < 8; j++) {
        let td = teacherRow[j].querySelectorAll(".schedule")[dayNumber];
        let firstSub = Array.from(getLessonSchedule(schedule, dayNumber, j+1));
        if (firstSub.length === 1 && firstSub[0].period === "Кожен тиждень"){
            let sc = clearTd(td);
            sc.removeChild(td.querySelector(".numerator"));
            let denominator = sc.querySelector(".denominator");
            fillPartOfCell(denominator, firstSub[0], teacherSc);
        }
        else {
            for (let k = 0; k < firstSub.length; k++) {
                if (firstSub[k].period === "Чисельник"){
                    let sc = clearNumeratorTd(td);
                    let numerator = sc.querySelector(".numerator");
                    numerator.style.borderBottom = "1px solid";
                    fillPartOfCell(numerator, firstSub[k], teacherSc);
                }
                else if (firstSub[k].period === "Знаменник") {
                    let sc = clearDenominatorTd(td);
                    let denominator = sc.querySelector(".denominator");
                    denominator.style.borderTop = "1px solid";
                    fillPartOfCell(denominator, firstSub[k], teacherSc);
                }
            }
        }

    }

}

function getLessonSchedule(schedule, dayNumber, lessonNumber){
    return new Set(schedule.filter(e=>e.dayOfWeek === getDay(dayNumber) && e.lessonNumber === lessonNumber.toString()));

}

function getDay(dayNumber){
    switch (dayNumber){
        case 0:
            return "Понеділок";
        case 1:
            return "Вівторок";
        case 2:
            return "Середа";
        case 3:
            return "Четвер";
        case 4:
            return "П'ятниця";
        case 5:
            return "Субота";
    }
}

function fillPartOfCell(part, schedule, teacherSc){
    part.querySelector(".class-name").innerText = '';
    part.querySelector(".subject").innerText = '';
    part.querySelector(".group").innerText = '';
    let indexCell = part.parentElement.cellIndex;
    let indexRow = part.parentElement.parentElement.rowIndex;
    let sc_class;
    if (schedule.schoolClass.nameOfClass !== undefined){
        sc_class = schedule.schoolClass.nameOfClass;
    }
    else {
        let sc_class_id = schedule.schoolClass;
        for (let i = 0; i < teacherSc.length; i++) {

            if (teacherSc[i].schoolClass.id===sc_class_id){
                sc_class = teacherSc[i].schoolClass.nameOfClass;
            }
        }
    }

    let subject = schedule.subject.name;
    if (schedule.classGroup !== null) {
        part.querySelector(".class-name").innerText = sc_class + ', ';
        part.querySelector(".group").innerText = 'п.' + schedule.classGroup.numberOfGroup;
    }
    else {
        part.querySelector(".class-name").innerText = sc_class;
    }

    part.querySelector(".subject").innerText = subject;
    let str = `showEditForm('${schedule.period}',${indexCell-1},${indexRow-1},'${subject}',${event})`;
    console.log(str);
    part.setAttribute('onclick', `${str}`);
    let deleteStr = `deleteSchedule(${getTextInBrackets(str)})`;
    part.querySelector(".make-admin").setAttribute("onclick", deleteStr)
}

/*------------------PopUP----------------------------------------*/
for (let i = 0; i < scheduleCell.length; i++) {
    scheduleCell[i].addEventListener("click", ()=>{
        showPopUpDelete(i);
    })
}

function showPopUpDelete(i){

    document.querySelector(".save-schedule-plan").setAttribute("edit", '');

    let td = document.querySelectorAll(".schedule")[i];
    if (td.querySelector(".denominator").querySelector(".class-name").innerText !== '' &&
        td.querySelector(".numerator") === null){
        document.getElementById("period").innerHTML = '<option value="Чисельник">Чисельник</option>';
    }
    else if (td.querySelector(".denominator").querySelector(".class-name").innerText !== '' &&
        td.querySelector(".numerator") !== null){
        document.getElementById("period").innerHTML = '<option value="Чисельник">Чисельник</option>';

    }
    else if (td.querySelector(".numerator").querySelector(".class-name").innerText !== ''){
        document.getElementById("period").innerHTML = '<option value="Знаменник">Знаменник</option>';
    }
    else {
        document.getElementById("period").innerHTML ='<option value="Кожен тиждень" selected>Кожен тиждень</option> <option value="Чисельник">Чисельник</option>' +
            '<option value="Знаменник">Знаменник</option>';
    }

    if (document.getElementById("schools__teacher").value !== "Оберіть вчителя"){
        document.getElementById("subject").innerHTML ='<option disabled selected>Оберіть предмет</option>';
        document.getElementById("school-class").innerHTML ='<option disabled selected>Оберіть клас</option>';
        document.getElementById("school-class-group").innerHTML ='<option disabled selected>Оберіть групу</option>';
        popUpSchedule.style.display = "block";
        document.querySelector(".teacher-name").innerText = document.getElementById("schools__teacher").options[document.getElementById("schools__teacher").selectedIndex].getAttribute("teacher_name");
        document.querySelector(".school-class").style.display = "none";
        document.querySelector(".school-class-group").style.display = "none";
        document.querySelector(".save-schedule-plan").setAttribute("disabled", "disabled")
        document.getElementById("subject").value = "Оберіть предмет";
        document.getElementById("school-class").value = "Оберіть клас";
        document.getElementById("school-class-group").value = "Оберіть групу";
        document.querySelector(".save-column-name").setAttribute("disabled", "disabled");
        document.querySelector(".save-schedule-plan").setAttribute("id",i);
        let raw_number = findRawNumber();
        document.querySelector(".save-schedule-plan").setAttribute("raw",raw_number-1);
        let day_of_week = findDayOfWeek(i);
        document.querySelector(".save-schedule-plan").setAttribute("day_of_week",day_of_week);
        document.querySelector(".save-schedule-plan").innerText = "Зберегти";
        fillSchoolSubjectsSelect();
    }
    else{
        alert("Оберіть вчителя!!!")
    }
}


async function fillSchoolSubjectsSelect() {
    let teacherId = document.getElementById("schools__teacher").value;
    let teacherSubjects = await getAllTeacherSubject(teacherId).then(res => {
        return res;
    });
    let subjectsName = [];
    for (let i = 0; i < teacherSubjects.length; i++) {
        subjectsName.push(teacherSubjects[i].name);
    }
    // let subjects = document.getElementById("content").getAttribute("subjects");
    if (subjectsName !== null) {
        fillSubjects(subjectsName);
    }

}

function fillSubjects(subArr){
    let subjectSelect = document.getElementById("subject");
    for (let i = 0; i < subArr.length; i++) {
        subjectSelect.innerHTML += `<option value='${subArr[i]}'>${subArr[i]}</option>`;
    }
}

async function getAllTeacherSubject(teacherId){
    let subjects;
    return subjects = fetch("http://localhost:8080/allteachersubjects?teacherId=" + teacherId).then(res => res.json())
        .then((json) => {
            return json
        });
}

function findRawNumber(){
    let raw = 0;
    let col_num = document.querySelector(".save-schedule-plan").getAttribute("id");
    if (col_num === '0'){
        return raw+1;
    }
    for (let i = col_num; i > 0 ; i-=6) {
        if (i-6 === 0){
            raw++;
            return raw+1;
        }
        raw++;
    }
    return raw;
}

function findDayOfWeek(cell_value){
    let col = 0;
    for (let i = cell_value; i >= 0 ; i-=6) {
        col = i;
    }
    return col+1;
}




document.querySelector(".save-schedule-plan").addEventListener("click", ()=>{

    let raw = document.querySelector(".save-schedule-plan").getAttribute("raw");
    let col = document.querySelector(".save-schedule-plan").getAttribute("day_of_week");
    let sc_raw = document.querySelectorAll(".teacher-row")[raw];
    let k = parseInt(col);
    let sc_td = sc_raw.children[k];
    if (sc_td.querySelector(".schedule-item") !== null){
        sc_td.removeChild(sc_td.children[0]);
    }
    let obj = transformData(raw,col);
    const formData = new FormData();
    formData.append("jsonData", JSON.stringify(obj));
    if (document.querySelector(".save-schedule-plan").getAttribute("edit")==="edit"){
        editTeacherSchedule(formData, sc_td);
    }
    else {
        saveSchedule(formData, sc_td, "teacher-schedule");
    }

    console.log(obj);

});

document.getElementById("school-class").addEventListener("click", ()=>{
    document.querySelector(".school-class-group").style.display = "none";
})


function transformData(raw, col){
    let subject = document.getElementById("subject").value;
    let sc_class = document.getElementById("school-class").value;       // id класу
    let teacher = document.getElementById("schools__teacher").value;        // userPersonalData id
    let lesson_number = parseInt(raw)+1;
    let day_of_week = document.querySelector(".head-table").children[col].innerText;
    let group = null;               // id групи
    let period = document.getElementById("period").value;
    if (window.getComputedStyle(document.querySelector(".school-class-group")).display === "block"
        && document.getElementById("school-class-group").value !== "Оберіть групу"){
        group = document.getElementById("school-class-group").value;
    }
    return {
        teacher: teacher, subject: subject, schoolClass: sc_class, classGroup: group, dayOfWeek: day_of_week,
        lessonNumber: lesson_number, period: period
    };
}

async function saveSchedule(formData, sc_td, request){
    let res = await fetch("http://localhost:8080/" + request, {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData,
    }).then(response=>{
        if (response.ok){
            fillCell(sc_td);
        }
        else {
            alert("Невірно введений розклад!!!");
        }
    });

}


function fillCell(sc_td){
    let period = checkPeriodSelect();
    if (period === "Кожен тиждень") {
        let td = clearTd(sc_td);
        td.removeChild(td.querySelector(".numerator"));
        let denominator = td.querySelector(".denominator");
        fillDataToCell(denominator, period);
    }
    else if (period === "Чисельник"){
        let td = clearNumeratorTd(sc_td);
        let numerator = td.querySelector(".numerator");
        numerator.style.borderBottom = "1px solid";
        fillDataToCell(numerator, period);
    }
    else if (period === "Знаменник"){
        let td = clearDenominatorTd(sc_td);
        let denominator = td.querySelector(".denominator");
        denominator.style.borderTop = "1px solid";
        fillDataToCell(denominator, period);
    }

}

function clearTd(sc_td){
    sc_td.innerHTML = '';
    sc_td.innerHTML += `<div class="numerator"><span class="class-name"></span><span style="color: slateblue" class="group"></span><br>
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
</div>`;
    return sc_td;
}

function clearNumeratorTd(sc_td){
    let denominator = sc_td.querySelector(".denominator").innerHTML;
    let denominatorOnClick = sc_td.querySelector(".denominator").getAttribute("onclick");
    sc_td.innerHTML = '';
    sc_td.innerHTML += `<div class="numerator">
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
</div>`;
    sc_td.querySelector(".denominator").innerHTML += denominator;
    sc_td.querySelector(".denominator").setAttribute("onclick", denominatorOnClick);
return sc_td;
}


function clearDenominatorTd(sc_td){
    let numeratorOnClick = sc_td.querySelector(".numerator").getAttribute("onclick");
    let numerator = sc_td.querySelector(".numerator").innerHTML;
    sc_td.innerHTML = '';
    sc_td.innerHTML += `<div class="numerator"></div>
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
</div>`;

    sc_td.querySelector(".numerator").innerHTML += numerator;
    sc_td.querySelector(".numerator").setAttribute("onclick", numeratorOnClick);
    return sc_td;
}

function getTextInBrackets(numeratorText){
    let init = numeratorText.indexOf('(');
    let fin = numeratorText.indexOf(')');
    return numeratorText.substr(init+1,fin-init-1);
}


function fillDataToCell(partOfCell, period){
    partOfCell.querySelector(".class-name").innerText = '';
    partOfCell.querySelector(".subject").innerText = '';
    partOfCell.querySelector(".group").innerText = '';
    let indexCell = partOfCell.parentElement.cellIndex;
    let indexRow = partOfCell.parentElement.parentElement.rowIndex;
    console.log(indexRow);
    let sc_class = document.getElementById("school-class").options[document.getElementById("school-class").selectedIndex].getAttribute("class_name");
    let subject = document.getElementById("subject").value;
    if (window.getComputedStyle(document.querySelector(".school-class-group")).display === "block") {
        partOfCell.querySelector(".class-name").innerText = sc_class + ', ';
        partOfCell.querySelector(".group").innerText = 'п.' + document.getElementById("school-class-group").options[document.getElementById("school-class-group").selectedIndex].getAttribute("class_group_name");
    } else {
        partOfCell.querySelector(".class-name").innerText = sc_class;
    }
    partOfCell.querySelector(".subject").innerText = subject;
    let str = `showEditForm('${period}', ${indexCell-1},${indexRow-1},'${subject}')`;
    partOfCell.setAttribute('onclick', `${str}`);
    partOfCell.querySelector(".addition").querySelector(".make-admin").setAttribute("onclick", str);
    document.getElementById("modalFour").style.display = "none";
}

function checkPeriodSelect(){
    return document.getElementById("period").value;
}


document.getElementById("subject").addEventListener("change", async () => {
    document.querySelector(".school-class-group").style.display = "none";
    document.getElementById("school-class").innerHTML ='<option disabled selected>Оберіть клас</option>';
    document.getElementById("school-class-group").innerHTML ='<option disabled selected>Оберіть групу</option>';
    let schoolClasses = await getAllClass().then(res => {
        return res;
    });
    fillClassesSelect(schoolClasses)
    document.querySelector(".school-class").style.display = "block";

})

function fillClassesSelect(schoolClasses){
    let classSelect = document.getElementById("school-class");
    for (let i = 0; i < schoolClasses.length; i++) {
        classSelect.innerHTML += `<option value='${schoolClasses[i].id}' class_name = '${schoolClasses[i].nameOfClass}'>${schoolClasses[i].nameOfClass}</option>`;
    }
}
async function getAllClass(){
    return fetch("http://localhost:8080/allclasses").then(res => res.json())
        .then((json) => {
            return json
        });
}


document.getElementById("school-class").addEventListener("change", async () => {
    document.getElementById("school-class-group").innerHTML ='<option disabled selected>Оберіть групу</option>';
    let classId = document.getElementById("school-class").value;
    let subjectName = document.getElementById("subject").value;
    let schoolClassesGroups = await getAllSubGroup(classId,subjectName).then(res => {
        return res;
    });
    if (schoolClassesGroups.length > 0){
        fillSubGroupsSelect(schoolClassesGroups);
        document.querySelector(".school-class-group").style.display = "block";
    }
    else {
        document.querySelector(".save-schedule-plan").removeAttribute("disabled");
    }
})

function fillSubGroupsSelect(subGroups){
    let groupSelect = document.getElementById("school-class-group");
    for (let i = 0; i < subGroups.length; i++) {
        groupSelect.innerHTML += `<option value='${subGroups[i].id}' class_group_name="${subGroups[i].numberOfGroup}">${subGroups[i].numberOfGroup}</option>`;
    }
}
async function getAllSubGroup(classId, subjectName){
    let sub_groups;
    return sub_groups = fetch("http://localhost:8080/allsubgroups?classId=" + classId + "&subjectName=" + subjectName)
        .then(res => res.json())
        .then((json) => {
            return json
        });
}




document.getElementById("school-class-group").addEventListener("change", ()=>{
    document.querySelector(".save-schedule-plan").removeAttribute("disabled");
})


async function getAllTeacher(){
    return fetch("http://localhost:8080/allteachers").then(res => res.json())
        .then((json) => {
            return json
        });
}



window.onclick = function(event) {
    if (event.target.className === "modal") {
        event.target.style.display = "none";
    }
    if (!event.target.classList.contains("fa-ellipsis") && !event.target.classList.contains("make-admin") &&
        !event.target.classList.contains("edit-subjects")) {
        for (let i = 0; i < document.querySelectorAll(".dropdown-content").length; i++) {
            document.querySelectorAll(".dropdown-content")[i].style.display = "none";
        }
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
}



/*==================================РЕДАГУВАННЯ=========================================*/

async function showEditForm(period,dayOfWeekNumber, lessonNumber, subject){
    event.stopPropagation();
    let el = event.target;
    let parentEl;
    if (el.classList.contains("numerator") || el.classList.contains("denominator")) {
        el.querySelector('.addition').style.display = "block";
    } else {
        while (true) {
            parentEl = el.parentElement;
            el = parentEl;
            if (parentEl.classList.contains("numerator") || parentEl.classList.contains("denominator")) {
                break;
            }
        }
    }
    if (el.classList.contains("teacher-row")){
        document.querySelector(".save-schedule-plan").setAttribute("raw",el.rowIndex);
    }
    else if (el.classList.contains("schedule")){
        document.querySelector(".save-schedule-plan").setAttribute("col",el.cellIndex);
    }
    else{
        let i = 0;
        let parentEl1;
        while (true){
            parentEl1 = el.parentElement;
            el = parentEl1;
            if (parentEl1.classList.contains("teacher-row")){
                el = parentEl1;
                document.querySelector(".save-schedule-plan").setAttribute("raw",el.rowIndex-1);
                i++;
            }
            else if (parentEl1.classList.contains("schedule")){
                el = parentEl1;
                document.querySelector(".save-schedule-plan").setAttribute("day_of_week",el.cellIndex);
                i++;
            }
            if (i === 2){
                break;
            }
        }
    }

    await fillSelects(period,dayOfWeekNumber, lessonNumber, subject);
    let str;
    document.querySelector(".save-schedule-plan").setAttribute("edit", 'edit');
    let scClass = document.getElementById("school-class").value;
    let classGroup = document.getElementById("school-class-group").value;
    let teacher = document.getElementById("schools__teacher").value;
    if (classGroup !== "Оберіть групу"){
        str = `${teacher}, '${subject}',${scClass},${classGroup},${getDay(dayOfWeekNumber)},${lessonNumber+1},${period}`;
    }
    else {
        str = `${teacher}, ${subject},${scClass},'${null}',${getDay(dayOfWeekNumber)},${lessonNumber+1},${period}`;
    }

    document.querySelector(".save-schedule-plan").setAttribute("deleteObj", str);


}

async function fillSelects(period, dayOfWeekNumber, lessonNumber, subject) {

    document.getElementById("subject").innerHTML = `<option disabled selected>Оберіть предмет</option>`
    document.getElementById("school-class").innerHTML = `<option disabled selected>Оберіть клас</option>`
    document.getElementById("school-class-group").innerHTML = `<option disabled selected>Оберіть групу</option>`
    document.getElementById("period").innerHTML ='<option value="Кожен тиждень" selected>Кожен тиждень</option> <option value="Чисельник">Чисельник</option>' +
        '<option value="Знаменник">Знаменник</option>';

    let tr = document.querySelectorAll(".teacher-row")[lessonNumber];
    let td = tr.querySelectorAll('.schedule')[dayOfWeekNumber];
    let elem;

    if (period === "Чисельник") {
        elem = td.querySelector('.numerator');
    } else if (period === "Знаменник") {
        elem = td.querySelector('.denominator');
    } else {
        elem = td.querySelector('.denominator');
    }

    await fillSubjectSelect(elem);
    fillPeriodSelect(period);
    await fillClassSelect(elem);
    await fillGroupSelect(elem);
    document.querySelector(".save-schedule-plan").innerText = 'Редагувати';
    document.querySelector(".teacher-name").innerText = document.getElementById("schools__teacher").options[document.getElementById("schools__teacher").selectedIndex].getAttribute("teacher_name");
    document.getElementById("school-class").parentElement.style.display = "block";
    document.getElementById("modalFour").style.display = "block";
}


async function fillSubjectSelect(elem) {
    let teacherId = document.getElementById("schools__teacher").value;
    let teacherSubjects = await getAllTeacherSubject(teacherId).then(res => {
        return res;
    });
    let subjectsName = [];
    for (let i = 0; i < teacherSubjects.length; i++) {
        subjectsName.push(teacherSubjects[i].name);
    }
    fillSubjects(subjectsName);
    for (let i = 0; i < document.getElementById("subject").options.length; i++) {
        if (document.getElementById("subject").options[i].innerText === elem.querySelector(".subject").innerText) {
            document.getElementById("subject").options[i].setAttribute("selected", 'selected');
        }
    }
}

function fillPeriodSelect(period){
    let periodSelect = document.getElementById("period");
    periodSelect.innerHTML = `<option value="${period}">${period}</option>`;
}

async function fillClassSelect(elem) {
    let schoolClasses = await getAllClass().then(res => {
        return res;
    });
    fillClassesSelect(schoolClasses);
    for (let i = 0; i < document.getElementById("school-class").options.length; i++) {
        if (document.getElementById("school-class").options[i].innerText === elem.querySelector(".class-name").innerText.substring(0,3)) {
            document.getElementById("school-class").options[i].setAttribute("selected", 'selected');
        }
        else{
            document.getElementById("school-class").options[i].removeAttribute("selected");
        }
    }
}

async function fillGroupSelect(elem){
    let classId = document.getElementById("school-class").value;
    let subjectName = document.getElementById("subject").value;
    let schoolClassesGroups = await getAllSubGroup(classId,subjectName).then(res => {
        return res;
    });
    if (schoolClassesGroups.length > 0) {
        fillSubGroupsSelect(schoolClassesGroups);
        document.querySelector(".school-class-group").style.display = "block";


        for (let i = 0; i < document.getElementById("school-class-group").options.length; i++) {
            if (document.getElementById("school-class-group").options[i].innerText === elem.querySelector(".group").innerText.substring(2)) {
                document.getElementById("school-class-group").options[i].setAttribute("selected", 'selected');
            } else {
                document.getElementById("school-class-group").options[i].removeAttribute("selected");
            }
        }
    }
    else {
        document.querySelector(".save-schedule-plan").removeAttribute("disabled");
    }
}

function editTeacherSchedule(formData, sc_td) {
    let str = document.querySelector(".save-schedule-plan").getAttribute("deleteObj").split(',');
    let oldObj = {
        teacher: str[0],
        subject: str[1].trim().replaceAll("'", ''),
        schoolClass: str[2],
        classGroup: str[3],
        dayOfWeek: str[4].trim(),
        lessonNumber: str[5],
        period: str[6]
    };
    formData.append("oldObject", JSON.stringify(oldObj));
    saveSchedule(formData, sc_td, 'edit-teacher-schedule');
}


/*==================Видалити розклад==========================*/

document.querySelector(".schedule-table-wrapper").addEventListener('contextmenu',(event)=>{
    event.preventDefault()
});
function openContMenu(){
    let parentEl;
    let deleteBtn;
    let indexCell;
    let indexRow
    let el = event.target;
    if (el.classList.contains("teacher-row")){
        el.querySelector('.addition').style.display = "block";
    }
    else if (el.classList.contains("schedule")){
         indexCell= el.cellIndex;
         indexRow= el.parentElement.rowIndex;
    }
    else{
        while (true){
            parentEl = el.parentElement;
            el = parentEl;
            if (parentEl.classList.contains("numerator") || parentEl.classList.contains("denominator")){
                el = parentEl;
                break;
            }
        }
        indexCell = el.parentElement.cellIndex;
        indexRow = el.parentElement.parentElement.rowIndex;
    }

    let deleteData = el.querySelector('.addition').querySelector('.make-admin').getAttribute('onclick');
    let deleteD = getTextInBrackets(deleteData).split(',');
    let period = deleteD[0];
    let lessonNumber = deleteD[2];
    let dayOfWeek = deleteD[1];
    let subject = deleteD[3];
    let teacher = document.getElementById("schools__teacher").value;
    let scClass = el.querySelector(".class-name").innerText.substring(0,3);
    let classGroup = null;
    if (el.querySelector('.group').innerText.substring(2) !== ''){
        classGroup = el.querySelector('.group').innerText.substring(2);
    }

    let deleteObj = `${teacher}, ${subject}, ${scClass},${classGroup},${getDay(parseInt(dayOfWeek))},${parseInt(lessonNumber)+1},${period}`;

    document.querySelector(".deletebtn").setAttribute("deleteObj", deleteObj);
    document.querySelector(".deletebtn").setAttribute("day_of_week", indexCell-1);
    document.querySelector(".deletebtn").setAttribute("lessonNum", indexRow-1);
    document.getElementById("delete-modal").style.display = "block";
}


document.querySelector(".deletebtn").addEventListener("click", ()=>{
    let tr = document.querySelectorAll('.teacher-row')[document.querySelector(".deletebtn").getAttribute("lessonNum")];
    let td = tr.querySelectorAll('.schedule')[document.querySelector(".deletebtn").getAttribute("day_of_week")];
    let deleteData = document.querySelector(".deletebtn").getAttribute("deleteObj");
    let period = deleteData.split(",")[6].replace(/[']/g, '').trim();
    let lessonNumber = deleteData.split(",")[5].trim();
    let dayOfWeek = deleteData.split(",")[4].replace(/[']/g, '').trim();
    let subject = deleteData.split(",")[1].replace(/[']/g, '').trim();
    let teacher = deleteData.split(",")[0].trim();
    let scClass = deleteData.split(",")[2].replace(/[']/g, '').trim();
    let classGroup = deleteData.split(",")[3].trim();

    let deleteObj = {teacher:teacher,subject:subject,schoolClass:scClass, classGroup: classGroup, dayOfWeek:dayOfWeek, lessonNumber:lessonNumber, period: period};
    const formData = new FormData();
    formData.append("jsonData", JSON.stringify(deleteObj));
    deleteSchedule(td, formData)

});


async function deleteSchedule(td, formData){
    let res = await fetch("http://localhost:8080/delete-teacher-schedule", {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData,
    }).then(response=>{
        if (response.ok){
            clearTd(td);
            document.querySelector("#delete-modal").style.display = "none";
        }
        else {
            alert("Журнал з даним предметом вже містить виставлені оцінки!!!");
        }
    });

}