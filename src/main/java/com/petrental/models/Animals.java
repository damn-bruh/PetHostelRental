package com.petrental.models;

public abstract class Animals {
    protected int ID;
    protected String name;

    public Animals(int id, String name) {
        this.ID = id;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}

