<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/13/18
 * Time: 9:26 PM
 */

session_start();
require_once "../assets/pdo.php";

if ( ! isset($_SESSION['who']) ) {
    die("Not Logged in");
}

// If the user cancel, go back to index.php
if ( isset($_POST['cancel']) ) {
    header('Location: index.php');
    return;
}

// Add Model
if ( isset($_POST['add']) ) {

    if ( ! is_numeric($_POST['year']) || ! is_numeric($_POST['mileage'])) {
        $_SESSION['$error'] = "Mileage and year must be numeric";
        header("Location: add.php");
        return;
    }

    if ( ! isset($_POST['make']) || strlen($_POST['make']) < 1 ) {
        $_SESSION['$error'] = "Make is required";
        header("Location: add.php");
        return;
    }

    $stmt = $pdo -> prepare('INSERT INTO autos (make, year, mileage) VALUES (:mk, :yr, :mi)');
    $stmt -> execute(array(
            ':mk' => $_POST['make'],
            ':yr' => $_POST['year'],
            ':mi' => $_POST['mileage'])
    );

    $_SESSION['success'] = "Record Inserted";
    header("Location: view.php");
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
    <h1> Tracking Autos for <?php echo htmlentities($_SESSION['who']); ?> </h1>
    <form method="post">
        <label for="make"> Make: </label>
        <input type="text" id="make" name="make" size="40"> <br>
        <label for="year"> Year: </label>
        <input type="text" id="make" name="year"> <br>
        <label for="mileage" > Mileage: </label>
        <input type="text" id="mileage" name="mileage"> <br>
        <input type="submit" value="Add" name="add">
        <input type="submit" value="Cancel" name="cancel">
    </form>
</body>
