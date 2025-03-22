package com.petrental.models;

import java.util.ArrayList;
import java.util.List;

public class Booking {
    private int bookingID;
    private User user;
    private Animals animal;
    private Room room;
    private boolean isConfirmed;

    private static List<Booking> bookings = new ArrayList<>();

    public Booking(int bookingID, User user, Animals animal, Room room) {
        if (room.isOccupied()) {
            throw new IllegalArgumentException("Room is already occupied: " + room.getId());
        }
        this.bookingID = bookingID;
        this.user = user;
        this.animal = animal;
        this.room = room;
        this.isConfirmed = false;
    }

    public int getBookingID() {
        return bookingID;
    }

    public User getUser() {
        return user;
    }

    public Animals getAnimal() {
        return animal;
    }

    public Room getRoom() {
        return room;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void confirmBooking() {
        if (room.isOccupied()) {
            throw new IllegalStateException("Room not available for booking!");
        }
        this.isConfirmed = true;
        room.setOccupied(true);
        room.setAnimal(animal);
    }

    public static void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public static List<Booking> getBookings() {
        return bookings;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingID + "\nUser: " + user.getEmail() +
                "\nAnimal: " + animal.getName() + "\nRoom Type: " + room.getType() +
                "\nRoom ID: " + room.getId() + "\nConfirmed: " + isConfirmed;
    }
}
