package com.example.demo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Constant {
    public static final BigDecimal DEFAULT_CREDIT_LIMIT = BigDecimal.valueOf(1_000_000);
    public static final BigDecimal MIN_INTEREST_RATE = BigDecimal.valueOf(0.1);
    public static final BigDecimal MAX_INTEREST_RATE = BigDecimal.valueOf(0.5);
    public static final BigDecimal EARLY_PAY_REWARD = BigDecimal.valueOf(0.001);
    public static final BigDecimal LATE_PAY_FINE = BigDecimal.valueOf(0.001);
    public static final List<Integer> VALID_INSTALLMENT_COUNTS = List.of(6, 9, 12, 24);

    public static final String ENDPOINT_USER = "/user";
    public static final String ENDPOINT_USER_REGISTER = ENDPOINT_USER + "/register";
    public static final String ENDPOINT_USER_LOGIN = ENDPOINT_USER + "/login";

    public static final String ENDPOINT_LOAN = "/loan";
    public static final String ENDPOINT_GET_LOAN = ENDPOINT_LOAN + "/{loanId}";
    public static final String ENDPOINT_LIST_LOAN = ENDPOINT_LOAN;

    public static final String ENDPOINT_LOAN_INSTALLMENT = "loan-installments";
    public static final String ENDPOINT_LOAN_INSTALLMENT_LIST = ENDPOINT_LOAN_INSTALLMENT + "/{loanId}";
}
