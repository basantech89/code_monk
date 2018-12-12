<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/4/18
 * Time: 8:33 AM
 */

$stuff = array("name" => "Chuck", "course" => "WA4E");
echo "<pre>\n";
print_r($stuff);
echo "\n</pre>\n";
echo "<pre>\n";
var_dump($stuff);
echo "\n</pre>\n";

$thing = FALSE;
echo "One\n";
print_r($thing);
echo "Two\n";
var_dump($thing);

$va = array();
$va[] = "Hello";
$va[] = "World";
print_r($va);

$za = array();
$za["name"] = "bla";
$za["Course"] = "blabla";
print_r($za);

foreach ($stuff as $x => $y) {
    echo "Key=$x Val=$y\n";
}

echo "\n";

for ($i = 0; $i < count($va); $i++)
    echo "I=", $i, " Val=", $va[$i], "\n";

echo "\n2-D Arrays\n";

$products = array(
    'paper' => array(
        'copier' => "Copier and Multipurpose",
        'inkjet' => "Inkjet Printer",
        'laster' => "Laser Printer",
        'photo' => "Photographic Paper"),
    'pens' => array(
        'ball' => "Ball Point",
        'hilite' => "Highlighters",
        'marker' => "Markers"),
    'misc' => array(
        'tape' => "Sticky Tape",
        'glue' => "Adhesives",
        'clips' => "Paperclips")
);

echo $products["pens"]["marker"];
echo "\n\n";
print_r($products);

if (array_key_exists('Course', $za))
    echo "Course exists\n";
else
    echo "Course doesn't exists\n";

echo isset($za['name']) ? "name is set\n" : "name is not set\n";
echo isset($za['addr']) ? "addr is set\n" : "addr is not set\n";

// PHP >= 7.0 only -- Null Coalesce operator
$name = $za['name'] ?? "not found";
$addr = $za['addr'] ?? "not found";
echo "\nName=$name\nAddr=$addr\n\n";

