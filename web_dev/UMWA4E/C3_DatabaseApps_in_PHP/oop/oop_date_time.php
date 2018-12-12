<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 10:40 AM
 */
date_default_timezone_set('Asia/Kolkata');

$nextWeek = time() + (7 * 24 * 60 * 60);
echo "Now:      " . date('Y-m-d') . "\n";
echo "Next Week:        " . date('Y-m-d', $nextWeek) . "\n";

echo "===============\n";

$now = new DateTime();
$nextWeek = new DateTime('today + 1 week');
echo "Now:      " . $now->format('Y-m-d'). "\n";
echo "Next Week:      " . $nextWeek->format('Y-m-d'). "\n";
