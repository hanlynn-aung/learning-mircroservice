package com.natrix.card.controller;

import com.natrix.card.constants.CardsConstants;
import com.natrix.card.dto.CardsDto;
import com.natrix.card.dto.ErrorResponseDto;
import com.natrix.card.dto.ResponseDto;
import com.natrix.card.service.ICardsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(
        name = "Cards Management APIs",
        description = "REST APIs for CREATE, UPDATE, FETCH and DELETE Card details"
)
@RestController
@RequestMapping(value = "/api/cards", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class CardsController {

    private final ICardsService cardService;

    private static final String MOBILE_REGEX =
            "(^$|^(09\\d{7,9}|\\+959\\d{7,9})$)";

    /* ===================== CREATE ===================== */

    @Operation(summary = "Create Card", description = "Create a new card using mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Card created successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam
                                                  @Pattern(regexp = MOBILE_REGEX, message = "Invalid mobile number")
                                                  String mobileNumber) {

        cardService.createCard(mobileNumber);

        return buildSuccessResponse(
                CardsConstants.CREATED,
                CardsConstants.STATUS_201,
                CardsConstants.MESSAGE_201,
                HttpStatus.CREATED
        );
    }

    /* ===================== FETCH ===================== */

    @Operation(summary = "Fetch Card Details", description = "Fetch card details by mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Card details fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCardDetails(@RequestParam
                                                     @Pattern(regexp = MOBILE_REGEX, message = "Invalid mobile number")
                                                     String mobileNumber) {

        CardsDto cardsDto = cardService.fetchCard(mobileNumber);
        return ResponseEntity.ok(cardsDto);
    }

    /* ===================== UPDATE ===================== */

    @Operation(summary = "Update Card Details", description = "Update card details by card number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Card updated successfully"),
            @ApiResponse(responseCode = "417", description = "Update failed"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateCard(@Valid @RequestBody CardsDto cardsDto) {

        boolean updated = cardService.updateCard(cardsDto);

        return updated
                ? buildSuccessResponse(
                CardsConstants.SUCCESS,
                CardsConstants.STATUS_200,
                CardsConstants.MESSAGE_200,
                HttpStatus.OK
        )
                : buildErrorResponse(
                "/update",
                CardsConstants.MESSAGE_417_UPDATE
        );
    }

    /* ===================== DELETE ===================== */

    @Operation(summary = "Delete Card", description = "Delete card by mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Card deleted successfully"),
            @ApiResponse(responseCode = "417", description = "Delete failed"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCard(@RequestParam
                                        @Pattern(regexp = MOBILE_REGEX, message = "Invalid mobile number")
                                        String mobileNumber) {

        boolean deleted = cardService.deleteCard(mobileNumber);

        return deleted
                ? buildSuccessResponse(
                CardsConstants.SUCCESS,
                CardsConstants.STATUS_200,
                CardsConstants.MESSAGE_200,
                HttpStatus.OK
        )
                : buildErrorResponse(
                "/delete",
                CardsConstants.MESSAGE_417_DELETE
        );
    }

    /* ===================== COMMON RESPONSE BUILDERS ===================== */

    private ResponseEntity<ResponseDto> buildSuccessResponse(
            String status,
            Integer statusCode,
            String message,
            HttpStatus httpStatus) {

        return ResponseEntity.status(httpStatus)
                .body(new ResponseDto(
                        status,
                        statusCode,
                        message,
                        LocalDateTime.now()
                ));
    }

    private ResponseEntity<ErrorResponseDto> buildErrorResponse(
            String path,
            String message) {

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ErrorResponseDto(
                        path,
                        CardsConstants.ERROR,
                        HttpStatus.EXPECTATION_FAILED.value(),
                        message,
                        LocalDateTime.now()
                ));
    }

}
