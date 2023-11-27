package com.sigloV1.web.dtos.res.pais;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CiudadDTORes {

    private Long id;
    private String nombre;
    private String indicativo;
}
