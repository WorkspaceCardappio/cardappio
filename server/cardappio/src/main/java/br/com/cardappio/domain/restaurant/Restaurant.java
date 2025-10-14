package br.com.cardappio.domain.restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.cardappio.domain.address.Address;
import br.com.cardappio.domain.table.TableRestaurant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
@ToString
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = Messages.EMPTY_NAME)
    @Size(max = 255, message = Messages.SIZE_255)
    @Column(name = "name", nullable = false, length = 255, unique = true)
    private String name;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @NotBlank(message = Messages.EMPTY_DOCUMENT)
    @Size(max = 14, message = Messages.SIZE_14)
    @Column(name = "cnpj", unique = true, nullable = false, length = 14)
    private String cnpj;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @JsonIgnoreProperties("restaurant")
    @OneToMany(mappedBy = "restaurant", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<TableRestaurant> tables = new ArrayList<>();

    public static Restaurant of(final UUID id) {
        final Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        return restaurant;
    }
}
