package io.github.joselitosn.notifications;

import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class SMTPNotification extends NotificationHandler {
    private String destination;

    public SMTPNotification(String destination) {
        this.destination = destination;
    }

    @Override
    public void handle(Notification notification) {
        // send email notification to destination
        String from = "test@gmail.com";
        String subject = notification.getTitle();
        String body = notification.getMessage();

        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(from, "your_password");
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setFrom(from);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destination));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (Exception e) {
            System.err.println("Error sending email notification: " + e.getMessage());
        }


        System.out.println("Sending email notification: " + notification.getMessage());
        super.handle(notification);
    }
}
