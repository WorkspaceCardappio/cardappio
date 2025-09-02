package br.com.cardappio.entity;

import java.math.BigDecimal;
import java.util.UUID;


import com.cardappio.core.entity.EntityModel;

import br.com.cardappio.utils.Messages;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class MenuProduct implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @NotNull(message = Messages.EMPTY_MENU)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @NotNull(message = "O produto é obrigatório")
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    @NotNull(message = Messages.EMPTY_PRICE)
    private BigDecimal price;
}
