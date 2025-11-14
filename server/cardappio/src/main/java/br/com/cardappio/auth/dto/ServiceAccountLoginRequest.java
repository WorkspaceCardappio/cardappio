package br.com.cardappio.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAccountLoginRequest {

    @NotBlank(message = "Client ID é obrigatório")
    private String clientId;

    @NotBlank(message = "Client Secret é obrigatório")
    private String clientSecret;
}