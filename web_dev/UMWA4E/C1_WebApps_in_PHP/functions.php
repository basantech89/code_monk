<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/4/18
 * Time: 2:24 PM
 */

$str = "Hello World!";
echo strrev($str) . "\n";
echo str_repeat("bla ", 4) . "\n";
echo strtoupper("hooray!!") . "\n";
echo strlen($str) . "\n";

function greet() {
    print "Hello\n";
}

greet();
GrEEt();

function howdy ($lang = "es") {
    if ( $lang == "es" ) return "Hola";
    if ( $lang == "fr" ) return "Bonjour";
    return "Hello";
}

print howdy() . " Glenn\n";
print howdy('fr') . " Sally\n";

// call by value
function double($alias) {
    $alias *= 2;
    return $alias;
}

$val = 10;
$dval = double($val);
echo "Val = $val Doubled = $dval\n";

// call by reference
function triple(&$realthing) {
    $realthing *= 3;
    return $realthing;
}

$val = 10;
$trip = triple($val);
echo "Triple $trip Val = $val\n";



















