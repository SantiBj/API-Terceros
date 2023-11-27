package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contrato_modelo_actor")
public class ContratoModeloActorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_actor",referencedColumnName = "id",nullable = false)
    private ActorEntity actor;

    @ManyToOne
    @JoinColumn(name = "id_modelo_especializacion",referencedColumnName = "id",nullable = false)
    private ContratoModeloEntity modeloEspecializacion;

    @NotNull
    @NotBlank
    @Size(max = 3)
    private String porcentaje;

    @OneToMany(targetEntity = ModeloActorOtrosiEntity.class,mappedBy = "modeloActor",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<ModeloActorOtrosiEntity> otrosi;
}
