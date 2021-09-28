<?php

$mysql = new mysqli(
    "localhost",
    "root",
    "",
    "barber"
);

if ($mysql->connect_error){
    die("Error al conectarse" . $mysql->connect_error);
}