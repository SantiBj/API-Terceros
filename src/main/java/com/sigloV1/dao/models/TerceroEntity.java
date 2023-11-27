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
@Table(name = "tercero")
public class TerceroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(min=4,max=12)
    private String identificacion;

    @NotBlank
    @Size(min = 5, max = 255)
    private String nombre;

    @Size(min = 5,max = 255)
    @Column(name = "razon_social")
    private String razonSocial;

    @Size(min = 5,max = 255)
    @Column(name = "nombre_comercial")
    private String nombreComercial;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_pais",referencedColumnName = "id",nullable = false)
    private PaisEntity pais;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_doc_detalles",referencedColumnName = "id",nullable = false)
    private DocDetallesEntity docDetalles;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_tercero_padre",referencedColumnName = "id",nullable = false)
    private TerceroEntity terceroPadre;

    //se evita que se creen entidades desde aca
    //se debe enviar solo entidades ya existentes
    @OneToMany(mappedBy = "terceroPadre",cascade = {},fetch = FetchType.LAZY)
    @Column(insertable = false,updatable = false)
    private List<TerceroEntity> terceroHijos;

    @ManyToOne
    @JoinColumn(name = "id_tipo_tercero",referencedColumnName = "id")
    private TipoTerceroEntity tipoTercero;

    @NotBlank
    @NotNull
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimento;

    @OneToMany(targetEntity = TerceroDireccionEntity.class,mappedBy = "tercero",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<TerceroDireccionEntity> direcciones;

    @OneToMany(targetEntity = TerceroTelefonoEntity.class,mappedBy = "tercero",cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    private List<TerceroTelefonoEntity> telefonos;

    @OneToMany(targetEntity = ContactoEntity.class, mappedBy = "tercero",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ContactoEntity> contactos;

    @OneToMany(targetEntity = TerceroRolEntity.class,mappedBy = "tercero",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<TerceroRolEntity> roles;
}
