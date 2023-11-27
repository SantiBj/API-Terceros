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
@Table(name = "otrosi")
public class OtrosiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String nombre;

    @OneToMany(targetEntity = ModeloActorOtrosiEntity.class,mappedBy = "otrosi",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<ModeloActorOtrosiEntity> modeloActor;
}
