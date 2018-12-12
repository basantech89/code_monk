function check() {
    var email1 = document.getElementById("email_addr");
    var email12 = document.getElementById("email_repeat");
    if (email1.value != email12.value) {
        alert("the emails must match");
        return false;
    }
}

function nicknameFunction() {
    if (document.getElementById("yesNick").checked) {
        document.getElementById("nick").style.display = "inline";
        document.getElementById("nickname").setAttribute('required', "");
    }
    else {
        document.getElementById("nickname").removeAttribute('required');
        document.getElementById("nick").style.display = 'none';
    }
}