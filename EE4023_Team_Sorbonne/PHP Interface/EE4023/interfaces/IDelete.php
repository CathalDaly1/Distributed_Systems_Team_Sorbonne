<?php include "ICheckSession.php";
    if (isset($_POST['ttt_player_id']) && isset($_POST['ttt_game_id']))
    {
        //Using the delete game fucntion in the webservice to delete a game created by the user of that game
        $response = $client->deleteGame(['gid' => htmlspecialchars($_POST['ttt_game_id']), 'uid' => htmlspecialchars($_POST['ttt_player_id'])]);
        //We didn't want to a delete game that was in progress
        if($response->return=='ERROR-GAMESTARTED'){
            $response = ['error' => 'Game has already started!'];
        }
        elseif ($response->return=='ERROR-DB'){
            $response = ['error' => 'Error accessing database!'];
        }
        else{
            //Refresh the page is success
            header("Location: /EE4023/game-list.php");
        }
        echo json_encode($response);
    }

