package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "direccion_telefono_tercero_contacto")
public class DirTelTerContEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_direccion_telefono",referencedColumnName = "id",nullable = false)
    private DirTelTerEntity direccionTelefono;

    @ManyToOne
    @JoinColumn(name = "id_contacto",referencedColumnName = "id",nullable = false)
    private ContactoEntity contacto;

    @Size(max = 6)
    private String extension;

    @NotNull
    @Column(name = "estado_telefono")
    private Boolean estadoTelefono;

    @NotNull
    @Column(name = "estado_direccion")
    private Boolean estadoDireccion;
}
