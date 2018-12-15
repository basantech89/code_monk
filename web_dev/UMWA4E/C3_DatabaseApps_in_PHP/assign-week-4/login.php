<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 9:57 PM
 */

require_once "../assets/pdo.php";
session_start();

if ( isset($_POST['cancel'] ) ) {
    // Redirect the browser to game.php
    header("Location: index.php");
    return;
}

$salt = 'XyZzy12*_';
//$stored_hash = 'a8609e8d62c043243c4e201cbb342862';  // Pw is meow123
$stored_hash = '1a52e17fa899cf40fb04cfc42e6352f1';  // MD5 of salt concatenated with plaintext php123

// Check to see if we have some POST data, if we do process it
if ( isset($_POST['email'] ) && isset( $_POST['pass']) ) {
    unset($_SESSION['email']);    // Logout current user

    // in case if username or pass is empty, user will be redirected to login.php as coded after this if-else
    if ( strlen($_POST['email']) < 1 || strlen($_POST['pass']) < 1 )
        $_SESSION['$error'] = "User email and password are required";

    else {
        $check = hash('md5', $salt.$_POST['pass']);

        if ( $check == $stored_hash ) {
            error_log("Login success ".$_POST['email']);
            $_SESSION['email'] = $_POST['email'];
            $_SESSION['success'] = "Logged In";
            // Redirect the browser to index.php
            header("Location: index.php?email=".urlencode($_POST['email']));
            return;
        }

        else {
            $_SESSION['$error'] = "Incorrect password";
            error_log("Login fail ".$_POST['email']." $check");
        }
    }

    header("Location: login.php");
    return;
}

// Fall through into the View
?>


<?php
    require_once "header.php";
    require_once "bootstrap.php";
?>
<body>

<div class="container">
    <h1> Please Log In </h1>
    <?php
        if ( isset($_SESSION['$error']) ) {
            echo "<p style='color: red'>".htmlentities( $_SESSION['$error'] )."</p>\n";
            unset($_SESSION['$error']);
        }
    ?>

    <form method="POST">
        <label for="email"> User email </label>
        <input type="text" name="email" id="email"> <br/>
        <label for="pass"> Password </label>
        <input type="text" name="pass" id="pass"> <br/>
        <input type="submit" value="Log In">
        <input type="submit" name="cancel" value="Cancel">
    </form>

    <p>
        For a password hint, view source and find a password hint
        in the HTML comments.
        <!-- Hint: The password is the four character sound a cat
        makes (all lower case) followed by 123. -->
    </p>

</div>

</body>