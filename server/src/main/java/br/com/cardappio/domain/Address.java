package br.com.cardappio.domain;

import br.com.cardappio.enums.UnidadeFederativa;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Getter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String street;

    @NotNull
    private String number;

    @NotNull
    private String zipCode;

    @NotNull
    private String district;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidade_federativa", length = 2)
    @NotNull
    private UnidadeFederativa unidadeFederativa;
}
