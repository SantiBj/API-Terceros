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
@Table(name = "ciudad")
public class CiudadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 3,max = 100)
    @Column(nullable=false)
    private String nombre;

    @NotBlank
    @NotNull
    @Size(max = 5)
    @Column(nullable = false)
    private String indicativo;

    @ManyToOne
    @JoinColumn(name = "id_estado",referencedColumnName = "id")
    private EstadoEntity estado;

    @OneToMany(targetEntity = DireccionEntity.class,mappedBy = "ciudad",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<DireccionEntity> direcciones;
}
