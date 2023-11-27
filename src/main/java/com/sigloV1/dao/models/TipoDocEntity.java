package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tipo_doc")
public class TipoDocEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 2,max = 100)
    @Column(unique = true,nullable = false)
    private String name;

    @OneToMany(targetEntity = DocDetallesEntity.class,mappedBy = "tipoDoc",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @Column(insertable = false,updatable = false)
    private List<DocDetallesEntity> docDetalles;
}
