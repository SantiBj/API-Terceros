package com.sigloV1.service.impl.DirTelTerCon.returnMethods;

import com.sigloV1.dao.models.TelefonoEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnCustomTelefono {
    private TelefonoEntity telefono;
    private Boolean alReadyExisted;
}
