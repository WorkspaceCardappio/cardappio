package br.com.cardappio.employee.service;

import br.com.cardappio.config.KeycloakAdminConfig;
import br.com.cardappio.employee.dto.CreateEmployeeRequest;
import br.com.cardappio.employee.dto.EmployeeDTO;
import br.com.cardappio.employee.dto.UpdateEmployeeRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakAdminService {

    private final KeycloakAdminConfig keycloakConfig;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String getUserToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getTokenValue();
        }
        throw new RuntimeException("Usuário não autenticado");
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getUserToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private String getAdminUrl() {
        return keycloakConfig.getServerUrl() + "/admin/realms/" + keycloakConfig.getRealm();
    }

    public List<EmployeeDTO> getAllEmployees() {
        try {
            String url = getAdminUrl() + "/users";
            HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            List<UserRepresentation> users = objectMapper.readValue(
                    response.getBody(),
                    new TypeReference<List<UserRepresentation>>() {}
            );

            return users.stream()
                    .map(this::mapToEmployeeDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Erro ao buscar funcionários do Keycloak", e);
            throw new RuntimeException("Erro ao buscar funcionários", e);
        }
    }

    public EmployeeDTO getEmployeeById(String userId) {
        try {
            String url = getAdminUrl() + "/users/" + userId;
            HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            UserRepresentation user = objectMapper.readValue(
                    response.getBody(),
                    UserRepresentation.class
            );

            return mapToEmployeeDTO(user);
        } catch (Exception e) {
            log.error("Erro ao buscar funcionário com ID: {}", userId, e);
            throw new RuntimeException("Funcionário não encontrado", e);
        }
    }

    public String createEmployee(CreateEmployeeRequest request) {
        try {
            String url = getAdminUrl() + "/users";

            UserRepresentation user = new UserRepresentation();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setEnabled(request.getEnabled() != null ? request.getEnabled() : true);
            user.setEmailVerified(request.getEmailVerified() != null ? request.getEmailVerified() : false);

            HttpEntity<UserRepresentation> entity = new HttpEntity<>(user, createHeaders());

            ResponseEntity<Void> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    Void.class
            );

            if (response.getStatusCode() == HttpStatus.CREATED) {
                String location = response.getHeaders().getLocation().toString();
                String userId = location.replaceAll(".*/([^/]+)$", "$1");

                setUserPassword(userId, request.getPassword());

                if (request.getRoles() != null && !request.getRoles().isEmpty()) {
                    assignRolesToUser(userId, request.getRoles());
                }

                log.info("Funcionário criado com sucesso. ID: {}", userId);
                return userId;
            } else {
                throw new RuntimeException("Erro ao criar funcionário. Status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Erro ao criar funcionário no Keycloak", e);
            throw new RuntimeException("Erro ao criar funcionário", e);
        }
    }

    public void updateEmployee(String userId, UpdateEmployeeRequest request) {
        try {
            UserRepresentation user = getUserRepresentation(userId);

            if (request.getEmail() != null) {
                user.setEmail(request.getEmail());
            }
            if (request.getFirstName() != null) {
                user.setFirstName(request.getFirstName());
            }
            if (request.getLastName() != null) {
                user.setLastName(request.getLastName());
            }
            if (request.getEnabled() != null) {
                user.setEnabled(request.getEnabled());
            }

            String url = getAdminUrl() + "/users/" + userId;
            HttpEntity<UserRepresentation> entity = new HttpEntity<>(user, createHeaders());

            restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    entity,
                    Void.class
            );

            if (request.getRoles() != null) {
                List<RoleRepresentation> currentRoles = getUserRealmRoles(userId);
                if (!currentRoles.isEmpty()) {
                    removeRolesFromUser(userId, currentRoles);
                }

                assignRolesToUser(userId, request.getRoles());
            }

            log.info("Funcionário atualizado com sucesso. ID: {}", userId);
        } catch (Exception e) {
            log.error("Erro ao atualizar funcionário com ID: {}", userId, e);
            throw new RuntimeException("Erro ao atualizar funcionário", e);
        }
    }

    public void deleteEmployee(String userId) {
        try {
            String url = getAdminUrl() + "/users/" + userId;
            HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

            restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );

            log.info("Funcionário deletado com sucesso. ID: {}", userId);
        } catch (Exception e) {
            log.error("Erro ao deletar funcionário com ID: {}", userId, e);
            throw new RuntimeException("Erro ao deletar funcionário", e);
        }
    }

    private UserRepresentation getUserRepresentation(String userId) {
        try {
            String url = getAdminUrl() + "/users/" + userId;
            HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return objectMapper.readValue(response.getBody(), UserRepresentation.class);
        } catch (Exception e) {
            log.error("Erro ao buscar representação do usuário. ID: {}", userId, e);
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
    }

    private void setUserPassword(String userId, String password) {
        try {
            String url = getAdminUrl() + "/users/" + userId + "/reset-password";

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(password);
            credential.setTemporary(false);

            HttpEntity<CredentialRepresentation> entity = new HttpEntity<>(credential, createHeaders());

            restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    entity,
                    Void.class
            );
        } catch (Exception e) {
            log.error("Erro ao definir senha do usuário. ID: {}", userId, e);
            throw new RuntimeException("Erro ao definir senha", e);
        }
    }

    private void assignRolesToUser(String userId, List<String> roleNames) {
        try {
            List<RoleRepresentation> roles = roleNames.stream()
                    .map(this::getRoleByName)
                    .collect(Collectors.toList());

            String url = getAdminUrl() + "/users/" + userId + "/role-mappings/realm";
            HttpEntity<List<RoleRepresentation>> entity = new HttpEntity<>(roles, createHeaders());

            restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    Void.class
            );
        } catch (Exception e) {
            log.error("Erro ao atribuir roles ao usuário. ID: {}", userId, e);
            throw new RuntimeException("Erro ao atribuir roles", e);
        }
    }

    private void removeRolesFromUser(String userId, List<RoleRepresentation> roles) {
        try {
            String url = getAdminUrl() + "/users/" + userId + "/role-mappings/realm";
            HttpHeaders headers = createHeaders();

            HttpEntity<List<RoleRepresentation>> entity = new HttpEntity<>(roles, headers);

            restTemplate.exchange(
                    url,
                    HttpMethod.DELETE,
                    entity,
                    Void.class
            );
        } catch (Exception e) {
            log.error("Erro ao remover roles do usuário. ID: {}", userId, e);
        }
    }

    private RoleRepresentation getRoleByName(String roleName) {
        try {
            String url = getAdminUrl() + "/roles/" + roleName;
            HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return objectMapper.readValue(response.getBody(), RoleRepresentation.class);
        } catch (Exception e) {
            log.error("Erro ao buscar role: {}", roleName, e);
            throw new RuntimeException("Role não encontrada: " + roleName, e);
        }
    }

    private List<RoleRepresentation> getUserRealmRoles(String userId) {
        try {
            String url = getAdminUrl() + "/users/" + userId + "/role-mappings/realm/composite";
            HttpEntity<Void> entity = new HttpEntity<>(createHeaders());

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            return objectMapper.readValue(
                    response.getBody(),
                    new TypeReference<List<RoleRepresentation>>() {}
            );
        } catch (Exception e) {
            log.error("Erro ao buscar roles do usuário. ID: {}", userId, e);
            return Collections.emptyList();
        }
    }

    private EmployeeDTO mapToEmployeeDTO(UserRepresentation user) {
        List<String> roles = getUserRoles(user.getId());

        return EmployeeDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .enabled(user.isEnabled())
                .emailVerified(user.isEmailVerified())
                .roles(roles)
                .createdTimestamp(user.getCreatedTimestamp())
                .build();
    }

    private List<String> getUserRoles(String userId) {
        try {
            List<RoleRepresentation> roleRepresentations = getUserRealmRoles(userId);
            return roleRepresentations.stream()
                    .map(RoleRepresentation::getName)
                    .filter(roleName -> !roleName.startsWith("default-")
                            && !roleName.equals("offline_access")
                            && !roleName.equals("uma_authorization"))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Erro ao buscar roles do usuário. ID: {}", userId, e);
            return new ArrayList<>();
        }
    }
}
