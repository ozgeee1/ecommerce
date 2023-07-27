package com.ecommerce.usermanagement.request;

import lombok.Data;

@Data
public class AddressRequest {

    private String header;

    private String city;

    private String district;

    private String addressLine;

    private String zipcode;
}
