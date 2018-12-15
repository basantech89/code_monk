<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/12/18
 * Time: 9:58 PM
 */

session_start();
session_destroy();
header("Location: app.php");