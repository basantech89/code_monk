<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 1/3/19
 * Time: 11:49 AM
 */

include_once "util.php";

if ( !isset($_GET['term']) ) die("Missing required term parameter");

// Let's not start a session unless we already have one
if ( !isset($_COOKIE[session_name()]) ) die("Must be logged in");

session_start();

if ( !isset($_SESSION['user_id']) ) die("ACCESS DENIED");

// Don't even make a database connection until we are happy
require_once "../assets/pdo.php";

header("Content-type: application/json; charset=utf-8");

$term = $_GET['term'];
error_log("Looking up typehead term=".$term);

$stmt = $pdo -> prepare('SELECT name FROM Institution WHERE name LIKE :prefix');
$stmt -> execute(array(':prefix' => $term."%"));
$retval = array();
while ( $row = $stmt -> fetch(PDO::FETCH_ASSOC)) {
    $retval[] = $row['name'];
}

echo(json_encode($retval, JSON_PRETTY_PRINT));