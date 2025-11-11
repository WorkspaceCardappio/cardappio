package br.com.cardappio.payment.dto;

public record AbacatePixResponseDTO(
        String pixId,
        String status,
        String brCode,
        String brCodeBase64
) {
}