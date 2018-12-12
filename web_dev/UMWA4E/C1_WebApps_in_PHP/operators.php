<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/3/18
 * Time: 12:49 PM
 */
$x = "15" + 27;
echo $x;
echo "\n";
echo $x, "\n";
print $x;
PRINT "\n";
print($x);
print "\n";
print $x;
print "\n";
$y = "15" . 27;
echo $y . "\n";

$www = 123;
$msg = $www > 100 ? "Large" : "Small";
echo "First: $msg \n";
$msg = $www % 2 == 0 ? "Even" : "Odd";
echo "Second: $msg \n";
$msg = $www % 2 ? "Odd" : "Even";
echo "Third: $msg \n";

echo "Casting\n";
$a = 56; $b = 12;
$c = $a / $b;
echo "C: $c\n";
$d = "100" + 36.25 + TRUE;
echo "D: " . $d . "\n";
echo "D2: " . (string) $d . "\n";
$e = (int) 9.9 - 1;
echo "E: $e\n";
//$f = "sam" + 25;
//echo "F: $f\n";
$g = "sam" . 25;
echo "G: $g\n";

echo "Focus on below outputs\n";
echo "A" . FALSE . "B\n";
echo "X" . TRUE . "Y\n";
echo TRUE;
echo FALSE;
echo "\n";


echo "Equality vs Identity\n";
if (123 == "123") print "Equality 1\n";
if (123 == "100" + 23) print "Equality 2\n";
if (FALSE == "0") print "Equality 3\n";
if (5 < 6 == "2" - "1") print "Equality 4\n";
if (5 < 6 === "2" - "1") print "Equality 5\n";
if (5 < 6 === TRUE) print "Equality 5\n";


$vv = "Hello World!";
echo "First: " . strpos($vv, "Wo"). "\n";
echo "Second: " . strpos($vv, "He"). "\n";
echo "Third: " . strpos($vv, "zz"). "\n";
if (strpos($vv, "He") == FALSE) echo "Wrong A\n";
if (strpos($vv, "ZZ") == FALSE) echo "Right B\n";
if (strpos($vv, "He") !== FALSE) echo "Right C\n";
if (strpos($vv, "ZZ") === FALSE) echo "Right D\n";
print_r(FALSE); print FALSE;

echo '\'' . "\n";
echo '\'' . "\n";
echo "\"" . "\n";
