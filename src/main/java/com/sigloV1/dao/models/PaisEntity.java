package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pais")
public class PaisEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 100)
    @Column(name = "nombre_pais",unique = true,nullable = false)
    private String nombrePais;

    @NotNull
    @NotBlank
    @Size(max = 4)
    @Column(nullable = false)
    private String indicativo;

    private Boolean estado;

    @OneToMany(targetEntity = TerceroEntity.class,mappedBy = "pais",cascade = {},fetch = FetchType.LAZY)
    @Column(insertable = false,updatable = false)
    private List<TerceroEntity> terceros;

    @OneToMany(targetEntity = DocDetallesEntity.class,mappedBy = "pais",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @Column(insertable = false,updatable = false)
    private List<DocDetallesEntity> docDetalles;

    @OneToMany(targetEntity = EstadoEntity.class,mappedBy = "pais",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<EstadoEntity> estados;
}
