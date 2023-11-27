package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contrato_modelo")
public class ContratoModeloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_contrato",referencedColumnName = "id",nullable = false)
    private ContratoEntity contrato;

    @ManyToOne
    @JoinColumn(name = "id_especializacion",referencedColumnName = "id")
    private EspecializacionModeloEntity especializacion;

    @ManyToOne
    @JoinColumn(name = "id_moneda",referencedColumnName = "id",nullable = false)
    private MonedaEntity moneda;

    @NotNull
    @NotBlank
    private Boolean estado;

    @OneToMany(targetEntity = ContratoModeloActorEntity.class,mappedBy = "modeloEspecializacion",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<ContratoModeloActorEntity> actores;
}
