package com.ecommerce.productmanagement.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "category")
public class Category extends BaseEntity{


    @NotBlank
    private String header;

    @NotBlank
    private String category;

    @NotBlank
    private String subCategory;

//    @OneToOne(mappedBy = "category")
//    private List<Product> products;
}
