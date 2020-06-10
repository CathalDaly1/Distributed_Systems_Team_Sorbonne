<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login!</title>
        <link rel="stylesheet" href="css/stylesheet.css" />
        <script src="js/scripts.js"></script>
        <?php include "interfaces/ICheckSession.php"; ?>
        <?php include "interfaces/ICheckLogin.php"; ?>
    </head>
    <body class="body">
        <div class="header">
            <h1>Welcome to Tic Tac Toe!</h1>
            <h3>Team Serbonne</h3>
            <div class="header-right">
                <a  href="home.php">Home</a>
                <a class="active" href="score.php">My Scores</a>
                <a href="scoreboard.php">Leaderboard</a>
                <a href="game-list.php">My Open Games</a>
            </div>
        </div>
        <div class="content">
            <section class="my-game-grid">
                <div class="content-header">
                    <h2>My Games</h2>
                    <?php include "displays/loadOwnScoreboard.php" ?>
                </div>
            </section>
        </div>
    </body>
</html>
