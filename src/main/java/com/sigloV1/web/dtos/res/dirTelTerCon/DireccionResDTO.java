package com.sigloV1.web.dtos.res.dirTelTerCon;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DireccionResDTO {
    private Long id;
    private Long idRelacionTer;
    private String nombre;
    private String direccion;
    private String codigoPostal;
    private Long ciudad;
    private Boolean estado;
}
