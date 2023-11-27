package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contacto_direccion")
public class ContactoDireccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_direccion",referencedColumnName = "id",nullable = false)
    private DireccionEntity direccion;

    @ManyToOne
    @JoinColumn(name = "id_contacto",referencedColumnName = "id",nullable = false)
    private ContactoEntity contacto;

    @NotBlank
    @NotNull
    private Boolean estado;
}
