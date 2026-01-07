package com.natrix.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Schema(
        name = "ErrorResponse",
        description = "Schema to hold error response information"
)
public class ErrorResponseDto {

        @Schema(
            description = "API path invoked by client"
    )
    private String apiPath;

    private String title;

        @Schema(
            description = "Error code representing the error happened"
    )
    private Integer errorCode;

        @Schema(
            description = "Error message representing the error happened"
    )
    private String errorMessage;

        @Schema(
            description = "Time representing when the error happened"
    )
    private LocalDateTime errorTime;

}
