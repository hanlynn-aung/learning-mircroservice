package com.natrix.loan.controller;

import com.natrix.loan.constants.LoansConstants;
import com.natrix.loan.dto.ErrorResponseDto;
import com.natrix.loan.dto.LoansDto;
import com.natrix.loan.dto.ResponseDto;
import com.natrix.loan.service.ILoansService;
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

@Tag(
        name = "CRUD REST APIs for Loans in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class LoansController {

    private final ILoansService loanService;

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new loan inside EazyBank"
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
    public ResponseEntity<?> createLoan(@RequestParam
                                        @Pattern(
                                                regexp = "(^$|^(09\\d{7,9}|\\+959\\d{7,9})$)",
                                                message = "Invalid mobile number"
                                        ) String mobileNumber) {

        this.loanService.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(
                        LoansConstants.CREATED,
                        LoansConstants.STATUS_201,
                        LoansConstants.MESSAGE_201,
                        LocalDateTime.now()));
    }

    @Operation(
            summary = "Fetch Loan Details REST API",
            description = "REST API to fetch loan details based on a mobile number"
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
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam
                                                     @Pattern(
                                                             regexp = "(^$|^(09\\d{7,9}|\\+959\\d{7,9})$)",
                                                             message = "Invalid mobile number"
                                                     ) String mobileNumber) {

        LoansDto loansDto = this.loanService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(loansDto);
    }

    @Operation(
            summary = "Update Loan Details REST API",
            description = "REST API to update loan details based on a loan number"
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
    }
    )
    @PutMapping("/update")
    public ResponseEntity<?> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {

        boolean isUpdated = this.loanService.updateLoan(loansDto);
        if (isUpdated) {

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDto(
                            LoansConstants.SUCCESS,
                            LoansConstants.STATUS_200,
                            LoansConstants.MESSAGE_200,
                            LocalDateTime.now()));
        } else {

            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ErrorResponseDto(
                            "/update",
                            LoansConstants.ERROR,
                            LoansConstants.STATUS_417,
                            LoansConstants.MESSAGE_417_UPDATE,
                            LocalDateTime.now()));
        }
    }

    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
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
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLoanDetails(@RequestParam
                                               @Pattern(
                                                       regexp = "(^$|^(09\\d{7,9}|\\+959\\d{7,9})$)",
                                                       message = "Invalid mobile number"
                                               ) String mobileNumber) {

        boolean isDeleted = this.loanService.deleteLoan(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(
                            LoansConstants.SUCCESS,
                            LoansConstants.STATUS_200,
                            LoansConstants.MESSAGE_200,
                            LocalDateTime.now()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ErrorResponseDto(
                            "/delete",
                            LoansConstants.ERROR,
                            LoansConstants.STATUS_417,
                            LoansConstants.MESSAGE_417_DELETE,
                            LocalDateTime.now()));
        }
    }

}
