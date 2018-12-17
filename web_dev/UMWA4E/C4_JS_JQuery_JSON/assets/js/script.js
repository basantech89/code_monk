function doValidate() {
    console.log("Validating the user input in login form");
    let em;
    let pw;
    try {
        em = document.getElementById('email').value;
        pw = document.getElementById('pass').value;
        console.log("Validating the password " + pw + "\nand email " + em);

        if (pw == null || pw === "" || em == null || em === "") {
            alert("Both fields must be filled out");
            return false;
        }

        else if (em.indexOf('@') < 0) {
            alert("Email address must contain @");
            return false;
        }
        console.log("Validated data successfully")
        return true;
    }

    catch (e) {
        return false;
    }
}