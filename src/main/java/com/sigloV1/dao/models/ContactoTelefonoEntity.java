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
@Table(name = "contacto_telefono")
public class ContactoTelefonoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_telefono",referencedColumnName = "id",nullable = false)
    private TelefonoEntity telefono;

    @ManyToOne
    @JoinColumn(name = "id_contacto",referencedColumnName = "id",nullable = false)
    private ContactoEntity contacto;

    @NotBlank
    @NotNull
    private Boolean estado;
}
