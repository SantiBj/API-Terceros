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
@Table(name = "direccion")
public class DireccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 3,max = 100)
    @Column(nullable = false)
    private String nombre;

    @NotBlank
    @NotNull
    @Size(min = 3,max = 200)
    @Column(nullable = false,unique = true)
    private String direccion;

    @NotBlank
    @NotNull
    @Size(min = 3,max = 11)
    @Column(nullable = false,name = "codigo_postal")
    private String codigoPostal;

    @ManyToOne
    @JoinColumn(name = "id_ciudad",referencedColumnName = "id")
    private CiudadEntity ciudad;


    @OneToMany(targetEntity = TerceroDireccionEntity.class,mappedBy = "direccion",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<TerceroDireccionEntity> terceros;

    @OneToMany(targetEntity = DireccionTelefonoEntity.class,mappedBy = "direccion",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<DireccionTelefonoEntity> telefonos;

    @OneToMany(targetEntity = ContactoDireccionEntity.class,mappedBy = "direccion",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ContactoDireccionEntity> contactos;
}
