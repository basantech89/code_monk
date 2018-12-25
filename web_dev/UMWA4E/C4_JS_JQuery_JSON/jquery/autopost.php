<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/19/18
 * Time: 7:43 PM
 */

?>

<form id="target">
    <input type="text" name="one" value="Hello there" style="vertical-align: middle;">
    <img src="../assets/spinner.gif" id="spinner" height="25"
         style="vertical-align: middle; display: none;">
</form>
<div id="result"></div>

<script type="text/javascript" src="../assets/js/jquery-3.3.1.js"></script>
<script type="text/javascript">
    $('#target').change(function (event) {
        event.preventDefault();
        $('#spinner').show();
        var form = $('#target');
        var txt = form.find('input[name="one"]').val();
        window.console && console.log('Sending POST');
        // data --> the response that comes back from POST
        $.post('autoecho.php', { 'val': txt }, function( data ) {
                window.console && console.log(data);
                $('#result').empty().append(data);
                $('#spinner').hide();
            }
        ).error( function() {
            window.console && console.log('error');
        });

        return false;
    });
</script>