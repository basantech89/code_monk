<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 3:21 PM
 */

//require_once "pdo.php";   // a better idea
$pdo = new PDO('mysql:host=localhost;dbname=misc', 'lab', 'lab');

$stmt = $pdo->query("SELECT * FROM users");

while ( $row = $stmt -> fetch(PDO::FETCH_ASSOC) ) {
    print_r( $row );
}