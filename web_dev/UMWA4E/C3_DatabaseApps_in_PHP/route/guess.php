<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/12/18
 * Time: 7:50 PM
 */

$guess = "";
$message = false;

if ( isset($_POST['guess']) ) {
    // Trick for integer / numeric parameters
    $guess = $_POST['guess'] + 0;

    if ( $guess == 42 )
        $message = "Great Job";

    elseif ( $guess < 42 )
        $message = "Too Low";

    else
        $message = "Too High...";
}

?>

<html>
    <head>
        <title> Guessing Game </title>
    </head>
    <body style="font-family: sans-serif">
        <p> Guessing Game </p>
        <?php
            if ( $message !== false)
                echo "<p> $message </p>\n"
        ?>
        <form method="post">
            <p>
                <label for="guess"> Input Guess </label>
                <input type="text" name="guess" id="guess" size="40"
                <?php
                    echo 'value="' .htmlentities( $guess ). '"';
                ?>>
            </p>
            <input type="submit">
        </form>
    </body>
</html>
