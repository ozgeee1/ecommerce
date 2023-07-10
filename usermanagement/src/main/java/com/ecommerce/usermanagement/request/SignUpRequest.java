package com.ecommerce.usermanagement.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRequest {

    private String email;
    private String password;
}
