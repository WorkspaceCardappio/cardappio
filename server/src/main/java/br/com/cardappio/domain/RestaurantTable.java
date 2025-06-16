package br.com.cardappio.domain;

import br.com.cardappio.enums.TableStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "restaurant_table")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@Getter
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    private String number;

    @Enumerated(EnumType.ORDINAL)
    @NotNull
    private TableStatus status;

}
