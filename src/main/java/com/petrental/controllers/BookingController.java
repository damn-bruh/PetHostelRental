package com.petrental.controllers;

import com.petrental.models.*;
import com.petrental.services.BookingService;
import com.petrental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    /**
     * Books a room for a specific type of animal and user.
     *
     * @param userEmail User's email.
     * @param animalType Type of animal (Dog, Cat, Bird, Hamster).
     * @param animalID Animal's ID.
     * @param animalName Animal's name.
     * @param breed Animal's breed.
     * @param age Animal's age.
     * @param weight Animal's weight.
     * @param gender Animal's gender.
     * @param roomId Room ID.
     * @return Response indicating the booking status.
     */
    @PostMapping("/bookRoom")
    public ResponseEntity<String> bookRoom(
            @RequestParam String userEmail,
            @RequestParam String animalType,
            @RequestParam int animalID,
            @RequestParam String animalName,
            @RequestParam String breed,
            @RequestParam int age,
            @RequestParam int weight,
            @RequestParam String gender,
            @RequestParam int roomId) {

        System.out.println("bookRoom endpoint hit!");

        if (userEmail.isEmpty() || animalType.isEmpty() || animalName.isEmpty() || breed.isEmpty() || gender.isEmpty()) {
            return ResponseEntity.badRequest().body("All parameters must be provided!");
        }

        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found with email: " + userEmail);
        }

        Animals animal = switch (animalType.toLowerCase()) {
            case "dog" -> new Dog(animalID, animalName, breed, age, weight, gender);
            case "cat" -> new Cat(animalID, animalName, breed, age, weight, gender);
            default -> null;
        };

        if (animal == null) {
            return ResponseEntity.badRequest().body("Invalid animal type: " + animalType);
        }

        Room room = new Room(roomId, "Room Type");

        String bookingResult = bookingService.bookRoom(user, animal, room);
        if (bookingResult.startsWith("Booking confirmed")) {
            return ResponseEntity.ok(bookingResult);
        } else {
            return ResponseEntity.badRequest().body(bookingResult);
        }
    }
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("BookingController is working!");
    }


    /**
     * Retrieves all current bookings.
     *
     * @return List of all bookings.
     */
    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

}
