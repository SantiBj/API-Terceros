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
@Table(name = "telefono")
public class TelefonoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 7 , max = 10)
    @Column(nullable = false,unique = true)
    private String numero;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "tipo_telefono",nullable = false)
    private ETipoTelefono tipoTelefono;
}
