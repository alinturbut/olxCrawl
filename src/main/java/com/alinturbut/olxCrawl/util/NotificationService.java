package com.alinturbut.olxCrawl.util;


import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.Charset;

@Service
public class NotificationService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void send(String href) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo("alinturbut@gmail.com");
            helper.setReplyTo("alinturbut@gmail.com");
            helper.setFrom("alinturbut@gmail.com");
            helper.setSubject("Masini noi pe OLX");
            helper.setText("Fugi repideeeeeee! Ultima masina adaugata: " + href);
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {}

        javaMailSender.send(mail);
    }
}
