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
@Table(name = "direccion_telefono_tercero")
public class DirTelTerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_tercero",referencedColumnName = "id")
    private TerceroEntity tercero;

    @ManyToOne
    @JoinColumn(name = "id_telefono",referencedColumnName = "id")
    private TelefonoEntity telefono;


    @Column(name = "estado_telefono")
    private Boolean estadoTelefono;

    @Size(max = 6)
    private String extension;

    @ManyToOne
    @JoinColumn(name = "id_direccion",referencedColumnName = "id")
    private DireccionEntity direccion;


    @Column(name = "estado_direccion")
    private Boolean estadoDireccion;

    @NotNull
    @Column(name = "contacto")
    private Boolean usadaEnContacto;

}
