package com.sigloV1.web.dtos.req.direccion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DireccionReqDTO {
    @NotBlank
    @NotNull
    @Size(max = 100)
    private String nombre;

    @NotBlank
    @NotNull
    @Size(max = 255)
    private String direccion;

    @NotBlank
    @NotNull
    @Size(max = 20)
    private String codigoPostal;

    @NotNull
    private Long ciudad;
}