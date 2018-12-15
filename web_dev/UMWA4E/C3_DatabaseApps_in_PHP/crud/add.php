<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/14/18
 * Time: 8:25 PM
 */

require_once "../assets/pdo.php";
session_start();

if ( isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password']) ) {

    // Data Validation
    if ( strlen($_POST['name']) < 1 || strlen($_POST['password']) < 1) {
        $_SESSION['error'] = "Missing data";
        header("Location: add.php");
        return;
    }

    if ( strpos($_POST['email'], '@') === false ) {
        $_SESSION['error'] = "Bad data";
        header("Location: add.php");
        return;
    }

    $sql = "INSERT INTO users (name, email, password) VALUES (:name, :email, :password)";
    $stmt = $pdo -> prepare( $sql );
    $stmt -> execute(array(
        ':name' => $_POST['name'],
        ':email' => $_POST['email'],
        ':password' => $_POST['password']
    ));
    $_SESSION['success'] = "Record Added";
    header("Location: index.php");
    return;
}

?>

<!-- View -->

<?php
require_once "../assets/header.php";
require_once "../assets/bootstrap.php";

// Flash Data
if ( isset($_SESSION['error']) ) {
    echo "<p style='color: red'>".$_SESSION['error']."</p>\n";
    unset($_SESSION['error']);
}

?>

<p> Add a New User </p>
<form method="post">
    <p>
        <label for="name"> Name: </label>
        <input type="text" id="name" name="name">
    </p>
    <p><label for="email"> Email: </label>
        <input type="text" id="email" name="email">
    </p>
    <p>
        <label for="password"> Password: </label>
        <input type="text" id="password" name="password">
    </p>
    <p> <input type="submit" value="Add New"> </p>
    <p> <a href="index.php"> Cancel </a> </p>
</form>
