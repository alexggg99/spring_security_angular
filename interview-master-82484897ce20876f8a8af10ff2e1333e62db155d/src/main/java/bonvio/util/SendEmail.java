package bonvio.util;


import bonvio.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class SendEmail {

    @Value("${smtp.host}")
    private String host;
    @Value("${smtp.user}")
    private String username;
    @Value("${smtp.password}")
    private String password;
    @Value("${restore.url}")
    private String restoreURL;


    public void sendMail(User user) {

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(user.username));
            message.setSubject("Restore password");
            String mes = //"Dear ," + user.getFirstName() != null ? user.getFirstName() : user.username
                    "\r This is automated generated email." +
                    "\r You can restore your password by this link : " + restoreURL +
                    "\r Restore token: " + user.getRestoreToken() +
                    "\r Please restore password within one hour !";
            message.setText(mes);
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }



}
