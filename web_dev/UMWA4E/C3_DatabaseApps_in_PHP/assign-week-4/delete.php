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
if ( isset($_POST['delete']) && isset($_POST['auto_id']) ) {
    $sql = "DELETE FROM autos WHERE auto_id = :zip";
    $stmt = $pdo -> prepare( $sql );
    $stmt -> execute( array(':zip' => $_POST['auto_id']) );
    $_SESSION['success'] = "Record deleted";
    header("Location: index.php");
    return;
}

// Guardian Make sure that auto_id is present
if ( ! isset($_GET['auto_id']) ) {
    $_SESSION['error'] = "Missing auto_id";
    header("Location: index.php");
    return;
}

// Select the auto_id to be deleted
$stmt = $pdo -> prepare("SELECT make, auto_id FROM autos WHERE auto_id = :id");
$stmt -> execute(array(':id' => $_GET['auto_id']));
$row = $stmt -> fetch(PDO::FETCH_ASSOC);
if ( $row === false ) {
    $_SESSION['error'] = "Bad value for auto_id";
    header('Location: index.php');
    return;
}

?>

<?php

require_once "../assets/header.php";
require_once "../assets/bootstrap.php";

?>

<body>
    <p> Confirm: Deleting <?= htmlentities($row['make']) ?> </p>
    <form method="post">
        <input type="hidden" name="auto_id" value=" <?= $row['auto_id'] ?>">
        <input type="submit" value="Delete" name="delete">
        <a href="index.php"> Cancel </a>
    </form>
</body>
