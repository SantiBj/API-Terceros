package com.sigloV1.web.dtos.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoTerceroDTORq {

    @NotBlank
    @NotNull
    @Size(min = 4,max = 100)
    private String nombre;
}
