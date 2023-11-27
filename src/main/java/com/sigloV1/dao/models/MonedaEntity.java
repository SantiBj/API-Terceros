package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "moneda")
public class MonedaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 100)
    @Column(unique = true)
    private String nombre;

    @OneToMany(targetEntity = ContratoModeloEntity.class,mappedBy = "moneda", cascade = {},fetch = FetchType.LAZY)
    @Column(updatable = false,insertable = false)
    private List<ContratoModeloEntity> modelosContrato;
}
