package com.sigloV1.service.logica.DirTelTerCont.Params;

import com.sigloV1.dao.models.TelefonoEntity;
import com.sigloV1.dao.models.TerceroEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefonoParams {

    private TelefonoEntity telefono;
    private String extension;
}
