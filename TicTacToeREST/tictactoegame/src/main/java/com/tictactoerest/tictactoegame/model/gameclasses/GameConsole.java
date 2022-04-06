package com.tictactoerest.tictactoegame.model.gameclasses;

import com.tictactoerest.tictactoegame.model.parsersloggers.DOM;
import com.tictactoerest.tictactoegame.model.Player;
import com.tictactoerest.tictactoegame.model.Step;
import com.tictactoerest.tictactoegame.repository.GameHistory;
import com.tictactoerest.tictactoegame.model.parsersloggers.Jackson_JSON;

import java.io.IOException;

import static com.tictactoerest.tictactoegame.TicTacToe.log;

public class GameConsole extends Game implements GameInterface {

    @Override
    public void setMark(int x) {
        this.map[x / 3][x % 3] = -1 + 2 * (this.turnsLeft % 2);
        this.steps.add(new Step(10-this.turnsLeft, (this.turnsLeft - 1) % 2 + 1, x + 1));
        this.turnsLeft--;
    }

    public void out() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print("| ");
                switch (this.map[i][j]) {
                    case -1 -> System.out.print("O ");
                    case 1 -> System.out.print("X ");
                    case 0 -> System.out.print("- ");
                }
            }
            System.out.println("|");
        }
        System.out.println();
    }

    @Override
    public boolean notPossibleTurn(int x) {
        if (x < 9 && x >= 0) {
            if (this.map[x / 3][x % 3] == 0) {
                this.setMark(x);
                log.addStep(x, 9 - this.getTurnsLeft());
                this.gameContinue = this.checkNoWin();
                return false;
            } else
                System.out.println("Invalid input. Try again.");
        } else
            System.out.println("Invalid input. Try again.");
        return true;
    }

    @Override
    public GameConsole createGame(String name1, String name2){
        this.initialize();
        Player[] players = {new Player(1, name1, "X"), new Player(2, name2, "O")};
        this.setPlayers(players);
        log = new DOM();
        log.setPlayers(players);
        log.start();
        return this;
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
            return false;
        }
        if (this.getTurnsLeft() == 0 && this.gameContinue) {
            log.endLog();
            Jackson_JSON.gameResult = null;
            try {
                Jackson_JSON.serialize(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            GameHistory.GameDraw(this.getPlayers());
            System.out.println("Draw!");
            return false;
        }
        return true;
    }
}