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
@Table(name = "contacto_email")
public class ContactoEmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_email",referencedColumnName = "id",nullable = false)
    private EmailEntity email;

    @ManyToOne
    @JoinColumn(name = "id_contacto",referencedColumnName = "id",nullable = false)
    private ContactoEntity contacto;

    @NotNull
    @NotBlank
    private Boolean estado;
}
