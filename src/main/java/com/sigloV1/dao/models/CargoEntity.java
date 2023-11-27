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
@Table(name = "cargo")
public class CargoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 3,max = 100)
    @Column(nullable = false,unique = true)
    private String nombre;

    @OneToMany(targetEntity = ContactoEntity.class,mappedBy = "cargo",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    @Column(insertable = false,updatable = false)
    private List<ContactoEntity> contactos;
}
