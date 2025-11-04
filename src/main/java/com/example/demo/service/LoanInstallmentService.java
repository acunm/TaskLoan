package com.example.demo.service;

import com.example.demo.entity.Loan;
import com.example.demo.entity.LoanInstallment;
import com.example.demo.repository.LoanInstallmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanInstallmentService {
    private final LoanInstallmentRepository loanInstallmentRepository;

    public List<LoanInstallment> createLoanInstallments(Loan loan) {
        BigDecimal paybackAmount = loan.getLoanAmount().multiply(BigDecimal.ONE.add(loan.getInterestRate()));
        BigDecimal installmentAmount = paybackAmount.divide(BigDecimal.valueOf(loan.getNumberOfInstallment()), RoundingMode.HALF_EVEN);

        List<LoanInstallment> loanInstallmentList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for(int i=0; i < loan.getNumberOfInstallment(); i++) {
            LoanInstallment li = LoanInstallment.builder()
                    .loanId(loan.getLoanId())
                    .dueDate(calculateNextDueDate(currentDate))
                    .paymentDate(null)
                    .amount(installmentAmount)
                    .isPaid(false)
                    .paidAmount(BigDecimal.ZERO)
                    .build();

            loanInstallmentList.add(li);
            currentDate = li.getDueDate();
        }

        return loanInstallmentRepository.saveAll(loanInstallmentList);
    }

    private LocalDate calculateNextDueDate(LocalDate currentDate) {
        return currentDate.with(TemporalAdjusters.firstDayOfNextMonth());
    }
}
