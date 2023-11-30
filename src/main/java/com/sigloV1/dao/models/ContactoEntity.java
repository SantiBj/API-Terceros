package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "contacto")
public class ContactoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_contacto", referencedColumnName = "id", nullable = false)
    private TerceroEntity contacto;

    @ManyToOne
    @JoinColumn(name = "id_tercero", referencedColumnName = "id", nullable = false)
    private TerceroEntity tercero;

    @ManyToOne
    @JoinColumn(name = "id_cargo", referencedColumnName = "id", nullable = false)
    private CargoEntity cargo;

    @NotBlank
    @NotNull
    private Boolean estado;

    @OneToMany(mappedBy = "contacto", targetEntity = DireccionTelefonoContactoEntity.class, cascade = {}, fetch = FetchType.EAGER)
    private List<DireccionTelefonoContactoEntity> direccionesTelefonos;

    @OneToOne(targetEntity = ContactoEmailEntity.class, mappedBy = "contacto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ContactoEmailEntity> emails;
}
