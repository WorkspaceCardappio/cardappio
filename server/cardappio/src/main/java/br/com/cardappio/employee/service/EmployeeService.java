package br.com.cardappio.employee.service;

import br.com.cardappio.employee.dto.CreateEmployeeRequest;
import br.com.cardappio.employee.dto.EmployeeDTO;
import br.com.cardappio.employee.dto.UpdateEmployeeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final KeycloakAdminService keycloakAdminService;

    public List<EmployeeDTO> getAllEmployees() {
        log.info("Buscando todos os funcionários");
        return keycloakAdminService.getAllEmployees();
    }

    public EmployeeDTO getEmployeeById(String id) {
        log.info("Buscando funcionário com ID: {}", id);
        return keycloakAdminService.getEmployeeById(id);
    }

    public String createEmployee(CreateEmployeeRequest request) {
        log.info("Criando novo funcionário: {}", request.getUsername());
        return keycloakAdminService.createEmployee(request);
    }

    public void updateEmployee(String id, UpdateEmployeeRequest request) {
        log.info("Atualizando funcionário com ID: {}", id);
        keycloakAdminService.updateEmployee(id, request);
    }

    public void deleteEmployee(String id) {
        log.info("Deletando funcionário com ID: {}", id);
        keycloakAdminService.deleteEmployee(id);
    }
}
