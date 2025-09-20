package br.com.cardappio.entity;

import br.com.cardappio.DTO.AddressDTO;
import com.cardappio.core.entity.EntityModel;
import br.com.cardappio.utils.Messages;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Address implements EntityModel<UUID> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @NotBlank(message = Messages.EMPTY_STREET)
    @Length(max = 255, message = Messages.SIZE_255)
    private String street;

    @NotBlank(message = Messages.EMPTY_ZIP_CODE)
    @Length(max = 8, message = Messages.SIZE_8)
    private String zipCode;

    @Column
    @NotBlank(message = Messages.EMPTY_DISTRICT)
    @Length(max = 255, message = Messages.SIZE_255)
    private String district;

    @Column
    @NotBlank(message = Messages.EMPTY_NUMBER)
    @Length(max = 10, message = Messages.SIZE_10)
    private String number;

    @Column(nullable = false)
    private Boolean active = Boolean.TRUE;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "city_id")
    private City city;

    public static Address of(final AddressDTO dto){

        final Address address = new Address();
        address.setId(dto.id());
        address.setStreet(dto.street());
        address.setZipCode(dto.zipCode());
        address.setDistrict(dto.district());
        address.setNumber(dto.number());
        address.setActive(dto.active());
        address.setCity(dto.city());

        return address;
    }
}