<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/14/18
 * Time: 9:15 PM
 */

session_start();
require_once "../assets/pdo.php";

if ( ! isset($_SESSION['email']) ) {
    die("Access Denied");
}

// If the user cancel, go back to index.php
if ( isset($_POST['cancel']) ) {
    header('Location: index.php');
    return;
}

// Guardian Make sure that auto_id is present
if ( ! isset($_GET['auto_id']) ) {
    $_SESSION['error'] = "Missing auto_id";
    header("Location: index.php");
    return;
}

if ( isset($_POST['make']) && isset($_POST['year']) && isset($_POST['mileage'])
    && isset($_POST['model']) && isset($_POST['auto_id']) ) {

    if ( ! is_numeric($_POST['year']) || ! is_numeric($_POST['mileage'])) {
        $_SESSION['error'] = "Mileage and year must be numeric";
        header("Location: edit.php?auto_id=".$_REQUEST['auto_id']);
        return;
    }

    if ( strlen($_POST['make']) < 1 || strlen($_POST['year']) < 1 ||
        strlen($_POST['mileage']) < 1 || strlen($_POST['model']) < 1 ) {
        $_SESSION['error'] = "All fields are required";
        header("Location: edit.php?auto_id=".$_REQUEST['auto_id']);
        return;
    }

    $sql = "UPDATE autos SET make = :mk, model = :md,
                 year = :yr, mileage = :mi WHERE auto_id = :id";
    $stmt = $pdo -> prepare($sql);
    $stmt -> execute(array(
        ':mk' => $_POST['make'],
        ':md' => $_POST['model'],
        ':yr' => $_POST['year'],
        ':mi' => $_POST['mileage'],
        ':id' => $_POST['auto_id']
    ));

    $_SESSION['success'] = "Record edited";
    header("Location: index.php");
    return;
}


// Select the user_id to be edited
$stmt = $pdo -> prepare("SELECT * FROM autos WHERE auto_id = :id");
$stmt -> execute(array(':id' => $_GET['auto_id']));
$row = $stmt -> fetch(PDO::FETCH_ASSOC);
if ( $row === false ) {
    $_SESSION['error'] = "Bad value for auto_id";
    header('Location: index.php');
    return;
}


?>

<?php
require_once "../assets/header.php";
require_once "../assets/bootstrap.php";

if ( isset($_SESSION['error']) ) {
    echo "<h3 style='color: red'>".htmlentities($_SESSION['error'])."</h3>\n";
    unset($_SESSION['error']);
}

$mk = htmlentities($row['make']);
$md = htmlentities($row['model']);
$yr = htmlentities($row['year']);
$mi = htmlentities($row['mileage']);
$id = $row['auto_id'];

?>

<h3> Editing Automobile </h3>
<form method="post">
    <p>
        <label for="make"> Make: </label>
        <input type="text" id="make" name="make" value="<?= $mk ?>"> <br>
    </p>
    <p>
        <label for="model"> Model: </label>
        <input type="text" id="model" name="model" value="<?= $md ?>"> <br>
    </p>
    <p>
        <label for="year"> Year: </label>
        <input type="text" id="make" name="year" value="<?= $yr ?>"> <br>
    </p>
    <p>
        <label for="mileage"> Mileage: </label>
        <input type="text" id="mileage" name="mileage" value="<?= $mi ?>"> <br>
    </p>
    <input type="hidden" name="auto_id" value="<?= $id ?>"
    <p> <input type="submit" value="Update" name="update"> </p>
    <p> <a href="index.php"> Cancel </a> </p>
</form>
