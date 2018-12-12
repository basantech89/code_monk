<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title> Basant Soni </title>
    </head>

    <body>
    <h1> Welcome to my guessing game </h1>
    <p>
        <?php
            $correct = 46;
            if ( ! isset($_GET["guess"]) )
                echo "Missing guess parameters";

            else if ( strlen($_GET["guess"]) < 1 )
                echo "Your guess is too short";

            else if ( ! is_numeric($_GET["guess"]) )
                echo "Your guess is not a number";

            else if ( $_GET["guess"] < $correct )
                echo "Your guess is too low";

            else if ( $_GET["guess"] > $correct )
                echo "Your guess is too high";

            else
                echo "Congratulation - You are right";
        ?>
    </p>
    </body>
</html>
