<!--//Checks is username or player id is set, if it is not, it sends user back to index to login or register-->
<?php if (!isset($_SESSION["ttt_username"])): ?>
    <?php header("Location: /EE4023/index.php"); ?>
<?php endif; ?>
<?php if (!isset($_SESSION["ttt_player_id"])): ?>
    <?php header("Location: /EE4023/index.php"); ?>
<?php endif; ?>