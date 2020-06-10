<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Play Game</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="scripts/play.js"></script>
        <link rel="stylesheet" href="css/stylesheet.css" />
        <?php include "interfaces/ICheckSession.php"; ?>
        <?php include "interfaces/ICheckLogin.php"; ?>
    </head>
    <body class="body">
        <div class="header">
            <h1>Welcome to Tic Tac Toe!</h1>
            <h3>Team Sorbonne</h3>
            <div class="header-right">
                <a href="home.php">Home</a>
                <a href="score.php">My Scores</a>
                <a href="scoreboard.php">Leaderboard</a>
                <a href="game-list.php">My Open Games</a>
            </div>
        </div>
        <div class="content">
            <br><h2>Select tile to make first move:</h2>
            <div id="game-info">
<!--                This is the message that keeps the player updated on the state of the game-->
                <div id="game-status-message"></div>
            </div>
            <section class="play">
                <section id="play-grid">
<!--                    Here is the board, seperated into x / y cords. -->
<!--                    We got inspiration on using a board from the following locations, although we used our own method to create it-->
<!--                    https://stackoverflow.com/questions/47842209/creating-checkers-game-with-javascript-->
<!--                    https://codepen.io/calincojo/pen/wBQqYm-->
<!--                    We only required the HTML side of their board to see how they displayed it correctly-->
                    <div x="0" y="0" class="play-grid-tile cell"></div>
                    <div x="1" y="0" class="play-grid-tile cell"></div>
                    <div x="2" y="0" class="play-grid-tile cell"></div>
                    <div x="0" y="1" class="play-grid-tile cell"></div>
                    <div x="1" y="1" class="play-grid-tile cell"></div>
                    <div x="2" y="1" class="play-grid-tile cell"></div>
                    <div x="0" y="2" class="play-grid-tile cell"></div>
                    <div x="1" y="2" class="play-grid-tile cell"></div>
                    <div x="2" y="2" class="play-grid-tile cell"></div>
                </section>
            </section>
        </div>
        <script>
            // Setting these variables so the play.js file has them ready to use
            //Essentially they allow the JS file to find out the player Id and game ID
            var pid = <?php echo htmlspecialchars($_POST["ttt_player_id"]); ?>;
            var gid = <?php echo htmlspecialchars($_POST["ttt_game_id"]); ?>;
        </script>
    </body>
</html>
