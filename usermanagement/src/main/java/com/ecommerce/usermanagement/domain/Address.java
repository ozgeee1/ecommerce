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

import java.util.Objects;

@Data
@Entity
@Table(name = "address")
public class Address extends BaseEntity {

    @NotBlank
    private String header;

    @NotBlank
    private String city;

    @NotBlank
    private String district;

    @NotBlank
    private String addressLine;

    private String zipcode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Address address = (Address) o;
        return Objects.equals(header, address.header) && Objects.equals(city, address.city) && Objects.equals(district, address.district)
                && Objects.equals(addressLine, address.addressLine) && Objects.equals(zipcode, address.zipcode);
    }

}
