package com.tictactoerest.tictactoegame.utils;

public class DatabaseUtils {
    public static final String JDBC_DRIVER = "org.h2.Driver";
    public static final String DB_URL = "jdbc:h2:mem:tictactoe_db;MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1";
    public static final String USER = "sa";
    public static final String PASS = "";

    public static final String createTablePlayers = "CREATE TABLE IF NOT EXISTS `Players` (" +
            "  `idPlayers` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
            "  `name` VARCHAR(45) NOT NULL," +
            "  `gamesWon` INT UNSIGNED NULL," +
            "  `totalGames` INT UNSIGNED NULL," +
            "  PRIMARY KEY (`idPlayers`)," +
            "  UNIQUE INDEX `idPlayers_UNIQUE` (`idPlayers` ASC) )" +
            "ENGINE = InnoDB;";

    public static final String createTableGames = "CREATE TABLE IF NOT EXISTS `Games` (" +
            "  `idGames` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
            "  `gameResult` JSON NULL," +
            "  `gameEnded` TINYINT NULL DEFAULT 0," +
            "  `time` VARCHAR(45) NOT NULL," +
            "  `player1_id` INT UNSIGNED NOT NULL," +
            "  `player2_id` INT UNSIGNED NOT NULL," +
            "  PRIMARY KEY (`idGames`)," +
            "  UNIQUE INDEX `idGames_UNIQUE` (`idGames` ASC) ," +
            "  INDEX `p1_idx` (`player1_id` ASC) ," +
            "  INDEX `p2_idx` (`player2_id` ASC) ," +
            "  CONSTRAINT `p1`" +
            "    FOREIGN KEY (`player1_id`)" +
            "    REFERENCES `Players` (`idPlayers`)" +
            "    ON DELETE NO ACTION" +
            "    ON UPDATE NO ACTION," +
            "  CONSTRAINT `p2`" +
            "    FOREIGN KEY (`player2_id`)" +
            "    REFERENCES `Players` (`idPlayers`)" +
            "    ON DELETE NO ACTION" +
            "    ON UPDATE NO ACTION)" +
            "ENGINE = InnoDB;";

    public static final String createTableSteps = "CREATE TABLE IF NOT EXISTS `Steps` (" +
            "  `idSteps` INT UNSIGNED NOT NULL AUTO_INCREMENT," +
            "  `gameId` INT UNSIGNED NOT NULL," +
            "  `pos` INT UNSIGNED NOT NULL," +
            "  `num` INT UNSIGNED NOT NULL," +
            "  PRIMARY KEY (`idSteps`)," +
            "  UNIQUE INDEX `idSteps_UNIQUE` (`idSteps` ASC) ," +
            "  INDEX `game_idx` (`gameId` ASC)," +
            "  CONSTRAINT `game`" +
            "    FOREIGN KEY (`gameId`)" +
            "    REFERENCES `Games` (`idGames`)" +
            "    ON DELETE NO ACTION" +
            "    ON UPDATE NO ACTION)" +
            "ENGINE = InnoDB;";

    public static final String addPlayer = "INSERT INTO Players (name, gamesWon, totalGames) " +
            "VALUES (\'%s\', 0, 0)";

    public static final String findPlayerByName = "SELECT idPlayers FROM Players WHERE name = \'%s\'";

    public static final String updateGameStatsByPlayerId = "UPDATE Players SET gamesWon = gamesWon + %d," +
            "totalGames = totalGames + 1  WHERE idPlayers = %d";

    public static final String updateGameResult = "UPDATE Games SET gameEnded = 1, gameResult = \'%s\' WHERE idGames = %d";

    public static final String addGame = "INSERT INTO Games (player1_id, player2_id, gameEnded, time) VALUES " +
            "(%d , %d, 0, LOCALTIMESTAMP)";

    public static final String addStep = "INSERT INTO Steps (gameId, pos, num) VALUES (%d, %d, %d)";
}
