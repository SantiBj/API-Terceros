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
@Table(name="tercero_telefono")
public class TerceroTelefonoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_telefono",referencedColumnName = "id",nullable = false)
    private TelefonoEntity telefono;

    @ManyToOne
    @JoinColumn(name = "id_tercero",referencedColumnName = "id",nullable = false)
    private TerceroEntity tercero;

    @NotNull
    private Boolean estado;
}
