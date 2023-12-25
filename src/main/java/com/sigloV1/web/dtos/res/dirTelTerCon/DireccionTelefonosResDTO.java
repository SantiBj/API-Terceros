package com.sigloV1.web.dtos.res.dirTelTerCon;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//si la relacionId esta nula es porque tiene telefonos asignados
public class DireccionTelefonosResDTO{
    private Long id;
    private Long relacionId;
    private String nombre;
    private String direccion;
    private String codigoPostal;
    private String ciudad;
    private List<TelefonoResDTO> telefonos;
    private Boolean estado;
}
