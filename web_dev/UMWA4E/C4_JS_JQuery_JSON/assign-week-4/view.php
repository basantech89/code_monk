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
    echo "<p> Headline:<br>" .htmlentities($row['headline']). "</p>";
    echo "<p> Summary:<br>" .htmlentities($row['summary']). "</p>";

    echo "<p> Education";
    $stmt = $pdo -> prepare('SELECT institution_id, year FROM Education WHERE profile_id = :pid');
    $stmt -> execute(array(
        ':pid' => $_GET['profile_id']
    ));
    echo "<ul>";
    while ($row = $stmt -> fetch(PDO::FETCH_ASSOC)) {
        $institution_id = $row['institution_id'];
        $stmt2 = $pdo -> prepare('SELECT name FROM Institution WHERE institution_id = :iid');
        $stmt2 -> execute(array(':iid' => $institution_id));
        $row2 = $stmt2 -> fetch(PDO::FETCH_ASSOC);
        echo "<li> ".$row['year'].": ".$row2['name']." </li>";
    }
    echo "</ul> </p>";

    echo "<p> Position";
    $stmt = $pdo -> prepare('SELECT year, description FROM Position WHERE profile_id = :pid');
    $stmt -> execute(array(
            ':pid' => $_GET['profile_id']
    ));
    echo "<ul>";
    while ($row = $stmt -> fetch(PDO::FETCH_ASSOC)) {
        echo "<li> ".$row['year'].": ".$row['description']." </li>";
    }
    echo "</ul> </p>";
    ?>
    <a href="index.php"> Done </a>
</div>
</body>
