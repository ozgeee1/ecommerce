package com.ecommerce.usermanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User extends BaseEntity {


    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private String phoneNumber;

    private LocalDate birthDate;

    private Long selectedAddressId;

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
