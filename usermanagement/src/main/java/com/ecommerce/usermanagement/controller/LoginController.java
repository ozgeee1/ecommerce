package com.ecommerce.usermanagement.controller;

import com.ecommerce.usermanagement.domain.Constants;
import com.ecommerce.usermanagement.domain.User;
import com.ecommerce.usermanagement.repository.UserRepository;
import com.ecommerce.usermanagement.request.SignUpRequest;
import com.ecommerce.usermanagement.service.EmailService;
import com.ecommerce.usermanagement.service.VerificationCodeGenerator;
import jakarta.persistence.Cacheable;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class LoginController {


    private final EmailService emailService;

    private final UserRepository userRepository;

    @PostMapping("user")
    public ResponseEntity<String> signUpUser(@RequestBody SignUpRequest signUpRequest){
        User newUser = User.builder().email(signUpRequest.getEmail()).password(signUpRequest.getPassword()).build();

        //todo created at calısmıyor bak
        newUser.setCreated_at(LocalDateTime.now());
        userRepository.save(newUser);
        String code = VerificationCodeGenerator.generateRandomNumber(Constants.VERIFICATION_CODE_LENGTH);
        emailService.sendVerificationCode(newUser.getEmail(), Constants.EMAIL_SUBJECT,code);
        return ResponseEntity.ok(String.format("Doğrulama kodunuz %s adresine gönderilmiştir",signUpRequest.getEmail()));
    }

}
