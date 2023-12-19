package com.sigloV1.web.dtos.req.DirTelTerCon;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DireccionIdTelefonosReqDTO {

    @NotNull
    private Long idTercero;

    @NotNull
    private Long direccionId;

    private Long contactoId;

    private List<TelefonoReqDTO> telefonos;
}
