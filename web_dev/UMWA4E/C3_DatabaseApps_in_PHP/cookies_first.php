<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/11/18
 * Time: 8:23 AM
 */

// NOte - can't have any output before setcookie
if ( ! isset($_COOKIE['zap']) )
    setcookie('zap', '42', time() + 3600);
?>

<pre>
    <?php print_r($_COOKIE); ?>
</pre>
<p>
    <a href="cookies_first.php"> Click Me </a>
</p>
