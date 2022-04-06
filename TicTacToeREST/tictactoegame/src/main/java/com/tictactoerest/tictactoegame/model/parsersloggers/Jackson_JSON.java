package com.tictactoerest.tictactoegame.model.parsersloggers;

import com.fasterxml.jackson.databind.*;
import com.tictactoerest.tictactoegame.model.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tictactoerest.tictactoegame.exceptions.LogfileNotFoundException.LogfileNotFoundLoadException;
import com.tictactoerest.tictactoegame.model.gameclasses.Game;
import com.tictactoerest.tictactoegame.model.gameclasses.GameConsole;
import com.tictactoerest.tictactoegame.model.gameclasses.Gameplay;

import static com.tictactoerest.tictactoegame.RestServiceApp.gameList;

public class Jackson_JSON implements Parser {

    public static Player gameResult = new Player();

    @Override
    public void parse(String path) {
        Gameplay game;
        ObjectMapper objMapper = new ObjectMapper();
        try {
            GameConsole map = new GameConsole();
            map.initialize();
            objMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
            File jsonF = new File(path);
            game = objMapper.readValue(jsonF, Gameplay.class);
            for (Step s : game.getSteps()) {
                map.setMark(s.getCellId() - 1);
                map.out();
            }
            if (!game.getWinner().equals("\"Draw\"")) {
                Player winner;
                winner = objMapper.readValue(game.getWinner(), Player.class);
                System.out.println(winner.getName() + " as Player " + winner.getId() + "(" + winner.getMark() + ") is winner");
            }
            else
                System.out.println("Draw between players " + game.getPlayers()[0].getName() + " and " +
                        game.getPlayers()[1].getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void serialize(Game endedGame) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        Gameplay g = new Gameplay();
        g.setPlayers(endedGame.getPlayers());
        g.setSteps(endedGame.getSteps());
        if (gameResult != null)
            g.setWinner(mapper.writeValueAsString(gameResult));
        else
            g.setWinner("\"Draw\"");
        File f;
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        File dir = new File(System.getProperty("user.dir") + "\\LogsJSON\\");
        if (!dir.exists())
            dir.mkdir();
        f = new File(dir.getPath() + "\\" + formatForDateNow.format(new Date()) + "_" + g.getPlayers()[0].getName() +
                "_vs_" + g.getPlayers()[1].getName() + ".json");
        if (!f.exists())
            try {
                f.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        mapper.writerWithDefaultPrettyPrinter().writeValue(f, g);
        gameList.add(f);
    }

    public static Gameplay loadToREST(int id) {
        if (id >= gameList.size() || id < 0)
            throw new LogfileNotFoundLoadException(id + 1);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        try {
            return mapper.readValue(gameList.get(id-1), Gameplay.class);
        } catch (IOException ex){
            ex.getMessage();
            return null;
        }
    }

    public static Gameplay addFromREST(Gameplay addGame) throws IOException {
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        File f = new File(System.getProperty("user.dir") + "\\LogsJSON\\" + "import_" +
                formatForDateNow.format(new Date()) + ".json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(f, addGame);
        gameList.add(f);
        return addGame;
    }
}