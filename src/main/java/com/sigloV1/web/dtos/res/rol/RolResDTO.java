package com.sigloV1.web.dtos.res.rol;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolResDTO {
    private Long id;
    private String nombre;
    private Long tipoRol;
}
