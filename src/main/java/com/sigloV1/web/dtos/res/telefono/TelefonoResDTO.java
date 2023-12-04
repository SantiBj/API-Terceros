package com.sigloV1.web.dtos.res.telefono;

import com.sigloV1.dao.models.ETipoTelefono;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefonoResDTO {

    private Long id;

    private String numero;

    private ETipoTelefono tipoTelefono;

    private String extension;

    private Boolean estado;
}
