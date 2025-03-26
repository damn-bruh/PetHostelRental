package com.petrental;

import com.petrental.services.UserService;
import com.petrental.swingui.LoginFrame;
import javax.swing.SwingUtilities;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PetRentalApplication {
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false"); // Ensure GUI mode

        // Start Spring Boot and get the ApplicationContext
        ApplicationContext context = SpringApplication.run(PetRentalApplication.class, args);

        // Retrieve UserService from Spring context
        UserService userService = context.getBean(UserService.class);

        // Run the UI inside Swing's event thread
        SwingUtilities.invokeLater(() -> new LoginFrame(userService).setVisible(true));
    }
}
