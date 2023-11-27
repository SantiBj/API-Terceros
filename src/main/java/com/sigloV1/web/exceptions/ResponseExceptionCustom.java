package com.sigloV1.web.exceptions;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseExceptionCustom {
    private Date timeStamp;
    private String message;
    private String description;
    private String codeState;
}
