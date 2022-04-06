package com.tictactoerest.tictactoegame.exceptions;

public class LogfileNotFoundException {
    public static class LogfileNotFoundDeleteException extends IndexOutOfBoundsException{
        public LogfileNotFoundDeleteException (int id){
            super("Could not find logfile number " + id + " to delete!");
        }
    }

    public static class LogfileNotFoundLoadException extends IndexOutOfBoundsException{
        public LogfileNotFoundLoadException(int id) {
            super("Could not find logfile number " + id + " to load!");
        }
    }
}
