<?php

    if($_SERVER['REQUEST_METHOD'] == 'POST'){

        require_once("dbbarber.php");

        $cita = $_POST['cita'];
        $servicio = $_POST['servicio'];
        $username = $_POST['username'];

        $query = "INSERT INTO citas (cita, servicio, username) VALUES ('$cita', '$servicio', '$username')";

        $verificar_correo = mysqli_query($mysql, "SELECT * FROM citas WHERE cita='$cita'");

        if(mysqli_num_rows($verificar_correo) > 0) {
            echo "ERROR! Esa cita no está disponible";

            exit();
        }

        $query = "INSERT INTO citas (cita, servicio, username) VALUES ('$cita', '$servicio', '$username')";
        $result = $mysql->query($query);

        if ($result === TRUE){
            echo "La cita se creó correctamente";
        }else{
            echo "Error al crear la cita";
        }

        $mysql->close();
    }