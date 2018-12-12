<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 5:01 PM
 */

require_once "pdo.php";
require_once "header.php";

if ( isset($_POST['user_id']) ) {
    $sql = "DELETE FROM users WHERE user_id = :zip";
    echo "<pre>\n $sql \n</pre>\n";
    $stmt = $pdo -> prepare( $sql );
    $stmt -> execute(array(
        ':zip' => $_POST['user_id']
    ));
}

?>

<body>
    <p> Delete A User </p>
    <form method="post">
        <label for="ip01"> ID to Delete </label>
        <input type="text" name="user_id" id="ip01">
        <input type="submit" value="Delete">
    </form>
</body>
