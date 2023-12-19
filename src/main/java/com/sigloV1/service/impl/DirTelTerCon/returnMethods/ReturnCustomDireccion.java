package com.sigloV1.service.impl.DirTelTerCon.returnMethods;

import com.sigloV1.dao.models.DireccionEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnCustomDireccion {

    private DireccionEntity direccion;

    private Boolean alReadyExisted;
}
