package com.sigloV1.web.dtos.req.contacto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactoReqDTO {
    @NotBlank
    @NotNull
    private Long contactoId;

    @NotBlank
    @NotNull
    private Long tercero;

    @NotBlank
    @NotNull
    private Long cargo;
}
