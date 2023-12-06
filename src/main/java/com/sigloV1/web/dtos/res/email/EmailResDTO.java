package com.sigloV1.web.dtos.res.email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailResDTO {
    private Long id;
    private String email;
    private Boolean estado;
}
