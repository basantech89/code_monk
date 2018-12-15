<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/14/18
 * Time: 9:15 PM
 */

require_once "../assets/pdo.php";
session_start();

if ( isset($_POST['name']) && isset($_POST['user_id']) &&
    isset($_POST['email']) && isset($_POST['password']) ) {

    // Data validation should go here
    $sql = "UPDATE users SET name = :name,
                 email = :email, password = :password WHERE user_id = :user_id";
    $stmt = $pdo -> prepare($sql);
    $stmt -> execute(array(
        ':name' => $_POST['name'],
        ':email' => $_POST['email'],
        ':password' => $_POST['password'],
        ':user_id' => $_POST['user_id']
    ));

    $_SESSION['success'] = "Record updated";
    header("Location: index.php");
    return;
}


// Guardian" Make sure that user_id is present
if ( ! isset($_GET['user_id']) ) {
    $_SESSION['error'] = "Missing user_id";
    header("Location: index.php");
    return;
}

// Select the user_id to be edited
$stmt = $pdo -> prepare("SELECT * FROM users WHERE user_id = :id");
$stmt -> execute(array(':id' => $_GET['user_id']));
$row = $stmt -> fetch(PDO::FETCH_ASSOC);
if ( $row === false ) {
    $_SESSION['error'] = "Bad value for user_id";
    header('Location: index.php');
    return;
}


?>

<?php
require_once "../assets/header.php";
require_once "../assets/bootstrap.php";

$n = htmlentities($row['name']);
$e = htmlentities($row['email']);
$p = htmlentities($row['password']);
$user_id = $row['user_id'];

?>

<p> Edit User </p>
<form method="post">
    <p>
        <label for="name"> Name: </label>
        <input type="text" id="name" name="name" value="<?= $n ?>">
    </p>
    <p><label for="email"> Email: </label>
        <input type="text" id="email" name="email" value="<?= $e ?>">
    </p>
    <p>
        <label for="password"> Password: </label>
        <input type="text" id="password" name="password" value="<?= $p ?>">
    </p>
    <input type="hidden" name="user_id" value="<?= $user_id ?>"
    <p> <input type="submit" value="Update"> </p>
    <p> <a href="index.php"> Cancel </a> </p>
</form>

