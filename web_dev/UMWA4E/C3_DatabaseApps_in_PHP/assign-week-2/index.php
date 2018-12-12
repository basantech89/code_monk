<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 9:53 PM
 */

require_once "pdo.php";
require_once "header.php";
require_once "../assets/bootstrap.php";

?>


<body>
    <div class="container">
        <h1> Welcome to Autos Database </h1>

        <p>
            <a href="login.php">Please Log In</a>
        </p>

        <p>
            Attempt to go to
            <a href="autos.php"> autos.php </a> without logging in -
            it should fail with an error message.
        </p>
    </div>
</body>
