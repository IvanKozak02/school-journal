document.querySelector(".view").addEventListener("click", ()=>{
    clearInputs();
    document.getElementById("addSchoolProfile").style.display = "block";
})

window.onclick = function (event) {
    if (event.target.className === "modal") {
        event.target.style.display = "none";
        document.body.style.overflow = "auto";
    }
};

document.querySelector(".save-year-structure").addEventListener("click", ()=>{
    const formData = new FormData();
    let firstSemStart = document.getElementById("first-sem-start").value;
    let firstSemFinish = document.getElementById("first-sem-finish").value;
    let secondSemStart = document.getElementById("second-sem-start").value;
    let secondSemFinish = document.getElementById("second-sem-finish").value;
    let firstSem = {semStart:firstSemStart, semFinish:firstSemFinish, nameOfSem: "I семестр"};
    let secondSem = {semStart:secondSemStart, semFinish:secondSemFinish, nameOfSem: "II семестр"};

    formData.append("firstSem",JSON.stringify(firstSem));
    formData.append("secondSem", JSON.stringify(secondSem));

    saveYearStructure(formData);
});

async function saveYearStructure(formData) {
    let res = await fetch("http://localhost:8080/year-structure", {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData,
    }).then(response => {
        if (response.ok) {
            window.location.href = "http://localhost:8080/settings";
        } else {
            alert("Помилка");
        }
    });
}


document.querySelector(".edit-structure").addEventListener("click", async () => {
    clearInputs();
    let structure = await getStructure().then(res => {
        return res;
    });

    fillAllInputs(structure);
    document.getElementById("addSchoolProfile").style.display = "block";
})

function fillAllInputs(structure){
    document.getElementById("first-sem-start").value = structure[0].startOfSem;
    document.getElementById("first-sem-finish").value = structure[0].finishOfSem;

    document.getElementById("second-sem-start").value = structure[1].startOfSem;
    document.getElementById("second-sem-finish").value = structure[1].finishOfSem;
}

function clearInputs(){
    document.getElementById("first-sem-start").value = '';
    document.getElementById("first-sem-finish").value = '';

    document.getElementById("second-sem-start").value = '';
    document.getElementById("second-sem-finish").value = '';
}

async function getStructure(){
    let structure;
    return structure = fetch("http://localhost:8080/structure").then(res => res.json())
        .then((json) => {
            return json
        });
}