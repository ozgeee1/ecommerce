package com.ecommerce.usermanagement.service;

import jakarta.persistence.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class VerificationCodeGenerator {


    public static String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();
    }
}
