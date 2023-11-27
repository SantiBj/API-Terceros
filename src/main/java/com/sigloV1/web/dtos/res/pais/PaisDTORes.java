package com.sigloV1.web.dtos.res.pais;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaisDTORes {

    private Long id;
    private String nombrePais;
    private String indicativo;
    private String estado;

}
