<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 5:47 PM
 */

require_once "pdo.php";
require_once "header.php";

if ( isset($_POST['email']) && isset($_POST['password']) ) {
    echo "<p> Handling Post Data </p>";

    $sql = "SELECT name FROM users WHERE email = :em AND password = :pw";
    echo "<pre>\n $sql \n</pre>\n";

    $stmt = $pdo -> prepare( $sql );
    $stmt -> execute(array(
        ':em' => $_POST['email'],
        ':pw' => $_POST['password'])
    );
    /*
     * When the statement is executed, the placeholders get replaced with the
actual strings and everything is automatically escaped!
    */
    $row = $stmt -> fetch(PDO::FETCH_ASSOC);
    var_dump($row);
    echo "---->\n";

    if ( $row === false )
        echo "<h1> Login Incorrect </h1>\n";
    else
        echo "<h1> Login Success </h1>\n";

}

?>

<body>
    <p> Please Login </p>
    <form method="post">
        <label for="ip01"> Email: </label>
        <input type="text" id="ip01" name="email"> <br>
        <label for="ip02"> Password: </label>
        <input type="text" id="ip02" name="password"> <br>
        <input type="submit" value="Login">
        <a href="<?php echo($_SERVER['PHP_SELF']); ?>" > Refresh </a>
    </form>
    <p> Check this out <a href="http://xkcd.com/327/" target="_blank"> XKCD comic</a> </p>
</body>
