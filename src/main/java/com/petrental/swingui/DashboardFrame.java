package com.petrental.swingui;

import com.petrental.services.UserService;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private JButton bookingButton, registerButton, viewBookingsButton;
    private JPanel contentPanel;
    private UserService userService;

    public DashboardFrame(UserService userService) {
        this.userService = userService;
        setTitle("Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel sidebar = new JPanel(new GridLayout(5, 1, 10, 10));

        bookingButton = new JButton("Book a Room");
        registerButton = new JButton("Register as Member");
        viewBookingsButton = new JButton("View My Bookings");

        sidebar.add(bookingButton);
        sidebar.add(registerButton);
        sidebar.add(viewBookingsButton);

        contentPanel = new JPanel(new CardLayout());

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);

        bookingButton.addActionListener(e -> showBookingPanel());
        registerButton.addActionListener(e -> showMemRegPanel());
        viewBookingsButton.addActionListener(e -> showBookingsPanel());
    }

    private void showBookingPanel() {
        contentPanel.removeAll();
        contentPanel.add(new BookingPanel(this, userService));
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showMemRegPanel() {
        contentPanel.removeAll();
        contentPanel.add(new MembershipRegistration(this, userService));
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showBookingsPanel() {
        contentPanel.removeAll();
        contentPanel.add(new DisplayBookingPanel());
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
