package com.swayam.banking.configuration;

import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeycloakManager {
    private KeycloakProperties keycloakProperties;

    @Autowired
    public KeycloakManager(KeycloakProperties keycloakProperties){
        this.keycloakProperties = keycloakProperties;
    }

    public RealmResource getKeyCloakInstanceWithRealm() {
        return keycloakProperties.getInstance().realm(keycloakProperties.getRealm());
    }
}
