package com.sigloV1.web.dtos.req.tercero;

import com.sigloV1.web.dtos.req.DirTelTerCon.TelefonoReqDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DireccionTerDTO {

    @NotBlank
    @NotNull
    @Size(min = 3,max = 100)
    private String nombreDireccion;

    @NotBlank
    @NotNull
    @Size(min = 3,max = 200)
    private String direccion;

    @NotBlank
    @NotNull
    @Size(min = 3,max = 11)
    private String codigoPostal;

    @NotNull
    private Long ciudad;

    private List<TelefonoTerDTO> telefonos;
}
