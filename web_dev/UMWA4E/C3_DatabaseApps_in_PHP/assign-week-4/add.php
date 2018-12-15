<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/13/18
 * Time: 9:26 PM
 */

require_once "../assets/pdo.php";
session_start();

if ( ! isset($_SESSION['email']) ) {
    die("Access Denied");
}

// If the user cancel, go back to index.php
if ( isset($_POST['cancel']) ) {
    header('Location: index.php');
    return;
}

// Add Model
if ( isset($_POST['make']) && isset($_POST['year']) && isset($_POST['mileage'])
    && isset($_POST['model']) && isset($_POST['add']) ) {

        if ( ! is_numeric($_POST['year']) || ! is_numeric($_POST['mileage'])) {
            $_SESSION['$error'] = "Mileage and year must be numeric";
            header("Location: add.php");
            return;
        }

        if ( strlen($_POST['make']) < 1 || strlen($_POST['year']) < 1 ||
            strlen($_POST['mileage']) < 1 || strlen($_POST['model']) < 1 ) {
            $_SESSION['$error'] = "All fields are required";
            header("Location: add.php");
            return;
        }

        $stmt = $pdo -> prepare('INSERT INTO autos (make, model, year, mileage) VALUES (:mk, :md, :yr, :mi)');
        $stmt -> execute(array(
                ':mk' => $_POST['make'],
                ':md' => $_POST['model'],
                ':yr' => $_POST['year'],
                ':mi' => $_POST['mileage'])
        );

        $_SESSION['success'] = "Record Added";
        header("Location: index.php");
        return;
}

?>

<?php
require_once "header.php";
require_once "bootstrap.php";

if ( isset( $_SESSION['$error']) ) {
    echo "<h3 style='color: red'>" .htmlentities( $_SESSION['$error'] ). "</h3>\n";
    unset($_SESSION['$error']);
}

?>

<body>
    <h1> Tracking Autos for <?php echo htmlentities($_SESSION['email']); ?> </h1>
    <form method="post">
        <p>
            <label for="make"> Make: </label>
            <input type="text" id="make" name="make" size="40"> <br>
        </p>
        <p>
            <label for="model"> Model: </label>
            <input type="text" id="model" name="model" size="40"> <br>
        </p>
        <p>
            <label for="year"> Year: </label>
            <input type="text" id="make" name="year"> <br>
        </p>
        <p>
            <label for="mileage"> Mileage: </label>
            <input type="text" id="mileage" name="mileage"> <br>
        </p>
        <input type="submit" value="Add" name="add">
        <input type="submit" value="Cancel" name="cancel">
    </form>
</body>
