package br.com.cardappio.entity;

import java.util.UUID;

import br.com.cardappio.converter.PersonTypeConverter;
import br.com.cardappio.enums.PersonType;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = Messages.EMPTY_STREET)
    @Size(max = 255)
    @Column(nullable = false)
    private String street;

    @NotBlank(message = Messages.EMPTY_ZIP_CODE)
    @Size(max = 8)
    @Column(nullable = false)
    private String zipCode;

    @NotBlank(message = Messages.EMPTY_DISTRICT)
    @Size(max = 255)
    @Column(nullable = false)
    private String district;

    @NotBlank(message = Messages.EMPTY_NUMBER)
    @Size(max = 10)
    @Column(nullable = false)
    private String number;

    @Column(nullable = false)
    private Boolean active = true;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false, insertable = false, updatable = false)
    private City city;
}
