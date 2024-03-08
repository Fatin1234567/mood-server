package com.fatin.moodserver.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
@Service
public class EmailService {

    private Properties props;
    private Session session;



    @Value("${email}")
    private String email;
    @Value("${password}")
    private String password;

    // Default constructor with predefined properties
    public EmailService() {
        this.props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        // Initialize session here or in the sendEmail method
    }

    // Constructor that accepts custom properties
    public EmailService(Properties props) {
        this.props = props;
    }

    public boolean sendWelcomeEmail(String toEmail, String name) {
        String subject = "Welcome to MoodTracker Application!";
        String body = "Dear " + name + ",\n\n" +
                "Welcome to MoodTracker application! Here is our website to check how you can interact with the application: https://www.moodtracker.com";

        return sendEmail(toEmail, subject, body);
    }

    private boolean sendEmail(String toEmail, String subject, String body) {
        // Lazy initialization of session if not already initialized
        if (this.session == null) {
            this.session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            });
        }

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Mail sent successfully to " + toEmail);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setProps(Properties props){
        this.props = props;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
