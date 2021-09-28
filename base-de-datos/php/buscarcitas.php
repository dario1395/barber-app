<?php
if($_SERVER['REQUEST_METHOD'] == 'GET'){

    require_once("dbbarber.php");

    $username = $_GET['username'];

    $query ="SELECT cita, servicio FROM citas WHERE username = '$username'";
    $result = $mysql->query($query);

    if ($mysql->affected_rows > 0){
        while($row = $result->fetch_assoc()){
            $arry = $row;
        }

        echo json_encode($arry);

    }else{
        echo "No se encontró ninguna columna";
    }


    $result->close();
    $mysql->close();
}