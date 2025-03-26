package com.petrental.models;

import java.time.LocalDate;

public class Booking {
    private String pet;
    private String room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int totalPrice;

    public Booking() {
        // Default constructor for JSON serialization
    }

    public Booking(String pet, String room, LocalDate checkIn, LocalDate checkOut, int totalPrice) {
        this.pet = pet;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
    }

    public String getPet() { return pet; }
    public void setPet(String pet) { this.pet = pet; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }

    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }

    public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }
}
