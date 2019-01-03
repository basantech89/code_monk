<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/19/18
 * Time: 8:45 PM
 */

function flashMessages () {
    if ( isset($_SESSION['success']) ) {
        echo "<h3 style='color: green'>".htmlentities($_SESSION['success'])."</h3>\n";
        unset($_SESSION['success']);
    }

    if ( isset($_SESSION['error']) ) {
        echo "<h3 style='color: red'>".htmlentities($_SESSION['error'])."</h3>\n";
        unset($_SESSION['error']);
    }
}

function validateProfile () {
    if ( strlen($_POST['fname']) < 1 || strlen($_POST['lname']) < 1 || strlen($_POST['email']) < 1 ||
        strlen($_POST['headline']) < 1 || strlen($_POST['summary']) < 1) {
        return "All fields are required";
    }

    elseif ( strpos($_POST['email'], '@') === false ) {
        return "Email address must contain @";
    }

    return true;
}

function validatePos () {
    for ($i = 1; $i <= 9; $i++) {
        if ( ! isset($_POST['year'.$i]) ) continue;
        if ( ! isset($_POST['desc'.$i]) ) continue;
        $year = $_POST['year'.$i];
        $desc = $_POST['desc'.$i];

        if ( strlen($year) == 0 || strlen($desc) == 0 ) {
            return "All fields are required";
        }

        if ( ! is_numeric($year) ) {
            return "Position year must be numberic";
        }
    }

    return true;
}

function loadPos ($pdo, $profile_id) {
    $stmt = $pdo -> prepare('SELECT * FROM Position
        WHERE profile_id = :pid ORDER BY rank');
    $stmt -> execute(array(':pid' => $profile_id));

    $positions = array();
    while ( $row = $stmt -> fetch(PDO::FETCH_ASSOC) ) {
        $positions[] = $row;
    }

    return $positions;
}

function loadEdu ($pdo, $profile_id) {
    $stmt = $pdo -> prepare('SELECT year, name FROM Education
        JOIN Institution ON Education.institution_id = Institution.institution_id
        WHERE profile_id = :pid ORDER BY rank');
    $stmt -> execute(array(':pid' => $profile_id));
    $schools = $stmt -> fetchAll(PDO::FETCH_ASSOC);     // return array of multiple rows where each row itself is an array of key value pairs

    /* what fetchAll does -
     * $schools = array();
    while ( $row = $stmt -> fetch(PDO::FETCH_ASSOC) ) {
        $schools[] = $row;
    }
     * */

    return $schools;
}

function insertPos ($pdo, $profile_id) {
    // Insert the position entries
    $rank = 1;
    for ($i = 1; $i <= 9; $i++) {
        if ( ! isset($_POST['year'.$i]) ) continue;
        if ( ! isset($_POST['desc'.$i]) ) continue;
        $year = $_POST['year'.$i];
        $desc = $_POST['desc'.$i];

        $stmt = $pdo -> prepare('INSERT INTO Position
              (profile_id, rank, year, description)
              VALUES (:pid, :rank, :year, :desc)');
        $stmt -> execute(array(
                ':pid' => $profile_id,
                ':rank' => $rank,
                ':year' => $year,
                ':desc' => $desc)
        );
        $rank++;
    }
}

function insertEdu ($pdo, $profile_id) {
    $rank = 1;
    for ($i = 1; $i <= 9; $i++) {
        if ( ! isset($_POST['edu_year'.$i]) ) continue;
        if ( ! isset($_POST['edu_school'.$i]) ) continue;
        $year = $_POST['edu_year'.$i];
        $school = $_POST['edu_school'.$i];

        // Lookup the school if it is there
        $institution_id = false;
        $stmt = $pdo -> prepare('SELECT institution_id FROM Institution WHERE name = :name');
        $stmt -> execute(array(':name' => $school));
        $row = $stmt -> fetch(PDO::FETCH_ASSOC);
        if ( $row !== false ) $institution_id = $row['institution_id'];

        // If there was no institution, insert it
        if ( $institution_id === false ) {
            $stmt = $pdo -> prepare('INSERT INTO Institution (name) VALUES (:name)');
            $stmt -> execute(array(':name' => $school ));
            $institution_id = $pdo -> lastInsertId();
        }

        $stmt = $pdo -> prepare('INSERT INTO Education
              (profile_id, rank, year, institution_id)
              VALUES (:pid, :rank, :year, :iid)');
        $stmt -> execute(array(
                ':pid' => $profile_id,
                ':rank' => $rank,
                ':year' => $year,
                ':iid' => $institution_id)
        );
        $rank++;
    }
}