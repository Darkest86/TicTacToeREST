package com.tictactoerest.tictactoegame.model;

public class Step {
    int id;
    int playerId;
    int cellId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getCellId() {
        return cellId;
    }

    public void setCellId(int cellId) {
        this.cellId = cellId;
    }

    public Step(){
        this.id = 0;
        this.playerId = 0;
        this.cellId = 0;
    }

    public Step(int id, int pid, int cid){
        this.id = id;
        this.playerId = pid;
        this.cellId = cid;
    }
}