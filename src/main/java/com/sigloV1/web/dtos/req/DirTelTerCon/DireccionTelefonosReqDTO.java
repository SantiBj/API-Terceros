package com.sigloV1.web.dtos.req.DirTelTerCon;

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
public class DireccionTelefonosReqDTO {

    @NotNull
    private Long terceroId;

    @NotBlank
    @NotNull
    @Size(min = 3,max = 100)
    private String nombre;

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

    private Long contactoId;

    private List<TelefonoReqDTO> telefonos;
}
