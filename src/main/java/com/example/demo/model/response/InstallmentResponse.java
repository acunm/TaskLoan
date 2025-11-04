package com.example.demo.model.response;

import com.example.demo.entity.LoanInstallment;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class InstallmentResponse {
    private LocalDate dueDate;
    private BigDecimal installmentAmount;

    public InstallmentResponse(LoanInstallment loanInstallment) {
        dueDate = loanInstallment.getDueDate();
        installmentAmount = loanInstallment.getAmount();
    }
}
