package com.sigloV1.web.dtos.res.tercero;

import com.sigloV1.dao.models.DocDetallesEntity;
import com.sigloV1.dao.models.PaisEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.models.TipoTerceroEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TerceroResDTO {

    private Long id;

    private String identificacion;

    private String nombre;

    private String razonSocial;

    private String nombreComercial;

    private PaisEntity pais;

    private DocDetallesEntity docDetalles;

    private TerceroEntity terceroPadre;

    private TipoTerceroEntity tipoTercero;

    private Date fechaNacimento;
}
