package com.tictactoerest.tictactoegame.exceptions;

public class GameExceptions {
    public static class GameIsNotStartedException extends RuntimeException{
        public GameIsNotStartedException(){
            super("Game is not started! Start new game.");
        }
    }

    public static class GameIsEndedException extends RuntimeException{
        public GameIsEndedException(){
            super("Game is ended! Start new game.");
        }
    }
}
