<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login!</title>
        <link rel="stylesheet" href="css/stylesheet.css" />
        <?php include "interfaces/ICheckSession.php"; ?>
        <?php include "interfaces/ICheckLogin.php"; ?>
    </head>
    <body class="body">
        <div class="header">
            <h1>Welcome to Tic Tac Toe!</h1>
            <h3>Team Serbonne</h3>
            <div class="header-right">
                <a href="home.php">Home</a>
                <a href="score.php">My Scores</a>
                <a href="scoreboard.php">Leaderboard</a>
                <a class="active" href="game-list.php">My Open Games</a>
            </div>
        </div>
        <div class="content">
            <section class="open-game-grid">
                <div class="content-header">
                    <h2>My Open Games</h2>
                </div>
                <div id="open-game-header">
                </div>
                <div id="open-game-rows">
                    <?php include "displays/gamesList.php" ?>
                </div>
            </section>
        </div>
    </body>
</html>
