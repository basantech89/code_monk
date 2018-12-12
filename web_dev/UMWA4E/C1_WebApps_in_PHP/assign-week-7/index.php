<!--
* Created by PhpStorm.
* User: luv
* Date: 12/4/18
* Time: 4:09 PM
-->

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <title> Basant Soni </title>
        <style> pre { line-height: 0.5em; } </style>
    </head>

    <body>

        <section>
            <h1> MD5 cracker </h1>
            <p>
                This application takes an MD5 hash of a four digit pin and check
                all 10,000 possible four digit PINs to determine the PIN.
            </p>
            <p> Debug Output: </p>

            <?php
                $pass = null;

                if ( ! isset($_GET["md5"]) )
                    echo "Pl provide the md5 input\n";

                else if ( strlen($_GET["md5"]) < 1 )
                    echo "Pl provide the hash\n";

                else
                    $pass = crackMD5( $_GET["md5"] );
            ?>

            <form action="index.php" method="GET">
                <fieldset>
                    <legend> Bruteforce Cracking </legend>
                    <input type="text" name="md5" id="md5" size="40" required>
                    <input type="submit" value="Crack MD5">
                </fieldset>
            </form>

        </section>
    </body>
</html>


<?php

/**
 * Function to crack a md5 password
 * This function bruteforce all the 4 digit numerical possibilities
 * @param $hashed - the md5 hashed value to crack
 * @return int - the password if found, otherwise null
 */
function crackMD5 ( $hashed ) {

    $start_time = microtime(true);
    for ($i = 0; $i < 10000; $i++) {
        if ( strlen($i) == 1 )
            $i = "000" . $i;

        elseif ( strlen($i) == 2 )
            $i = "00" . $i;

        elseif ( strlen($i) == 3 )
            $i = "0" . $i;

        $currHash = hash("md5", (string) $i);

        if ($i < 15) {
            echo "<pre>";
            echo "$currHash $i";
            echo "</pre>";
        }

        if ( hash("md5", (string) $i) == $hashed ) {
            echo "Total checks: $i";
            echo "<br>";
            $end_time = microtime(true);
            $ellapsed_time = $end_time - $start_time;
            echo "Ellapsed time: $ellapsed_time";
            echo "<p>";
            echo "PIN: $i\n";
            echo "</p>";
            return $i;
        }

    }

    echo "<p>";
    echo "PIN: Not found";
    echo "</p>";
    return null; // haven't found the password
}