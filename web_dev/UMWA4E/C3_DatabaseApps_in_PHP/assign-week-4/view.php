<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/13/18
 * Time: 7:46 PM
 */

session_start();
require_once "pdo.php";

if ( ! isset($_SESSION['who']) ) {
    die("Not Logged in");
}

// Delete Model
if ( isset($_POST['delete']) && isset($_POST['auto_id']) ) {
    $sql = "DELETE FROM autos WHERE auto_id = :zip";
    $stmt = $pdo -> prepare( $sql );
    $stmt -> execute( array(':zip' => $_POST['auto_id']) );
    header("Location: view.php");
    return;
}

?>

<?php
require_once "header.php";
require_once "bootstrap.php";
?>

<body>

    <?php
        if ( isset($_SESSION['success']) ) {
            echo "<h3 style='color: green'>".htmlentities($_SESSION['success'])."</h3>\n";
            unset($_SESSION['success']);
        }
    ?>

    <h1> Tracking Autos for <?php echo htmlentities($_SESSION['who']) ?> </h1>
    <h2> Automobiles </h2>
    <?php
        $stmt = $pdo -> query("SELECT make, year, mileage, auto_id FROM autos ORDER BY make");
        echo '<table border = "1" class="style-table">' . "\n";
        echo "<colgroup>";
        echo "<col class='less-width'>";
        echo "<col class='min-width'>";
        echo "<col class='min-width'>";
        echo "<col class='min-width'>";
        echo "</colgroup>";
        while ( $row = $stmt -> fetch(PDO::FETCH_ASSOC) ) {
            echo "<tr> <td>";
            echo htmlentities($row['make']);
            echo "</td> <td>";
            echo htmlentities($row['year']);
            echo "</td> <td>";
            echo htmlentities($row['mileage']);
            echo "</td> <td>\n";
            echo('<form method="post"> <input type="hidden" name="auto_id" value="'.htmlentities($row['auto_id']).'">' ."\n");
            // echo('<name="user_id" value="'.$row['auto_id'].'"> '. "\n");
            echo('<input type="submit" value="Del" name="delete" id="submit">');
            echo("\n</form>\n");
            echo "</td> </tr>\n";
        }
        echo "</table>\n";
    ?>

    <br> <br>
    <a href="add.php"> Add New </a> <span> | </span>
    <a href="logout.php"> Logout </a>
</body>
