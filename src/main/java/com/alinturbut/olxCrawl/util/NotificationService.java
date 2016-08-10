package com.alinturbut.olxCrawl.util;


import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

@Service
public class NotificationService {
    private Logger log = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(String href) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            String author = readToAndFrom();
            helper.setTo(author);
            helper.setReplyTo(author);
            helper.setFrom(author);
            helper.setSubject("Your search on OLX now has other results");
            helper.setText("Latest added item: " + href);
        } catch (MessagingException e) {
            log.error(e.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        javaMailSender.send(mail);
    }

    public String readToAndFrom() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.properties");

        if(inputStream != null) {
            properties.load(inputStream);
        } else {
            log.error("No properties file found!");
        }

        return properties.getProperty("spring.mail.username");
    }
}
