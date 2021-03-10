package tunifast.esprit.Utils;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import tunifast.esprit.Service.ReservationCrud;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dimassi Abdelhak
 */
public class JavamailUtilRes {
    public static void sendMail(String recepient,String lieu,int place) throws Exception{
        System.out.println("Preparing to send:");
        Properties properties = new Properties();       
        
        String myAccountEmail ="razi.fertani@esprit.tn";
        String password ="RaziFertani1";
        
        properties.put("com.hof.email.starttime","20170519094544");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.connectiontimeout","60000");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.ssl.trust","*");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.timeout","60000");
        properties.put("mail.transport.protocol","smtp");
        
        
        
        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        
        Message message = prepareMessage(session,myAccountEmail,recepient,lieu,place);
        
        Transport.send(message);
        System.out.println("message send successfully");
    }
    
    private static Message prepareMessage(Session session, String myAccountEmail,String recepient,String lieuu, int placee){
        try {
            ReservationCrud Act = new ReservationCrud();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            
            message.setSubject("Nouvelle réservation");
            message.setText("Une nouvelle réservation a été effectué, qui part de " +lieuu+" \nIl vous reste "+placee);
            
            return message;
        } catch (MessagingException ex) {
            Logger.getLogger(JavamailUtilRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
