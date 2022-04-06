package com.tictactoerest.tictactoegame;

import com.tictactoerest.tictactoegame.model.gameclasses.GameREST;
import com.tictactoerest.tictactoegame.repository.H2_JDBC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.ArrayList;

import static com.tictactoerest.tictactoegame.services.Loaders.loadListJSON;

@SpringBootApplication
public class RestServiceApp {
    public static ArrayList<File> gameList = loadListJSON();
    public static GameREST gameREST = new GameREST();
    public static void main(String[] args) {
        H2_JDBC.initDB();
        SpringApplication.run(RestServiceApp.class, args);
    }
}
