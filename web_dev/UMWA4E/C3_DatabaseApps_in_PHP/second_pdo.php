<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 4:22 PM
 */

require_once "pdo.php";

$stmt = $pdo->query("SELECT name, email, password FROM users");

echo '<table border = "1">' . "\n";
while ( $row = $stmt -> fetch(PDO::FETCH_ASSOC) ) {
    echo "<tr> <td>";
    echo $row['name'];
    echo "</td> <td>";
    echo $row['email'];
    echo "</td> <td>";
    echo $row['password'];
    echo "</td> </tr>\n";
}
echo "</table>\n";