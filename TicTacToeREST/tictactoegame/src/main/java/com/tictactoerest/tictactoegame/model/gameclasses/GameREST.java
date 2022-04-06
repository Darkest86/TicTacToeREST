package com.tictactoerest.tictactoegame.model.gameclasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.tictactoerest.tictactoegame.exceptions.GameExceptions.*;
import com.tictactoerest.tictactoegame.exceptions.InvalidStepException.*;
import com.tictactoerest.tictactoegame.model.parsersloggers.DOM;
import com.tictactoerest.tictactoegame.model.Player;
import com.tictactoerest.tictactoegame.model.Step;
import com.tictactoerest.tictactoegame.repository.GameHistory;
import com.tictactoerest.tictactoegame.model.parsersloggers.Jackson_JSON;

import java.io.IOException;

import static com.tictactoerest.tictactoegame.RestServiceApp.gameREST;
import static com.tictactoerest.tictactoegame.TicTacToe.log;
import static com.tictactoerest.tictactoegame.repository.H2_JDBC.*;

@JsonRootName("Game")
public class GameREST extends Game implements GameInterface{
    @Override
    public void setMark(int x) {
        this.map[x / 3][x % 3] = -1 + 2 * (this.turnsLeft % 2);
        this.steps.add(new Step(10-this.turnsLeft, (this.turnsLeft - 1) % 2 + 1, x + 1));
        this.turnsLeft--;
        addStep(this.steps.get(this.steps.size() - 1));
    }

    @Override
    public boolean notPossibleTurn(int x) {
        if (x < 9 && x >= 0) {
            if (this.map[x / 3][x % 3] == 0) {
                this.setMark(x);
                log.addStep(x, 9 - this.getTurnsLeft());
                this.gameContinue = this.checkNoWin();
                return false;
            } else {
                System.out.println("Invalid input. Try again.");
                throw new CellIsNotEmptyException(x + 1);
            }
        } else {
            System.out.println("Invalid input. Try again.");
            throw new CellIsOutOfBoundsException(x + 1);
        }
    }

    @Override
    public GameREST createGame(String name1, String name2) {
        gameREST.initialize();
        Player[] players = {new Player(1, name1, "X"), new Player(2, name2, "O")};
        gameREST.setPlayers(players);
        startGame(gameREST);
        log = new DOM();
        log.setPlayers(players);
        log.start();
        return gameREST;
    }

    public GameREST tryStep (int pos){
        if (gameREST.isNull())
            throw new GameIsNotStartedException();
        if (!gameREST.gameContinue)
            throw new GameIsEndedException();
        gameREST.notPossibleTurn(pos);
        return gameREST;
    }

    @JsonIgnore
    public boolean isNull (){
        return this.map == null && this.players == null;
    }

    @Override
    public boolean checkNoWin() {
        int h1 = 0, h2 = 0, h3 = 0, v1 = 0, v2 = 0, v3 = 0, d1 = 0, d2 = 0;
        for (int i = 0; i < 3; i++) {
            h1 += this.map[0][i];
            h2 += this.map[1][i];
            h3 += this.map[2][i];
            v1 += this.map[i][0];
            v2 += this.map[i][1];
            v3 += this.map[i][2];
            d1 += this.map[i][i];
            d2 += this.map[2 - i][i];
        }
        if (h1 == 3 || h2 == 3 || h3 == 3 || v1 == 3 || v2 == 3 || v3 == 3 || d1 == 3 || d2 == 3) {
            GameHistory.GameEndWin(this.players, 0);
            log.endLog(this.players[0]);
            Jackson_JSON.gameResult = this.players[0];
            try {
                Jackson_JSON.serialize(this);
            } catch (IOException ex) {
                ex.getMessage();
            }
            System.out.println(this.players[0].getName() + " won!");
            setWinner(this.players[0]);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            try {
                endGame(gameREST, mapper.writeValueAsString(this.players[0]));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return false;
        }
        if (h1 == -3 || h2 == -3 || h3 == -3 || v1 == -3 || v2 == -3 || v3 == -3 || d1 == -3 || d2 == -3) {
            GameHistory.GameEndWin(this.players, 1);
            log.endLog(this.players[1]);
            Jackson_JSON.gameResult = this.players[1];
            try {
                Jackson_JSON.serialize(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(this.players[1].getName() + " won!");
            setWinner(this.players[1]);
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            try {
                endGame(gameREST, mapper.writeValueAsString(this.players[1]));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return false;
        }
        if (this.getTurnsLeft() == 0 && this.gameContinue) {
            this.gameContinue = false;
            log.endLog();
            Jackson_JSON.gameResult = null;
            try {
                Jackson_JSON.serialize(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GameHistory.GameDraw(this.getPlayers());
            System.out.println("Draw!");
            endGame(gameREST, "Draw!");
            return false;
        }
        return true;
    }
}
