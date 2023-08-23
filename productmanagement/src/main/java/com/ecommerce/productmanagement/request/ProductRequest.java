package com.ecommerce.productmanagement.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    private BigDecimal price;

    private String description;

    private Long categoryId;

}
