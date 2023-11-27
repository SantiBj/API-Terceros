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
@Table(name = "estado")
public class EstadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 100)
    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_pais", referencedColumnName = "id")
    private PaisEntity pais;

    @OneToMany(targetEntity = CiudadEntity.class,mappedBy = "estado",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CiudadEntity> ciudades;

}
