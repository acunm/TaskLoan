package com.example.demo.model.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PayInstallmentResponse {
    private BigDecimal totalPaidAmount;
    private int totalPaidCount;
    private boolean isLoanPaid;

}
