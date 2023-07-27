package com.ecommerce.productmanagement.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product extends BaseEntity{


    @NotBlank
    private BigDecimal price;

    @NotBlank
    private String brand;

    @NotBlank
    private String description;

    @OneToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    private Category category;

    //TODO burayı ımplement et(gerek var mı nasıl olmalı?)
    //private DiscountDto discount;
}
