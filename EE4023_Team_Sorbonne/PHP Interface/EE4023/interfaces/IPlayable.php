<?php include "ICheckSession.php";
//This interface is used to join a game
    if (isset($_POST['_game_request']))
    {
        $response = $client->showOpenGames();
        if($response->return=='ERROR-NOGAMES'){
            $response = ['error' => "NO GAMES!"];
        }
        elseif ($response->return=='ERROR-DB'){
            $response = ['error' => "Database Died"];
        }
        else{
            $game_list = [];
            foreach (explode("\n", $response->return) as $row)
            {
                $row = explode(",", $row);
                array_push($game_list, ['id' => $row[0], 'host' => $row[1], 'date-created' => $row[2]]);
            }
            $response = $game_list;
        }
        echo json_encode($response);
    }


