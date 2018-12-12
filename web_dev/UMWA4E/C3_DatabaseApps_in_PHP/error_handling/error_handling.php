<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 6:56 PM
 */

// Always uncomment below line for better error handling
//require_once "pdo.php";

$stmt = $pdo -> prepare("SELECT * FROM users where user_id = :xyz");
$stmt -> execute(array(":pizza" => $_GET['user_id']));
$row = $stmt -> fetch(PDO::FETCH_ASSOC);
if ($row === false)
    echo "<p> user_id not found </p>\n";
else
    echo "<p> user_id found </p>\n";
?>

<body>
    <form>
        <label for="ip01"> Name: </label>
        <input type="text" id="ip01" name="user_id">
    </form>
</body>
