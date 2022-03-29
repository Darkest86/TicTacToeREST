# TicTacToeREST
web browser localhost:8080/gameplay<br>
GET<br>
/new-game<br>
    player1 and player2 keys, return new game state<br>
/current-game<br>
    returns current state of game<br>
/current-game/step<br>
        setMark key (1-9) and return game state after turn<br>
/load<br>
    return list of games to load in "LogsJSON" directory<br>
/load/{id}<br>
    return game by id in list<br>
DELETE<br>
/load/{id}<br>
    delete game by id in list and return new list<br>
POST<br>
/add-game<br>
    adding game from JSON string in "LogsJSON" directory and return gameplay stats<br>
    example from POSTMAN:<br>
    curl --location --request POST '127.0.0.1:8080/gameplay/add-game' \
--header 'Content-Type: application/json' \
--data-raw '{
    "Gameplay": {
        "Players": [
            {
                "id": 1,
                "name": "P1",
                "mark": "X"
            },
            {
                "id": 2,
                "name": "P2",
                "mark": "O"
            }
        ],
        "Game Result": "{\"Player\":{\"id\":2,\"name\":\"P2\",\"mark\":\"O\"}}",
        "Steps": [
            {
                "id": 1,
                "playerId": 1,
                "cellId": 3
            },
            {
                "id": 2,
                "playerId": 2,
                "cellId": 6
            },
            {
                "id": 3,
                "playerId": 1,
                "cellId": 9
            },
            {
                "id": 4,
                "playerId": 2,
                "cellId": 2
            },
            {
                "id": 5,
                "playerId": 1,
                "cellId": 8
            },
            {
                "id": 6,
                "playerId": 2,
                "cellId": 5
            },
            {
                "id": 7,
                "playerId": 1,
                "cellId": 1
            },
            {
                "id": 8,
                "playerId": 2,
                "cellId": 4
            }
        ]
    }
}'
