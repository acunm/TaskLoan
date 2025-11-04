package com.example.demo.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserRegisterRequest {
    private String username;
    private String password;
    private BigDecimal customLoanLimit;
}
