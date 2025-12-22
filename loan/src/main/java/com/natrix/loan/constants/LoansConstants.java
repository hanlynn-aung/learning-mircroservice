package com.natrix.loan.constants;

public final class LoansConstants {



    private LoansConstants() {
        // restrict instantiation
    }

    public static final String HOME_LOAN = "Home Loan";

    public static final Integer NEW_LOAN_LIMIT = 1_00_000;

    public static final String CREATED = "CREATED";

    public static final String SUCCESS = "SUCCESS";

    public static final String ERROR = "ERROR";

    public static final Integer STATUS_201 = 201;

    public static final String MESSAGE_201 = "Loan created successfully";

    public static final Integer STATUS_200 = 200;

    public static final String MESSAGE_200 = "Request processed successfully";

    public static final Integer STATUS_417 = 417;

    public static final String MESSAGE_417_UPDATE = "Update operation failed. Please try again or contact Dev team";

    public static final String MESSAGE_417_DELETE = "Delete operation failed. Please try again or contact Dev team";
    // public static final Integer  STATUS_500 = 500;
    // public static final String  MESSAGE_500 = "An error occurred. Please try again or contact Dev team";

}
