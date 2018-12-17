<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 9:53 PM
 */

session_start();
require_once "../assets/pdo.php";

// Guardian Make sure that profile_id is present
// profile_id is should be received from index.php
if ( ! isset($_GET['profile_id']) ) {
    $_SESSION['error'] = "Missing profile_id";
    header("Location: index.php");
    return;
}


?>

<?php
require_once "../assets/header.php";
require_once "../assets/bootstrap.php";
?>

<body>
<div class="container">
    <h1> Profile information </h1>

    <?php

    $stmt = $pdo -> prepare("SELECT first_name, last_name, email, headline, summary  FROM Profile WHERE profile_id = :pid");
    $stmt -> execute(array(
            ':pid' => $_GET['profile_id']
    ));
    $row = $stmt -> fetch(PDO::FETCH_ASSOC);

    echo "<p> First Name: " .htmlentities($row['first_name']). "</p>";
    echo "<p> Last Name: " .htmlentities($row['last_name']). "</p>";
    echo "<p> Email: " .htmlentities($row['email']). "</p>";
    echo "<p> Headline: " .htmlentities($row['headline']). "</p>";
    echo "<p> Summary: " .htmlentities($row['summary']). "</p>";

    ?>
</div>
</body>
