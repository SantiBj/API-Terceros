package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "poliza")
public class PolizaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_contrato",referencedColumnName = "id",nullable = false)
    private ContratoEntity contrato;

    @NotBlank
    @NotNull
    private Boolean estado;

    @NotNull
    @NotBlank
    private Date fecha;

    @NotNull
    @NotBlank
    @Size(min = 5)
    private String url;

    @ManyToOne
    @JoinColumn(name = "id_aseguradora",referencedColumnName = "id",nullable = false)
    private TerceroRolTipoTer aseguradora;
}
