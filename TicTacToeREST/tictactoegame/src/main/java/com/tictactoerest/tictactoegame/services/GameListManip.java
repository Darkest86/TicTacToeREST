package com.tictactoerest.tictactoegame.services;

import com.tictactoerest.tictactoegame.exceptions.LogfileNotFoundException.*;

import java.io.File;
import java.util.ArrayList;

import static com.tictactoerest.tictactoegame.RestServiceApp.gameList;

public class GameListManip {

    public static String prettyGameHistoryList() {
        String result = "";
        for (int i = 0; i < gameList.size(); i++) {
            result += (i + 1) + ". " + gameList.get(i) + "<br>";
        }
        return result;
    }

    public static ArrayList<File> deleteFromList(int id){
        deleteAndRemove(id);
        return gameList;
    }

    public static void deleteAndRemove(int id) {
        if (id >= gameList.size() || id < 0)
            throw new LogfileNotFoundDeleteException(id + 1);
        gameList.get(id).delete();
        gameList.remove(id);
    }
}
