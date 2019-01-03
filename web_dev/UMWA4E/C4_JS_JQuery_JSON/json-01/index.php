<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/31/18
 * Time: 4:48 PM
 */

?>

<html>
<head>

</head>
<body>
<p> Check out console.log to see the cool object </p>
<script type="text/javascript" src="../assets/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
    $(document).ready( function () {
        // do a get request for json.php and pass the
        // response as argument (data) in below function
        // getJSON automatically decode the response string into a JS object
        $.getJSON('json.php', function (data) {
            $('#back').html(data.first);
            window.console && console.log(data);
        })
    })
</script>

<p id="back"> before </p>

</body>
</html>

