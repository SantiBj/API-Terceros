package com.sigloV1.dao.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "direccion_telefono")
public class DireccionTelefonoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_telefono",referencedColumnName = "id",nullable = false)
    private TelefonoEntity telefono;

    @ManyToOne
    @JoinColumn(name = "id_direccion",referencedColumnName = "id",nullable = false)
    private DireccionEntity direccion;
}
