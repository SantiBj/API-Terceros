package com.sigloV1.web.dtos.req.pais;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstadoDTOReqBi {
    @NotBlank
    @NotNull
    private String nombre;

    @NotBlank
    @NotNull
    private List<CiudadDTOReq> ciudades;
}
