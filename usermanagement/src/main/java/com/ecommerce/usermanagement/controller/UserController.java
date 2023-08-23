package com.ecommerce.usermanagement.controller;

import com.ecommerce.usermanagement.exception.CustomAuthenticationException;
import com.ecommerce.usermanagement.request.SignUpUserRequest;
import com.ecommerce.usermanagement.request.UpdateUserRequest;
import com.ecommerce.usermanagement.service.KeyCloakService;
import com.ecommerce.usermanagement.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "user")
public class UserController {

    private final KeyCloakService service;

    private final SecurityService securityService;

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody SignUpUserRequest signUpUserRequest){
        return service.addUser(signUpUserRequest);
    }

    @PutMapping(path = "/update")
    public String updateUser(@RequestParam("email") String email,   @RequestBody UpdateUserRequest updateUserRequest){
        service.updateUser(email, updateUserRequest);
        return "User Details Updated Successfully.";
    }

    @GetMapping(path = "/reset-password")
    public String sendResetPassword(@RequestParam("email") String email){
        service.sendResetPassword(email);
        return "Reset Password Link Send Successfully to Registered E-mail Id.";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public String deleteUser(@RequestParam("email") String email){
        service.deleteUser(email);
        return "User Deleted Successfully.";
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/user-role")
    public String userRole(){
        return "hello user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/admin-role")
    public String adminRole(){
        return "hello admin";
    }

    @GetMapping("/keycloak-user")
    public List<UserRepresentation> getUser(@RequestParam String email){
        boolean emailEqualToPrincipal = securityService.isEmailEqualToPrincipal(email);
        if(emailEqualToPrincipal){
            List<UserRepresentation> user;
            user = service.getUser(email);
            return user;
        }
        throw new CustomAuthenticationException("E-posta eşleşmedi!");
    }



}
