package com.natrix.accounts.controller;

import com.natrix.accounts.constants.AccountsConstants;
import com.natrix.accounts.dto.CustomerDto;
import com.natrix.accounts.dto.ResponseDto;
import com.natrix.accounts.service.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class AccountController {

    private final IAccountsService accountsService;

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

    @GetMapping("/fetch")
    public ResponseEntity<?> fetchAccountDetails(@RequestParam
                                                 @Pattern(
                                                         regexp = "(^$|^(09\\d{7,9}|\\+959\\d{7,9})$)",
                                                         message = "Invalid mobile number"
                                                 ) String mobileNumber) {

        CustomerDto customerDto = accountsService.fetchAccount(mobileNumber);

        return ResponseEntity.status(HttpStatus.OK).body(customerDto);
    }

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
                    .body(new ResponseDto(
                            AccountsConstants.ERROR,
                            AccountsConstants.STATUS_417,
                            AccountsConstants.MESSAGE_417_UPDATE,
                            LocalDateTime.now()));
        }
    }

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
                    .body(new ResponseDto(
                            AccountsConstants.ERROR,
                            AccountsConstants.STATUS_417,
                            AccountsConstants.MESSAGE_417_DELETE,
                            LocalDateTime.now()
                    ));
        }
    }

}
