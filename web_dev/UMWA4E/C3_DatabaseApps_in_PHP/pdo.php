<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 4:22 PM
 */

$pdo = new PDO('mysql:host=localhost;dbname=misc', 'lab', 'lab');

// See the error folder for details, good to be verbose and show the errors
$pdo -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);