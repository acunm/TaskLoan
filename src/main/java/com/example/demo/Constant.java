package com.example.demo;

import java.math.BigDecimal;
import java.util.List;

public class Constant {
    public static final BigDecimal DEFAULT_CREDIT_LIMIT = BigDecimal.valueOf(1_000_000);
    public static final BigDecimal MIN_INTEREST_RATE = BigDecimal.valueOf(0.1);
    public static final BigDecimal MAX_INTEREST_RATE = BigDecimal.valueOf(0.5);
    public static final List<Integer> VALID_INSTALLMENT_COUNTS = List.of(6, 9, 12, 24);

    public static final String ENDPOINT_USER = "/user";
    public static final String ENDPOINT_USER_REGISTER = ENDPOINT_USER + "/register";
    public static final String ENDPOINT_USER_LOGIN = ENDPOINT_USER + "/login";

    public static final String ENDPOINT_LOAN = "/loan";

}
