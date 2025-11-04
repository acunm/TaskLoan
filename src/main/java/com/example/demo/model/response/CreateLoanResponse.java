package com.example.demo.model.response;

import com.example.demo.entity.Loan;
import com.example.demo.entity.LoanInstallment;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreateLoanResponse {
    private BigDecimal loanAmount;
    private LocalDate nextDueDate;
    private List<InstallmentResponse> installments;

    public CreateLoanResponse(Loan loan, List<LoanInstallment> installments) {
        loanAmount = loan.getLoanAmount();
        this.installments = installments.stream().map(InstallmentResponse::new).toList();
        nextDueDate = this.installments.getFirst().getDueDate();
    }
}
