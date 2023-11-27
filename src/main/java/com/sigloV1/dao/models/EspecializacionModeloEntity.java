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
@Table(name = "especializacion_modelo")
public class EspecializacionModeloEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_modelo",referencedColumnName = "id",nullable = false)
    private ModeloEntity modelo;

    @ManyToOne
    @JoinColumn(name = "id_especializacion",referencedColumnName = "id",nullable = false)
    private EspecializacionEntity especializacion;

    @OneToMany(targetEntity = ContratoModeloEntity.class,mappedBy = "especializacion",cascade = {}, fetch = FetchType.LAZY)
    private List<ContratoModeloEntity> contratos;
}
