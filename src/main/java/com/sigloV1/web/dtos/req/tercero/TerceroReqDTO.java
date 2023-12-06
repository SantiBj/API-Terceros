package com.sigloV1.web.dtos.req.tercero;

import com.sigloV1.dao.models.DocDetallesEntity;
import com.sigloV1.dao.models.PaisEntity;
import com.sigloV1.dao.models.TerceroEntity;
import com.sigloV1.dao.models.TipoTerceroEntity;
import com.sigloV1.web.dtos.req.direccion.DireccionTelefonosReqDTO;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TerceroReqDTO {

    @NotBlank
    @NotNull
    @Size(min=4,max=12)
    private String identificacion;

    @NotBlank
    @Size(min = 5, max = 255)
    private String nombre;

    @Size(min = 5,max = 255)
    private String razonSocial;

    @Size(min = 5,max = 255)
    private String nombreComercial;

    @NotNull
    private Long pais;

    @NotNull
    private Long docDetalles;

    @NotNull
    private Long terceroPadre;

    @NotNull
    private Long tipoTercero;

    @NotBlank
    @NotNull
    private Date fechaNacimento;

    private List<DireccionTelefonosReqDTO> direcciones;

    private List<TerceroReqDTO> telefonos;
}
