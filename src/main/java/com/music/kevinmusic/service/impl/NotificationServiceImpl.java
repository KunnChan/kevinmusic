package com.music.kevinmusic.service.impl;

import com.music.kevinmusic.request.Notification;
import com.music.kevinmusic.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    @Value("${kevin.music.to.email}")
    private String toMail;

    @Value("${kevin.music.from.email}")
    private String fromMail;

    private final JavaMailSender javaMailSender;

    public NotificationServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    @Override
    public void sendNotification(Notification notification) {

        log.info("Notification Body {} ", notification);
        log.info("From {} ", fromMail);
        log.info("To {} ", toMail);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toMail);
        mail.setFrom(fromMail);
        mail.setSubject("PlayMySong Feedback from "+ notification.getTitle());
        mail.setText(notification.getDesc());
        javaMailSender.send(mail);

        log.info("Email Sent!");
    }
}
