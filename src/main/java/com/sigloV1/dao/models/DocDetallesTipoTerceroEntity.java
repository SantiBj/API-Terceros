package com.sigloV1.dao.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "doc_detalles_tipo_tercero")
public class DocDetallesTipoTerceroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_doc_detalles",referencedColumnName = "id")
    private DocDetallesEntity docDetalles;

    @ManyToOne
    @JoinColumn(name = "id_tipo_tercero",referencedColumnName = "id")
    private TipoTerceroEntity tipoTercero;

}
