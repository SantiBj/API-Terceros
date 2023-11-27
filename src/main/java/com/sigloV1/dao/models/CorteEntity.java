package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "corte")
public class CorteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private Date fecha;

    @NotNull
    @NotBlank
    private Boolean estado;

    @ManyToMany(mappedBy = "cortes",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ModeloActorOtrosiEntity> modeloActorOtrosi;
}
