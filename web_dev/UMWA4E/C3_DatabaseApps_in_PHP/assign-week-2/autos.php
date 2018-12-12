<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 10:00 PM
 */

require_once "pdo.php";

if ( ! isset($_GET['name']) || strlen($_GET['name']) < 1 )
    die('Name parameter missing');

// If the user requested logout go back to index.php
if ( isset($_POST['logout']) ) {
    header('Location: index.php');
    return;
}

// Add Model
if ( isset($_POST['add']) ) {

    if ( ! is_numeric($_POST['year']) || ! is_numeric($_POST['mileage'])) {
        echo "Mileage and year must be numeric";
        return;
    }

    if ( ! isset($_POST['make']) || strlen($_POST['make']) < 1 ) {
        echo "Make is required";
        return;
    }

    $stmt = $pdo -> prepare('INSERT INTO autos (make, year, mileage) VALUES (:mk, :yr, :mi)');
    $stmt -> execute(array(
            ':mk' => $_POST['make'],
            ':yr' => $_POST['year'],
            ':mi' => $_POST['mileage'])
    );

    echo "<p class='success'> Record inserted </p>\n";
}

// Delete Model
if ( isset($_POST['delete']) && isset($_POST['auto_id']) ) {
    $sql = "DELETE FROM autos WHERE auto_id = :zip";
    $stmt = $pdo -> prepare( $sql );
    $stmt -> execute( array(':zip' => $_POST['auto_id']) );
}

?>

<?php
    require_once "header.php";
    require_once "bootstrap.php";
?>

<body>
    <?php
        if ( isset($_REQUEST['name']) ) {
            echo "<h1> Tracking Autos for ";
            echo htmlentities( $_REQUEST['name'] );
            echo "</h1>\n";
        }
    ?>

    <form method="post">
        <label for="make"> Make: </label>
        <input type="text" id="make" name="make" size="40"> <br>
        <label for="year"> Year: </label>
        <input type="text" id="make" name="year"> <br>
        <label for="mileage" > Mileage: </label>
        <input type="text" id="mileage" name="mileage"> <br>
        <input type="submit" value="Add" name="add">
        <input type="submit" value="Logout" name="logout">
    </form>

    <h1> Automobiles </h1>
    <?php
        $stmt = $pdo -> query("SELECT make, year, mileage, auto_id FROM autos ORDER BY make");
        echo '<table border = "1">' . "\n";
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
            echo('<input type="submit" value="Del" name="delete">');
            echo("\n</form>\n");
            echo "</td> </tr>\n";
        }
        echo "</table>\n";
    ?>

</body>

