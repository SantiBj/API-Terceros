package com.sigloV1.web.dtos.req.contacto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CargoReqDTO {

    @NotNull
    @NotBlank
    @Size(max = 100)
    private String nombre;
}
