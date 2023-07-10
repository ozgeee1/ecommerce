package com.ecommerce.usermanagement.service;

import com.ecommerce.usermanagement.domain.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private final JavaMailSender javaMailSender;

    @Async
    public void sendVerificationCode(String toEmail, String subject,String verificationCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(Constants.EMAIL_MESSAGE+verificationCode);
        javaMailSender.send(mailMessage);
    }
}
