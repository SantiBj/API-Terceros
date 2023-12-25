package com.sigloV1.service.logica.DirTelTerCont.Params;

import com.sigloV1.dao.models.DireccionEntity;
import com.sigloV1.dao.models.TelefonoEntity;
import com.sigloV1.dao.models.TerceroEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelacionarALL {

    private DireccionEntity direccion;
    private List<TelefonoParams> telefonos;

    private Long usadaComoContacto;

    private String nombreDireccion;

    private TerceroEntity tercero;
}
