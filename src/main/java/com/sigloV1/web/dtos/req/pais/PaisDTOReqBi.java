package com.sigloV1.web.dtos.req.pais;

import com.sigloV1.service.logica.MethodRequiredForGeneric;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaisDTOReqBi implements MethodRequiredForGeneric {
    @NotBlank
    @NotNull
    @Size(min = 3,max = 100)
    private String nombrePais;

    @NotNull
    @NotBlank
    @Size(min = 1,max = 5)
    private String indicativo;

    private List<EstadoDTOReqBi> estados;
}
