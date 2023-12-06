package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contrato")
public class ContratoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 100)
    @Column(name = "numero_contrato",unique = true,nullable = false)
    private String numero;

    @NotNull
    @NotBlank
    private Date fecha;

    @NotNull
    @NotBlank
    private Boolean estado;

    @ManyToMany(mappedBy = "contratos",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<TerceroRolTipoTer> terceros;

    @OneToMany(targetEntity = ContratoModeloEntity.class,mappedBy = "contrato",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<ContratoModeloEntity> modelos;

    @OneToMany(targetEntity = PolizaEntity.class,mappedBy = "contrato",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<PolizaEntity> polizas;
}
