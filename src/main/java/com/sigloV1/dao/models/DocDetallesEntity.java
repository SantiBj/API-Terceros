package com.sigloV1.dao.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "doc_detalles")
public class DocDetallesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pais",referencedColumnName = "id",nullable = false)
    private PaisEntity pais;

    @ManyToOne
    @JoinColumn(name = "id_nombre_doc",referencedColumnName = "id",nullable = false)
    private NombreDocEntity nombreDoc;

    @ManyToOne
    @JoinColumn(name = "id_tipo_doc",referencedColumnName = "id",nullable = false)
    private TipoDocEntity tipoDoc;

    @OneToMany(targetEntity = TerceroEntity.class,mappedBy = "docDetalles",cascade = {},fetch = FetchType.LAZY)
    @Column(insertable = false,updatable = false)
    private List<TerceroEntity> terceros;

    @OneToMany(targetEntity = DocDetallesTipoTerceroEntity.class ,mappedBy = "docDetalles",cascade = CascadeType.REMOVE , fetch = FetchType.LAZY)
    @Column(insertable = false,updatable = false)
    private List<DocDetallesTipoTerceroEntity> docDetallesTipoTercero;

}
