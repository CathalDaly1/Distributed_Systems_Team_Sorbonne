<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Home screen</title>
        <link rel="stylesheet" href="css/stylesheet.css" />
        <!--  Script used for AJAX Functionality  -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script src="scripts/home.js"></script>
        <?php include "interfaces/ICheckSession.php"; ?>
        <?php include "interfaces/ICheckLogin.php"; ?>
        <script>
            //This variable is used for the JS home.js.
            var _player_id = <?php echo htmlspecialchars($_SESSION["ttt_player_id"]); ?>;
        </script>
    </head>
    <body class="body">
        <div class="header">
            <h1>Welcome to Tic Tac Toe!</h1>
            <h3>Team Serbonne</h3>
            <div class="header-right">
                <a class="active" href="home.php">Home</a>
                <a href="score.php">My Scores</a>
                <a href="scoreboard.php">Leaderboard</a>
                <a href="game-list.php">My Open Games</a>
            </div>
        </div>
        <div class="content">
            <section class="home_page_menu">
                <section id="menu-panel">
                </section>

                <section id="open-games" class="">
                    <div class="content-header">
                        <div> <button id="create-game" href="play.php" value="Create game">Create Game</button></div>
                        <h2>Open Games</h2>
                    </div>
                    <div id="open-games-table">
                    </div>
                </section>
            </section>
        </div>
    </body>
</html>
