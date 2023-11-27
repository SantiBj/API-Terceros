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
@Table(name = "modelo")
public class ModeloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 3,max = 100)
    private String nombre;

    @OneToMany(targetEntity = EspecializacionModeloEntity.class,mappedBy = "modelo",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<EspecializacionModeloEntity> especializaciones;
}
