package com.tictactoerest.tictactoegame.exceptions;

public class InvalidStepException {
    public static class CellIsNotEmptyException extends RuntimeException{
        public CellIsNotEmptyException(int id){
            super("Cell with id " + id + " marked already! Use another cell.");
        }
    }

    public static class CellIsOutOfBoundsException extends IndexOutOfBoundsException{
        public CellIsOutOfBoundsException(int id) {
            super("Cell with id " + id + " doesn't exist! Use cells with id from 1 to 9.");
        }
    }
}
