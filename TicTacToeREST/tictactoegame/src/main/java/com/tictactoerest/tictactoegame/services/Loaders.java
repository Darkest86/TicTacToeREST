package com.tictactoerest.tictactoegame.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import static com.tictactoerest.tictactoegame.TicTacToe.scan;
import static com.tictactoerest.tictactoegame.utils.FileFilters.jsonFilter;
import static com.tictactoerest.tictactoegame.utils.FileFilters.xmlFilter;

public class Loaders {
    static String loadPath;

    public static ArrayList<File> loadListXML() {
        loadPath = System.getProperty("user.dir") + "\\LogsXML\\";
        File dir = new File(loadPath);
        File[] fileArray = dir.listFiles(xmlFilter);
        ArrayList<File> fileList = new ArrayList<>();
        Collections.addAll(fileList, fileArray);
        return fileList;
    }

    public static ArrayList<File> loadListJSON() {
        loadPath = System.getProperty("user.dir") + "\\LogsJSON\\";
        File dir = new File(loadPath);
        File[] fileArray = dir.listFiles(jsonFilter);
        ArrayList<File> fileList = new ArrayList<>();
        if (fileArray != null)
            Collections.addAll(fileList, fileArray);
        return fileList;
    }

    public static String loadGame(ArrayList<File> gameList) {
        if (gameList != null) {
            System.out.println("Select game by number:");
            int index = 1;
            for (File f : gameList) {
                System.out.print(index + ". ");
                System.out.println(f.getName());
                index++;
            }
            int i;
            while (true) {
                i = Integer.parseInt(scan.nextLine());
                if (!(i > gameList.size() || i <= 0))
                    break;
                else
                    System.out.println("Invalid input. Try again.");
            }
            return gameList.get(i - 1).toString();
        }
        return null;
    }
}