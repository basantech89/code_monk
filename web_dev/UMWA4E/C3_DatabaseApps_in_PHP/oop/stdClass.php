<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 11:58 AM
 */

// old school way of creating classes in PHP
$player = new stdClass();

$player->name = "Chuck";
$player->score = 0;

$player->score++;

print_r($player);

class Player {
    public $name = "Sally";
    public $score = 0;
}

$p2 = new Player();
$p2 -> score++;

print_r($p2);