package com.ecommerce.usermanagement.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public boolean isEmailEqualToPrincipal(String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String principalEmail = (String) authentication.getPrincipal();
        return email.equals(principalEmail);
    }
}
