package com.natrix.account.controller;

import com.natrix.account.constants.AccountsConstants;
import com.natrix.account.dto.AccountsContactInfoDto;
import com.natrix.account.dto.CustomerDto;
import com.natrix.account.dto.ErrorResponseDto;
import com.natrix.account.dto.ResponseDto;
import com.natrix.account.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Tag(
        name = "Accounts Management APIs",
        description = "REST APIs for CREATE, UPDATE, FETCH and DELETE Account & Customer details"
)
@RestController
@RequestMapping(value = "/api/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class AccountsController {

    private final IAccountsService accountsService;

    private final Environment environment;

    private final AccountsContactInfoDto accountsContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    public AccountsController(IAccountsService accountsService,
                              Environment environment,
                              AccountsContactInfoDto accountsContactInfoDto) {

        this.accountsService = accountsService;
        this.environment = environment;
        this.accountsContactInfoDto = accountsContactInfoDto;
    }

    private static final String MOBILE_REGEX =
            "(^$|^(09\\d{7,9}|\\+959\\d{7,9})$)";

    /* ===================== CREATE ===================== */

    @Operation(summary = "Create Account", description = "Create a new customer and account")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created successfully",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        accountsService.createAccount(customerDto);

        return buildSuccessResponse(
                AccountsConstants.CREATED,
                AccountsConstants.STATUS_201,
                AccountsConstants.MESSAGE_201,
                HttpStatus.CREATED
        );
    }

    /* ===================== FETCH ===================== */

    @Operation(summary = "Fetch Account Details", description = "Fetch account details by mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account details fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                           @Pattern(regexp = MOBILE_REGEX, message = "Invalid mobile number")
                                                           String mobileNumber) {

        CustomerDto customerDto = accountsService.fetchAccount(mobileNumber);
        return ResponseEntity.ok(customerDto);
    }

    /* ===================== UPDATE ===================== */

    @Operation(summary = "Update Account Details", description = "Update account and customer details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account updated successfully"),
            @ApiResponse(responseCode = "417", description = "Update failed"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PutMapping("/update")
    public ResponseEntity<?> updateAccount(@Valid @RequestBody CustomerDto customerDto) {

        boolean updated = accountsService.updateAccount(customerDto);

        return updated
                ? buildSuccessResponse(
                AccountsConstants.SUCCESS,
                AccountsConstants.STATUS_200,
                AccountsConstants.MESSAGE_200,
                HttpStatus.OK
        )
                : buildErrorResponse(
                "/update",
                AccountsConstants.MESSAGE_417_UPDATE
        );
    }

    /* ===================== DELETE ===================== */

    @Operation(summary = "Delete Account", description = "Delete account and customer by mobile number")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account deleted successfully"),
            @ApiResponse(responseCode = "417", description = "Delete failed"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(@RequestParam
                                           @Pattern(regexp = MOBILE_REGEX, message = "Invalid mobile number")
                                           String mobileNumber) {

        boolean deleted = accountsService.deleteAccount(mobileNumber);

        return deleted
                ? buildSuccessResponse(
                AccountsConstants.SUCCESS,
                AccountsConstants.STATUS_200,
                AccountsConstants.MESSAGE_200,
                HttpStatus.OK
        )
                : buildErrorResponse(
                "/delete",
                AccountsConstants.MESSAGE_417_DELETE
        );
    }

    @Deprecated
    @Operation(summary = "Get Build Information", description = "Get Build Information that is deployed into account microservice")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildVersion() {

        return ResponseEntity.ok(buildVersion);
    }

    @Deprecated
    @Operation(summary = "Get Java Version Information", description = "Get Java Version Information that is deployed into account microservice")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/java-info")
    public ResponseEntity<String> getJavaVersion() {

        return ResponseEntity.ok(environment.getProperty("JAVA_HOME"));
    }

    @Deprecated
    @Operation(summary = "Get Contact Information", description = "Get Contact Information that can be reached out in case of any issues")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "HTTP Status 200 OK"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {

        return ResponseEntity.ok(accountsContactInfoDto);
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
                        AccountsConstants.ERROR,
                        AccountsConstants.STATUS_417,
                        message,
                        LocalDateTime.now()
                ));
    }

}
