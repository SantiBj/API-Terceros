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
@Table(name = "tercero_rol_contacto_email")
public class TerceroRolEmailContEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_tercero",referencedColumnName = "id")
    private TerceroRolTipoTerEntity tercero;

    @ManyToOne
    @JoinColumn(name = "id_email",referencedColumnName = "id",nullable = false)
    private EmailEntity email;

    @ManyToOne
    @JoinColumn(name = "id_contacto",referencedColumnName = "id")
    private ContactoEntity contacto;

    @NotBlank
    @NotNull
    private Boolean estado;
}
