<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 11:43 AM
 */

class MyClass {
    public $pub = "public";
    protected $pro = "protected";
    private $priv = "private";

    function printHello () {
        echo $this -> pub . "\n";
        echo $this -> pro . "\n";
        echo $this -> priv . "\n";
    }
}

class MyClass2 extends MyClass {
    function printHello()
    {
        echo $this->pub . "\n";
        echo $this->pro . "\n";
//        echo $this->priv . "\n"; // not gonna work
    }
}


$obj = new MyClass();
$obj2 = new MyClass2();
//echo $obj -> pub . "\n";
//echo $obj -> pro . "\n";
//echo $obj -> priv . "\n";
//$obj -> printHello();
$obj2 -> printHello();