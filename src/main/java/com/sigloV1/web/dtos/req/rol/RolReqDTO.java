package com.sigloV1.web.dtos.req.rol;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolReqDTO {

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String nombre;

    @NotNull
    private Long tipoRol;
}
