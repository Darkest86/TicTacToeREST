package com.tictactoerest.tictactoegame.controller;

import com.tictactoerest.tictactoegame.model.gameclasses.GameREST;
import com.tictactoerest.tictactoegame.model.gameclasses.Gameplay;
import com.tictactoerest.tictactoegame.model.parsersloggers.Jackson_JSON;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.tictactoerest.tictactoegame.RestServiceApp.gameREST;
import static com.tictactoerest.tictactoegame.services.GameListManip.*;
import static com.tictactoerest.tictactoegame.model.parsersloggers.Jackson_JSON.loadToREST;

@RestController
public class GameRESTController {

    @GetMapping("/gameplay/current-game")
    public GameREST GameplayPrint(){
        return gameREST;
    }

    @GetMapping("/gameplay/load")
    public String GameplayHistoryPrint(){
        return prettyGameHistoryList();
    }

    @GetMapping("/gameplay/load/{id}")
    public Gameplay getById(@PathVariable int id) {
        return loadToREST(id-1);
    }

    @GetMapping("/gameplay/new-game")
    public GameREST newGame(@RequestParam(value = "player1", defaultValue = "P1") String name1, @RequestParam(value = "player2",
            defaultValue = "P2") String name2){
        return gameREST.createGame(name1, name2);
    }

    @GetMapping("/gameplay/current-game/step")
    public GameREST newStep(@RequestParam(value = "setMark") int pos){
        return gameREST.tryStep(pos - 1);
    }

    @DeleteMapping("/gameplay/load/{id}")
    public ArrayList<File> deleteById(@PathVariable int id){
        return deleteFromList(id-1);
    }

    @ResponseBody
    @RequestMapping(value = "/gameplay/add-game", headers = {"content-type=application/json"},
            consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public Gameplay addGame(@RequestBody Gameplay newGame) throws IOException {
        return Jackson_JSON.addFromREST(newGame);
    }

}
