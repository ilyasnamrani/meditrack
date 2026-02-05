package com.meditrack.auth.service;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public String createUser(String username, String email, String password, String firstName, String lastName,
            String role) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(email); // Use email as username
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmailVerified(true);
        // user.setGroups(Collections.singletonList(role)); // Groups/Roles strategy
        // depends on your Keycloak setup

        UsersResource usersResource = keycloak.realm(realm).users();
        Response response = usersResource.create(user);

        if (response.getStatus() == 201) {
            String userId = CreatedResponseUtil.getCreatedId(response);

            // Set Password
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(password);

            usersResource.get(userId).resetPassword(passwordCred);

            return userId;
        } else {
            throw new RuntimeException("Failed to create user in Keycloak. Status: " + response.getStatus());
        }
    }

    public void updateUser(String keycloakId, String email, String firstName, String lastName, String password) {
        UserRepresentation user = new UserRepresentation();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        // user.setUsername(email); // Typically username is immutable or needs special
        // handling

        UsersResource usersResource = keycloak.realm(realm).users();
        usersResource.get(keycloakId).update(user);

        if (password != null && !password.isEmpty()) {
            CredentialRepresentation passwordCred = new CredentialRepresentation();
            passwordCred.setTemporary(false);
            passwordCred.setType(CredentialRepresentation.PASSWORD);
            passwordCred.setValue(password);
            usersResource.get(keycloakId).resetPassword(passwordCred);
        }
    }

    public void deleteUser(String keycloakId) {
        UsersResource usersResource = keycloak.realm(realm).users();
        Response response = usersResource.delete(keycloakId);
        if (response.getStatus() != 204 && response.getStatus() != 200) {
            throw new RuntimeException("Failed to delete user from Keycloak. Status: " + response.getStatus());
        }
    }

    // Helper to parse ID from Location header (simplification)
    private static class CreatedResponseUtil {
        public static String getCreatedId(Response response) {
            java.net.URI location = response.getLocation();
            if (location == null) {
                return null;
            }
            String path = location.getPath();
            return path.substring(path.lastIndexOf('/') + 1);
        }
    }
}
