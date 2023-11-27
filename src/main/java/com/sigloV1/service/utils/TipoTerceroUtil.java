package com.sigloV1.service.utils;

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
