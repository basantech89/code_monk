<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/3/18
 * Time: 12:23 PM
 */

echo 'this is a simple string\n';

echo 'You can alos have embedded newlines in
        strings this way as it is
        okay to do\n';

echo 'Arnold once said: "I\'ll be back"\n';
// Outputs: This will expand:
echo 'This will expand: \na newline\n';

$expand = 12;
echo 'Variables do $expand\n';
echo "\n";

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

// Comments in PHP -- c++ style
/* c style
    Multiline Comment
    Mostly used for documenting the code */
# bash and perl style comments also work