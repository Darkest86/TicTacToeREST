package com.tictactoerest.tictactoegame.model.gameclasses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tictactoerest.tictactoegame.model.Player;
import com.tictactoerest.tictactoegame.model.Step;

import java.util.ArrayList;

public class Game {
    int[][] map;
    int turnsLeft;
    public boolean gameContinue;
    ArrayList<Step> steps;
    Player[] players;
    Player winner;

    @JsonProperty("winner")
    public Player getWinner() {
        return this.winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    @JsonProperty("players")
    public Player[] getPlayers() {
        return this.players;
    }

    public void initialize(){
        this.map = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        this.turnsLeft = 9;
        this.steps = new ArrayList<>();
        this.players = new Player[2];
        this.gameContinue = true;
        this.winner = null;
    }

    @JsonProperty("turnsLeft")
    public int getTurnsLeft() {
        return this.turnsLeft;
    }

    @JsonProperty("steps")
    public ArrayList<Step> getSteps() {
        return this.steps;
    }

    @JsonProperty("map")
    public int[][] getMap() {
        return this.map;
    }

    @JsonProperty("gameContinue")
    public boolean isGameContinue() {
        return gameContinue;
    }

    public void setGameContinue(boolean gameContinue) {
        this.gameContinue = gameContinue;
    }
}
