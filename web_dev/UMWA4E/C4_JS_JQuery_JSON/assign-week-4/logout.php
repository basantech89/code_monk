<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/13/18
 * Time: 9:25 PM
 */

session_start();
session_destroy();
header("Location: index.php");