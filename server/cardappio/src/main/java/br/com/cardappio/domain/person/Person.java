package br.com.cardappio.domain.person;

import java.util.UUID;

import br.com.cardappio.converter.PersonTypeConverter;
import br.com.cardappio.domain.address.Address;
import br.com.cardappio.enums.PersonType;
import br.com.cardappio.utils.Messages;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = Messages.EMPTY_NAME)
    @Size(max = 255)
    @Column(nullable = false)
    private String name;

    @Convert(converter = PersonTypeConverter.class)
    private PersonType type;

    @Size(max = 14)
    @Column(unique = true, length = 14)
    private String document;

    @Size(max = 11)
    @Column(length = 11)
    private String phone;

    @NotBlank(message = Messages.EMPTY_PASSWORD)
    @Size(max = 255)
    @Column(nullable = false)
    private String password;

    @Email(message = Messages.INVALID_EMAIL)
    @Size(max = 255)
    @Column
    private String email;

    @Column(nullable = false)
    private Boolean active = true;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    public static Person of(final UUID id) {
        final Person person = new Person();
        person.setId(id);

        return person;
    }
}
