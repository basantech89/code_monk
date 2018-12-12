<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 9:57 PM
 */

require_once "pdo.php";

if ( isset($_POST['cancel'] ) ) {
    // Redirect the browser to game.php
    header("Location: index.php");
    return;
}

$salt = 'XyZzy12*_';
//$stored_hash = 'a8609e8d62c043243c4e201cbb342862';  // Pw is meow123
$stored_hash = '1a52e17fa899cf40fb04cfc42e6352f1';  // MD5 of salt concatenated with plaintext php123
$failure = false;  // If we have no POST data

// Check to see if we have some POST data, if we do process it
if ( isset($_POST['who'] ) && isset( $_POST['pass']) ) {

    if ( strlen($_POST['who']) < 1 || strlen($_POST['pass']) < 1 )
        $failure = "Email and password are required";

    elseif ( ! strpos($_POST['who'], '@') )
        $failure = "Email must have an at-sign (@)";

    else {
        $check = hash('md5', $salt.$_POST['pass']);

        if ( $check == $stored_hash ) {
            error_log("Login success ".$_POST['who']);
            // Redirect the browser to game.php
            header("Location: autos.php?name=".urlencode($_POST['who']));
            return;
        }

        else {
            $failure = "Incorrect password";
            error_log("Login fail ".$_POST['who']." $check");
        }
    }
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
        // Note triple not equals and think how badly double
        // not equals would work here...
        if ( $failure !== false ) {
            // Look closely at the use of single and double quotes
            echo('<p style="color: red;">'.htmlentities($failure)."</p>\n");
        }
    ?>

    <form method="POST">
        <label for="name"> User Name </label>
        <input type="text" name="who" id="name"> <br/>
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