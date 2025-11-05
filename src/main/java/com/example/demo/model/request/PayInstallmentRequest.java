package com.example.demo.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayInstallmentRequest {
    private BigDecimal amount;
}
