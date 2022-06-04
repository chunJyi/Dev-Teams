package com.spring.devteams.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@ConfigurationProperties(prefix = "my.mail")
@Data
public class MailHelper{


    private String host;
    private int post;
    private String username;
    private String password;
    private Properties properties;

    public void sendEmail(String email,String subject,String url){
        try {

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(host);
        sender.setPort(post);
        sender.setUsername(username);
        sender.setPassword(password);
        sender.setJavaMailProperties(properties);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(url);

        sender.send(message);

        }catch (MailException me){
            me.printStackTrace();
        }
    }

}
