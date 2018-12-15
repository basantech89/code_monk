<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/12/18
 * Time: 7:04 PM
 */


if ( isset($_POST['where']) ) {
    if ( $_POST['where'] == '1' ) {
        header("Location: redir1.php");
        return;
    }

    if ( $_POST['where'] == '2') {
        header("Location: redir2.php?param=123");
        return;
    }

    else {
        header("Location: http://www.dr-chuck.com");
        return;
    }
}

?>

<htm>
    <body style="font-family: sans-serif;">
        <p> I am router one </p>
        <form method="post">
            <p>
                <label for="ip01"> Where to go? (1-3) </label>
                <input type="text" name="where" id="ip01" size="5"> <br>
                <input type="submit">
            </p>
        </form>
    </body>
</htm>
