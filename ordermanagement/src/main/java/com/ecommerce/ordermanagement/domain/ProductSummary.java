package com.ecommerce.ordermanagement.domain;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProductSummary {

    private List<ProductDTO> products;

    private LocalDate deliveredDay;

    private OrderStatus status;
}
