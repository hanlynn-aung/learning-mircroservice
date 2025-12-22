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
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author Eazy Bytes
 */

@Tag(
        name = "CRUD REST APIs for Cards in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE card details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class CardsController {

    private final ICardsService cardService;

    @Operation(
            summary = "Create Card REST API",
            description = "REST API to create new Card inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<?> createCard(@Valid @RequestParam
                                        @Pattern(
                                                regexp = "(^$|^(09\\d{7,9}|\\+959\\d{7,9})$)",
                                                message = "Invalid mobile number"
                                        )
                                        String mobileNumber) {

        this.cardService.createCard(mobileNumber);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(
                        CardsConstants.CREATED,
                        CardsConstants.STATUS_201,
                        CardsConstants.MESSAGE_201,
                        LocalDateTime.now()));
    }

    @Operation(
            summary = "Fetch Card Details REST API",
            description = "REST API to fetch card details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<?> fetchCardDetails(@RequestParam
                                              @Pattern(
                                                      regexp = "(^$|^(09\\d{7,9}|\\+959\\d{7,9})$)",
                                                      message = "Invalid mobile number"
                                              )
                                              String mobileNumber) {

        CardsDto cardsDto = this.cardService.fetchCard(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    @Operation(
            summary = "Update Card Details REST API",
            description = "REST API to update card details based on a card number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateCardDetails(@Valid @RequestBody CardsDto cardsDto) {

        boolean isUpdated = this.cardService.updateCard(cardsDto);
        if (isUpdated) {

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDto(
                            CardsConstants.SUCCESS,
                            CardsConstants.STATUS_200,
                            CardsConstants.MESSAGE_200,
                            LocalDateTime.now()));
        } else {

            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ErrorResponseDto(
                            "/update",
                            CardsConstants.ERROR,
                            CardsConstants.STATUS_417,
                            CardsConstants.MESSAGE_417_UPDATE,
                            LocalDateTime.now()));
        }
    }

    @Operation(
            summary = "Delete Card Details REST API",
            description = "REST API to delete Card details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCardDetails(@RequestParam
                                               @Pattern(
                                                       regexp = "(^$|^(09\\d{7,9}|\\+959\\d{7,9})$)",
                                                       message = "Invalid mobile number"
                                               )
                                               String mobileNumber) {

        boolean isDeleted = this.cardService.deleteCard(mobileNumber);

        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(
                            CardsConstants.SUCCESS,
                            CardsConstants.STATUS_200,
                            CardsConstants.MESSAGE_200,
                            LocalDateTime.now()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ErrorResponseDto(
                            "/delete",
                            CardsConstants.ERROR,
                            CardsConstants.STATUS_417,
                            CardsConstants.MESSAGE_417_DELETE,
                            LocalDateTime.now()));
        }
    }

}
