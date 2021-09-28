<?php
    if ($_SERVER['REQUEST_METHOD'] == 'POST') {

        require_once("dbbarber.php");
        $username = $_POST['username'];

        $query = "DELETE FROM citas WHERE username = '$username'";
        $result = $mysql->query($query);

        if ($mysql->affected_rows > 0) {
            if ($result == TRUE){
                echo "Se canceló su cita correctamente";
            }
        }else{
            echo "No se canceló, porque no tienes ninguna cita pendiente";
        }

        $mysql->close();
    }