let color = document.querySelectorAll(".color");
const currentYear = new Date().getFullYear();



let addEventBtn = document.querySelectorAll(".add-event");
let input = document.getElementById("name-input");
let saveEventBtn = document.querySelector(".save-event");
let period = document.getElementById("date-period");

for (let i = 0; i < addEventBtn.length; i++) {
    addEventBtn[i].addEventListener("click", ()=>{
        if (document.getElementById("content").getAttribute("role") === "YES") {

            saveEventBtn.removeAttribute("editEventId");
            clearColorInput()
            initializeDatePicker()
            saveEventBtn.removeAttribute("disabled");
            input.value = "";
            period.value = ""
            input.parentElement.removeAttribute("data-error");
            period.parentElement.removeAttribute("date-error");
            switch (i) {
                case 0:
                    document.querySelector(".add-column-text").innerText = "Канікули";
                    break;
                case 1:
                    document.querySelector(".add-column-text").innerText = "Шкільні події";
                    break;
                case 2:
                    document.querySelector(".add-column-text").innerText = "Державні свята";
                    break;
            }
            document.body.style.overflow = "hidden";
            document.getElementById("modalFive").style.display = "block";
        }
    })

}

document.getElementById("date-period").addEventListener("input", ()=>{
    if (document.getElementById("date-period").value === ""){
        document.querySelectorAll(".air-datepicker-cell").forEach(el =>{
            el.classList.remove("-selected-");
            el.classList.remove("-range-from-");
            el.classList.remove("-range-to-");
            el.classList.remove("-in-range-");
        })
    }


})



saveEventBtn.addEventListener("click", ()=>{
    if (input.value === ""){
        input.parentElement.setAttribute("data-error", "Поле не може бути порожнім");
        saveEventBtn.setAttribute("disabled","disabled");
    }
    else if (period.value === ""){
        period.parentElement.setAttribute("date-error", "Оберіть дату");
    }
    else if (!checkIfColorIsSelect()){
        alert("Оберіть колір події!!!");
    }
    else if (!saveEventBtn.hasAttribute("editEventId")){
        const formData = new FormData();
        let colorCode;
        for (let i = 0; i < color.length; i++) {
            if (color[i].checked ===true) {
                colorCode = window.getComputedStyle(color[i]).backgroundColor;
                break;
            }
        }
        const obj = {id:'',eventType:document.querySelector(".add-column-text").innerText,
                    eventName: input.value, dateOfStartEvent: period.value.split('-')[0],
                    dateOfFinishEvent:period.value.split('-')[1], eventColor: colorCode};

        formData.append("jsonData", JSON.stringify(obj));
        saveEvent(formData, "addschoolcalendarevents");
    }
    else {
        const formData = new FormData();
        let colorCode;
        for (let i = 0; i < color.length; i++) {
            if (color[i].checked === true) {
                colorCode = window.getComputedStyle(color[i]).backgroundColor;
                break;
            }
        }
        const obj = {id:saveEventBtn.getAttribute("editEventId"),eventType:document.querySelector(".add-column-text").innerText,
            eventName: input.value, dateOfStartEvent: period.value.split('-')[0],
            dateOfFinishEvent:period.value.split('-')[1], eventColor: colorCode};

        formData.append("jsonData", JSON.stringify(obj));
        saveEvent(formData, "updateevent");
    }
})


async function saveEvent(formData, request){
    let res = await fetch("http://localhost:8080/" + request, {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData,
    }).then(response=>{
        if (response.ok){
            window.location.href = "http://localhost:8080/school-calendar";
        }
        else {
            alert("Помилка");
        }
    });
}

window.onload = async function () {
    let events = await getSchoolEvents().then(res => {
        return res;
    });

    fillCalendar(events);


}

function fillCalendar(events) {
    let dataSource = [];
    for (let i = 0; i < events.length; i++) {
        let startDateDay = parseInt(events[i].dateOfStartEvent.split('.')[0]);
        let startDateMonth = parseInt(events[i].dateOfStartEvent.split('.')[1]);
        if (events[i].dateOfFinishEvent === null) {
            dataSource.push({
                color: events[i].eventColor,
                startDate: new Date(currentYear, startDateMonth - 1, startDateDay),
                endDate: new Date(currentYear, startDateMonth - 1, startDateDay)
            });
        } else {
            let finishDateDay = parseInt(events[i].dateOfFinishEvent.split('.')[0]);
            let finishDateMonth = parseInt(events[i].dateOfFinishEvent.split('.')[1]);
            dataSource.push({
                color: events[i].eventColor,
                startDate: new Date(currentYear, startDateMonth - 1, startDateDay),
                endDate: new Date(currentYear, finishDateMonth - 1, finishDateDay)
            });

        }
        initializeCalendar(dataSource);
    }
}

async function getSchoolEvents(){
    return fetch("http://localhost:8080/school-events").then(res => res.json())
        .then((json) => {
            return json
        });
}

function checkIfColorIsSelect(){
    for (let i = 0; i < color.length; i++) {
        if (color[i].checked === true){
            return true;
        }
    }
    return false;
}


input.addEventListener("input", ()=>{
    input.parentElement.removeAttribute("data-error");
    saveEventBtn.removeAttribute("disabled");
})


period.addEventListener("click", ()=>{
    period.parentElement.removeAttribute("date-error");
    saveEventBtn.removeAttribute("disabled");
})




for (let i = 0; i < color.length; i++) {
    color[i].addEventListener("change", ()=>{
        clearColorInput();
        document.querySelectorAll(".cont")[i].style.border = "2px solid rgb(0 0 0)";
        color[i].checked = true;
    })

}

function clearColorInput(){
    for (let i = 0; i < color.length; i++) {
        document.querySelectorAll(".cont")[i].style.border = "none";
            color[i].checked = false;
    }
}


/*---------------------------Канікули---------------------------------------*/


let holidaysEditBtn = document.querySelectorAll(".holidays-update");
let holidaysDeleteBtn = document.querySelectorAll(".holidays-delete");

let holidaysEventName = document.querySelectorAll(".holidays-dates-name");
let holidaysEventStart = document.querySelectorAll(".holidays-dates-start");
let holidaysEventFinish = document.querySelectorAll(".holidays-dates-finish");


for (let i = 0; i < holidaysEditBtn.length; i++) {
    holidaysEditBtn[i].addEventListener("click", ()=>{
        if (document.getElementById("content").getAttribute("role") === "YES") {
            clearColorInput()
            let colorCode = document.querySelectorAll(".holidays-dates-color")[i].style.background;
            for (let i = 0; i < color.length; i++) {
                if (window.getComputedStyle(color[i]).backgroundColor === colorCode) {
                    color[i].parentElement.style.border = "2px solid rgb(0 0 0)";
                    color[i].checked = true;
                }
            }
            saveEventBtn.removeAttribute("editEventId");
            saveEventBtn.setAttribute("editEventId", document.querySelectorAll(".holidays-name")[i].getAttribute("id"));
            input.parentElement.removeAttribute("data-error");
            period.parentElement.removeAttribute("date-error");
            initializeDatePicker();
            document.querySelector(".add-column-text").innerText = "Канікули";


            saveBtn.removeAttribute("elem-name");
            saveBtn.setAttribute("elem-name", holidaysEventName[i].innerText)
            document.body.style.overflow = "hidden";
            document.getElementById("modalFive").style.display = "block";
            input.value = holidaysEventName[i].innerText;
            period.value = holidaysEventStart[i].innerText + " - " + holidaysEventFinish[i].innerText;
        }
    });

}


function initializeCalendar(dataSource){
    let calendar= new Calendar('#calendar', {
        style:'background',
        dataSource: dataSource,
        response:true,
    });
    calendar.setLanguage('ua');

}


for (let i = 0; i < holidaysDeleteBtn.length; i++) {
    holidaysDeleteBtn[i].addEventListener("click", ()=>{
        if (document.getElementById("content").getAttribute("role") === "YES") {
            deleteBtn.setAttribute("eventId", document.querySelectorAll(".holidays-name")[i].getAttribute("id"));
            document.body.style.overflow = "hidden";
            document.getElementById("modalSix").style.display = "block";
        }
    })
}

/*---------------------------Канікули---------------------------------------*/

/*---------------------------Шкільні події---------------------------------------*/

let schoolEventEditBtn = document.querySelectorAll(".school-events-edit");
let schoolEventDeleteBtn = document.querySelectorAll(".school-events-delete");

let schoolEventName = document.querySelectorAll(".school-events-dates-name");
let schoolEventStart = document.querySelectorAll(".school-events-dates-start");
let schoolEventFinish = document.querySelectorAll(".school-events-dates-finish");

let deleteBtn = document.getElementById("delete-event");
let saveBtn = document.getElementById("save-events");

for (let i = 0; i < schoolEventEditBtn.length; i++) {
    schoolEventEditBtn[i].addEventListener("click", ()=>{
        if (document.getElementById("content").getAttribute("role") === "YES") {
            clearColorInput()
            let colorCode = document.querySelectorAll(".school-events-dates-color")[i].style.background;
            for (let i = 0; i < color.length; i++) {
                if (window.getComputedStyle(color[i]).backgroundColor === colorCode) {
                    color[i].parentElement.style.border = "2px solid rgb(0 0 0)";
                    color[i].checked = true;
                }
            }
            saveEventBtn.removeAttribute("editEventId");
            saveEventBtn.setAttribute("editEventId", document.querySelectorAll(".school-events-name")[i].getAttribute("id"));
            initializeDatePicker();
            input.parentElement.removeAttribute("data-error");
            period.parentElement.removeAttribute("date-error");
            document.querySelector(".add-column-text").innerText = "Шкільні події";
            saveBtn.removeAttribute("elem-name");
            saveBtn.setAttribute("elem-name", schoolEventName[i].innerText)
            document.body.style.overflow = "hidden";
            document.getElementById("modalFive").style.display = "block";
            input.value = schoolEventName[i].innerText;
            if (schoolEventFinish[i] !== undefined) {
                period.value = schoolEventStart[i].innerText + " - " + schoolEventFinish[i].innerText;
            } else {
                period.value = schoolEventStart[i].innerText;
            }
        }

    });
}

for (let i = 0; i < schoolEventDeleteBtn.length; i++) {
    schoolEventDeleteBtn[i].addEventListener("click", ()=>{
        if (document.getElementById("content").getAttribute("role") === "YES") {

            deleteBtn.setAttribute("eventId", document.querySelectorAll(".school-events-name")[i].getAttribute("id"));
            document.body.style.overflow = "hidden";
            document.getElementById("modalSix").style.display = "block";
        }
    });
}


/*---------------------------Шкільні події---------------------------------------*/


/*---------------------------Державні вихідні---------------------------------------*/

let publicHolidayEditBtn = document.querySelectorAll(".public-holiday-edit");
let publicHolidayDeleteBtn = document.querySelectorAll(".public-holiday-delete");

let publicHolidayName = document.querySelectorAll(".public-holiday-dates-name");
let publicHolidayStart = document.querySelectorAll(".public-holiday-dates-start");
let publicHolidayFinish = document.querySelectorAll(".public-holiday-dates-finish");


for (let i = 0; i < publicHolidayEditBtn.length; i++) {
    publicHolidayEditBtn[i].addEventListener("click", ()=>{
        if (document.getElementById("content").getAttribute("role") === "YES") {
            clearColorInput()
            let colorCode = document.querySelectorAll(".public-holiday-dates-color")[i].style.background;
            for (let i = 0; i < color.length; i++) {
                if (window.getComputedStyle(color[i]).backgroundColor === colorCode) {
                    color[i].parentElement.style.border = "2px solid rgb(0 0 0)";
                    color[i].checked = true;
                }
            }
            saveEventBtn.removeAttribute("editEventId");
            saveEventBtn.setAttribute("editEventId", document.querySelectorAll(".public-holiday-name")[i].getAttribute("id"));
            input.parentElement.removeAttribute("data-error");
            period.parentElement.removeAttribute("date-error");

            initializeDatePicker();
            document.querySelector(".add-column-text").innerText = "Державні вихідні";
            saveBtn.removeAttribute("elem-name");
            saveBtn.setAttribute("elem-name", publicHolidayName[i].innerText)
            document.body.style.overflow = "hidden";
            document.getElementById("modalFive").style.display = "block";
            input.value = publicHolidayName[i].innerText;
            if (publicHolidayFinish[i] !== undefined) {
                period.value = publicHolidayStart[i].innerText + " - " + publicHolidayFinish[i].innerText;
            } else {
                period.value = publicHolidayStart[i].innerText;
            }
        }

    });
}

for (let i = 0; i < publicHolidayDeleteBtn.length; i++) {
    publicHolidayDeleteBtn[i].addEventListener("click", ()=>{
        if (document.getElementById("content").getAttribute("role") === "YES") {
            deleteBtn.setAttribute("eventId", document.querySelectorAll(".public-holiday-name")[i].getAttribute("id"));
            document.body.style.overflow = "hidden";
            document.getElementById("modalSix").style.display = "block";
        }


    })
}

/*---------------------------Державні вихідні---------------------------------------*/
let cancelBt = document.querySelector(".cancelbtn");

cancelBt.addEventListener("click", ()=>{
    document.getElementById("modalSix").style.display = "none";
})

deleteBtn.addEventListener("click", ()=>{
    let eventId = document.querySelector(".deletebtn").getAttribute("eventId");
    deleteEvent(eventId);
});

async function deleteEvent(eventId){
    let res = await fetch("http://localhost:8080/school-event?eventId=" + eventId, {
        method: 'DELETE',
        headers: {
            Accept: 'application/json',
        }
    }).then(response=>{
        if (response.ok){
            window.location.href = "http://localhost:8080/school-calendar";
        }
        else {
            alert("Помилка");
        }
    });
}

function initializeDatePicker() {
    new AirDatepicker('#date-period', {
        inline: false,
        position: 'top center',
        locale: localeEs,
        range: true,
        multipleDatesSeparator: ' - '
    });
}
