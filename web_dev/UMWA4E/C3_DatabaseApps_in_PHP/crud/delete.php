<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/14/18
 * Time: 8:50 PM
 */

require_once "../assets/pdo.php";
session_start();

// Delete Model
if ( isset($_POST['delete']) && isset($_POST['user_id']) ) {
    $sql = "DELETE FROM users WHERE user_id = :zip";
    $stmt = $pdo -> prepare( $sql );
    $stmt -> execute( array(':zip' => $_POST['user_id']) );
    $_SESSION['success'] = "Record deleted";
    header("Location: index.php");
    return;
}


// Guardian" Make sure that user_id is present
if ( ! isset($_GET['user_id']) ) {
    $_SESSION['error'] = "Missing user_id";
    header("Location: index.php");
    return;
}

// Select the user_id to be deleted
$stmt = $pdo -> prepare("SELECT name, user_id FROM users WHERE user_id = :id");
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

?>

<body>
    <p> Confirm: Deleting <?= htmlentities($row['name']) ?> </p>
    <form method="post">
        <input type="hidden" name="user_id" value=" <?= $row['user_id'] ?>">
        <input type="submit" value="Delete" name="delete">
        <a href="index.php"> Cancel </a>
    </form>
</body>
