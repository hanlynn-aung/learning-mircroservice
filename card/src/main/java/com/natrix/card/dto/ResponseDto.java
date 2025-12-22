package com.natrix.card.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(
        name = "Response",
        description = "Schema to hold successful response information"
)
public class ResponseDto {

    private String title;

    @Schema(
            description = "Status code in the response"
    )
    private Integer statusCode;

    @Schema(
            description = "Status message in the response"
    )
    private String statusMsg;

    private LocalDateTime timestamp;

}
