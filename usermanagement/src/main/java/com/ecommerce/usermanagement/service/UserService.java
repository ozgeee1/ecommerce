package com.ecommerce.usermanagement.service;

import com.ecommerce.usermanagement.domain.Address;
import com.ecommerce.usermanagement.domain.Authority;
import com.ecommerce.usermanagement.domain.User;
import com.ecommerce.usermanagement.exception.BadRequestException;
import com.ecommerce.usermanagement.mapper.UserMapper;
import com.ecommerce.usermanagement.repository.AuthorityRepository;
import com.ecommerce.usermanagement.repository.UserRepository;
import com.ecommerce.usermanagement.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;


    public void addUser(String email){
        User newUser = User.builder().email(email).build();
        User savedUser = userRepository.save(newUser);
        Authority authority = Authority.builder().role("USER").user(savedUser).build();
        Authority savedAuthority = authorityRepository.save(authority);

    }

    public User getUserByEmail(String email){
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()){
            return byEmail.get();
        }
        throw new BadRequestException("There is no user with this email "+email);
    }

    public User getUserById(Long id){
        Optional<User> byId = userRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        throw new BadRequestException("There is no user with this id "+id);
    }

    public User saveUserToDB(User user){
        return userRepository.save(user);
    }

    public void updateUser(String email, UpdateUserRequest updateUserRequest){
        User beforeUpdate = getUserByEmail(email);
        User afterUpdate = UserMapper.MAPPER.updateRequestToUser(updateUserRequest, beforeUpdate);
        userRepository.save(afterUpdate);

    }

    public void deleteUser(String email){
        User userByEmail = getUserByEmail(email);
        userRepository.delete(userByEmail);
    }


}
