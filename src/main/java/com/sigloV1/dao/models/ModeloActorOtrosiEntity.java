package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "modelo_actor_otrosi")
public class ModeloActorOtrosiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_modelo_actor",referencedColumnName = "id",nullable = false)
    private ContratoModeloActorEntity modeloActor;

    @ManyToOne
    @JoinColumn(name = "id_otrosi",referencedColumnName = "id",nullable = false)
    private OtrosiEntity otrosi;

    @NotNull
    @NotBlank
    private Date fecha;

    @NotNull
    @NotBlank
    private Integer periodicidad;

    @NotNull
    @NotBlank
    @Column(name = "montoMinimo")
    private BigDecimal montoMinimo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_codigo_she",referencedColumnName = "id",unique = true,nullable = false)
    private CodigoSheEntity codigo;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "modelo_actor_otrosi_corte",
            joinColumns = @JoinColumn(name = "id_modelo_actor_otrosi"),
            inverseJoinColumns = @JoinColumn(name = "id_corte",referencedColumnName = "id")
    )
    private List<CorteEntity> cortes;
}
