<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 4:30 PM
 */

require_once "header.php";
require_once "pdo.php";

// The Model

// Add Model
if ( isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password']) ) {
    $sql = "INSERT INTO users (name, email, password) VALUES (:name, :email, :password)";
    $stmt = $pdo -> prepare( $sql );
    $stmt -> execute( array(
        ':name' => $_POST['name'],
        ':email' => $_POST['email'],
        ':password' => $_POST['password'])
    );
}

// Delete Model
if ( isset($_POST['delete']) && isset($_POST['user_id']) ) {
   $sql = "DELETE FROM users WHERE user_id = :zip";
   echo "<pre>\n $sql \n</pre>\n";
   $stmt = $pdo -> prepare( $sql );
   $stmt -> execute( array(':zip' => $_POST['user_id']) );
}

?>

<!-- The View -->
<body>
<?php
    $stmt = $pdo -> query("SELECT name, email, password, user_id FROM users");
    echo '<table border = "1">' . "\n";
    while ( $row = $stmt -> fetch(PDO::FETCH_ASSOC) ) {
        echo "<tr> <td>";
        echo $row['name'];
        echo "</td> <td>";
        echo $row['email'];
        echo "</td> <td>";
        echo $row['password'];
        echo "</td> <td>\n";
        echo('<form method="post"> <input type="hidden" name="user_id" value="'.$row['user_id'].'">' ."\n");
        // echo('<name="user_id" value="'.$row['user_id'].'"> '. "\n");
        echo('<input type="submit" value="Del" name="delete">');
        echo("\n</form>\n");
        echo "</td> </tr>\n";
    }
echo "</table>\n";
?>
    <p> Add A New User </p>
    <form method="post">
        <label for="ip01"> Name: </label>
        <input type="text" name="name" size="40" id="ip01"> <br>
        <label for="ip02"> Email: </label>
        <input type="email" name="email" id="ip02"> <br>
        <label for="ip03"> Password: </label>
        <input type="password" name="password" id="ip03"> <br>
        <input type="submit" value="Add New">
    </form>
</body>

