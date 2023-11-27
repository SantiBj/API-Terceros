package com.sigloV1.web.dtos.req.estado;

import com.sigloV1.web.dtos.req.pais.CiudadDTOReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstadoDTOReqBiPais {
    @NotBlank
    @NotNull
    private String nombre;

    @NotNull
    private Long pais;

    @NotNull
    private List<CiudadDTOReq> ciudades;
}
