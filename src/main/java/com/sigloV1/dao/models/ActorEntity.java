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
@Table(name = "actor")
public class ActorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 3,max = 100)
    private String nombre;

    @OneToMany(targetEntity = ContratoModeloActorEntity.class,mappedBy = "actor",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @Column(insertable = false,updatable = false)
    private List<ContratoModeloActorEntity> contratoModeloEspecializacion;
}
