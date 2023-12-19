package com.sigloV1.web.dtos.req.DirTelTerCon;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DatosDeContactoDTO {
    private DireccionReqDTO direccion;
    private TelefonoReqDTO telefono;
    private DireccionTelefonosReqDTO direccionTelefonos;
    private DireccionIdTelefonosReqDTO direccionIdTelefonos;
}
