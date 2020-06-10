<?php include_once "ICheckSession.php";
//This page refreshes the board state while a game is in play and refreshes the games status message displayed at the top of the screen
    if (isset($_POST['ttt_game_id']))
    {
        $_POST['ttt_game_id'] = htmlspecialchars($_POST['ttt_game_id']);
        //Asks client for the current game state give a game ID
        $response = $client->getGameState(['gid' => $_POST['ttt_game_id']]);

        //If response is -1, return -1. FAILED
        if($response->return == -1){
            $response = ['message' => -1];
        }
        //If SUCCESS then continue
        elseif ($response->return == 0){
            //Asks client to get the current board
            $response = $client->getBoard(['gid' => $_POST['ttt_game_id']]);
            if($response->return=='ERROR-DB'){
                $response = ['error' => "Database died"];
            }
            elseif($response->return=='ERROR-NOMOVES'){
                $response = ['error' => "Waiting for player", 'waiting' => true];
            }
            else{
                //run refresh function and return how many moves are left
                $return = Refresh($client);
                $response = ['message' => 0, 'moves' => $return['moves'], 'last' => $return['last']];
            }
        }
        //Signals that player 1 won
        elseif ($response->return == 1){
            $return = Refresh($client);
            $response = ['message' => 1, 'winner' => $return['last'], 'moves' => $return['moves']];
        }
        //Signals that player 2 won
        elseif ($response->return == 2){
            $return = Refresh($client);
            $response = ['message' => 2, 'winner' => $return['last'], 'moves' => $return['moves']];
        }
        //Signals that it was a draw
        elseif ($response->return == 3){
            $return = Refresh($client);
            $response = ['message' => 3, 'moves' => $return['moves']];
        }
        else{
            $response = ['error' => "ERROR"];
        }
        echo json_encode($response);
    }
    //Function to Update board
    function Refresh($client)
    {
        $response = $client->getBoard(['gid' => $_POST['ttt_game_id']]);
        $moves = [];
        foreach (explode("\n", $response->return) as $move)
        {
            $entity = explode(",", $move);
            array_push($moves, ['player_id' => $entity[0], 'x' => $entity[1], 'y' => $entity[2]]);
        }
        return ['moves' => $moves, 'last' => $moves[sizeof($moves)-1]['player_id']];
    }