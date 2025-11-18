package br.com.cardappio.employee.controller;

import br.com.cardappio.config.security.IsAdmin;
import br.com.cardappio.employee.dto.CreateEmployeeRequest;
import br.com.cardappio.employee.dto.EmployeeDTO;
import br.com.cardappio.employee.dto.UpdateEmployeeRequest;
import br.com.cardappio.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    @IsAdmin
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        log.info("GET /api/employees - Listando todos os funcionários");
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    @IsAdmin
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String id) {
        log.info("GET /api/employees/{} - Buscando funcionário por ID", id);
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PostMapping
    @IsAdmin
    public ResponseEntity<String> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        log.info("POST /api/employees - Criando novo funcionário: {}", request.getUsername());
        String employeeId = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeId);
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> updateEmployee(@PathVariable String id, @Valid @RequestBody UpdateEmployeeRequest request) {
        log.info("PUT /api/employees/{} - Atualizando funcionário", id);
        employeeService.updateEmployee(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        log.info("DELETE /api/employees/{} - Deletando funcionário", id);
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
