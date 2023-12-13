package com.sigloV1.web.dtos.res.rol;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolRelacionResDTO {
    private Long idRol;
    private String nombre;
    private Long tipoRol;

    private Long relacionTipoTercero;

    private Long tipoTerceroRelacionado;
}
