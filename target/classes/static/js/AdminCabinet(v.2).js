function getJournal(){
    let select = document.querySelector(".class-teacher").value;
    window.location.href = "http://localhost:8080/attendance-record/" + select.split('.')[1];
}

function getInstruction(){
    let select = document.querySelector(".class-teacher").value;
    window.location.href = "http://localhost:8080/instructions/" + select;
}

function getMotionTable(){
    let select = document.querySelector(".class-teacher").value;
    window.location.href = "http://localhost:8080/motion-table/" + select;
}

window.onload = function (){
    let selects = document.querySelectorAll("select");
    for (let i = 0; i < selects.length; i++) {
        selects[i].value = selects[i].children[0].innerText;
    }
}


async function getSubjects() {
    let classId = document.querySelector(".class-select").value;
    let subjectSelect = document.querySelector(".subject-select")
    subjectSelect.innerHTML = `<option selected disabled>Оберіть предмет</option>`;
    let subjects = await getSubjectsByClass(classId).then(res => {
        return res;
    });
    for (let i = 0; i < subjects.length; i++) {
        subjectSelect.innerHTML += `<option value="${subjects[i]}">${subjects[i]}</option>`
    }
    subjectSelect.removeAttribute("disabled");
    document.querySelector(".class-group").style.display = "none";

}


async function getSubjectsByClass(classId){
    return fetch("http://localhost:8080/admin-cabinet-subjects/" + classId, {}).then(res => res.json())
        .then((json) => {
            return json
        });
}


async function getGroup() {
    let classId = document.querySelector(".class-select").value;
    let subject = document.querySelector(".subject-select").value;
    let classJournals = await getJournalByClassAndSubjectName(classId, subject).then(res => {
        return res;
    });
    if (classJournals.length > 1){
       let groupSelect = document.querySelector(".group-select");
       groupSelect.innerHTML = `<option selected disabled>Оберіть групу</option>`;
        for (let i = 0; i < classJournals.length; i++) {
            groupSelect.innerHTML += `<option>${classJournals[i].schedule.classGroup.numberOfGroup}</option>`;
        }
        document.querySelector(".class-group").style.display = "block";
    }
    else {
        window.location.href = "http://localhost:8080/journal/" + classJournals[0].id;
    }
}

async function getSubjectJournal() {
    let classId = document.querySelector(".class-select").value;
    let subject = document.querySelector(".subject-select").value;
    let group = document.querySelector(".group-select").value;
    let classJournal = await getJournalByClassAndSubjectNameAndGroup(classId, subject,group).then(res => {
        return res;
    });

    window.location.href = "http://localhost:8080/journal/" + classJournal[0].id;


}

async function getJournalByClassAndSubjectName(classId, subject){
    return fetch("http://localhost:8080/admin-cabinet-subject-journal/?classId=" + classId + "&subject=" + subject, {}).then(res => res.json())
        .then((json) => {
            return json
        });
}

async function getJournalByClassAndSubjectNameAndGroup(classId, subject, group){
    return fetch("http://localhost:8080/admin-cabinet-subject-journal-group/?classId=" + classId + "&subject=" + subject + "&group=" + group, {}).then(res => res.json())
        .then((json) => {
            return json
        });
}