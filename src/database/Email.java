package database;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.AddressException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * The Email-class is used to handle everything that has to do with emails
 * @author Mahmoud, Håkon
 */
public class Email {

    /**
     * This method checks the validity of an email address
     * @param email the email address to validate
     * @return true if the email is valid, false if invalid
     */
    public static boolean isValidEmail(String email){
        try {
            InternetAddress address = new InternetAddress(email);
            address.validate();
        } catch (AddressException e){
            System.out.println("Invalid email address");
            e.printStackTrace();

            return false;
        }

        return true;
    }

    /**
     * This method sends an email to an email address
     * @param email the email address to send to
     * @param title title of the email
     * @param msg the message in the email
     * @return rue if email was sent successfully, false otherwise
     */
    public static boolean send(String email, String title, String msg){

        //Sets properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("monoplyteam12@gmail.com","12345678team12");
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("monoplyteam12@gmail.com", "Monopoly Game"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(title);
            message.setText(msg);

            Transport.send(message);
        } catch (UnsupportedEncodingException | MessagingException e) {
            System.out.println("Could not sent mail to " + email);
            e.printStackTrace();

            return false;
        }

        return true;
    }
}