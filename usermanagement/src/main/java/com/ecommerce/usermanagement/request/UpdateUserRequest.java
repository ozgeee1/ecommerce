package com.ecommerce.usermanagement.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {


    private String firstName;

    private String lastName;

    private String phoneNumber;

    private LocalDate birthDate;

}
