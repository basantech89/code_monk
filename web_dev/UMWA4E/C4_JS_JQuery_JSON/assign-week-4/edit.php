<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/14/18
 * Time: 9:15 PM
 */

require_once "../assets/pdo.php";
require_once "util.php";
session_start();

if ( ! isset($_SESSION['name']) ) {
    die("Not Logged In");
}

// Guardian first_name sure that profile_id is present
if ( ! isset($_GET['profile_id']) ) {
    $_SESSION['error'] = "Missing profile_id";
    header("Location: index.php");
    return;
}

if ( isset($_POST['fname']) && isset($_POST['lname']) && isset($_POST['email']) &&
    isset($_POST['headline']) && isset($_POST['summary']) && isset($_POST['profile_id']) ) {

    // Validate profile
    $msg = validateProfile();
    if ( is_string($msg) ) {
        $_SESSION['error'] = $msg;
        header("Location: edit.php?profile_id=".$_REQUEST['profile_id']);
        return;
    }

    // Validate position entries if present
    $msg = validatePos();
    if ( is_string($msg) ) {
        $_SESSION['error'] = $msg;
        header("Location: edit.php?profile_id=".$_REQUEST['profile_id']);
        return;
    }

    $sql = "UPDATE Profile SET first_name = :fname, last_name = :lname,
                 email = :em, headline = :headline, summary = :summary WHERE profile_id = :pid AND user_id=:uid";
    $stmt = $pdo -> prepare($sql);
    $stmt -> execute(array(
        ':fname' => $_POST['fname'],
        ':lname' => $_POST['lname'],
        ':em' => $_POST['email'],
        ':headline' => $_POST['headline'],
        ':summary' => $_POST['summary'],
        ':pid' => $_POST['profile_id'],
        ':uid' => $_SESSION['user_id']
    ));

    // Clear out the old position entries
    $stmt = $pdo -> prepare('DELETE FROM Position WHERE profile_id=:pid');
    $stmt -> execute(array(':pid' => $_REQUEST['profile_id']));

    insertPos($pdo, $_REQUEST['profile_id']);

    // Clear out the old education entries
    $stmt = $pdo -> prepare('DELETE FROM Education WHERE profile_id=:pid');
    $stmt -> execute(array(':pid' => $_REQUEST['profile_id']));
    
    insertEdu($pdo, $_REQUEST['profile_id']);

    $_SESSION['success'] = "Record edited";
    header("Location: index.php");
    return;
}

// Select the profile_id to be edited
$stmt = $pdo -> prepare("SELECT * FROM Profile WHERE profile_id = :id");
$stmt -> execute(array(':id' => $_GET['profile_id']));
$row = $stmt -> fetch(PDO::FETCH_ASSOC);
if ( $row === false ) {
    $_SESSION['error'] = "Bad value for profile_id";
    header('Location: index.php');
    return;
}

// Checking if user own the profile data entry
if ( $_SESSION['user_id'] !== $row['user_id'] ) {
    $_SESSION['error'] = "Pl choose profile owned by you";
    header("Location: index.php");
    return;
}

// Load up the position and education rows
$positions = loadPos($pdo, $_REQUEST['profile_id']);
$schools = loadEdu($pdo, $_REQUEST['profile_id']);

?>

<?php
require_once "../assets/header.php";
require_once "../assets/bootstrap.php";

flashMessages();

$fname = htmlentities($row['first_name']);
$lname = htmlentities($row['last_name']);
$em = htmlentities($row['email']);
$headline = htmlentities($row['headline']);
$summary = htmlentities($row['summary']);
$id = $row['profile_id'];

?>

<h3> Editing Profile </h3>
<form method="post">
    <p>
        <label for="fname"> First Name: </label>
        <input type="text" id="fname" name="fname" value="<?= $fname ?>"> <br>
    </p>
    <p>
        <label for="lname"> Last Name: </label>
        <input type="text" id="lname" name="lname" value="<?= $lname ?>"> <br>
    </p>
    <p>
        <label for="email"> Email: </label>
        <input type="text" id="email" name="email" value="<?= $em ?>"> <br>
    </p>
    <p>
        <label for="headline"> Headline: </label>
        <input type="text" id="headline" name="headline" value="<?= $headline ?>"> <br>
    </p>
    <p>
        <label> Summary: <br>
            <textarea rows="8" cols="80" name="summary"> <?= $summary ?> </textarea>
        </label> <br>
    </p>

    <?php
        $countEdu = 0;
        echo "<p> Education: <input type='submit' id='addEdu' value='+'>"."\n";
        echo '<div id="edu_fields">'."\n";
        if ( count($schools) > 0 ) {
            foreach ( $schools as $school ) {
                $countEdu++;
                echo '<div id="edu'.$countEdu.'">';
                echo '<p> 
                        Year: <input type="text" name="edu_year' .$countEdu.'" value="'.$school['year'].'" />
                        <input type="button" value="-" onclick="$(\'#edu'.$countEdu.'\').remove(); return false;">
                      </p>
                      <p>
                        School: <input type="text" size="80" name="edu_school'.$countEdu.'" class="school" 
                        value="'.htmlentities($school['name']).'" />
                      </p>';
                echo "\n</div>\n";
            }
        }
        echo "</div></p>\n";

        $pos = 0;
        echo('<p> Position: <input type="submit" id="addPos" value="+">'."\n");
        echo('<div id="position_fields"></div>'."\n");
        foreach ( $positions as $position ) {
            $pos++;
            echo('<div id="position'.$pos.'">'."\n");
            echo('<p> Year: <input type="text" name="year'.$pos.'"'."\n");
            echo(' value="'.$position['year'].'"/>'."\n");
            echo('<input type="button" value="-" ');
            echo('onclick="$(\'#position'.$pos.'\').remove();return false;">'."\n");
            echo "</p>\n";
            echo('<textarea name="desc'.$pos.'" rows="8" cols="80">'."\n");
            echo(htmlentities($position['description'])."\n");
            echo("\n</textarea>\n</div>\n");
        }
        echo("\n</div></p>\n");
    ?>
    <input type="hidden" name="profile_id" value="<?= $id ?>"
    <p> <input type="submit" value="Update" name="update"> </p>
    <p> <a href="index.php"> Cancel </a> </p>
</form>

<script>
    countPos = <?= $pos ?>;
    countEdu = <?= $countEdu ?>;
    
    $(document).ready(function () {
        window.console && console.log('Document ready called');
        
        $('#addPos').click(function (event) {
            event.preventDefault();

            if (countPos >= 9) {
                alert("Maximum of nine positions entries exceeded");
                return;
            }

            countPos++;
            window.console && console.log("Adding position " + countPos);

            $('#position_fields').append(
                '<div id="position' + countPos + '"> \
                <p> Year: <input type="text" name="year' + countPos + '" value="" /> \
                <input type="button" value="-" \
                    onclick="$(\'#position' + countPos + '\').remove(); return false;"> </p> \
                <textarea name="desc' + countPos + '" rows="8" cols="80"></textarea> \
                </div>');
        });

        $('#addEdu').click(function (event) {
            event.preventDefault();

            if (countEdu >= 9) {
                alert("Maximum of nine education entries exceeded");
                return;
            }

            countEdu++;
            window.console && console.log("Adding education " + countEdu);

            // Grab some HTML with hot spots and inert into the DOM
            var source = $('#edu-template').html();
            $('#edu_fields').append(source.replace(/@COUNT@/g, countEdu));

            // Add the event handler to the new ones
            $('.school').autocomplete({
                source: "school.php"
            });

        });

        $('.school').autocomplete({
            source: "school.php"
        });

    });

</script>

<!-- HTML with substitution hot spots -->
<script id="edu-template" type="text">
    <div id="edu@COUNT@">
        <p>
            Year: <input type="text" name="edu_year@COUNT@" value="" />
            <input type="button" value="-" onclick="$('#edu@COUNT@').remove(); return false;"><br>
            <p> School: <input type="text" size="80" name="edu_school@COUNT@" class="school" value="" /> </p>
        </p>
</script>