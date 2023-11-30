package com.sigloV1.dao.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "direccion_telefono_contacto")
public class DireccionTelefonoContactoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_direccionTer_telefonoTer",referencedColumnName = "id",nullable = false)
    private DireccionTelefonoEntity direccionTelefonoTer;

    @ManyToOne
    @JoinColumn(name = "id_contacto",referencedColumnName = "id",nullable = false)
    private ContactoEntity contacto;

    private Boolean estado;

}
