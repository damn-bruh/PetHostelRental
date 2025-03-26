package com.petrental.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrental.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private EmailService emailService;

    private final List<User> users = new ArrayList<>();
    private final Map<String, String> user2FACodes = new HashMap<>();
    private final Map<String, Long> codeExpiryTimes = new HashMap<>();

    private static final long CODE_EXPIRY_DURATION_MS = 5 * 60 * 1000; // 5 minutes
    private static final String FILE_PATH = "users.json"; // File to store user data

    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserService() {
        loadUsersFromFile(); // Load users on startup
    }

    //Registers a new user and saves to file
    public boolean registerUser(String email, String password) {
        email = email.trim();
        password = password.trim();

        if (isUserExists(email)) {
            System.out.println("User already exists with email: " + email);
            return false;
        }
        users.add(new User(email, password));
        saveUsersToFile(); // Save users to file
        return true;
    }

    public boolean send2FACode(String email) {
        email = email.trim(); // Trim email input
        User user = getUserByEmail(email);
        if (user == null) return false;

        String code = generate2FACode();
        user2FACodes.put(email, code);
        codeExpiryTimes.put(email, System.currentTimeMillis() + CODE_EXPIRY_DURATION_MS);

        emailService.send2FAEmail(email, code);
        return true;
    }


    //Authenticates the user with email, password, and 2FA code
    public boolean authenticateUser(String email, String password, String code) {
        email = email.trim();
        User user = getUserByEmail(email);
        if (user == null || !user.getPassword().equals(password.trim())) {
            return false;
        }
        return validate2FACode(email, code);
    }

    //Returns a list of all registered users
    public List<User> getAllUsers() {
        return users;
    }

    //Validates the 2FA code for a given email
    public boolean validate2FACode(String email, String code) {
        email = email.trim(); // Trim email
        code = code.trim(); // Trim code

        String storedCode = user2FACodes.get(email);
        Long expiryTime = codeExpiryTimes.get(email);

        if (storedCode == null || !storedCode.equals(code)) {
            System.out.println("Invalid 2FA code.");
            return false;
        }

        if (expiryTime == null || System.currentTimeMillis() > expiryTime) {
            System.out.println("2FA code expired.");
            user2FACodes.remove(email);
            codeExpiryTimes.remove(email);
            return false;
        }

        user2FACodes.remove(email);
        codeExpiryTimes.remove(email);
        return true;
    }


    //Checks if a user with the given email exists
    public boolean isUserExists(String email) {
        return users.stream().anyMatch(user -> user.getEmail().equals(email));
    }


     //Retrieves a user by their email
    public User getUserByEmail(String email) {
        return users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
    }

    //Generates a random 6-digit 2FA code
    private String generate2FACode() {
        return String.format("%06d", new Random().nextInt(1000000));
    }

    //Saves the list of users to a JSON file
    private void saveUsersToFile() {
        try {
            objectMapper.writeValue(new File(FILE_PATH), users);
            System.out.println("Users saved to file.");
        } catch (IOException e) {
            System.err.println("Failed to save users: " + e.getMessage());
        }
    }

    // Loads the list of users from a JSON file
    private void loadUsersFromFile() {
        try {
            File file = new File(FILE_PATH);
            if (file.exists()) {
                List<User> loadedUsers = objectMapper.readValue(file, new TypeReference<List<User>>() {});
                users.clear();
                users.addAll(loadedUsers);
                System.out.println("Users loaded from file.");
            }
        } catch (IOException e) {
            System.err.println("Failed to load users: " + e.getMessage());
        }
    }
}
