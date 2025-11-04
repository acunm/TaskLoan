package com.example.demo.model.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RegisterResponse {
    private String username;
    private BigDecimal loanLimit;
}
