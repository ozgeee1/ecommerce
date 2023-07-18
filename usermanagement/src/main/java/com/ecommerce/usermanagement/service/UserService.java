package com.ecommerce.usermanagement.service;

import com.ecommerce.usermanagement.config.EcommerceUserDetails;
import com.ecommerce.usermanagement.constants.ApplicationConstants;
import com.ecommerce.usermanagement.domain.Authority;
import com.ecommerce.usermanagement.domain.User;
import com.ecommerce.usermanagement.exception.BadRequestException;
import com.ecommerce.usermanagement.exception.UserNotFoundException;
import com.ecommerce.usermanagement.repository.AuthorityRepository;
import com.ecommerce.usermanagement.repository.UserRepository;
import com.ecommerce.usermanagement.request.LoginRequest;
import com.ecommerce.usermanagement.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final RedisService redisService;


    private final EcommerceUserDetails userDetails;
    
    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;


    public ResponseEntity<?> signUpUser(SignUpRequest signUpRequest) throws Exception {
        if(isEmailTaken(signUpRequest.getEmail())){
            throw new BadRequestException("This email is already taken");
        }
        User newUser = saveUserWithAuthority(signUpRequest, "USER");
        String code = VerificationCodeGenerator.generateRandomNumber(ApplicationConstants.VERIFICATION_CODE_LENGTH);
        redisService.set(signUpRequest.getEmail(),code, ApplicationConstants.REDIS_EXPIRATION_MIN);
        emailService.sendVerificationCode(newUser.getEmail(), ApplicationConstants.EMAIL_SUBJECT,code);
        return ResponseEntity.ok(String.format("Verify code has been sent to  %s email address. ",signUpRequest.getEmail()));

    }

    public User saveUserWithAuthority(SignUpRequest signUpRequest, String role) {
        String hashPwd = passwordEncoder.encode(signUpRequest.getPassword());
        Authority authority = Authority.builder().role(role).build();
        authority.setCreated_at(LocalDateTime.now());
        User newUser = User.builder().email(signUpRequest.getEmail()).password(hashPwd).build();
        newUser.setCreated_at(LocalDateTime.now());

        //todo created at calısmıyor bak

        User save = userRepository.save(newUser);
        authority.setUser(save);
        authorityRepository.save(authority);

        return save;
    }


    private User getUserByEmail(String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException(String.format("User with %s email not found",email));
        }
        return optionalUser.get();

    }

    public ResponseEntity<?> verifyAccount(String code,String userEmail){
        Object value = redisService.get(userEmail);
        User byEmail = getUserByEmail(userEmail);
        boolean verified = false;
        if (value!=null && code.equals(value)){
            verified=true;
            byEmail.setEmailVerified(true);
            userRepository.save(byEmail);

        }
        return verified ? ResponseEntity.ok("Email is verified") : ResponseEntity.ok("Email is not verified");

    }

    private boolean isEmailTaken(String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        return optionalUser.isPresent();

    }

    private void authenticate(String email, String password) throws Exception {
        Objects.requireNonNull(email);
        Objects.requireNonNull(password);

    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest) throws Exception {
        authenticate(loginRequest.getEmail(),loginRequest.getPassword());
        UserDetails uDetails = userDetails.loadUserByUsername(loginRequest.getEmail());
        return ResponseEntity.ok("ok");
    }


}
