package com.tictactoerest.tictactoegame;

import com.tictactoerest.tictactoegame.model.gameclasses.GameConsole;
import com.tictactoerest.tictactoegame.model.parsersloggers.DOM;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;

import static com.tictactoerest.tictactoegame.services.UserFriendlyConsoleMenu.mainMenu;

public class TicTacToe {
    public static Scanner scan = new Scanner(System.in);
    public static GameConsole gameConsole = new GameConsole();
    public static DOM log = new DOM();
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        while (mainMenu()) {}
    }
}
