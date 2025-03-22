package com.petrental.models;

public class Room {
    private int id;
    private String type; // "Small Room" or "Large Room"
    private boolean occupied;
    private Animals animal;

    public Room(int id, String type) {
        this.id = id;
        this.type = type;
        this.occupied = false;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public Animals getAnimal() {
        return animal;
    }

    public void setAnimal(Animals animal) {
        this.animal = animal;
    }

    @Override
    public String toString() {
        return "Room ID: " + id + "\nType: " + type +
                "\nOccupied: " + (occupied ? "Yes" : "No");
    }
}
