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