package com.example.DigitalMoneyHouseJR.usersservice.service;

import com.example.DigitalMoneyHouseJR.usersservice.entities.AccessKeycloak;
import com.example.DigitalMoneyHouseJR.usersservice.entities.Login;
import com.example.DigitalMoneyHouseJR.usersservice.entities.User;
import com.example.DigitalMoneyHouseJR.usersservice.config.KeycloakClientConfig;
import jakarta.ws.rs.BadRequestException;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.*;


@Service
public class KeycloakService {

    @Autowired
    private KeycloakClientConfig keycloakClientConfig;
    @Value("${dh.keycloak.realm}")
    private String realm;
    @Value("${dh.keycloak.serverUrl}")
    private String serverUrl;
    @Value("${dh.keycloak.clientId}")
    private String clientId;
    @Value("${dh.keycloak.clientSecret}")
    private String clientSecret;
    @Value("${dh.keycloak.tokenEndpoint}")
    private String tokenEndpoint;

    private static final String UPDATE_PASSWORD = "UPDATE_PASSWORD";

    public RealmResource getRealm() {
        return keycloakClientConfig.getInstance().realm(realm);
    }

    public User createUser(User userKeycloak) throws Exception {
        // Buscar si el usuario ya existe en Keycloak por email
        List<UserRepresentation> existingUsers = getRealm().users().searchByEmail(userKeycloak.getEmail(), true);

        if (!existingUsers.isEmpty()) {
            throw new Exception("(!) User already exists");
        }

        // Si no existe, proceder a crearlo
        UserRepresentation userRepresentation = new UserRepresentation();
        Map<String, List<String>> attributes = new HashMap<>();

        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(userKeycloak.getUsername());
        userRepresentation.setEmail(userKeycloak.getEmail());
        userRepresentation.setFirstName(userKeycloak.getName());
        userRepresentation.setLastName(userKeycloak.getLastName());
        userRepresentation.setEmailVerified(true);
        attributes.put("phoneNumber", Collections.singletonList(String.valueOf(userKeycloak.getPhoneNumber())));
        userRepresentation.setAttributes(attributes);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(userKeycloak.getPassword());
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));

        Response response = getRealm().users().create(userRepresentation);

        if (response.getStatus() >= 400) {
            System.out.println("Error Keycloak: " + response.readEntity(String.class));
            throw new BadRequestException("(!) Something happened, try again later");
        }

        userRepresentation.setId(CreatedResponseUtil.getCreatedId(response));

        return User.toUser(userRepresentation);

    }

    public AccessKeycloak login(Login login) throws Exception {
        try{

            AccessKeycloak tokenAccess = null;
            Keycloak keycloakClient = null;
            TokenManager tokenManager = null;

            keycloakClient = Keycloak.getInstance(serverUrl,realm,login.getEmail(), login.getPassword(), clientId, clientSecret);

            tokenManager = keycloakClient.tokenManager();

            tokenAccess = AccessKeycloak.builder()
                    .accessToken(tokenManager.getAccessTokenString())
                    .expiresIn(tokenManager.getAccessToken().getExpiresIn())
                    .refreshToken(tokenManager.refreshToken().getRefreshToken())
                    .scope(tokenManager.getAccessToken().getScope())
                    .build();

            return tokenAccess;

        }  catch (Exception e) {
            throw new AuthenticationException("Invalid Credentials");
        }
    }

    public void logout(String userId) {
        getRealm().users().get(userId).logout();
    }

    public void forgotPassword(String username) {
        UsersResource usersResource = getRealm().users();
        List<UserRepresentation> representationList = usersResource.searchByUsername(username, true);
        UserRepresentation userRepresentation = representationList.stream().findFirst().orElse(null);
        if(userRepresentation!=null) {
            UserResource userResource = usersResource.get(userRepresentation.getId());
            List<String> actions = new ArrayList<>();
            actions.add(UPDATE_PASSWORD);

            userResource.executeActionsEmail(actions);
            return;
        }
        throw new RuntimeException("User not found");
    }

    public void updateUser(User oldUserData, User newUserData) {
        UserResource userResource = getRealm().users().get(oldUserData.getKeycloakId());

        UserRepresentation updatedUserRepresentation = updateUserRepresentation(userResource.toRepresentation(), newUserData);

        getRealm().users().get(oldUserData.getKeycloakId()).update(updatedUserRepresentation);
    }

    private UserRepresentation updateUserRepresentation (UserRepresentation userRepresentation, User user) {

        if (user.getName() != null && !user.getName().equals(userRepresentation.getFirstName())) {
            userRepresentation.setFirstName(user.getName());
        }

        if (user.getLastName() != null && !Objects.equals(userRepresentation.getLastName(), user.getLastName())) {
            userRepresentation.setLastName(user.getLastName());
        }

        if (user.getUsername() != null && !Objects.equals(userRepresentation.getUsername(), user.getUsername())) {
            userRepresentation.setUsername(user.getUsername());
            System.out.println("hola");
        }

        if (user.getEmail() != null && !Objects.equals(userRepresentation.getEmail(), user.getEmail())) {
            userRepresentation.setEmail(user.getEmail());
        }

        if (user.getPassword() != null) {
            userRepresentation.setCredentials(Collections.singletonList(newCredential(user.getPassword())));
        }

        if (user.getPhoneNumber() != null && !userRepresentation.getAttributes().get("phoneNumber").equals(user.getPhoneNumber())){
            userRepresentation.getAttributes().put("phoneNumber", Collections.singletonList(user.getPhoneNumber()));
        }

        return userRepresentation;
    }

    private CredentialRepresentation newCredential(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType("password");
        credential.setValue(password);
        return credential;
    }

}