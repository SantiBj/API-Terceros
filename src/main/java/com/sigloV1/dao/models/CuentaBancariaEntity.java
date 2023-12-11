package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cuenta_bancaria")
public class CuentaBancariaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(max = 30)
    private String numeroCuenta;

    @ManyToOne
    @JoinColumn(name = "id_propietario",referencedColumnName = "id",nullable = false)
    private TerceroRolTipoTerEntity propietario;

    @ManyToOne
    @JoinColumn(name = "id_banco",referencedColumnName = "id",nullable = false)
    private TerceroRolTipoTerEntity banco;

    @NotBlank
    @NotNull
    private Boolean estado;
}
