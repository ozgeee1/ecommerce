package com.ecommerce.usermanagement.service;

import com.ecommerce.usermanagement.domain.Constants;
import com.ecommerce.usermanagement.domain.User;
import com.ecommerce.usermanagement.exception.BadRequestException;
import com.ecommerce.usermanagement.repository.UserRepository;
import com.ecommerce.usermanagement.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final RedisService redisService;


    public ResponseEntity<?> signUpUser(SignUpRequest signUpRequest){
        User newUser = User.builder().email(signUpRequest.getEmail()).password(signUpRequest.getPassword()).build();
        if(isEmailTaken(newUser.getEmail())){
            throw new BadRequestException("This email is already taken");
        }

        //todo created at calısmıyor bak
        newUser.setCreated_at(LocalDateTime.now());
        userRepository.save(newUser);
        String code = VerificationCodeGenerator.generateRandomNumber(Constants.VERIFICATION_CODE_LENGTH);
        redisService.set(signUpRequest.getEmail(),code,Constants.REDIS_EXPIRATION_MIN);
        emailService.sendVerificationCode(newUser.getEmail(), Constants.EMAIL_SUBJECT,code);
        return ResponseEntity.ok(String.format("Verify code has been sent to  %s email address. ",signUpRequest.getEmail()));

    }

    public ResponseEntity<?> verifyAccount(String code,String userEmail){
        Object value = redisService.get(userEmail);
        User byEmail = userRepository.findByEmail(userEmail);
        boolean verified = false;
        if (value!=null & code.equals(value)){
            verified=true;
            byEmail.setEmailVerified(true);
            userRepository.save(byEmail);

        }
        return verified ? ResponseEntity.ok("Email is verified") : ResponseEntity.ok("Email is not verified");

    }

    private boolean isEmailTaken(String email){
        User byEmail = userRepository.findByEmail(email);
        return byEmail != null ;

    }


}
