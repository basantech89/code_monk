<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/5/18
 * Time: 6:20 PM
 */
require_once "header.php";
?>

<p> Many field types... </p>
<form method="post">

    <p>
        <label for="ip01"> Account: </label>
        <input type="text" name="account" id="ip01" size="40">
    </p>

    <p>
        <label for="ip02"> Password: </label>
        <input type="password" name="pw" id="ip02" size="40">
    </p>

    <p>
        <input type="radio" name="when" value="am" id="ip03">
        <label for="ip03"> AM </label> <br>
        <input type="radio" name="when" value="pm" id="ip04" checked>
        <label for="ip04"> PM </label>
    </p>

    <p>
        <label for="ip05"> SI502 - Networked Tech </label>
        <input type="checkbox" name="class1" id="ip05" value="si502" checked> <br>
        <label for="ip06"> SI539 - App Engine </label>
        <input type="checkbox" name="class2" id="ip06" value="si539" checked> <br>
        <label for="ip07"> SI543 - Java </label>
        <input type="checkbox" name="class3" id="ip07" value="si543">
    </p>

    <p>
        <label for="ip08"> Which soda: </label>
        <select name="soda" id="ip08">
            <option value="0"> -- Please Select -- </option>
            <option value="1"> Coke </option>
            <option value="2"> Pepsi </option>
            <option value="3" selected> Mountain Dew </option>
            <option value="4"> Orange Juice </option>
            <option value="5"> Lemonade </option>
        </select>
    </p>

    <p>
        <label for="ip09"> Which snack: </label>
        <select name="snack" id="ip09">
            <option value=""> -- Please Select -- </option>
            <option value="chips"> Chips </option>
            <option value="peanuts"> Peanuts </option>
            <option value="cookie" selected> Cookie </option>
        </select>
    </p>

    <p>
        <label for="ip10"> Tell us about urself: </label> <br>
        <textarea rows="10" cols="48" id="ip10" name="about">
            I love building web sites in PHP and MySQL
        </textarea>
    </p>

    <p>
        <label for="ip11"> Which are awesome? </label> <br>
        <select multiple="multiple" name="code[]" id="ip11">
            <option value="python"> Python </option>
            <option value="css"> CSS </option>
            <option value="html"> HTML </option>
            <option value="php"> PHP </option>
        </select>
    </p>

    <p>
        <input type="submit" name="dopost" value="Submit">
        <input type="button" value="Escape" onclick="location.href='https://www.google.com'; return false;">
    </p>

</form>

<?php
    echo "<pre>";
    print_r($_POST);
    echo "</pre>";
?>