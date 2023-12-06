package com.sigloV1.dao.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "rol_tipo_tercero")
public class RolTipoTerceroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_tipo_tercero",referencedColumnName = "id",nullable = false)
    private TipoTerceroEntity tipoTercero;

    @ManyToOne
    @JoinColumn(name = "id_rol",referencedColumnName = "id",nullable = false)
    private RolEntity rol;

}
