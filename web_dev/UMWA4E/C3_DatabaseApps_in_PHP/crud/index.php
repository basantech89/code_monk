<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 9:53 PM
 */

require_once "../assets/pdo.php";
session_start();
?>

<?php
require_once "../assets/header.php";
require_once "../assets/bootstrap.php";
?>
<body>
    <?php
        if ( isset($_SESSION['success']) ) {
            echo "<h3 style='color: green'>".htmlentities($_SESSION['success'])."</h3>\n";
            unset($_SESSION['success']);
        }

        if ( isset($_SESSION['error']) ) {
            echo "<h3 style='color: red'>".htmlentities($_SESSION['error'])."</h3>\n";
            unset($_SESSION['error']);
        }

    $stmt = $pdo -> query("SELECT name, email, password, user_id FROM users");
    echo '<table border = "1" class="style-table">'."\n";
        echo "<colgroup>";
            echo "<col class='less-width'>";
            echo "<col class='more-width'>";
            echo "<col class='more-width'>";
            echo "<col class='more-width'>";
            echo "</colgroup>";
        while ( $row = $stmt -> fetch(PDO::FETCH_ASSOC) ) {
        echo "<tr> <td>";
                echo htmlentities($row['name']);
                echo "</td> <td>";
                echo htmlentities($row['email']);
                echo "</td> <td>";
                echo htmlentities($row['password']);
                echo "</td> <td>\n";
                echo('<a href="edit.php?user_id='.$row['user_id'].'"> Edit </a> / ');
                echo('<a href="delete.php?user_id='.$row['user_id'].'"> Delete </a>');
                echo "</td> </tr>\n";
        }
        echo "</table>\n";
    ?>

    <a href="add.php"> Add New </a>

</body>
