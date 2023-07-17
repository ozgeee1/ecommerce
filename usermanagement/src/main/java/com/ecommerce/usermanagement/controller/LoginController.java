package com.ecommerce.usermanagement.controller;

import com.ecommerce.usermanagement.config.JwtTokenUtil;
import com.ecommerce.usermanagement.request.LoginRequest;
import com.ecommerce.usermanagement.request.SignUpRequest;
import com.ecommerce.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final UserService userService;


    @PostMapping("user")
    public ResponseEntity<?> signUpUser(@RequestBody SignUpRequest signUpRequest) throws Exception {
       return userService.signUpUser(signUpRequest);
    }

    @GetMapping("verify-account")
    public ResponseEntity<?> verifyUserAccount(@RequestParam("code") String code, @RequestParam("userEmail") String userEmail){
        return userService.verifyAccount(code,userEmail);
    }

    @GetMapping("hello")
    public String sayHello(){
        return "hello";
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) throws Exception {
        return userService.loginUser(loginRequest);
    }


}
