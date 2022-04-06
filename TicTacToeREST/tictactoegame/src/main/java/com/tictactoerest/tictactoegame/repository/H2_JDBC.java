package com.tictactoerest.tictactoegame.repository;

import com.tictactoerest.tictactoegame.model.gameclasses.Game;
import com.tictactoerest.tictactoegame.model.gameclasses.GameREST;
import com.tictactoerest.tictactoegame.model.Step;
import com.tictactoerest.tictactoegame.utils.DatabaseUtils;

import java.sql.*;

public class H2_JDBC {

    public static void initDB(){
        Statement stmt = null;
        Connection conn = null;
        try {
            Class.forName(DatabaseUtils.JDBC_DRIVER);
            conn = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.USER, DatabaseUtils.PASS);
            stmt = conn.createStatement();
            stmt.execute(DatabaseUtils.createTablePlayers);
            stmt.execute(DatabaseUtils.createTableGames);
            stmt.execute(DatabaseUtils.createTableSteps);
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {}
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static int currentGameId = -1;
    public static int[] currentPlayersId = {-1, -1};

    public static void startGame(GameREST g){
        currentPlayersId[0] = findPlayer(g.getPlayers()[0].getName());
        if (currentPlayersId[0] == -1)
            addPlayer(g.getPlayers()[0].getName(), 0);
        currentPlayersId[1] = findPlayer(g.getPlayers()[1].getName());
        if (currentPlayersId[1] == -1)
            addPlayer(g.getPlayers()[1].getName(), 1);
        addGame();
    }

    public static void endGame(Game g, String gameplayWinner){
        updateGameResult(gameplayWinner);
        if (g.getPlayers()[0].equals(g.getWinner())) {
            updatePlayerStats(currentPlayersId[0], 1);
            updatePlayerStats(currentPlayersId[1], 0);
        }
        else {
            updatePlayerStats(currentPlayersId[0], 0);
            updatePlayerStats(currentPlayersId[1], 1);
        }
    }

    public static int findPlayer(String name){
        Statement stmt = null;
        Connection conn = null;
        int id = -1;
        try {
            Class.forName(DatabaseUtils.JDBC_DRIVER);
            conn = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.USER, DatabaseUtils.PASS);
            stmt = conn.createStatement();
            stmt.execute(String.format(DatabaseUtils.findPlayerByName, name));
            ResultSet rs = stmt.getResultSet();
            if (rs.next())
                id = rs.getInt(1);
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {}
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return id;
    }

    public static void updatePlayerStats(int id, int winner){
        Statement stmt = null;
        Connection conn = null;
        try {
            Class.forName(DatabaseUtils.JDBC_DRIVER);
            conn = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.USER, DatabaseUtils.PASS);
            stmt = conn.createStatement();
            stmt.executeUpdate(String.format(DatabaseUtils.updateGameStatsByPlayerId, winner, id));
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {}
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void updateGameResult(String winner){
        Statement stmt = null;
        Connection conn = null;
        try {
            Class.forName(DatabaseUtils.JDBC_DRIVER);
            conn = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.USER, DatabaseUtils.PASS);
            stmt = conn.createStatement();
            stmt.executeUpdate(String.format(DatabaseUtils.updateGameResult, winner, currentGameId));
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {}
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void addPlayer(String name, int id){
        Statement stmt = null;
        Connection conn = null;
        try {
            Class.forName(DatabaseUtils.JDBC_DRIVER);
            conn = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.USER, DatabaseUtils.PASS);
            stmt = conn.createStatement();
            stmt.execute(String.format(DatabaseUtils.addPlayer, name), Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                currentPlayersId[id] = rs.getInt(1);
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {}
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void addGame(){
        Statement stmt = null;
        Connection conn = null;
        try {
            Class.forName(DatabaseUtils.JDBC_DRIVER);
            conn = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.USER, DatabaseUtils.PASS);
            stmt = conn.createStatement();
            stmt.execute(String.format(DatabaseUtils.addGame, currentPlayersId[0], currentPlayersId[1]), Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next())
                currentGameId = rs.getInt(1);
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {}
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void addStep(Step s){
        Statement stmt = null;
        Connection conn = null;
        try {
            Class.forName(DatabaseUtils.JDBC_DRIVER);
            conn = DriverManager.getConnection(DatabaseUtils.DB_URL, DatabaseUtils.USER, DatabaseUtils.PASS);
            stmt = conn.createStatement();
            stmt.execute(String.format(DatabaseUtils.addStep, currentGameId, s.getCellId(), s.getId()));
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {}
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
