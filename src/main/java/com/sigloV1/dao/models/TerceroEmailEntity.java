package com.sigloV1.dao.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tercero_email")
public class TerceroEmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_tercero",referencedColumnName = "id",nullable = false)
    private TerceroRolEntity tercero;

    @ManyToOne
    @JoinColumn(name = "id_email",referencedColumnName = "id",nullable = false)
    private EmailEntity email;

    @NotBlank
    @NotNull
    private Boolean estado;
}
