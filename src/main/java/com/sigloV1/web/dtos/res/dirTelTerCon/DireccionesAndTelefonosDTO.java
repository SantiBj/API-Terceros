package com.sigloV1.web.dtos.res.dirTelTerCon;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DireccionesAndTelefonosDTO {

    private List<DireccionTelefonosResDTO> direcciones;
    private List<TelefonoResDTO> telefonos;

}
