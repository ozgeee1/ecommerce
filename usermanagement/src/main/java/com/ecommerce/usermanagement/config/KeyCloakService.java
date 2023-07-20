package com.ecommerce.usermanagement.config;

import com.ecommerce.usermanagement.domain.Credentials;
import com.ecommerce.usermanagement.domain.User;
import com.ecommerce.usermanagement.dto.UserDTO;
import com.ecommerce.usermanagement.error.ApiError;
import com.ecommerce.usermanagement.repository.UserRepository;
import com.ecommerce.usermanagement.request.SignUpRequest;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;



@Service
@RequiredArgsConstructor
public class KeyCloakService {

    @Value("${keycloak.realm}")
    public String realm;

    private final KeycloakProvider kcProvider;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> addUser(SignUpRequest signUpRequest){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(signUpRequest.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setEmail(signUpRequest.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        UsersResource instance = getInstance();
        Response response = instance.create(user);
        if(response.getStatus()!=201){
            ApiError apiError = ApiError.builder().statusCode(response.getStatus()).message(response.getStatusInfo().getReasonPhrase())
                    .build();
            return ResponseEntity.ok(apiError);
        }
        User newUser = User.builder().email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword())).build();
        String userUrl = response.getLocation().toString();
        String userId = userUrl.substring(userUrl.lastIndexOf("/") + 1);
        sendVerificationLink(userId);
        userRepository.save(newUser);
        return ResponseEntity.ok("To complete sign up process please verify your email!");
    }


    public List<UserRepresentation> getUser(String userName){
        UsersResource usersResource = getInstance();
        List<UserRepresentation> user = usersResource.search(userName, true);
        return user;

    }

    public void updateUser(String userId, UserDTO userDTO){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(userDTO.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setUsername(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmailId());
        user.setCredentials(Collections.singletonList(credential));

        UsersResource usersResource = getInstance();
        usersResource.get(userId).update(user);
    }
    public void deleteUser(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .remove();
    }


    public void sendVerificationLink(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .sendVerifyEmail();
    }

    public void sendResetPassword(String userId){
        UsersResource usersResource = getInstance();

        usersResource.get(userId)
                .executeActionsEmail(Arrays.asList("UPDATE_PASSWORD"));
    }

    public UsersResource getInstance(){
        return kcProvider.getInstance().realm(realm).users();
    }
}
