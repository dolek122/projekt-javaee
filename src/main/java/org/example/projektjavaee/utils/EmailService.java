package org.example.projektjavaee.utils;

import jakarta.ejb.Stateless;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


//wysylanie powiadomien na maila

import java.util.Properties;

@Stateless
public class EmailService {

    private final String fromEmail = "ezreal9956@gmail.com"; // <- zmień na swój
    private final String password = "mnmm jpca grdo ityw";            // <- uwzględnij hasło aplikacji
    private final String host = "smtp.gmail.com";

    public void sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("[EMAIL] Wysłano do: " + to);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
