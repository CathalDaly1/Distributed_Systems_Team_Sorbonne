<?php include "ICheckSession.php";
if (isset($_POST['_player_id']) && isset($_POST['_game_id']))
{
    $response = $client->joinGame(['gid' => htmlspecialchars($_POST['_game_id']), 'uid' => htmlspecialchars($_POST['_player_id'])]);
    if($response->return=='ERROR-DB'){
        $response = ['error' => 'Database died'];
    }
    elseif ($response->return==0){
        //User didn't connect to game correctly
        $response = ['message' => 0];
    }
    else{
        //Joined game successfully , send response back with game ID
        $response = ['message' => 1, 'game_id' => htmlspecialchars($_POST['_game_id'])];
    }
    echo json_encode($response);
}

