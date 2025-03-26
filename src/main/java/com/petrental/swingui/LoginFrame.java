package com.petrental.swingui;

import com.petrental.services.UserService;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final UserService userService;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private JLabel statusLabel;

    public LoginFrame(UserService userService) {
        this.userService = userService;

        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));

        emailField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        statusLabel = new JLabel("", SwingConstants.CENTER);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> new RegisterFrame(userService).setVisible(true));

        add(panel);
        add(loginButton);
        add(registerButton);
        add(statusLabel);
    }

    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Fields cannot be empty!");
            statusLabel.setForeground(Color.RED);
            return;
        }

        if (userService.isUserExists(email)) {
            userService.send2FACode(email); // Send 2FA Code
            new TwoFactorAuthFrame(userService, email, password).setVisible(true);
            dispose(); // Close login window
        } else {
            statusLabel.setText("Invalid email or password!");
            statusLabel.setForeground(Color.RED);
        }
    }
}
