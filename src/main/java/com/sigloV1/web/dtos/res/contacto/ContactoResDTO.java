package com.sigloV1.web.dtos.res.contacto;


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
public class ContactoResDTO {
    private Long idContactoTer;

    private Long contactoRelacionId;

    private String identificacion;

    private String nombre;

    private Boolean estado;
}
