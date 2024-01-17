package com.sigloV1.web.dtos.req.tercero;

import com.sigloV1.dao.models.ETipoTelefono;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefonoTerDTO {

    @NotNull
    @NotBlank
    @Size(min = 7,max = 10)
    private String numero;

    @NotNull
    private ETipoTelefono tipoTelefono;


    @NotBlank
    private String extension;
}
