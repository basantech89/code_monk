<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 9:53 PM
 */

session_start();
require_once "../assets/pdo.php";

?>

<?php
require_once "../assets/header.php";
require_once "../assets/bootstrap.php";
?>

<body>
<div class="container">
    <h1> User Resume Registry </h1>
    <?php

    if ( isset($_SESSION['success']) ) {
        echo "<h3 style='color: green'>".htmlentities($_SESSION['success'])."</h3>\n";
        unset($_SESSION['success']);
    }

    if ( isset($_SESSION['error']) ) {
        echo "<h3 style='color: red'>".htmlentities($_SESSION['error'])."</h3>\n";
        unset($_SESSION['error']);
    }

    // request to login if user isn't logged in, else show options to add a new profile data and to logout
    if ( ! isset($_SESSION['name']) ) {
        echo "<p> <a href=\"login.php\"> Please Log In </a> </p>";
        echo "<p> Attempt to go to <a href=\"add.php\"> add data </a> without logging in </p> <br>";
    }

    else {
        echo "<a href=\"add.php\"> Add New Entry </a> <br>";
        echo "<a href=\"logout.php\"> Logout </a> <br> <br>";
    }

    // showing user the profile data whether user is logged in or not
    $stmt = $pdo -> query("SELECT first_name, last_name, headline, profile_id FROM Profile");
    $data = true;
    while ( $row = $stmt -> fetch(PDO::FETCH_ASSOC) ) {
        if ($data === true) {
            $data = false;
            echo '<table border = "1" class="style-table">' . "\n";
            echo "<colgroup>";
            echo "<col class='max-width'>";
            echo "<col class='max-width'>";

            // if user is logged in, then one more column will be shown
            if ( isset($_SESSION['name']) )
                echo "<col class='more-width'>";

            echo "</colgroup>";
            echo "<tr> <th> Name </th> <th> Headline </th>";

            // if user is logged in, then option to edit or delete the profile data will be shown
            if ( isset($_SESSION['name']) )
                echo "<th> Action </th> </tr>";

            else
                echo "</tr>";
        }

        echo "<tr> <td>";
        echo ('<a href="view.php?profile_id='.$row['profile_id'].'"> '.htmlentities($row["first_name"]." ".htmlentities($row["last_name"])).' </a>');
        echo "</td> <td>";
        echo htmlentities($row['headline']);

        // if user is logged in, then links to edit or delete the profile data will be shown
        if ( isset($_SESSION['name']) ) {
            echo "</td> <td>\n";
            echo('<a href="edit.php?profile_id='.$row['profile_id'].'"> Edit </a> / ');
            echo('<a href="delete.php?profile_id='.$row['profile_id'].'"> Delete </a>');
        }

        echo("\n</form>\n");
        echo "</td> </tr>\n";
    }
    echo "</table> <br>";

    if ( $row == false ) {
        echo "<h3> No rows found </h3>";
    }

    ?>
</div>
</body>
