<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/4/18
 * Time: 5:44 PM
 */

for ($i = 0000; $i < 10000; $i++) {
    if ( strlen($i) == 1 )
        $i = "000" . $i;
    elseif ( strlen($i) == 2 )
        $i = "00" . $i;
    elseif ( strlen($i) == 3 )
        $i = "0" . $i;
}