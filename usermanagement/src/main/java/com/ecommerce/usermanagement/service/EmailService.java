package com.ecommerce.usermanagement.service;

import com.ecommerce.usermanagement.domain.ApplicationProperties;
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

    private final JavaMailSender javaMailSender;

    private final ApplicationProperties applicationProperties;

    @Async
    public void sendVerificationCode(String toEmail, String subject,String verificationCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        String confirmationLink = "http://localhost:" + applicationProperties.getServerPort() +
                "/verify-account?code=" + verificationCode+"&userEmail="+toEmail;

        mailMessage.setText(confirmationLink);
        javaMailSender.send(mailMessage);
    }
}
