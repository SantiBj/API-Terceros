package com.sigloV1.web.dtos.req.estado;

import com.sigloV1.dao.models.PaisEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstadoDTOReqPais {
    @NotBlank
    @NotNull
    private String nombre;

    @NotNull
    private Long pais;
}
