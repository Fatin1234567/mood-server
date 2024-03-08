package com.fatin.moodserver;

import com.fatin.moodserver.Service.EmailService;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.mail.internet.MimeMessage;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmailServiceIntegrationTest {


    private GreenMail greenMail;
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(ServerSetupTest.SMTP);
        greenMail.setUser("test@localhost.com", "password");
        greenMail.start();

        // Manually set properties for the test
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", String.valueOf(greenMail.getSmtp().getPort()));


        // Assuming you add a setter method or public field for props
        this.emailService = new EmailService(props);
        this.emailService.setEmail("test@localhost.com");
        this.emailService.setPassword("password");
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @Test
    void testSendWelcomeEmail() throws Exception {
        String toEmail = "recipient@example.com";
        String name = "Recipient Name";

        boolean result = emailService.sendWelcomeEmail(toEmail, name);
        assertTrue(result);

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        assertEquals(1, receivedMessages.length);
        MimeMessage receivedMessage = receivedMessages[0];

        assertEquals("Welcome to MoodTracker Application!", receivedMessage.getSubject());
        assertTrue(receivedMessage.getContent().toString().contains(name));
        assertTrue(receivedMessage.getContent().toString().contains("Welcome to MoodTracker application!"));
    }
}
