<?php
    $gamesWon = 0;
    $gamesLost = 0;
    $gamesDrawn = 0;
    $gamesOnGoing = 0;
    $leaderboardSplit =  explode("\n", $client->leagueTable()->return);
    for($i = 0; $i < sizeof($leaderboardSplit); $i++){


        $details = explode(",",$leaderboardSplit[$i]);

        //Checks if your username was PLAYER 1 of that game, and determines if you won/drew/lost/ongoing
        if($details[1] == $_SESSION['ttt_username']){
            if($details[3] == 1){
                $gamesWon++;
            }
            else if($details[3] == 2){
                $gamesLost++;
            }
            else if($details[3] == 3){
                $gamesDrawn++;
            }else{
                $gamesOnGoing++;
            }
        }
        //Checks if your username was PLAYER 2 of that game, and determines if you won/drew/lost/ongoing
        else if($details[2] == $_SESSION['ttt_player_id']){
            if($details[3] == 2){
                $gamesWon++;
            }
            else if($details[3] == 1){
                $gamesLost++;
            }
            else if($details[3] == 3){
                $gamesDrawn++;
            }
            else{
                $gamesOnGoing++;
            }
        }
    }
    ?>
<!DOCTYPE html>
<html>

<head>
    <br><h2> <?php echo "Username:" . $_SESSION['ttt_username'] . "<br>"; ?> </h2>
    <h2>  <?php echo "Games Won:" . strval($gamesWon). "<br>";?> </h2>
    <h2>  <?php echo "Games Lost:" . strval($gamesLost). "<br>";?> </h2>
    <h2>  <?php echo "Games Drawn:" . strval($gamesDrawn). "<br>";?> </h2>
    <h2>  <?php echo "Games Ongoing:" . strval($gamesDrawn). "<br>";?> </h2>
</head>


