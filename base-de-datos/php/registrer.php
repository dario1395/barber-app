<?php

        require_once("dbbarber.php");

        $nombre = $_POST['nombre'];
        $apellidos = $_POST['apellidos'];
        $usuario = $_POST['usuario'];
        $password = $_POST['password'];
        $email = $_POST['email'];
        $phone = $_POST['phone'];

        $query = "INSERT INTO usuarios (nombre, apellidos, usuario, password, email, phone) VALUES ('$nombre', '$apellidos', '$usuario', '$password', '$email', '$phone')";

        $verificar_correo = mysqli_query($mysql, "SELECT * FROM usuarios WHERE email='$email' or usuario='$usuario'");

        if(mysqli_num_rows($verificar_correo) > 0) {
            echo "ERROR! El email o usuario ya está en uso";

            exit();
        }

        if(empty($nombre) || empty($apellidos) || empty($usuario) || empty($password) || empty($email) || empty($phone)) {

            // SI ALGUNA VARIABLE ESTA VACIA MUESTRA ERROR
            //echo "Se deben llenar los dos campos";
            echo "ERROR! Alguno de los campos está vacío";
    
        } else {


        $query = "INSERT INTO usuarios (nombre, apellidos, usuario, password, email, phone) VALUES ('$nombre', '$apellidos', '$usuario', '$password', '$email', '$phone')";
        $result = $mysql->query($query);


        if($result === TRUE) {

            echo "Registrado con éxito";

        }

    }

    mysqli_close($mysql);



?>