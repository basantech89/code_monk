<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/31/18
 * Time: 10:35 PM
 */

session_start();

if ( isset($_POST['reset']) ) {
    $_SESSION['chats'] = Array();
    header("Location: index.php");
    return;
}

if ( isset($_POST['message']) ) {
    if ( !isset($_SESSION['chats']) ) $_SESSION['chats'] = Array();
    $_SESSION['chats'][] = array($_POST['message'], date(DATE_RFC2822));
    header("Location: index.php");
    return;
}

?>

<html>
<head>
    <script type="text/javascript" src="../assets/js/jquery-3.3.1.js"></script>
</head>
<body>
<h1> Chat </h1>
<form method="post" action="index.php">
    <p>
        <input type="text" name="message" size="60" />
        <input type="submit" value="Chat" />
        <input type="submit" name="reset" value="Reset" />
    </p>
</form>
<div id="chatcontent">
    <img src="../assets/spinner.gif" alt="Loading..." />
</div>

<script type="text/javascript">
    function updateMsg() {
        window.console && console.log("Requesting JSON");
        $.ajax({
            url: "chatlist.php",
            cache: false,
            success: function (data) {
                window.console && console.log("JSON Received");
                window.console && console.log(data);
                $("#chatcontent").empty();
                for (var i = 0; i < data.length; i++) {
                    entry = data[i];
                    $("#chatcontent").append(
                        "<p>" + entry[0] + "<br/>&nbsp;&nbsp;" + entry[1] + "</p>\n");
                }
                // JS code, call itself again in 4000 milliseconds (4 seconds)
                setTimeout('updateMsg()', 4000);
            }
        });
    }
    window.console && console.log("Startup complete");
    updateMsg(); // call this first time to get started
</script>

</body>
</html>
