package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Loan;
import com.example.demo.entity.LoanInstallment;
import com.example.demo.entity.User;
import com.example.demo.model.response.InstallmentResponse;
import com.example.demo.repository.LoanInstallmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LoanInstallmentService {
    private final LoanInstallmentRepository loanInstallmentRepository;
    private final CustomerService customerService;
    private LoanService loanService;

    public List<LoanInstallment> createLoanInstallments(Loan loan) {
        BigDecimal paybackAmount = loan.getLoanAmount().multiply(BigDecimal.ONE.add(loan.getInterestRate()));
        BigDecimal installmentAmount = paybackAmount.divide(BigDecimal.valueOf(loan.getNumberOfInstallment()), RoundingMode.HALF_EVEN);

        List<LoanInstallment> loanInstallmentList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for(int i=0; i < loan.getNumberOfInstallment(); i++) {
            LoanInstallment li = LoanInstallment.builder()
                    .loan(loan)
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

    public List<InstallmentResponse> listLoanInstallments(Long loanId) throws AccessDeniedException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerService.findCustomerByUserId(user.getUserId());
        Loan loan = loanService.getLoan(loanId);

        if(!user.isAdmin() && !Objects.equals(loan.getCustomerId(), customer.getCustomerId())) {
            throw new AccessDeniedException("You are not allowed");
        }

        return loanInstallmentRepository.findAllByLoan_LoanId(loanId).stream().map(InstallmentResponse::new).toList();
    }

    public List<LoanInstallment> findLoanInstallments(Long loanId) {
        return loanInstallmentRepository.findAllByLoan_LoanId(loanId);
    }

    public LoanInstallment payInstallment(LoanInstallment installment, BigDecimal paidAmount) {
        installment.setPaidAmount(paidAmount);
        installment.setPaid(true);
        installment.setPaymentDate(LocalDate.now());
        return loanInstallmentRepository.save(installment);
    }

    @Autowired
    @Lazy
    public void setLoanService(LoanService loanService) {
        this.loanService = loanService;
    }
}
