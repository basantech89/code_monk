<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/11/18
 * Time: 10:28 AM
 */

// Tell PHP we won't use cookies
ini_set('session.use_cookies', 0);
ini_set('session.use_only_cookies', 0);
ini_set('session.use_trans_sid', 1);

session_start();

if ( ! isset($_SESSION['value']) ) {
    echo "<p> Session is empty </p>";
    $_SESSION['value'] = 0;
}

elseif ( $_SESSION['value'] < 3 ) {
    $_SESSION['value'] += 1;
    echo "<p> Added one \$_SESSION['value'] =".$_SESSION['value']."</p>\n";
}

else {
    session_destroy();
    session_start();
    echo "<p> Session Restarted </p>";
}

?>

<!-- start the view -->

<p> <strong> No Cookies for you </strong></p>
<p> <a href="sessions_without_cookies.php"> Click Me </a> </p>
<p>
    <form action="sessions_without_cookies.php" method="post">
        <input type="submit" name="click" value="Click">
    </form>
<p> Our session ID is: <?php echo session_id(); ?> </p>
<pre> <?php print_r($_SESSION); ?> </pre>
</p>