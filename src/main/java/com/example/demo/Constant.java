package com.example.demo;

import java.math.BigDecimal;

public class Constant {

    public static final BigDecimal DEFAULT_CREDIT_LIMIT = BigDecimal.valueOf(1_000_000);

    public static final String ENDPOINT_LOAN = "/loan";

    public static final String ENDPOINT_USER = ENDPOINT_LOAN + "/user";
    public static final String ENDPOINT_USER_REGISTER = ENDPOINT_USER + "/register";
    public static final String ENDPOINT_USER_LOGIN = ENDPOINT_USER + "/login";


}
