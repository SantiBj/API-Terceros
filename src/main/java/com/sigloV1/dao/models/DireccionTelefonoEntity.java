package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "direccionTer_telefonoTer")
public class DireccionTelefonoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_telefono_tercero",referencedColumnName = "id",nullable = false)
    private TerceroTelefonoEntity telefonoTer;

    @ManyToOne
    @JoinColumn(name = "id_direccion_tercero",referencedColumnName = "id",nullable = false)
    private TerceroDireccionEntity direccionTer;

    @NotNull
    private Boolean estado;
}
