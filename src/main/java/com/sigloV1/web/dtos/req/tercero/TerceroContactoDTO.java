package com.sigloV1.web.dtos.req.tercero;

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
public class TerceroContactoDTO {
    @NotBlank
    @NotNull
    @Size(min=4,max=12)
    private String identificacion;

    @Size(min = 5, max = 255)
    private String nombre;

    @NotNull
    private Long contactoDe;

    @NotNull
    private Long cargoId;

    @NotNull
    private Long pais;

    @NotNull
    private Long docDetalles;

    @NotNull
    private Date fechaExpedicion;

    private List<DireccionTerDTO> direcciones;

    private List<TelefonoTerDTO> telefonos;

    private List<EmailTerDTO> emails;
}
