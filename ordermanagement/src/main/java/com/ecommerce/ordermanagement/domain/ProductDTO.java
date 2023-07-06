package com.ecommerce.ordermanagement.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private BigDecimal price;

    private String brand;

    private String description;

    private CategoryDto category;

}
