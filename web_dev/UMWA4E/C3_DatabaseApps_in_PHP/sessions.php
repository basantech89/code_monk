<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/11/18
 * Time: 9:44 AM
 */

// Note - can't have any output before this
session_start();

if ( ! isset($_SESSION['pizza']) ) {
    echo "<p> Session is empty </p>";
    $_SESSION['pizza'] = 0;
}

elseif ( $_SESSION['pizza'] < 3 ) {
    echo "<p> Added one </p>";
    $_SESSION['pizza'] += 1;
}

else {
    session_destroy();
    session_start();
    echo "<p> Session Restarted </p>";
}

?>

<p> <a href="sessions.php"> Click Me </a> </p>
<p> Our session ID is: <?php echo session_id(); ?> </p>
<pre> <?php print_r($_SESSION); ?> </pre>