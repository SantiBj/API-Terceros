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
@Table(name = "tipo_rol")
public class TipoRolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 100)
    @Column(unique = true)
    private String nombre;

    @OneToMany(targetEntity = RolEntity.class,mappedBy = "tipoRol",cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private List<RolEntity> roles;
}
