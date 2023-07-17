package com.ecommerce.usermanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    private String firstName;

    private String lastName;


    @NotBlank(message = "Password can not be blank")
    @Size(min = 6,message = "Password must be at least 6 digits")
    private String password;

    @NotBlank(message = "Email can not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    private String phoneNumber;

    private LocalDate birthDate;

    private Long selectedAddressId;

    private boolean isEmailVerified;

    private boolean isPhoneNumberVerified;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private Set<Address> addressSet = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy="user",fetch= FetchType.EAGER)
    private Set<Authority> authorities;


    public String getFullName(){
        return getFirstName()+" "+getLastName();
    }
}
