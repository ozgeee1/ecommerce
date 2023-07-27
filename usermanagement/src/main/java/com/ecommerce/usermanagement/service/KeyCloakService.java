package com.ecommerce.usermanagement.service;

import com.ecommerce.usermanagement.config.KeycloakProvider;
import com.ecommerce.usermanagement.domain.Credentials;
import com.ecommerce.usermanagement.error.ApiError;
import com.ecommerce.usermanagement.request.SignUpUserRequest;
import com.ecommerce.usermanagement.request.UpdateUserRequest;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyCloakService {

    @Value("${keycloak.realm}")
    public String realm;

    private final KeycloakProvider kcProvider;

    private final UserService userService;

    @Value("${keycloak.resource}")
    public String clientID;

    public ResponseEntity<?> addUser(SignUpUserRequest signUpUserRequest){
        CredentialRepresentation credential = Credentials
                .createPasswordCredentials(signUpUserRequest.getPassword());
        UserRepresentation user = new UserRepresentation();
        user.setEmail(signUpUserRequest.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        user.setEnabled(true);

        UsersResource instance = getInstance();
        Response response = instance.create(user);


        if(response.getStatus()!=201){
            ApiError apiError = ApiError.builder().statusCode(response.getStatus()).message(response.getStatusInfo().getReasonPhrase())
                    .build();
            return ResponseEntity.ok(apiError);
        }

        String userUrl = response.getLocation().toString();
        String userId = userUrl.substring(userUrl.lastIndexOf("/") + 1);
        addRealmRoleToUser("USER",userId);

        userService.addUser(signUpUserRequest.getEmail());

        sendVerificationLink(userId);

        return ResponseEntity.ok("To complete sign up process please verify your email!");
    }

    public void addRealmRoleToUser(String roleName,String userId){
        UserResource user = kcProvider.getInstance()
                .realm(realm)
                .users()
                .get(userId);
        List<RoleRepresentation> roleToAdd = new LinkedList<>();

        String clientId = kcProvider.getInstance().realm(realm).clients().findByClientId(clientID).get(0).getId();

        roleToAdd.add( kcProvider.getInstance()
                .realm(realm)
                .clients()
                .get(clientId)
                .roles()
                .get("USER")
                .toRepresentation()
                
        );

        user.roles().clientLevel(clientId).add(roleToAdd);

    }

    /**
     *
     * @param userName
     * @return
     */
    public List<UserRepresentation> getUser(String userName){
        UsersResource usersResource = getInstance();
        List<UserRepresentation> user = usersResource.search(userName, true);
        return user;

    }

    public void updateUser(String email, UpdateUserRequest updateUserRequest){

        UsersResource usersResource = getInstance();

        List<UserRepresentation> users = usersResource.search(email);
        if (users.isEmpty()) {
            throw new NotFoundException("User not found: " + email);
        }

        UserRepresentation user = users.get(0);
        if (updateUserRequest.getFirstName() != null) {
            user.setFirstName(updateUserRequest.getFirstName());
        }
        if (updateUserRequest.getLastName() != null) {
            user.setLastName(updateUserRequest.getLastName());
        }

        usersResource.get(user.getId()).update(user);


        userService.updateUser(email, updateUserRequest);

    }
    public void deleteUser(String email){
        UsersResource usersResource = getInstance();

        List<UserRepresentation> users = usersResource.search(email);
        if (users.isEmpty()) {
            throw new NotFoundException("User not found: " + email);
        }
        UserRepresentation user = users.get(0);
        String userId = user.getId();

        usersResource.get(userId)
                .remove();

        userService.deleteUser(email);
    }


    public void sendVerificationLink(String userId){
        UsersResource usersResource = getInstance();
        usersResource.get(userId)
                .sendVerifyEmail();
    }

    public void sendResetPassword(String email){
        UsersResource usersResource = getInstance();
        List<UserRepresentation> users = usersResource.search(email);
        if (users.isEmpty()) {
            throw new NotFoundException("User not found: " + email);
        }

        UserRepresentation user = users.get(0);
        String userId = user.getId();

        usersResource.get(userId)
                .executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    public UsersResource getInstance(){
        return kcProvider.getInstance().realm(realm).users();
    }
}
