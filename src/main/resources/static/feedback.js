function initForm() {
    document.getElementById('success').style.display = 'none';
    document.getElementById('error').style.display = 'none';
    document.getElementById("form").addEventListener("submit", function (event) {
        event.preventDefault();
        sendData();
    });
}

function initTable() {
    document.getElementsByName("typeRadio")
        .forEach(el => el.addEventListener("change", fillTable));
    document.getElementsByName("sortRadio")
        .forEach(el => el.addEventListener("change", fillTable));
    fillTable();
}

function sendData() {
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();
    let contactType;
    const radios = document.getElementsByName('typeRadio');
    for (let i = 0, length = radios.length; i < length; i++) {
        if (radios[i].checked) {
            contactType = radios[i].value;
            break;
        }
    }
    const message = document.getElementById("message").value.trim();
    const xhr = new XMLHttpRequest();   // new HttpRequest instance
    const url = "/feedback/add";
    xhr.open("POST", url);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.onreadystatechange = function () {
        document.getElementById("spinner").style.visibility = 'hidden';
        if ( xhr.status == 200 ) {
            document.getElementById('success').style.display = 'block';
            document.getElementById('submit').style.visibility = 'hidden';
        } else {
            document.getElementById('submit').disabled = false;
            document.getElementById('error').style.display = 'block';
        }
    }
    enterFormPendingState();
    xhr.send(JSON.stringify({name, email, contactType, message}));
}

function enterFormIdleState() {
    document.getElementById("spinner").style.visibility = 'hidden';
    document.getElementById('submit').disabled = false;
}

function enterFormPendingState() {
    document.getElementById("spinner").style.visibility = 'visible';
    document.getElementById('submit').disabled = true;
}

// --------------- Table parts

function fillTable() {
    let contactType;
    const radios = document.getElementsByName('typeRadio');
    for (let i = 0, length = radios.length; i < length; i++) {
        if (radios[i].checked) {
            contactType = radios[i].value;
            break;
        }
    }
    let dir;
    const radioSorts = document.getElementsByName('sortRadio');
    for (let i = 0, length = radioSorts.length; i < length; i++) {
        if (radioSorts[i].checked) {
            dir = radioSorts[i].value;
            break;
        }
    }
    const xhr = new XMLHttpRequest();   // new HttpRequest instance
    const url = "/feedbacks/search/findByContactType?contactType=" + contactType + "&sort=submissionDate," + dir;
    document.getElementById("spinner").style.visibility = 'visible';
    xhr.open("GET", url);
    xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    xhr.onreadystatechange = function () {
        document.getElementById("spinner").style.visibility = 'hidden';
        if (xhr.readyState == 4 && xhr.status == 200) {
            const response = JSON.parse(this.responseText);
            const feedbackArray = response._embedded.feedbacks;
            fillTbody(feedbackArray);
        }
    }
    xhr.send();
}

function fillTbody(feedbackArray) {
    const tbody = document.getElementById("tbody");
    tbody.innerHTML = '';
    feedbackArray.forEach(feedback => addRow(tbody, feedback));
}

function addRow(tbody, feedback) {
    const tr = document.createElement("tr");
    tbody.appendChild(tr);
    addCell(tr, feedback.id);
    addCell(tr, feedback.submissionDate);
    addCell(tr, feedback.name);
    addCell(tr, feedback.email);
    addCell(tr, feedback.message);

}

function addCell(parent, value) {
    const td = document.createElement("td");
    td.innerText = value;
    parent.appendChild(td);
}