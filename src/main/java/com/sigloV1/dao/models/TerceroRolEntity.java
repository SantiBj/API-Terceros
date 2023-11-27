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
@Table(name = "tercero_rol")
public class TerceroRolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_rol",referencedColumnName = "id",nullable = false)
    private RolEntity rol;

    @ManyToOne
    @JoinColumn(name = "id_tercero",referencedColumnName = "id",nullable = false)
    private TerceroEntity tercero;

    @OneToMany(targetEntity = CuentaBancariaEntity.class,mappedBy = "propietario",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<CuentaBancariaEntity> cuentas;

    @OneToMany(targetEntity = TerceroEmailEntity.class,mappedBy = "tercero",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<TerceroEmailEntity> emails;

    @OneToMany(targetEntity = PolizaEntity.class,mappedBy = "aseguradora",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @Column(insertable = false,updatable = false)
    private List<PolizaEntity> polizas;

    @NotBlank
    @NotNull
    private Boolean estado;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "tercero_contrato",
            joinColumns= @JoinColumn(name = "id_tercero_rol",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_contrato",referencedColumnName = "id")
    )
    private List<ContratoEntity> contratos;
}
