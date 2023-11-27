package com.sigloV1.web.dtos.req.ciudad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DireccionReqDTO {
    @NotNull
    @NotBlank
    private String direccion;

    @NotNull
    @NotBlank
    private String nombre;

    @NotNull
    @NotBlank
    private String codigoPostal;
}
