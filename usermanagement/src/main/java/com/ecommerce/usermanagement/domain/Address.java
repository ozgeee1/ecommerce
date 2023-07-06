package com.ecommerce.usermanagement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    @NotBlank
    private String addressHeader;

    @NotBlank
    private String city;

    @NotBlank
    private String street;

    @NotBlank
    private String addressLine1;

    private String addressLine2;

    private String zipcode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
