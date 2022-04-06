package com.tictactoerest.tictactoegame.repository;

import com.tictactoerest.tictactoegame.model.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameHistory {
    static File f = new File("GameHistory.txt");

    public static void GameHistory() {
        if (!f.exists())
            try {
                f.createNewFile();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
    }

    public static void GameEndWin(Player[] players, int id) {
        try {
            FileWriter wr = new FileWriter(f, true);
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
            wr.write(formatForDateNow.format(new Date()) + " " + players[id].getName() + " won " +
                    players[(id + 1) % 2].getName() + '\n');
            wr.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void GameDraw(Player[] players) {
        try {
            FileWriter wr = new FileWriter(f, true);
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
            wr.write(formatForDateNow.format(new Date()) + " Draw between " + players[0].getName() + " and " +
                    players[1].getName() + '\n');
            wr.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}