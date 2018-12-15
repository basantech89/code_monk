<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/12/18
 * Time: 9:34 PM
 */

session_start();
if ( isset($_POST['account']) && isset($_POST['pw']) ) {
    unset($_SESSION['account']);    // Logout current user

    if ( $_POST['pw'] == 'umsi' ) {
        $_SESSION['account'] = $_POST['account'];
        $_SESSION['success'] = "Logged In";
        header("Location: app.php");
        return;
    }

    else {
        $_SESSION["error"] = "Incorrect Password";
        header("Location: login.php");
        return;
    }
}

?>

<html>
<head>
</head>
<body style="font-family: sans-serif;">
    <h1> Please Log In </h1>

    <?php
        if ( isset($_SESSION['error']) ) {
            echo "<p style='color: red'>".$_SESSION["error"]."</p>\n";
            unset($_SESSION['error']);
        }
    ?>

<form method="post">
    <p> Account: <input type="text" name="account" value=""> </p>
    <p> Password: <input type="text" name="pw" value=""> </p>
    <!-- password is umsi -->
    <p>
        <input type="submit" value="Log In">
        <a href="app.php"> Cancel </a>
    </p>
</form>

</body>
</html>
