package br.com.cardappio.domain;

import com.cardappio.core.entity.EntityModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "archive")
public class Archive implements EntityModel<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @Column(name = "mime_type", length = 200, nullable = false)
    private String mimeType;

    @Column(name = "full_path", length = 1200, nullable = false)
    private String fullPath;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;

    @Column(name = "archive_length", nullable = false)
    private Long archiveLength;
}
