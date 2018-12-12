<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/4/18
 * Time: 9:33 AM
 */

$za = array();
$za['name'] = "Chuck";
$za['course'] = "W4AE";

if (array_key_exists('Course', $za))
    echo "Course exists\n";
else
    echo "Course doesn't exists\n";

print_r(isset($za['addr']));
var_dump(isset($za['addr']));
echo isset($za['name']) ? "name is set\n" : "name is not set\n";
echo isset($za['addr']) ? "addr is set\n" : "addr is not set\n";

// PHP >= 7.0 only -- Null Coalesce operator
$name = $za['name'] ?? "not found";
$addr = $za['addr'] ?? "not found";
echo "\nName=$name\nAddr=$addr\n\n";

// PHP < 7.0 equivalent
$name2 = isset($za['name']) ? $za['name'] : "not found";
echo $name2 . "\n";

print "Count: " . count($za) . "\n";
if (is_array($za))
    echo '$za is an array' . "\n";
else
    echo '$za isn\'t an array' . "\n";

$za["topic"] = "PHP";
print_r($za);
/*
 * worst kind of sort in PHP, it sort the numerical arrays but doesn't
sort key value pair arrays very well, actually it remove the keys
*/
sort($za);
print_r($za);

$za = array("name" => "Chuck", "Course" => "W4AE", "topic" => "PHP");

ksort($za);
print_r($za);
asort($za);
print_r($za);
shuffle($za);
print_r($za);
var_dump($za);

// Exploding Arrays
$inp = "This is a sentence with seven words";
$temp = explode(' ', $inp);
print_r($temp);
