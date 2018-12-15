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
require_once "header.php";
require_once "../assets/bootstrap.php";
?>

<body>
    <div class="container">
        <h1> Welcome to Automobiles Database </h1>
        <?php

            if ( isset($_SESSION['success']) ) {
                echo "<h3 style='color: green'>".htmlentities($_SESSION['success'])."</h3>\n";
                unset($_SESSION['success']);
            }

            if ( isset($_SESSION['error']) ) {
                echo "<h3 style='color: red'>".htmlentities($_SESSION['error'])."</h3>\n";
                unset($_SESSION['error']);
            }

            if ( ! isset($_SESSION['email']) ) {
                echo "<p> <a href=\"login.php\"> Please Log In </a> </p>";
                echo "<p> Attempt to go to <a href=\"add.php\"> add data </a> without logging in </p>";
            }

            else {
                $stmt = $pdo -> query("SELECT make, year, mileage, model, auto_id FROM autos ORDER BY make");
                echo '<table border = "1" class="style-table">' . "\n";
                echo "<colgroup>";
                echo "<col class='max-width'>";
                echo "<col class='max-width'>";
                echo "<col class='more-width'>";
                echo "<col class='more-width'>";
                echo "<col class='max-width'>";
                echo "</colgroup>";
                echo "<tr> <th> Make </th> <th> Model </th> <th> Year </th> <th> Mileage </th> <th> Action </th> </tr>";
                while ( $row = $stmt -> fetch(PDO::FETCH_ASSOC) ) {
                    echo "<tr> <td>";
                    echo htmlentities($row['make']);
                    echo "</td> <td>";
                    echo htmlentities($row['model']);
                    echo "</td> <td>";
                    echo htmlentities($row['year']);
                    echo "</td> <td>";
                    echo htmlentities($row['mileage']);
                    echo "</td> <td>\n";
                    echo('<a href="edit.php?auto_id='.$row['auto_id'].'"> Edit </a> / ');
                    echo('<a href="delete.php?auto_id='.$row['auto_id'].'"> Delete </a>');
                    echo("\n</form>\n");
                    echo "</td> </tr>\n";
                }
                echo "</table> <br>";

                if ( $row === false ) {
                    echo "<h3> No rows found </h3>";
                }

                echo "<a href=\"add.php\"> Add New Entry </a> <br> <br>
                  <a href=\"logout.php\"> Logout </a>";
            }
        ?>
    </div>
</body>
