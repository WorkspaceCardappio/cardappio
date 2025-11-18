package br.com.cardappio.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRequest {

    @Email(message = "Email deve ser válido")
    private String email;

    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String firstName;

    @Size(min = 2, max = 50, message = "Sobrenome deve ter entre 2 e 50 caracteres")
    private String lastName;

    private List<String> roles;

    private Boolean enabled;
}
