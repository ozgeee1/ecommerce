package com.ecommerce.productmanagement.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "inventory")
public class Inventory extends BaseEntity{

    @NotNull
    private int productQuantity;

    @NotNull
    private Long productId;

}
