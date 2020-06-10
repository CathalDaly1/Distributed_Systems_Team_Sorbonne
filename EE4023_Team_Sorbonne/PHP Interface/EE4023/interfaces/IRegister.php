<?php  include "ICheckSession.php";
    $name = htmlspecialchars($_POST['name']);
    $surname = htmlspecialchars($_POST['surname']);
    $username = htmlspecialchars($_POST['username']);
    $password = htmlspecialchars($_POST['password']);
    $response = $client->register(array('username' => $username, 'password' => $password, 'name' => $name, 'surname' => $surname));
    if ($response->return > 0)
    {
        header("Location: /EE4023/index.php");
        die();
    }
    else
    {
        header("Location: /EE4023/index.php");
        die();
    }


