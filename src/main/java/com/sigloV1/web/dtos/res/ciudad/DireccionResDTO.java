package com.sigloV1.web.dtos.res.ciudad;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DireccionResDTO {
    private Long id;
    private String direccion;
    private String nombre;
    private String codigoPostal;
}
