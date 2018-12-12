function welcomeMsg(msg) {
    alert(msg);
}

function message(msg) {
    document.getElementById('output').innerHTML = msg + " event";
}

function message2(msg) {
    document.getElementById('clickable').innerText = msg + " event";
}

function displayDate() {
    document.getElementById("demo").innerHTML = Date();
}

function closeMe() {
    // Find the element
    var x = document.getElementById("demo");
    // Option 1: Change the style attribute directly
    x.style.display = "none";

    // Option 2: Change the class
    //x.className = "closed";
}

function openMe() {
    // Find the element
    var x = document.getElementById("demo");
    // Option 1: Change the style attribute directly
    x.style.display = "block";
    x.style.backgroundImage = "";
    // Option 2: Change the class
    //x.className = 'open';
}

function displayID(element) {
    console.log(element.id);
}

function showProperties(element) {
    document.getElementById('message').innerHTML = element.alt;
    document.getElementById('message').style.backgroundImage = "url(" + element.src + ")";
}