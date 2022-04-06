package com.tictactoerest.tictactoegame.services;

import com.tictactoerest.tictactoegame.model.gameclasses.GameConsole;
import com.tictactoerest.tictactoegame.model.Player;
import com.tictactoerest.tictactoegame.model.parsersloggers.Jackson_JSON;
import com.tictactoerest.tictactoegame.model.parsersloggers.SAX_DOM_XML;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static com.tictactoerest.tictactoegame.TicTacToe.*;
import static com.tictactoerest.tictactoegame.utils.FileFilters.*;

public class UserFriendlyConsoleMenu {
    static boolean notExit = true;

    public static void loadGame(){
        String gamePath = null;
        while (gamePath == null && notExit) {
            gamePath = loadList();
        }
        if (gamePath != null) {
            if (notExit) {
                if (json) {
                    Jackson_JSON parser = new Jackson_JSON();
                    parser.parse(gamePath);
                } else {
                    SAX_DOM_XML parser = new SAX_DOM_XML();
                    try {
                        parser.parse(gamePath);
                    } catch (ParserConfigurationException | SAXException | IOException pce) {
                        System.out.println(pce.getMessage());
                    }
                }
            }
        }
    }

    public static void setPlayerInfo(GameConsole gameConsole){
        String name1, name2;
        System.out.println("Enter player 1 name:");
        name1 = scan.nextLine();
        System.out.println("Enter player 2 name:");
        name2 = scan.nextLine();
        gameConsole.setPlayers(new Player[]{new Player(1, name1, "X"), new Player(2, name2, "O")});
        System.out.println("Game started");
    }

    public static void startGame(){
        gameConsole.initialize();
        setPlayerInfo(gameConsole);
        log.setPlayers(gameConsole.getPlayers());
        log.start();

        while (gameConsole.getTurnsLeft() > 0 && gameConsole.gameContinue) {
            int pos;
            do {
                System.out.println("Enter number from 1 to 9 (left up corner is 1, right up is 3, right down is 9)");
                gameConsole.out();
                pos = Integer.parseInt(scan.nextLine()) - 1;
            }
            while (gameConsole.notPossibleTurn(pos));
        }
    }

    public static String loadList() {
        String path = null;
        while (true) {
            System.out.println("Load XML (X)\nLoad JSON (J)\nBack (B)");
            String s = scan.nextLine();
            if (s.equals("X")) {
                json = false;
                break;
            }
            if (s.equals("J")) {
                json = true;
                break;
            }
            if (s.equals("B")) {
                notExit = false;
                break;
            }
            if (!s.equals("X") && !s.equals("J"))
                System.out.println("Invalid input. Try again.");
            System.out.println();
        }
        if (notExit)
            if (json)
                path = Loaders.loadGame(Loaders.loadListJSON());
            else
                path = Loaders.loadGame(Loaders.loadListXML());
        return path;
    }

    public static boolean mainMenu(){
        System.out.println("Start new game? (G)\nLoad game? (L)\nExit? (E)");
        String s = scan.nextLine();
        if (s.equals("G"))
            startGame();
        if (s.equals("L"))
            loadGame();
        if (s.equals("E"))
            return false;
        if (!s.equals("G") && !s.equals("L"))
            System.out.println("Invalid input. Try again.");
        System.out.println();
        return true;
    }
}
