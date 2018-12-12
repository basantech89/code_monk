<?php
/**
 * Created by PhpStorm
 * User: luv
 * Date: 12/5/18
 * Time: 7:24 PM
 */

$oldguess1 = isset( $_GET["guess1"] ) ? $_GET["guess1"] : "";
$oldguess2 = isset( $_GET["guess2"] ) ? $_GET["guess2"] : "";

?>

<p> Guessing game... </p>
<form method="get">
    <p>
        <label for="guess1"> Bad Code </label>
        <input type="text" name="guess1" id="guess1" size="40" value="<?= $oldguess1 ?>"/>
    </p>

    <!-- fix -->
    <p>
        <label for="guess2"> Good Code </label>
        <input type="text" name="guess2" id="guess2" size="40" value="<?= htmlentities( $oldguess2 ) ?>"/>
    </p>
    <input type="submit"/>
</form>


<?php
    echo "<pre>";
    print_r( $_GET );
    echo "</pre>";
?>