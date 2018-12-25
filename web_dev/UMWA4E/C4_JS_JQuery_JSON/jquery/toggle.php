<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/19/18
 * Time: 5:11 PM
 */
?>

<p id="para"> Where is the spinner?
    <img id="spinner" src="../assets/spinner.gif" height="25"
         style="vertical-align: middle; display: none;">
</p>

<a href="#" onclick="$('#spinner').toggle(); return false;"> Toggle </a>
<a href="#" onclick="$('#para').css('background-color', 'red'); return false;"> Red </a>
<a href="#" onclick="$('#para').css('background-color', 'green'); return false;"> Green </a>

<script type="text/javascript" src="../assets/js/jquery-3.3.1.js"></script>