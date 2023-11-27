package com.sigloV1.web.dtos.res.pais;

import com.sigloV1.web.dtos.res.pais.CiudadDTORes;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstadoDTOResBi {

    private Long id;
    private String nombre;
    private List<CiudadDTORes> ciudades;
}
