<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/14/18
 * Time: 9:15 PM
 */

session_start();
require_once "../assets/pdo.php";

if ( ! isset($_SESSION['name']) ) {
    die("Not Logged In");
}

// Guardian first_name sure that profile_id is present
if ( ! isset($_GET['profile_id']) ) {
    $_SESSION['error'] = "Missing profile_id";
    header("Location: index.php");
    return;
}

if ( isset($_POST['fname']) && isset($_POST['lname']) && isset($_POST['email']) &&
    isset($_POST['headline']) && isset($_POST['summary']) && isset($_POST['profile_id']) ) {


    if ( strlen($_POST['fname']) < 1 || strlen($_POST['email']) < 1 ||
        strlen($_POST['headline']) < 1 || strlen($_POST['lname']) < 1 ) {
        $_SESSION['error'] = "All fields are required";
        header("Location: edit.php?profile_id=".$_REQUEST['profile_id']);
        return;
    }

    elseif ( ! strpos($_POST['email'], '@') ) {
        $_SESSION['error'] = "Email address must contain @";
        header("Location: edit.php?profile_id=".$_REQUEST['profile_id']);
        return;
    }

    $sql = "UPDATE Profile SET first_name = :fname, last_name = :lname,
                 email = :em, headline = :headline, summary = :summary WHERE profile_id = :id";
    $stmt = $pdo -> prepare($sql);
    $stmt -> execute(array(
        ':fname' => $_POST['fname'],
        ':lname' => $_POST['lname'],
        ':em' => $_POST['email'],
        ':headline' => $_POST['headline'],
        ':summary' => $_POST['summary'],
        ':id' => $_POST['profile_id']
    ));

    $_SESSION['success'] = "Record edited";
    header("Location: index.php");
    return;
}

// Select the profile_id to be edited
$stmt = $pdo -> prepare("SELECT * FROM Profile WHERE profile_id = :id");
$stmt -> execute(array(':id' => $_GET['profile_id']));
$row = $stmt -> fetch(PDO::FETCH_ASSOC);
if ( $row === false ) {
    $_SESSION['error'] = "Bad value for profile_id";
    header('Location: index.php');
    return;
}

// Checking if user own the profile data entry
if ( $_SESSION['user_id'] !== $row['user_id'] ) {
    $_SESSION['error'] = "Pl choose profile owned by you";
    header("Location: index.php");
    return;
}

?>

<?php
require_once "../assets/header.php";
require_once "../assets/bootstrap.php";

if ( isset($_SESSION['error']) ) {
    echo "<h3 style='color: red'>".htmlentities($_SESSION['error'])."</h3>\n";
    unset($_SESSION['error']);
}

$fname = htmlentities($row['first_name']);
$lname = htmlentities($row['last_name']);
$em = htmlentities($row['email']);
$headline = htmlentities($row['headline']);
$summary = htmlentities($row['summary']);
$id = $row['profile_id'];

?>

<h3> Editing Profile </h3>
<form method="post">
    <p>
        <label for="fname"> First Name: </label>
        <input type="text" id="fname" name="fname" value="<?= $fname ?>"> <br>
    </p>
    <p>
        <label for="lname"> Last Name: </label>
        <input type="text" id="lname" name="lname" value="<?= $lname ?>"> <br>
    </p>
    <p>
        <label for="email"> Email: </label>
        <input type="text" id="email" name="email" value="<?= $em ?>"> <br>
    </p>
    <p>
        <label for="headline"> Headline: </label>
        <input type="text" id="headline" name="headline" value="<?= $headline ?>"> <br>
    </p>
    <p>
        <label> Summary: <br>
            <textarea rows="8" cols="80" name="summary"> <?= $summary ?> </textarea>
        </label> <br>
    </p>
    <input type="hidden" name="profile_id" value="<?= $id ?>"
    <p> <input type="submit" value="Update" name="update"> </p>
    <p> <a href="index.php"> Cancel </a> </p>
</form>
