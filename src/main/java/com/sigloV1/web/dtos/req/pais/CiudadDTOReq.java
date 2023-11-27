package com.sigloV1.web.dtos.req.pais;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CiudadDTOReq {
    @NotBlank
    @NotNull
    private String nombre;

    @NotBlank
    @NotNull
    private String indicativo;
}
