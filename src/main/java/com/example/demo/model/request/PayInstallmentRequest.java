package com.example.demo.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayInstallmentRequest {
    @NotBlank
    private BigDecimal amount;
}
