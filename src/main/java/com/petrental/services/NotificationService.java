package com.petrental.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends a notification email to the user.
     * @param toEmail The recipient's email address.
     * @param subject The subject of the email.
     * @param body The body of the email message.
     */
    public void sendNotification(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-email@example.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        try {
            mailSender.send(message);
            System.out.println("Notification sent successfully to " + toEmail);
        } catch (Exception e) {
            System.out.println("Failed to send notification: " + e.getMessage());
        }
    }
}
