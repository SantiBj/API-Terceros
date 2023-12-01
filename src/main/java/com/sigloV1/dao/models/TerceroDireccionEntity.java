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
@Table(name = "tercero_direccion")
public class TerceroDireccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_direccion",referencedColumnName = "id",nullable = false)
    private DireccionEntity direccion;


    @ManyToOne
    @JoinColumn(name = "id_tercero",referencedColumnName = "id",nullable = false)
    private TerceroEntity tercero;

    @NotNull
    private Boolean estado;

    @OneToMany(mappedBy = "direccionTer",targetEntity = DireccionTelefonoEntity.class,cascade = {},fetch = FetchType.EAGER)
    private List<DireccionTelefonoEntity> telefonos;
}
