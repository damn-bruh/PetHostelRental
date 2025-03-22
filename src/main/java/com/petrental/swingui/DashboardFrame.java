package com.petrental.swingui;

import com.petrental.services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardFrame extends JFrame {

    private JButton bookingButton;
    private JButton registerButton;
    private JPanel contentPanel;
    private UserService userService;

    public DashboardFrame(UserService userService) {
        this.userService = userService;
        setTitle("Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Sidebar with navigation buttons
        JPanel sidebar = new JPanel(new GridLayout(5, 1, 10, 10));
        bookingButton = new JButton("Book a Room");
        registerButton = new JButton("Register as Member");

        sidebar.add(bookingButton);
        sidebar.add(registerButton);

        // Content panel (where different sections will be shown)
        contentPanel = new JPanel(new CardLayout());

        // Add sidebar and content panel to the frame
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Button Click Action
        bookingButton.addActionListener(e -> showBookingPanel(this));
        registerButton.addActionListener(e -> showMembershipRegistration(this));
    }

    private void showBookingPanel(JFrame parentFrame) {
        contentPanel.removeAll(); // Clear previous content
        contentPanel.add(new BookingPanel(parentFrame, userService)); // Pass both JFrame and UserService
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showMembershipRegistration(JFrame parentFrame) {
        contentPanel.removeAll(); // Clear previous content
        contentPanel.add(new MembershipRegistration(parentFrame, userService)); // Pass both JFrame and UserService
        contentPanel.revalidate();
        contentPanel.repaint();
    }


}