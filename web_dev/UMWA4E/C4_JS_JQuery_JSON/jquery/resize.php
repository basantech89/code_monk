<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/19/18
 * Time: 4:54 PM
 */

?>

<html>
<head>
</head>
<body>
    <p> Here is some awesome page content </p>
    <script type="text/javascript" src="../assets/js/jquery-3.3.1.js"></script>
    <script type="text/javascript">
        $(window).resize(function () {
            console.log('.resize() called. width = ' +
                $(window).width() + ' height = ' + $(window).height());
        });
    </script>
</body>
</html>
