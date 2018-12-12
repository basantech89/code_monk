<?php
/**
 * Created by PhpStorm.
 * User: luv
 * Date: 12/9/18
 * Time: 11:30 AM
 */

class Hello {
    protected $lang;    // only accessible in this class

    function __construct( $lang ) {
        $this -> lang = $lang;
    }

    function greet() {
        if ( $this -> lang == "fr" ) return "Bonjour";
        if ( $this -> lang == "es" ) return "Hola";
        return "Hello";
    }
}

class Social extends Hello {
    function bye() {
        if ( $this -> lang == "fr" ) return "Au revoir";
        if ( $this -> lang == "es" ) return "Adios";
        return "Goodbye";
    }
}

//$hi = new Hello('es');
//echo $hi -> greet() . "\n";
$hi = new Social('es');
echo $hi -> greet() . "\n";
echo $hi -> bye() . "\n";
