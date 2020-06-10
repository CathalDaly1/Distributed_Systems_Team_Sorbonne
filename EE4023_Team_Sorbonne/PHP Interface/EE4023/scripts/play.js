var currentElement;
currentElement = document.getElementById("play-grid");
window.document.addEventListener("DOMContentLoaded", function()
{
    currentElement = document.getElementById("game-status-message");
    let currentPlayerMove = true;
    let gameStarted = false;
    function refreshBoard()
    {
        $.ajax({
            type:"post",
            url:"interfaces/IRefresh.php",
            data:{
                ttt_game_id: gid,
            },
            cache:false,
            success: (response) => {
                currentElement = document.getElementById("game-status-message");
                let parsedResponse = JSON.parse(response);
                checkGameState(parsedResponse);
            }
        });
    }
    //This function contantly updates which cell has an X or an O
    function refreshImages(parsedResponse)
    {
        let boardCell = document.getElementsByClassName("play-grid-tile");
        let moves = parsedResponse['moves'];
        let image;
        for (let i = 0; i < moves.length; i++)
        {
            for (let j = 0; j < boardCell.length; j++)
            {
                if (moves[i]['x'] == boardCell[j].getAttribute('x') && moves[i]['y'] == boardCell[j].getAttribute('y'))
                {
                    boardCell[j].innerHTML = "";
                    image = document.createElement("img");
                    image.setAttribute('class', "game-icon");
                    if (moves[i]['player_id'] == pid)
                        image.setAttribute('src', "Images/image_x.png");
                    else
                        image.setAttribute('src', "Images/image_o.png");
                    boardCell[j].appendChild(image);
                }
            }
        }
    }
    //calls the first function which starts the game for player 1
    refreshBoard();
    var gameStateTimer = window.setInterval(refreshBoard, 1000);

    let cells = document.getElementsByClassName("play-grid-tile");
    for (let i = 0; i < cells.length; i++)
        cells[i].addEventListener('click', function (event)
        {
            if (!currentPlayerMove)
                currentElement.innerHTML = "Please wait for your turn.";
            else if (!gameStarted)
                currentElement.innerHTML = "Waiting for player 2...";
            else
                $.ajax({
                    type:"post",
                    url:"interfaces/IMove.php",
                    data:{
                        player_id: pid,
                        game_id: gid,
                        x_coord: event.target.getAttribute("x"),
                        y_coord: event.target.getAttribute("y"),
                    },
                    cache:false,
                    success: (response) => {}
                });
        });

    function checkGameState(parsedResponse){
        if ("error" in parsedResponse && 'waiting' in parsedResponse)
        {
            gameStarted = true;
        }
        else
        {
            switch (parsedResponse['message'])
            {
                //If you're pid is the winning pid display winner message
                case 1:
                    if (parsedResponse['winner'] == pid)
                        currentElement.innerHTML = "Winner";
                    else
                        currentElement.innerHTML = "Better luck next time";
                    refreshImages(parsedResponse);
                    break;
                case 2:
                    if (parsedResponse['winner'] == pid)
                        currentElement.innerHTML = "Winner";
                    else
                        currentElement.innerHTML = "Better luck next time";
                    refreshImages(parsedResponse);
                    break;
                 //When the result from IRefresh is 3, it means you drew
                case 3:
                    refreshImages(parsedResponse);
                    currentElement.innerHTML = "DRAW";
                    break;
                    //This means you have no opponent
                case -1:
                    currentElement.innerHTML = "Waiting on player";
                    break;
                    //This means it's someones move, the following if statements checks if you pid went last to determines who's go it is
                case 0:
                    gameStarted = true;
                    refreshImages(parsedResponse);
                    //Checks if you went last, if so you must wait for opponent
                    if (pid == parsedResponse['last'])
                    {
                        currentPlayerMove = false;
                        currentElement.innerHTML = "Opponents turn ";
                    }
                    else
                    {
                        currentPlayerMove = true;
                        currentElement.innerHTML = "Take your turn";
                    }
                    break;
            }
        }
    }
});