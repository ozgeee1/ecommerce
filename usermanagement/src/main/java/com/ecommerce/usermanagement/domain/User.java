package com.ecommerce.usermanagement.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity {


    @NotBlank(message = "First name can not be blank")
    private String firstName;


    @NotBlank(message = "Last name can not be blank")
    private String lastName;


    @NotBlank(message = "Password can not be blank")
    @Size(min = 6,message = "Password must be at least 6 digits")
    private String password;

    @NotBlank(message = "Email can not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message="Phone number must not be blank")
    private String phoneNumber;

    private LocalDate birthDate;

    private Long selectedAddressId;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private Set<Address> addressSet = new HashSet<>();


    public String getFullName(){
        return getFirstName()+" "+getLastName();
    }
}
