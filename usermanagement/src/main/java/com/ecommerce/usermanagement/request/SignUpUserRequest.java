package com.ecommerce.usermanagement.request;

import lombok.Data;

@Data
public class SignUpUserRequest {

    private String email;
    private String password;
}
