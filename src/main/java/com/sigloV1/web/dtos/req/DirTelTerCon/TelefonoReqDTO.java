package com.sigloV1.web.dtos.req.DirTelTerCon;

import com.sigloV1.dao.models.ETipoTelefono;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefonoReqDTO {
    @NotNull
    @NotBlank
    @Size(min = 7 , max = 10)
    private String numero;

    @Enumerated(value = EnumType.STRING)
    private ETipoTelefono tipoTelefono;

    @NotNull
    private Long terceroId;

    private Long contactoId;

    @Size(max = 5)
    private String extension;
}
