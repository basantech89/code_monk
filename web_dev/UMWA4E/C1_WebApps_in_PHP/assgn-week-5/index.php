<!--/**
* Created by PhpStorm.
* User: Basant Soni
* Date: 12/3/18
* Time: 4:33 PM
*/
-->

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf8">
        <title> Basant Soni </title>
    </head>

    <body>

    <section>
        <h1> Assignment by Basant Soni </h1>
        <p>
            The SHA256 hash of "Basant Soni"is
            <?php
            print hash("sha256", "Basant Soni");
            ?>
        </p>
        <p> ASCII ART: </p>
        <pre>
            **********
            **        **
            **        **
            ************
            **        **
            **        **
            **********
        </pre>
        <a href="check.php" target="_blank"> click here to check the error setting </a> <br>
        <a href="fail.php" target="_blank"> click here to cause a traceback </a>
    </section>

    </body>
</html>



