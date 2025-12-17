package com.natrix.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Schema(
//        name = "Customer",
//        description = "Schema to hold Customer and Account information"
//)
public class CustomerDto {

    //    @Schema(
//            description = "Name of the customer", example = "Eazy Bytes"
//    )
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    //    @Schema(
//            description = "Email address of the customer", example = "tutor@eazybytes.com"
//    )
    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    //    @Schema(
//            description = "Mobile Number of the customer", example = "9345432123"
//    )
    @Pattern(
            regexp = "(^$|^(09\\d{7,9}|\\+959\\d{7,9})$)",
            message = "Invalid mobile number"
    )
    private String mobileNumber;

    //    @Schema(
//            description = "Account details of the Customer"
//    )
    private AccountsDto accountsDto;

}
