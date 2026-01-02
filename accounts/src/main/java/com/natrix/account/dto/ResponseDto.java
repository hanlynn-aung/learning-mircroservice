package com.natrix.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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