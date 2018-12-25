<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/13/18
 * Time: 9:26 PM
 */

require_once "../assets/pdo.php";
require_once "util.php";
session_start();

if ( ! isset($_SESSION['name']) ) {
    die("Not Logged In");
}

// If the user cancel, go back to index.php
if ( isset($_POST['cancel']) ) {
    header('Location: index.php');
    return;
}

// Add Model
if ( isset($_POST['fname']) && isset($_POST['lname']) && isset($_POST['email'])
    && isset($_POST['headline']) && isset($_POST['summary']) && isset($_POST['add']) ) {

    $msg = validateProfile();
    if ( is_string($msg) ) {
        $_SESSION['error'] = $msg;
        header("Location: add.php");
        return;
    }

    // Validate position entries if present
    $msg = validatePos();
    if ( is_string($msg) ) {
        $_SESSION['error'] = $msg;
        header("Location: add.php");
        return;
    }

    $stmt = $pdo -> prepare('INSERT INTO Profile (user_id, first_name, last_name, 
              email, headline, summary) VALUES (:uid, :fname, :lname, :em, :headline, :summary)');
    $stmt -> execute(array(
            ':uid' => $_SESSION['user_id'],
            ':fname' => $_POST['fname'],
            ':lname' => $_POST['lname'],
            ':em' => $_POST['email'],
            ':headline' => $_POST['headline'],
            ':summary' => $_POST['summary']
    ));

    $profile_id = $pdo -> lastInsertId();
    insertPos($pdo, $profile_id);

    $_SESSION['success'] = "Record Added";
    header("Location: index.php");
    return;
}

?>

<?php
require_once "../assets/header.php";
require_once "../assets/bootstrap.php";
flashMessages();
?>

<body>
    <h1> Adding Profile for <?= htmlentities($_SESSION['name']); ?> </h1>
    <form method="post">
        <p>
            <label for="fname"> First Name: </label>
            <input type="text" id="fname" name="fname" size="40"> <br>
        </p>
        <p>
            <label for="lname"> Last Name: </label>
            <input type="text" id="lname" name="lname" size="40"> <br>
        </p>
        <p>
            <label for="email"> Email: </label>
            <input type="text" id="email" name="email"> <br>
        </p>
        <p>
            <label for="headline"> Headline: </label> <br>
            <input type="text" id="headline" name="headline" size="60"> <br>
        </p>
        <p>
            <label> Summary: <br>
            <textarea rows="8" cols="80" name="summary"> </textarea>
            </label> <br>
        </p>
        <p>
            Position: <input type="submit" id="addPos" value="+">
            <div id="position_fields"></div>
        </p>
        <input type="submit" value="Add" name="add">
        <input type="submit" value="Cancel" name="cancel">
    </form>

    <script type="text/javascript">
        countPos = 0;
        $(document).ready(function () {
            window.console && console.log('Document ready called');
            $('#addPos').click(function (event) {
                event.preventDefault();

                if (countPos >= 9) {
                    alert("Maximum of nine positions entries exceeded");
                    return;
                }

                countPos++;
                window.console && console.log("Adding position " + countPos);

                $('#position_fields').append(
                    '<div id="position' + countPos + '"> \
                <p> Year: <input type="text" name="year' + countPos + '" value="" /> \
                <input type="button" value="-" \
                    onclick="$(\'#position' + countPos + '\').remove(); return false;"> </p> \
                <textarea  name="desc' + countPos + '" rows="8" cols="80"></textarea> \
                </div>');
            })
        });

    </script>

</body>
