package com.main.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("spring.mail.username")
    private String senderEmail;


    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public boolean sendResetCodeEmail(String recipientEmail, String resetCode) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(senderEmail);
            helper.setTo(recipientEmail);
            helper.setSubject("Password Reset Code");
            String contentBuilder = "Dear User,\n\n" +
                    "Your password reset code is: " + resetCode + "\n\n" +
                    "Please use this code to reset your password.";

            helper.setText(contentBuilder);

            javaMailSender.send(message);

            System.out.println("Password reset code email sent successfully.");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send password reset code email: " + e.getMessage());
            return false;
        } catch (Exception ex) {

            ex.printStackTrace();
            System.out.println("An error occurred: " + ex.getMessage());
            return false;
        }
    }
}
