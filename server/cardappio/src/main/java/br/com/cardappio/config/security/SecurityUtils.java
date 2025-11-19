package br.com.cardappio.config.security;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Jwt getJwt() {
        Authentication authentication = getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            return (Jwt) authentication.getPrincipal();
        }
        return null;
    }

    public static String getUsername() {
        Jwt jwt = getJwt();
        if (jwt != null) {
            return jwt.getClaimAsString("preferred_username");
        }
        return null;
    }

    public static String getUserId() {
        Jwt jwt = getJwt();
        if (jwt != null) {
            return jwt.getSubject();
        }
        return null;
    }

    public static String getEmail() {
        Jwt jwt = getJwt();
        if (jwt != null) {
            return jwt.getClaimAsString("email");
        }
        return null;
    }

    public static String getFullName() {
        Jwt jwt = getJwt();
        if (jwt != null) {
            return jwt.getClaimAsString("name");
        }
        return null;
    }

    /**
     * Retorna o melhor identificador disponível para o usuário/service account autenticado.
     * Tenta na ordem: name (nome completo), preferred_username, azp (client_id), subject.
     */
    public static String getUserIdentifier() {
        Jwt jwt = getJwt();
        if (jwt != null) {
            // Tenta nome completo primeiro (usuários normais)
            String name = jwt.getClaimAsString("name");
            if (name != null && !name.trim().isEmpty()) {
                return name;
            }

            // Tenta preferred_username
            String username = jwt.getClaimAsString("preferred_username");
            if (username != null && !username.trim().isEmpty()) {
                return username;
            }

            // Tenta azp (authorized party / client_id) para service accounts
            String clientId = jwt.getClaimAsString("azp");
            if (clientId != null && !clientId.trim().isEmpty()) {
                return clientId;
            }

            // Fallback final: subject
            String subject = jwt.getSubject();
            if (subject != null && !subject.trim().isEmpty()) {
                return subject;
            }
        }
        return null;
    }

    public static Collection<String> getRoles() {
        Authentication authentication = getAuthentication();
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .map(role -> role.replace("ROLE_", ""))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public static boolean hasRole(String role) {
        return getRoles().contains(role.toUpperCase());
    }

    public static boolean isAdmin() {
        return hasRole("ADMIN");
    }

    public static boolean isUser() {
        return hasRole("USER");
    }

    public static boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}