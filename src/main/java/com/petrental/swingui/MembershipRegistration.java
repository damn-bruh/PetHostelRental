package com.petrental.swingui;

import com.petrental.services.UserService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MembershipRegistration extends JPanel {
    private JTextField emailField;
    private JTextField ownerNameField;
    private JTextField phoneNumberField;
    private JButton registerButton;
    private JFrame parentFrame;
    private UserService userService;

    public MembershipRegistration(JFrame parentFrame, UserService userService) {
        this.parentFrame = parentFrame;
        this.userService = userService;

        setLayout(new GridLayout(4, 2, 10, 10));

        emailField = new JTextField();
        ownerNameField = new JTextField();
        phoneNumberField = new JTextField();
        registerButton = new JButton("Register Membership");

        add(new JLabel("Email:"));
        add(emailField);
        add(new JLabel("Owner Name:"));
        add(ownerNameField);
        add(new JLabel("Phone Number:"));
        add(phoneNumberField);
        add(new JLabel());
        add(registerButton);

        registerButton.addActionListener((ActionEvent e) -> {
            String email = emailField.getText().trim();
            String ownerName = ownerNameField.getText().trim();
            String phone = phoneNumberField.getText().trim();

            if (email.isEmpty() || ownerName.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(null, "Membership registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            parentFrame.dispose();
            new DashboardFrame(userService).setVisible(true);
        });
    }
}
