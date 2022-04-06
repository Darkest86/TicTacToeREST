package com.tictactoerest.tictactoegame.controller;

import com.tictactoerest.tictactoegame.exceptions.LogfileNotFoundException.*;
import com.tictactoerest.tictactoegame.exceptions.InvalidStepException.*;
import com.tictactoerest.tictactoegame.exceptions.GameExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsAdvice {

    @ResponseBody
    @ExceptionHandler(LogfileNotFoundDeleteException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String LogfileDeleteHandler (LogfileNotFoundDeleteException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(LogfileNotFoundLoadException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String LogfileLoadHandler (LogfileNotFoundLoadException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CellIsNotEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String CellIsNotEmptyHandler (CellIsNotEmptyException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(CellIsOutOfBoundsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String CellIdOutOfBoundsHandler (CellIsOutOfBoundsException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(GameIsNotStartedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String GameIsNotStartedHandler (GameIsNotStartedException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(GameIsEndedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String GameIsEndedHandler (GameIsEndedException ex){
        return ex.getMessage();
    }
}
