package com.tictactoerest.tictactoegame.model;

public class Player {
    int id;
    String name;
    String mark;

    public Player() {
        this.name = "";
        this.mark = "";
        this.id = 0;
    }

    public Player(int id, String name, String mark) {
        this.id = id;
        this.name = name;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
