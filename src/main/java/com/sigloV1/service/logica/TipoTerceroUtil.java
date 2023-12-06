package com.sigloV1.service.logica;

import com.sigloV1.dao.models.TipoTerceroEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TipoTerceroUtil {
    private TipoTerceroEntity tercero;
    private Boolean existe;
}
