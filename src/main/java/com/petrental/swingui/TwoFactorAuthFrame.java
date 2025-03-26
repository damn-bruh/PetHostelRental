package com.petrental.swingui;

import com.petrental.services.UserService;
import javax.swing.*;
import java.awt.*;

public class TwoFactorAuthFrame extends JFrame {
    private final UserService userService;
    private final String email;
    private final String password;

    private JTextField codeField;
    private JButton verifyButton;
    private JLabel statusLabel;

    public TwoFactorAuthFrame(UserService userService, String email, String password) {
        this.userService = userService;
        this.email = email;
        this.password = password;

        setTitle("Two-Factor Authentication");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1));

        codeField = new JTextField();
        verifyButton = new JButton("Verify");
        statusLabel = new JLabel("", SwingConstants.CENTER);

        verifyButton.addActionListener(e -> verifyCode());

        add(new JLabel("Enter 2FA Code:"));
        add(codeField);
        add(verifyButton);
        add(statusLabel);
    }

    private void verifyCode() {
        String code = codeField.getText().trim();

        if (userService.authenticateUser(email, password, code)) {
            statusLabel.setText("Login Successful!");
            statusLabel.setForeground(Color.GREEN);

            SwingUtilities.invokeLater(() -> {
                new DashboardFrame(userService).setVisible(true);
                dispose(); // Close 2FA window
            });
        } else {
            statusLabel.setText("Invalid 2FA code!");
            statusLabel.setForeground(Color.RED);
        }
    }
}
