/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruddemo.util;

import com.cruddemo.model.User;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.Security;
import java.util.Date;
import java.util.Properties;
import org.apache.catalina.util.SessionConfig;

/**
 *
 * @author nitin
 */
public class EmailUtils {

    private static final String FROM = "ashwini@takla.co.in";
    private static final String PASSWORD = "22";
    private static final String host = "192.168.1.70";

    public static boolean sendResetPasswordEmail(User user,String token) {
        Session session = getSession();
        if(session == null)
        {
            return false;
        }
        MimeMessage message = new MimeMessage(session);
        try {
            message.setSubject("Reset your password of PGC blog");
            message.setSentDate(new Date());
            message.setFrom(new InternetAddress(FROM));
            System.out.println("email is : "+user.getEmail());
            message.setRecipient(RecipientType.TO, new InternetAddress(user.getEmail()));
            //message.setContent("Please click this link to reset your password:" + GenerateLinkUtils.generateResetPwdLink(user) + " cheers! ", "text/html;charset=utf-8");
            
            message.setContent("Please click this link to reset your password:" + GenerateLinkUtils.generateResetPwdLink(token) + " cheers! ", "text/html;charset=utf-8");

           // send
            Transport.send(message);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    //todo connection
    public static Session getSession() {
       // Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
       // final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

        Properties props = new Properties();
        props.setProperty("mail.smtp.host", host);
       // props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
       // props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", "27");
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.debug", "true");

//        Authenticator auth = new Authenticator() {
//            public PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("pgcgroup2@sina.com", "aucklanduni");
//            }
//        };
//         Session session = Session.getInstance(props, auth);
        Session session = Session.getInstance(props, new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(FROM, PASSWORD);
            }

        });
        return session;
    }
}
