package com.wesolemarcheweczki.backend.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@PropertySource("classpath:backend.properties")
@Component
public class MailSender {
    private static final Properties prop = new Properties();
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    @Value("${com.wesolemarcheweczki.backend.mail.address}")
    String from;
    @Value("${com.wesolemarcheweczki.backend.mail.password}")
    String pass;
    @Value("${com.wesolemarcheweczki.backend.mail.host}")
    String host;
    @Value("${com.wesolemarcheweczki.backend.mail.port}")
    String port;

    public Future<Boolean> sendMailAsync(String to, String subject, String body) {
        return executor.submit(() -> sendMail(to, subject, body));
    }

    public boolean sendMail(String to, String subject, String body) {

        try {
            sendMessage(to, subject, body);
            return true;

        } catch (MessagingException ae) {
            ae.printStackTrace();
        }
        return false;
    }

    @PostConstruct
    private void init() {
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.ssl.trust", host);
        prop.put("mail.smtp.user", from);
        prop.put("mail.smtp.password", pass);
    }

    private void sendMessage(String to, String subject, String body) throws MessagingException {
        Session session = Session.getDefaultInstance(prop);

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setText(body);

        Transport transport = session.getTransport("smtp");

        transport.connect(host, from, pass);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
