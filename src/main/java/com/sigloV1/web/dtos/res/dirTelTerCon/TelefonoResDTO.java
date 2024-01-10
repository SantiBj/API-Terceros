package com.sigloV1.web.dtos.res.dirTelTerCon;

import com.sigloV1.dao.models.ETipoTelefono;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefonoResDTO {
    private Long id;
    private Long idRelacionTer;
    private ETipoTelefono tipo;
    private String indicativo;
    private String numero;
    private Boolean estado;
    private String extension;
}
