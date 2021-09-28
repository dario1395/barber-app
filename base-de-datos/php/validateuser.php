<?php
    include 'dbbarber.php';
    $usuario= $_POST['usuario'];
    $password= $_POST['password'];

    $sentencia=$mysql->prepare("SELECT * FROM usuarios WHERE usuario=? AND password=?");
    $sentencia->bind_param('ss',$usuario, $password);
    $sentencia->execute();

    $resultado = $sentencia->get_result();
    if ($fila = $resultado->fetch_assoc()) {
        echo json_encode($fila,JSON_UNESCAPED_UNICODE);
    }
    $sentencia->close();
    $mysql->close();
    ?>