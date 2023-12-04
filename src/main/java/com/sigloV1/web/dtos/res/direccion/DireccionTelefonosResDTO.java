package com.sigloV1.web.dtos.res.direccion;

import com.sigloV1.web.dtos.res.telefono.TelefonoResDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DireccionTelefonosResDTO{
    private Long id;
    private String nombre;
    private String direccion;
    private String codigoPostal;
    private Long ciudad;
    private List<TelefonoResDTO> telefonos;
    private Boolean estado;
}
