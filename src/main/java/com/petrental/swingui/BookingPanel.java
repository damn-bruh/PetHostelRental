package com.petrental.swingui;

import com.petrental.services.UserService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookingPanel extends JPanel {
    private JComboBox<String> petDropdown;
    private JComboBox<String> roomDropdown;
    private JTextField checkInField;
    private JTextField checkOutField;
    private JTextField petNameField;
    private JComboBox<String> petTypeDropdown;
    private JTextField breedField;
    private JTextField ageField;
    private JTextField weightField;
    private JComboBox<String> genderDropdown;
    private JCheckBox petSpecialCondition;
    private JButton addPetButton;
    private JButton bookButton;
    private JFrame parentFrame;
    private UserService userService;
    private List<String> petList;

    public BookingPanel(JFrame parentFrame, UserService userService) {
        this.parentFrame = parentFrame;
        this.userService = userService;
        this.petList = new ArrayList<>();

        setLayout(new GridLayout(0, 2, 10, 10));

        petDropdown = new JComboBox<>();
        updatePetDropdown();
        roomDropdown = new JComboBox<>(new String[]{"Select Room", "Small Room (RM50)", "Large Room (RM80)"});
        checkInField = new JTextField();
        checkOutField = new JTextField();
        petNameField = new JTextField();
        petTypeDropdown = new JComboBox<>(new String[]{"Cat", "Dog", "Other"});
        breedField = new JTextField();
        ageField = new JTextField();
        weightField = new JTextField();
        genderDropdown = new JComboBox<>(new String[]{"Male", "Female"});
        petSpecialCondition = new JCheckBox("Special Condition (Extra RM50)");
        addPetButton = new JButton("Add Pet");
        bookButton = new JButton("Book Room");

        add(new JLabel("Pet Name:"));
        add(petNameField);
        add(new JLabel("Pet Type:"));
        add(petTypeDropdown);
        add(new JLabel("Breed:"));
        add(breedField);
        add(new JLabel("Age:"));
        add(ageField);
        add(new JLabel("Weight (kg):"));
        add(weightField);
        add(new JLabel("Gender:"));
        add(genderDropdown);
        add(new JLabel());
        add(addPetButton);
        add(new JLabel("Select Pet:"));
        add(petDropdown);
        add(new JLabel("Select Room:"));
        add(roomDropdown);
        add(new JLabel("Check-in Date (YYYY-MM-DD):"));
        add(checkInField);
        add(new JLabel("Check-out Date (YYYY-MM-DD):"));
        add(checkOutField);
        add(new JLabel());
        add(petSpecialCondition);
        add(new JLabel());
        add(bookButton);

        addPetButton.addActionListener(e -> {
            String petName = petNameField.getText().trim();
            String petType = (String) petTypeDropdown.getSelectedItem();
            String breed = breedField.getText().trim();
            String age = ageField.getText().trim();
            String weight = weightField.getText().trim();
            String gender = (String) genderDropdown.getSelectedItem();

            if (petName.isEmpty() || breed.isEmpty() || age.isEmpty() || weight.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all pet details.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            petList.add(petName + " (" + petType + ", " + breed + ", " + age + " years, " + weight + " kg, " + gender + ")");
            updatePetDropdown();
            JOptionPane.showMessageDialog(null, "Pet added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            petNameField.setText("");
            breedField.setText("");
            ageField.setText("");
            weightField.setText("");
        });

        bookButton.addActionListener(e -> {
            String pet = (String) petDropdown.getSelectedItem();
            String room = (String) roomDropdown.getSelectedItem();
            String checkIn = checkInField.getText();
            String checkOut = checkOutField.getText();

            if (pet == null || pet.equals("Select Pet") || room.equals("Select Room") || checkIn.isEmpty() || checkOut.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
                LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);
                long daysBetween = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

                if (daysBetween <= 0) {
                    JOptionPane.showMessageDialog(null, "Check-out date must be after check-in date.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int roomPrice = room.equals("Small Room (RM50)") ? 50 : 80;
                int specialConditionFee = petSpecialCondition.isSelected() ? 50 : 0;
                int totalPrice = (int) ((roomPrice * daysBetween) + specialConditionFee);

                JOptionPane.showMessageDialog(null, "Booking confirmed for " + pet + " in " + room + " from " + checkIn + " to " + checkOut + ".\nTotal Price: RM" + totalPrice, "Success", JOptionPane.INFORMATION_MESSAGE);
                parentFrame.dispose();
                new DashboardFrame(userService).setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Please enter the date as YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void updatePetDropdown() {
        petDropdown.removeAllItems();
        petDropdown.addItem("Select Pet");
        for (String pet : petList) {
            petDropdown.addItem(pet);
        }
    }
}
