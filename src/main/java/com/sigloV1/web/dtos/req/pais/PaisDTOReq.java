package com.sigloV1.web.dtos.req.pais;

import com.sigloV1.service.utils.MethodRequiredForGeneric;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaisDTOReq implements MethodRequiredForGeneric {


    @Size(max = 100)
    private String nombrePais;


    @Size(max = 5)
    private String indicativo;
}
