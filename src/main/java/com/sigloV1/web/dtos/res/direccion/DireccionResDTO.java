package com.sigloV1.web.dtos.res.direccion;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DireccionResDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String codigoPostal;
    private Long ciudad;
}
