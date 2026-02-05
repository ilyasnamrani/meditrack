import Keycloak from 'keycloak-js';

// Configuration Keycloak
const keycloakConfig = {
  url: 'http://localhost:8080',
  realm: 'meditrack-realm',
  clientId: 'meditrack-client',
};

const keycloak = new Keycloak(keycloakConfig);

/**
 * Initialise Keycloak et vérifie l'authentification au démarrage
 */
export const initKeycloak = (onAuthenticatedCallback: () => void) => {
  keycloak
    .init({
      onLoad: 'check-sso',
      silentCheckSsoRedirectUri: window.location.origin + '/silent-check-sso.html',
      pkceMethod: 'S256',
    })
    .then((authenticated) => {
      if (authenticated) {
        console.log("User is authenticated");
      } else {
        console.log("User is not authenticated");
      }
      onAuthenticatedCallback();
    })
    .catch((error) => {
      console.error("Keycloak initialization failed", error);
    });
};

export const doLogin = keycloak.login;
export const doLogout = keycloak.logout;
export const getToken = () => keycloak.token;
export const isLoggedIn = () => !!keycloak.token;
export const updateToken = (successCallback: () => void) =>
  keycloak.updateToken(5).then(successCallback).catch(doLogin);
export const getUsername = () => keycloak.tokenParsed?.preferred_username;
export const hasRole = (roles: string[]) => roles.some((role) => keycloak.hasResourceRole(role));

// Helper for extracting roles
export const getUserRoles = () => {
  if (keycloak.resourceAccess && keycloak.resourceAccess['meditrack-client']) {
    return keycloak.resourceAccess['meditrack-client'].roles || [];
  }
  return [];
};

export const isStaff = () => {
  const roles = getUserRoles();
  return roles.includes('DOCTOR') || roles.includes('NURSE') || roles.includes('ADMIN');
};

export const isPatient = () => {
  const roles = getUserRoles();
  return roles.includes('PATIENT');
};

export default keycloak;
