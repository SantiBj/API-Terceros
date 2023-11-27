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
@Table(name = "especializacion")
public class EspecializacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 3, max = 100)
    @Column(unique = true)
    private String nombre;

    @OneToMany(targetEntity = EspecializacionModeloEntity.class,mappedBy = "especializacion",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<EspecializacionModeloEntity> contratos;
}
