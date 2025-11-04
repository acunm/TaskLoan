package com.example.demo.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateLoanRequest {
    @NotNull
    private BigDecimal loanAmount;
    @NotNull
    private Integer installmentCount;
    @NotNull
    private BigDecimal interestRate;
}
