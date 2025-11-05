package com.example.demo.model.response;

import com.example.demo.entity.Loan;
import com.example.demo.entity.LoanInstallment;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class LoanResponse {
    private BigDecimal loanAmount;
    private int installmentCount;
    private LocalDate createDate;
    private boolean isPaid;
    private LocalDate nextPaymentDate;
    private BigDecimal installmentAmount;

    public LoanResponse(Loan loan) {
        loanAmount = loan.getLoanAmount();
        installmentCount = loan.getNumberOfInstallment();
        createDate = loan.getCreateDate();
        isPaid = loan.isPaid();
        if(!isPaid) {
            LoanInstallment installment = loan.getLoanInstallments().stream().filter(i -> !i.isPaid()).findFirst().get();
            nextPaymentDate = installment.getPaymentDate();
            installmentAmount = installment.getAmount();

        }

    }
}
