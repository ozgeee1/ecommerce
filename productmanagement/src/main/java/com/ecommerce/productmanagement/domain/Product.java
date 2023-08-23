package com.ecommerce.productmanagement.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "products")
public class Product extends BaseEntity{


    @NotNull
    private BigDecimal price;

    @NotBlank
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;

    //TODO burayı ımplement et(gerek var mı nasıl olmalı?)
    //private DiscountDto discount;
}
