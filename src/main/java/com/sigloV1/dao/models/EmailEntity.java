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
@Table(name = "email")
public class EmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 6,max = 255)
    @Column(unique = true)
    private String email;

    @OneToMany(targetEntity = ContactoEmailEntity.class,mappedBy = "email", cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @Column(insertable = false,updatable = false)
    private List<ContactoEmailEntity> contactos;

    @OneToMany(targetEntity = TerceroEmailEntity.class,mappedBy = "email",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<TerceroEmailEntity> terceros;
}
