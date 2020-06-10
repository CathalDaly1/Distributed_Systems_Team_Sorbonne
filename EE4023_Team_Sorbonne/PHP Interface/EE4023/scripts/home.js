// Waits until the entire page is loaded before continuing script
window.document.addEventListener("DOMContentLoaded", function() {
    var currentElement;
    //Setting current element to be create game button
    currentElement = document.getElementById("create-game");
    if (currentElement !== null)
        //If create game button is clicked, the following ajax function will be sent
        currentElement.onclick = function ()
        {
            $.ajax({
                type:"post",
                url:"interfaces/ICreateNewGame.php",
                data:{
                    player_id: _player_id,
                },
                cache:false,
                success: (message) => {
                    appendCreateGameElements(message);
                }
            });
        };

    currentElement = document.getElementById("open-games-table");
    if (currentElement !== null)
    {
        function refreshOpenGamesList()
        {
            $.ajax({
                type:"post",
                url:"interfaces/IPlayable.php",
                data:{
                    _game_request:true,
                },
                cache:false,
                success: (response) => {
                    currentElement = document.getElementById("open-games-table");
                    currentElement.innerHTML = ""; // Clear contents
                    let parsedResponse = JSON.parse(response);
                    if ("error" in parsedResponse)
                        currentElement.innerHTML = parsedResponse["error"];
                    else // Games list was not empty
                    {
                        let gameInfo = null;
                        let joinButton = null;
                        for (let key in parsedResponse)
                        {
                            gameInfo = document.createElement("div");
                            gameInfo.setAttribute('class', "open-game-row");
                            joinButton = document.createElement("div");
                            joinButton.setAttribute('class', "game-host");
                            joinButton.innerHTML = parsedResponse[key]['host'];
                            gameInfo.append(joinButton);
                            joinButton = document.createElement("button");
                            joinButton.setAttribute('class', "game-join-button");
                            joinButton.innerHTML = "Join";
                            joinButton.addEventListener('click', function (event)
                            {
                                $.ajax({
                                    type:"post",
                                    url:"interfaces/IJoin.php",
                                    data:{
                                        _player_id: _player_id,
                                        _game_id: parsedResponse[key]['id'],
                                    },
                                    cache:false,
                                    success: (message) => {
                                        appendJoinGameElements(message)
                                    }
                                });
                            });
                            gameInfo.append(joinButton);
                            currentElement.appendChild(gameInfo); // Append row
                        }
                    }
                }
            });
        };

        refreshOpenGamesList();
        var openGameTimer = window.setInterval(refreshOpenGamesList, 1000); // Poll every 10 seconds

        function appendCreateGameElements(message){
            // This section sets up play.php with the player id and game id
            let parsedMessage = JSON.parse(message);
            let input = document.createElement('input');
            //sets up form of play.php
            currentElement = document.createElement("form");
            currentElement.method = "POST";
            currentElement.action = "play.php";
            input.type = 'hidden';
            input.name = "ttt_game_id";
            input.value = parsedMessage['game_id'];

            currentElement.appendChild(input);
            input = document.createElement('input');
            //sets up body of play
            input.type = 'hidden';
            input.name = "ttt_player_id";
            input.value = _player_id;
            currentElement.appendChild(input);
            document.body.appendChild(currentElement);
            //Submit current elements to play.php
            currentElement.submit();
        }

        function appendJoinGameElements(message){
            parsedResponse = JSON.parse(message);
            let outputElement = document.createElement("form");
            let inputElement = document.createElement('input');

            outputElement.method = "POST";
            outputElement.action = "play.php";
            inputElement.type = 'hidden';
            inputElement.name = "ttt_game_id";
            inputElement.value = parsedResponse['game_id'];

            outputElement.appendChild(inputElement);

            inputElement = document.createElement('input');
            inputElement.type = 'hidden';
            inputElement.name = "ttt_player_id";
            inputElement.value = _player_id;

            outputElement.appendChild(inputElement);

            inputElement = document.createElement('input');
            inputElement.type = 'hidden';
            inputElement.name = "ttt_second_player";
            inputElement.value = true;

            outputElement.appendChild(inputElement);
            document.body.appendChild(outputElement);

            outputElement.submit();
        }
    }
});