<?php
    $message = $client->showMyOpenGames(['uid' => $_SESSION['ttt_player_id']]);
    switch($message->return)
    {
        case 'ERROR-NOGAMES':
            echo "<div>No games found!</div>";
            break;
        default:
            foreach (explode("\n", $message->return) as $row)
            {
                echo "<div class=\"data-row ";
                echo "\">";
                    $i = 0;
                    $items = explode(",", $row);
                    foreach ($items as $item)
                    {
                        echo "<div class=\"open-game-row-item";
                        if ($i == 2)
                            $item = date_format(date_create($item), "dS F H:i");
                        echo "\">$item</div>";
                        $i++;
                    }
                    displayDelete($items[0]);
                    displayJoin($items[0]);
                echo "</div>";
            }
            break;
    }
    function displayJoin($item){
        echo "<div class=\"open-game-row-item\">";
            echo "<form method=\"POST\" action=\"/EE4023/play.php\">";
                echo "<input name=\"ttt_game_id\" type=\"hidden\" value=\"" . $item . "\" />";
                echo "<input name=\"ttt_player_id\" type=\"hidden\" value=\"" . $_SESSION['ttt_player_id'] ."\" />";
                echo "<input type=\"submit\" class=\"open-game-row-item\" value=\"Open\" />";
            echo "</form>";
        echo "</div>";
    }
    function displayDelete($item){
        echo "<div class=\"open-game-row-item\">";
            echo "<form method=\"POST\" action=\"/EE4023/interfaces/IDelete.php\">";
                echo "<input name=\"ttt_game_id\" type=\"hidden\" value=\"" . $item . "\" />";
                echo "<input name=\"ttt_player_id\" type=\"hidden\" value=\"" . $_SESSION['ttt_player_id'] ."\" />";
                echo "<input type=\"submit\" class=\"open-game-row-item\" value=\"Delete\" />";
            echo "</form>";
        echo "</div>";
    }