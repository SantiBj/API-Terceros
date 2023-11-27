package com.sigloV1.web.dtos.res.ciudad;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CiudadDTOResDir {

    private Long id;
    private String nombre;
    private String indicativo;
    private Long estado;
    private List<DireccionResDTO> direcciones;
}
