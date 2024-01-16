async function findAllN(period) {
    let journalId = document.querySelector(".semesters").getAttribute("journal");
    let N = await getAllN(parseInt(journalId), period).then(res => {
        return res;
    });
    fillTableByN(N);
}

function fillTableByN(N){
    let table = document.getElementById("data");
    let tbody = table.getElementsByTagName("tbody")[0];
    tbody.innerHTML = '';
    for (let i = 0; i < N.length; i++) {
        tbody.innerHTML += `<tr class="student-row">
              <th class="student">${N[i].split("-")[3]}</th>
              <td class="inserted">${N[i].split("-")[1]}</td>
              <td class="inserted">${N[i].split("-")[2]}</td>
            </tr>`;
    }
}

async function getAllN(journalId, period){
    return fetch("http://localhost:8080/count-of-N/?journalId=" + journalId + "&time=" + period, {}).then(res => res.json())
        .then((json) => {
            return json
        });
}