<?php include "ICheckSession.php";
// Retireve username and password from form via POST, uses client to check if user credentials is correct
    $response = $client->login(array('username' => htmlspecialchars($_POST['username']), 'password' => htmlspecialchars($_POST['password'])));
//    Returns player ID, since player ID will always be above 0, we set session details
    if ($response->return > 0)
    {
        //sets username and uid for user, and this will be used to check for log on
        //Not the mos t effective method for holding a log on but wanted to focus on core functionality for time being
        $_SESSION['ttt_username'] = $_POST['username'];
        $_SESSION['ttt_player_id'] = $response->return;
        header("Location: /EE4023/home.php");
        die();
    }
    else
    {
        //If user login was invalid, we send you back to log in and register
        header("Location: /EE4023/index.php");
        die();
    }
