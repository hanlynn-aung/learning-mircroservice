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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(
        name = "Loans Management APIs",
        description = "REST APIs for CREATE, UPDATE, FETCH and DELETE Loan details"
)
@RestController
@RequestMapping(value = "/api/loans", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class LoansController {

    private final ILoansService loanService;

    private static final String MOBILE_REGEX =
            "(^$|^(09\\d{7,9}|\\+959\\d{7,9})$)";

    /* ===================== CREATE ===================== */

    @Operation(summary = "Create Loan", description = "Create a new loan using mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Loan created successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@RequestParam
                                                  @Pattern(regexp = MOBILE_REGEX, message = "Invalid mobile number")
                                                  String mobileNumber) {

        loanService.createLoan(mobileNumber);

        return buildSuccessResponse(
                LoansConstants.CREATED,
                LoansConstants.STATUS_201,
                LoansConstants.MESSAGE_201,
                HttpStatus.CREATED
        );
    }

    /* ===================== FETCH ===================== */

    @Operation(summary = "Fetch Loan Details", description = "Fetch loan details by mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loan details fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam
                                                     @Pattern(regexp = MOBILE_REGEX, message = "Invalid mobile number")
                                                     String mobileNumber) {

        LoansDto loansDto = loanService.fetchLoan(mobileNumber);
        return ResponseEntity.ok(loansDto);
    }

    /* ===================== UPDATE ===================== */

    @Operation(summary = "Update Loan Details", description = "Update loan details using loan number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loan updated successfully"),
            @ApiResponse(responseCode = "417", description = "Update failed"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {

        boolean updated = loanService.updateLoan(loansDto);

        return updated
                ? buildSuccessResponse(
                LoansConstants.SUCCESS,
                LoansConstants.STATUS_200,
                LoansConstants.MESSAGE_200,
                HttpStatus.OK
        )
                : buildErrorResponse(
                "/update",
                LoansConstants.MESSAGE_417_UPDATE
        );
    }

    /* ===================== DELETE ===================== */

    @Operation(summary = "Delete Loan Details", description = "Delete loan details by mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loan deleted successfully"),
            @ApiResponse(responseCode = "417", description = "Delete failed"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteLoanDetails(@RequestParam
                                               @Pattern(regexp = MOBILE_REGEX, message = "Invalid mobile number")
                                               String mobileNumber) {

        boolean deleted = loanService.deleteLoan(mobileNumber);

        return deleted
                ? buildSuccessResponse(
                LoansConstants.SUCCESS,
                LoansConstants.STATUS_200,
                LoansConstants.MESSAGE_200,
                HttpStatus.OK
        )
                : buildErrorResponse(
                "/delete",
                LoansConstants.MESSAGE_417_DELETE
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
                        LoansConstants.ERROR,
                        HttpStatus.EXPECTATION_FAILED.value(),
                        message,
                        LocalDateTime.now()
                ));
    }

}
