package com.petrental.controllers;

import com.petrental.models.User;
import com.petrental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Registers a new user with their email and password.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @return A response indicating the result of the registration.
     */
    @PostMapping("/register")
    public String registerUser(@RequestParam String email, @RequestParam String password) {
        boolean isRegistered = userService.registerUser(email, password);
        if (isRegistered) {
            return "User registered successfully!";
        } else {
            return "Registration failed: User already exists with email " + email;
        }
    }
    /**
     * Retrieves all registered users.
     *
     * @return A list of registered users.
     */
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    /**
     * Initiates the 2FA process by sending a 2FA code to the user's email.
     *
     * @param email The user's email.
     * @return A response indicating if the 2FA code was sent.
     */
    @PostMapping("/send-2fa")
    public String send2FACode(@RequestParam String email) {
        boolean isSent = userService.send2FACode(email);
        if (isSent) {
            return "2FA code sent to " + email;
        } else {
            return "Failed to send 2FA code: User not found with email " + email;
        }
    }

    /**
     * Authenticates the user with email, password, and 2FA code.
     *
     * @param email    The user's email.
     * @param password The user's password.
     * @param code     The 2FA code provided by the user.
     * @return A response indicating the authentication status.
     */
    @PostMapping("/authenticate")
    public String authenticateUser(@RequestParam String email, @RequestParam String password, @RequestParam String code) {
        boolean isAuthenticated = userService.authenticateUser(email, password, code);
        if (isAuthenticated) {
            return "User authenticated successfully!";
        } else {
            return "Authentication failed: Invalid credentials or 2FA code.";
        }
    }
}
