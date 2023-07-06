package com.ecommerce.ordermanagement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@Document
public class Order {

    @Id
    private String id;

    private BigDecimal totalPrice;

    @Column(name = "ordered_day_time")
    private LocalDateTime orderedDayAndTime;

    @Column(name = "delivery_address")
    private String delivery_address;

    private Long userId;

    private List<ProductSummary> productIds;

}
