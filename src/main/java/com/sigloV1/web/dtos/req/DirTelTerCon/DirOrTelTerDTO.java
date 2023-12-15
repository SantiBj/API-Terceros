package com.sigloV1.web.dtos.req.DirTelTerCon;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DirOrTelTerDTO {

    @NotNull
    private Long idTercero;

    private DireccionReqDTO direccion;

    private TelefonoReqDTO telefono;

    private DireccionTelefonosReqDTO direccionTelefonos;
}
