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
@Table(name = "nombre_doc")
public class NombreDocEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Size(max = 100)
    @Column(nullable = false,unique = true)
    private String nombre;

    @OneToMany(targetEntity = DocDetallesEntity.class,mappedBy = "nombreDoc",cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @Column(updatable = false,insertable = false)
    private List<DocDetallesEntity> docDetalles;


}
