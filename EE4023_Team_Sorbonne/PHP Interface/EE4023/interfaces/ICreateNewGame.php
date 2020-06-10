<?php include "ICheckSession.php";
if (isset($_POST['player_id']))
{
//    Uses soap client to send a newGame request using the player ID provided
    $message = $client->newGame(['uid' => htmlspecialchars($_POST['player_id'])]);
    if($message->return == 'ERROR-NOTFOUND'){
        $message = array('message' => 'Game data lost');
    }
    elseif ($message->return == 'ERROR-RETRIEVE'){
        $message = array('message' => 'Game data lost');
    }
    elseif ($message->return == 'ERROR-INSERT'){
        $message = array('message' => 'Game could not be inserted into Database');
    }
    elseif ($message->return == 'ERROR-DB'){
        $message = array('message' => 'Database Died');
    }
    else{
        //returns game id if game creation is successful
        $message = ['game_id' => $message->return];
    }
    //returns json message with gameid and gid
    echo json_encode($message);
}

