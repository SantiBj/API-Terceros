package com.sigloV1.web.dtos.res.pais;

import com.sigloV1.web.dtos.res.pais.EstadoDTOResBi;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaisDTOResBi {
    private Long id;
    private String nombrePais;
    private String indicativo;
    private Boolean estado;
    private List<EstadoDTOResBi> estados;
}
