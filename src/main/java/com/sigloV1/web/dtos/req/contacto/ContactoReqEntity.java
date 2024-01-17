package com.sigloV1.web.dtos.req.contacto;

import com.sigloV1.dao.models.TerceroEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactoReqEntity {
    private TerceroEntity contacto;
    private TerceroEntity tercero;
    private Long cargo;

}
