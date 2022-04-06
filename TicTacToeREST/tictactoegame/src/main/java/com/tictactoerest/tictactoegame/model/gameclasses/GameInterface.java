package com.tictactoerest.tictactoegame.model.gameclasses;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface GameInterface <T extends GameInterface<T>> {
    void setMark(int x);
    @JsonIgnore
    boolean notPossibleTurn(int x);
    T createGame(String name1, String name2);
    @JsonIgnore
    boolean checkNoWin();
}
