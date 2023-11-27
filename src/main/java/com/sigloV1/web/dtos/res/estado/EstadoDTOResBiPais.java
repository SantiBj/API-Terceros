package com.sigloV1.web.dtos.res.estado;

import com.sigloV1.dao.models.PaisEntity;
import com.sigloV1.web.dtos.res.pais.CiudadDTORes;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstadoDTOResBiPais {

    private Long id;
    private String nombre;
    private List<CiudadDTORes> ciudades;
    private Long pais;
}
