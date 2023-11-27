package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "codigo_she")
public class CodigoSheEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3,max = 8)
    @Column(unique = true)
    private String codigo;

    @ManyToOne
    @JoinColumn(name = "id_codigo_padre",referencedColumnName = "id",nullable = false)
    private CodigoSheEntity codigoPadre;

    @OneToOne(mappedBy = "codigo")
    private ModeloActorOtrosiEntity modeloOtrosi;
}
