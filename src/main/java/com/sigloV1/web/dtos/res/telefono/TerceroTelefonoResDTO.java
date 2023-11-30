package com.sigloV1.web.dtos.res.telefono;

import com.sigloV1.web.dtos.res.telefono.TelefonoResDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TerceroTelefonoResDTO {
    private Long id;
    private TelefonoResDTO telefono;
    private TerceroTelefonoResDTO tercero;
}
