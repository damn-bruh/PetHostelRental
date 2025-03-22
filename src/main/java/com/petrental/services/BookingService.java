package com.petrental.services;

import com.petrental.models.Animals;
import com.petrental.models.Booking;
import com.petrental.models.Room;
import com.petrental.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private NotificationService notificationService;

    private int bookingIDCounter = 1;

    /**
     * Attempts to create and confirm a new booking for the specified user and animal in the given room.
     *
     * @param user   The user making the booking.
     * @param animal The animal to be booked.
     * @param room   The room where the animal will be booked.
     * @return Confirmation message regarding the booking status.
     */
    public String bookRoom(User user, Animals animal, Room room) {
        // 1. Validation: Check for null inputs
        if (user == null || animal == null || room == null) {
            return "Invalid booking: User, animal, and room must not be null!";
        }

        // 2. Validation: Check if the room is already occupied
        if (room.isOccupied()) {
            return "Room " + room.getId() + " is already occupied!";
        }

        // 3. Validation: Check if the user already has a booking for the same animal
        for (Booking existingBooking : Booking.getBookings()) {
            if (existingBooking.getUser().getEmail().equalsIgnoreCase(user.getEmail())
                    && existingBooking.getAnimal().getName().equalsIgnoreCase(animal.getName())) {
                return "Booking already exists for " + animal.getName() + " by user " + user.getEmail();
            }
        }

        // Proceed with booking
        Booking booking = new Booking(bookingIDCounter++, user, animal, room);
        booking.confirmBooking();
        Booking.addBooking(booking);

        String message = "Booking confirmed for " + animal.getName() + " in Room " + room.getId();
        notificationService.sendNotification(user.getEmail(), "Room Booking Confirmed", message);

        return message;
    }

    /**
     * Retrieves a list of all current bookings.
     *
     * @return A list of bookings.
     */
    public List<Booking> getAllBookings() {
        return Booking.getBookings();
    }

    /**
     * Retrieves all available rooms from the provided list.
     *
     * @param rooms A list of rooms to check for availability.
     * @return A list of available (unoccupied) rooms.
     */
    public List<Room> getAvailableRooms(List<Room> rooms) {
        return rooms.stream().filter(room -> !room.isOccupied()).toList();
    }
}
