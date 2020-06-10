
<?php include "ICheckSession.php";
//This interface is used for when a player selects a tile.
//It is communicated to by ajax by play.js and will update as long as an x-cord and y-cord is placed and updated
    if (isset($_POST['player_id']) && isset($_POST['game_id']) && isset($_POST['x_coord']) && isset($_POST['y_coord']))
    {
        $message = $client->takeSquare(['x' => htmlspecialchars($_POST['x_coord']),'y' => htmlspecialchars($_POST['y_coord']),'gid' => htmlspecialchars($_POST['game_id']), 'pid' => htmlspecialchars($_POST['player_id'])]);
        if($message->return== 0){
            $message = ['message' => -1];
        }
        elseif($message->return== 1){
            $message = $client->checkWin(['gid' => htmlspecialchars($_POST['game_id'])]);
            if($message->return==0){
                $message = ['message' => 0];
            }
            elseif($message->return==1){
                //player 1 won
                $client->setGameState(['gid' => htmlspecialchars($_POST['game_id']), 'gstate' => 1]);
                $message = ['message' => 1];
            }
            elseif($message->return==2){
                //player 2
                $client->setGameState(['gid' => htmlspecialchars($_POST['game_id']), 'gstate' => 2]);
                $message = ['message' => 2];
            }
            elseif($message->return==3){
                //draw
                $client->setGameState(['gid' => htmlspecialchars($_POST['game_id']), 'gstate' => 3]);
                $message = ['message' => 3];
            }
        }
        elseif($message->return== 'ERROR-TAKEN'){
            $message = ['error' => 'The square is already taken!'];
        }
        else{
            $message = ['error' => 'ERROR'];
        }
        echo $message;
    }
