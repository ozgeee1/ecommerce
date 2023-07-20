package com.ecommerce.usermanagement.controller;

import com.ecommerce.usermanagement.service.KeyCloakService;

import com.ecommerce.usermanagement.dto.UserDTO;
import com.ecommerce.usermanagement.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "user")
public class KeyCloakController {

    private final KeyCloakService service;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody SignUpRequest signUpRequest){
        return service.addUser(signUpRequest);
    }

    @GetMapping(path = "/{userName}")
    public List<UserRepresentation> getUser(@PathVariable("userName") String userName){
        List<UserRepresentation> user;
        user = service.getUser(userName);
        return user;
    }

    @PutMapping(path = "/update/{userId}")
    public String updateUser(@PathVariable("userId") String userId,   @RequestBody UserDTO userDTO){
        service.updateUser(userId, userDTO);
        return "User Details Updated Successfully.";
    }

    @DeleteMapping(path = "/{userId}")
    public String deleteUser(@PathVariable("userId") String userId){
        service.deleteUser(userId);
        return "User Deleted Successfully.";
    }

    @GetMapping(path = "/reset-password/{userId}")
    public String sendResetPassword(@PathVariable("userId") String userId){
        service.sendResetPassword(userId);
        return "Reset Password Link Send Successfully to Registered E-mail Id.";
    }
}
