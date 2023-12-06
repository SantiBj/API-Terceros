package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tercero_rol_tipo_tercero")
public class TerceroRolTipoTer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_tercero",referencedColumnName = "id",nullable = false)
    private TerceroEntity tercero;

    ///aca la relacion entre la intermedia de tipo_tercero y rol
    @ManyToOne
    @JoinColumn(name = "id_rol_tipo_tercero",referencedColumnName = "id",nullable = false)
    private RolTipoTerceroEntity rol;

    @NotNull
    @NotBlank
    private Boolean estado;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "tercero_contrato",
            joinColumns= @JoinColumn(name = "id_tercero_rol",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_contrato",referencedColumnName = "id")
    )
    private List<ContratoEntity> contratos;
}
