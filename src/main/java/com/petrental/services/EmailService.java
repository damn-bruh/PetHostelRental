package com.petrental.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Sends a customized 2FA email to the specified email address.
     *
     * @param toEmail The recipient's email address.
     * @param code    The 2FA code to be sent.
     */
    public void send2FAEmail(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-smtp-email@gmail.com"); // Ensure this is the authenticated sender email
        message.setTo(toEmail); // Dynamic recipient
        message.setSubject("Your 2FA Code for Pet Rental");
        message.setText("Dear User,\n\n" +
                "Your Two-Factor Authentication (2FA) code is: " + code + "\n" +
                "Please enter this code to complete your authentication process.\n\n" +
                "If you did not request this code, please ignore this email.\n\n" +
                "Thank you,\nPet Rental Team");

        try {
            mailSender.send(message);
            System.out.println("2FA email sent successfully to " + toEmail);
        } catch (Exception e) {
            System.out.println("Failed to send 2FA email: " + e.getMessage());
        }
    }

    /**
     * Sends a generic email.
     *
     * @param toEmail The recipient's email address.
     * @param subject The subject of the email.
     * @param body    The body of the email.
     */
    public void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your-smtp-email@gmail.com"); // Ensure this is the authenticated sender email
        message.setTo(toEmail); // Dynamic recipient
        message.setSubject(subject);
        message.setText(body);

        try {
            mailSender.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (Exception e) {
            System.out.println("Failed to send email: " + e.getMessage());
        }
    }
}
