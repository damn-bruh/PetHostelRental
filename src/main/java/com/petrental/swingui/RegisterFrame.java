package com.petrental.swingui;

import com.petrental.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    private final UserService userService;

    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JLabel statusLabel;

    public RegisterFrame(UserService userService) {
        this.userService = userService; // Store reference to UserService

        setTitle("User Registration");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1));

        // UI Components
        emailField = new JTextField();
        passwordField = new JPasswordField();
        registerButton = new JButton("Register");
        statusLabel = new JLabel("", SwingConstants.CENTER);

        // Panel to arrange input fields
        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        // Add action listener to button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });

        // Add components to frame
        add(panel);
        add(registerButton);
        add(statusLabel);
    }

    private void handleRegistration() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        // Validate input
        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Fields cannot be empty!");
            statusLabel.setForeground(Color.RED);
            return;
        }

        // Register user using UserService
        boolean success = userService.registerUser(email, password);
        if (success) {
            statusLabel.setText("Registration successful!");
            statusLabel.setForeground(Color.GREEN);
        } else {
            statusLabel.setText("User already exists!");
            statusLabel.setForeground(Color.RED);
        }
    }
}
