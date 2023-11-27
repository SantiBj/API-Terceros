package com.sigloV1.web.dtos.res.estado;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstadoDTOResPais {
    private Long id;
    private String nombre;
    private Long pais;
}
