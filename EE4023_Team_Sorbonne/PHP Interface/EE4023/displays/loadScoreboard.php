<?php
    $leagueTables =  $client->leagueTable();
    $gameDetails =  explode("\n",$leagueTables->return);
    $arrayOfUsers = array();
    $amountOfWins = 0;
    $playerOne = true;
    $playerTwo = true;
    $gamesOnGoing = 0;
    $amountOfLoses = 0;
    $draws = 0;
    for($i = 0; $i < sizeof($gameDetails); $i++) {
        $details = explode(",", $gameDetails[$i]);
        //Checks if user has already been displayed on leaderboard
        for($j = 0; $j < sizeof($arrayOfUsers); $j++ ){
            if($details[1] == $arrayOfUsers[$j]){
                $playerOne =  false;
            }
        }
        //Checks if user has already been displayed on leaderboard
        for($k = 0; $k < sizeof($arrayOfUsers); $k++ ){
            if($details[2] == $arrayOfUsers[$k]){
                $playerTwo =  false;
            }
        }
        //push player 1  to array
        if($playerOne){
            array_push($arrayOfUsers,$details[1]);
        }
        //push player 2 to array
        if($playerTwo){
            array_push($arrayOfUsers,$details[2]);
        }
        $playerOne = true;
        $playerTwo = true;
    }
    for($i = 0; $i < sizeof($arrayOfUsers); $i++){
        for($j = 0; $j < sizeof($gameDetails); $j++){
            $details = explode(",",$gameDetails[$j]);
            if($details[1] == $arrayOfUsers[$i]){
                if($details[3] == 1){
                    $amountOfWins++;
                }
                else if($details[3] == 2){
                    $amountOfLoses++;
                }
                else if($details[3] == 3){
                    $draws++;
                }
                else{
                    $gamesOnGoing++;
                }
            }else if($details[2] == $arrayOfUsers[$i]){
                if($details[3] == 2){
                    $amountOfWins++;
                }
                else if($details[3] == 1){
                    $amountOfLoses++;
                }
                else if($details[3] == 3){
                    $draws++;
                }
                else{
                    $gamesOnGoing++;
                }
            }
        }
        echo "<br><b><h3>"  . $arrayOfUsers[$i] . "</h3></b><br>";
        echo "Wins: " . strval($amountOfWins). "<br>";
        echo "Loses: " . $amountOfLoses. "<br>";
        echo "Draws: " . $draws. "<br><br><br>";
        $amountOfWins = 0;
        $amountOfLoses = 0;
        $draws = 0;
    }

