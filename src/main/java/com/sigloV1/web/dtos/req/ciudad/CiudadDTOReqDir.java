package com.sigloV1.web.dtos.req.ciudad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CiudadDTOReqDir {
    @NotBlank
    @NotNull
    private String nombre;

    @NotBlank
    @NotNull
    private String indicativo;

    @NotNull
    private Long estado;

    @NotNull
    private List<DireccionReqDTO> direcciones;
}
