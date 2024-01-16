document.querySelector(".messages").addEventListener("click", async () => {
    document.querySelector(".circle").style.background = 'none';
    document.querySelector(".note-form-wrap").classList.toggle("show");
    document.querySelector(".note-items").innerHTML ='';
    if (document.querySelector(".note-form-wrap").classList.contains("show")){
        getMes();
    }
})


getViewedMes();


 setInterval(()=>getViewedMes(), 60000);


async function getMes(){
    let allMes = await getAllMes().then(res => {
        return res;
    });

    if (allMes.length > 0){
        getNewMes(allMes);
    }
}

async function getViewedMes(){
    let allMes = await getAllViewedMes().then(res => {
        return res;
    });
    let viewMes = [];
    for (let i = 0; i < allMes.length; i++) {
        if (allMes[i].status === "Нове"){
            viewMes.push(allMes[i]);
        }
    }
    if (viewMes.length !== 0){
        document.querySelector(".circle").innerText = viewMes.length;
        document.querySelector(".circle").style.background = "#ff6a69";
    }
}


function getNewMes(allMes){
    for (let i = 0; i < allMes.length; i++) {
        document.querySelector(".note-items").innerHTML +=`<div id="${allMes[i].id}" class="notification">
      <div class="body">
        <div class="message" id="${allMes[i].id}" style="display: flex;font-size: 14px">${allMes[i].messageText}
          <i class="fa-solid fa-circle-minus" id="delete-i" onclick="deleteMessage(${allMes[i].id})"></i>
        </div>
        <div class="time">${allMes[i].localDateTime}</div>
      </div>
    </div>`;
    }

}


function deleteMessage(messageId){
    // let messageId = document.querySelectorAll(".message")[i].getAttribute("id");
    const obj = {id:messageId};
    const formData = new FormData();
    formData.append("jsonData", JSON.stringify(obj));
    deleteMesFromDB(formData,messageId);
}

async function deleteMesFromDB(formData, messageId){
    let res = await fetch("http://localhost:8080/deletemessage", {
        method: 'POST',
        headers: {
            Accept: 'application/json',
        },
        body: formData,
    }).then(response=>{
        if (response.ok){
            let message = FindByAttributeValue("id", `${messageId}`)
            document.querySelector(".note-items").removeChild(message);
            // getAllMes();
        }
        else {
            alert("Помилка");
        }
    });

}

function FindByAttributeValue(attribute, value)    {
    let All = document.querySelectorAll('.notification');
    for (let i = 0; i < All.length; i++){
        if (All[i].getAttribute(attribute) === value) {
            return All[i];
        }
    }
}



async function getAllMes(){
    return fetch("http://localhost:8080/messages").then(res => res.json())
        .then((json) => {
            return json
        });
}

async function getAllViewedMes(){
    return fetch("http://localhost:8080/viewedmessages").then(res => res.json())
        .then((json) => {
            return json
        });
}

window.onscroll=function (){
    document.querySelector(".note-form-wrap").classList.remove("show");
}