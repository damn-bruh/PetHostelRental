package com.petrental.controllers;

import com.petrental.models.*;
import com.petrental.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private RoomService roomService;

    private final AtomicInteger animalIDCounter = new AtomicInteger(100);

    /**
     * Registers a new user.
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam String email, @RequestParam String password) {
        return userService.registerUser(email, password) ?
                "Registration successful." : "User already exists.";
    }

    /**
     * Handles user login with 2FA validation.
     */
    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String code) {
        if (userService.authenticateUser(email, password, code) &&
                userService.validate2FACode(email, code)) {
            return "Login successful!";
        }
        return "Invalid email, password, or 2FA code.";
    }

    /**
     * Creates a booking for a room with an animal.
     */
    @PostMapping("/book")
    public String createBooking(@RequestParam String userEmail,
                                @RequestParam String animalType,
                                @RequestParam String name,
                                @RequestParam int age,
                                @RequestParam String gender,
                                @RequestParam String roomType,
                                @RequestParam(required = false) String breed,
                                @RequestParam(required = false) int weight,
                                @RequestParam(required = false) String species,
                                @RequestParam(required = false) String hamsterType) {

        User user = userService.getUserByEmail(userEmail);
        if (user == null) return "User not found!";

        Room availableRoom = roomService.getAvailableRoom(roomType);
        if (availableRoom == null) return "No available rooms of type: " + roomType;

        Animals animal = null;
        int animalID = animalIDCounter.getAndIncrement();

        switch (animalType.toLowerCase()) {
            case "dog" -> animal = new Dog(animalID, name, breed, age, weight, gender);
            case "cat" -> animal = new Cat(animalID, name, breed, age, weight, gender);
            default -> {
                return "Invalid animal type!";
            }
        }

        availableRoom.setAnimal(animal);
        roomService.markRoomOccupied(availableRoom.getId());
        return bookingService.bookRoom(user, animal, availableRoom);
    }

    /**
     * Retrieves all current bookings.
     */
    @GetMapping("/bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    /**
     * Retrieves a list of all available rooms.
     */
    @GetMapping("/available-rooms")
    public List<Room> getAvailableRooms() {
        return roomService.getAvailableRooms();
    }

    /**
     * Simulates a request for live pet status (video/photo).
     */
    @GetMapping("/live-pet-status")
    public String requestLivePetStatus() {
        return "[Request] Sending live video/photo of pet (simulated)";
    }

    /**
     * Sends pet status updates as notifications to users.
     */
    @GetMapping("/pet-status-updates")
    public void showPetStatusUpdates() {
        bookingService.getAllBookings().forEach(booking -> {
            String message = "Status Update for " + booking.getAnimal().getName();
            notificationService.sendNotification(booking.getUser().getEmail(), "Pet Status Update", message);
        });
    }
}
