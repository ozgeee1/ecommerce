package com.ecommerce.usermanagement.config;

import com.ecommerce.usermanagement.domain.Authority;
import com.ecommerce.usermanagement.domain.User;
import com.ecommerce.usermanagement.exception.BadRequestException;
import com.ecommerce.usermanagement.repository.AuthorityRepository;
import com.ecommerce.usermanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EcommerceUserDetails implements UserDetailsService {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String email, password = null;
        List<SimpleGrantedAuthority> authorities = null;
        Optional<User> optionalUser = userRepository.findByEmail(username);

        if (optionalUser.isEmpty() ){
            throw new UsernameNotFoundException("User details not found for the user : " + username);
        }
        User user = optionalUser.get();
        if(!user.isEmailVerified()){
            throw new BadRequestException("User needs to verify his/her email first!");
        }
        List<Authority> authorityList = authorityRepository.findAuthoritiesByUserId(user.getId());
        email = user.getEmail();
        password = user.getPassword();
        authorities =getGrantedAuthorities(authorityList);

        return new org.springframework.security.core.userdetails.User(email, password, authorities) {
        };
    }

    private List<SimpleGrantedAuthority> getGrantedAuthorities ( List<Authority> authorities){
       List<SimpleGrantedAuthority> authoritySet = new ArrayList<>();
        for (Authority authority : authorities){
            authoritySet.add(new SimpleGrantedAuthority(authority.getRole()));
        }
        return authoritySet;
    }
}
