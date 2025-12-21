package com.natrix.accounts.controller;

import com.natrix.accounts.constants.AccountsConstants;
import com.natrix.accounts.dto.CustomerDto;
import com.natrix.accounts.dto.ErrorResponseDto;
import com.natrix.accounts.dto.ResponseDto;
import com.natrix.accounts.service.IAccountsService;
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
        name = "CRUD REST APIs for Accounts in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE account details"
)
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class AccountController {

    private final IAccountsService accountsService;

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer &  Account inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED",
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
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        this.accountsService.createAccount(customerDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(
                        AccountsConstants.CREATED,
                        AccountsConstants.STATUS_201,
                        AccountsConstants.MESSAGE_201,
                        LocalDateTime.now()));
    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer &  Account details based on a mobile number"
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
    public ResponseEntity<?> fetchAccountDetails(@RequestParam
                                                 @Pattern(
                                                         regexp = "(^$|^(09\\d{7,9}|\\+959\\d{7,9})$)",
                                                         message = "Invalid mobile number"
                                                 ) String mobileNumber) {

        CustomerDto customerDto = accountsService.fetchAccount(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }


    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer &  Account details based on a account number"
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
    public ResponseEntity<?> updateAccount(@Valid @RequestBody CustomerDto customerDto) {

        boolean updateAccount = this.accountsService.updateAccount(customerDto);

        if (updateAccount) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(
                            AccountsConstants.SUCCESS,
                            AccountsConstants.STATUS_200,
                            AccountsConstants.MESSAGE_200,
                            LocalDateTime.now()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ErrorResponseDto(
                            "/update",
                            AccountsConstants.ERROR,
                            AccountsConstants.STATUS_417,
                            AccountsConstants.MESSAGE_417_UPDATE,
                            LocalDateTime.now()));
        }
    }

    @Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to delete Customer &  Account details based on a mobile number"
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
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(@RequestParam
                                           @Pattern(
                                                   regexp = "(^$|^(09\\d{7,9}|\\+959\\d{7,9})$)",
                                                   message = "Invalid mobile number"
                                           )
                                           String mobileNumber) {

        boolean isDeleted = this.accountsService.deleteAccount(mobileNumber);

        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(
                            AccountsConstants.SUCCESS,
                            AccountsConstants.STATUS_200,
                            AccountsConstants.MESSAGE_200,
                            LocalDateTime.now()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ErrorResponseDto(
                            "/delete",
                            AccountsConstants.ERROR,
                            AccountsConstants.STATUS_417,
                            AccountsConstants.MESSAGE_417_DELETE,
                            LocalDateTime.now()
                    ));
        }
    }

}
