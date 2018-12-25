<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/14/18
 * Time: 8:50 PM
 */

require_once "../assets/pdo.php";
session_start();

if ( ! isset($_SESSION['name']) ) {
    die("Not Logged In");
}

// Guardian Make sure that profile_id is present
if ( ! isset($_GET['profile_id']) ) {
    $_SESSION['error'] = "Missing profile_id";
    header("Location: index.php");
    return;
}

// Delete Model
if ( isset($_POST['delete']) && isset($_POST['profile_id']) ) {
    $sql = "DELETE FROM Profile WHERE profile_id = :zip";
    $stmt = $pdo -> prepare( $sql );
    $stmt -> execute( array(':zip' => $_POST['profile_id']) );
    $_SESSION['success'] = "Record deleted";
    header("Location: index.php");
    return;
}


// Select the profile_id to be deleted
$stmt = $pdo -> prepare("SELECT first_name, last_name, user_id, profile_id FROM Profile WHERE profile_id = :id");
$stmt -> execute(array(
        ':id' => $_GET['profile_id']));
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

?>

<body>
    <h1> Deleting Profile </h1>
    <p> First Name: <?= htmlentities($row['first_name']) ?> </p>
    <p> Last Name: <?= htmlentities($row['last_name']) ?> </p>
    <form method="post">
        <input type="hidden" name="profile_id" value=" <?= $row['profile_id'] ?>">
        <input type="submit" value="Delete" name="delete">
        <a href="index.php"> Cancel </a>
    </form>
</body>
