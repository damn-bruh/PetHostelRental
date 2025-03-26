package com.petrental.swingui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.petrental.models.Booking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DisplayBookingPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private static final String FILE_PATH = "bookings.json";
    private ObjectMapper objectMapper;

    public DisplayBookingPanel() {
        setLayout(new BorderLayout());
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Define table model with proper column names
        tableModel = new DefaultTableModel(new String[]{"Pet", "Room", "Check-in", "Check-out", "Total Price"}, 0);
        table = new JTable(tableModel);
        loadBookings();

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadBookings() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;  // If no file, do nothing

        try {
            // Read JSON file into List<Booking>
            List<Booking> bookings = objectMapper.readValue(file, new TypeReference<List<Booking>>() {});

            // Clear table before loading data
            tableModel.setRowCount(0);

            // Add each booking to the table
            for (Booking booking : bookings) {
                tableModel.addRow(new Object[]{
                        booking.getPet(),
                        booking.getRoom(),
                        booking.getCheckIn().toString(),
                        booking.getCheckOut().toString(),
                        "RM" + booking.getTotalPrice()
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
