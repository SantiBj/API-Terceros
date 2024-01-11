package com.sigloV1.web.dtos.req.email;

import com.sigloV1.dao.models.ETipoCorreo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailReqDTO {
    @NotNull
    @NotBlank
    @Email
    @Size(min = 6,max = 255)
    private String email;

    @NotNull
    private ETipoCorreo tipoCorreo;
}
