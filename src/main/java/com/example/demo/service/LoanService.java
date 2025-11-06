package com.example.demo.service;

import com.example.demo.Constant;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Loan;
import com.example.demo.entity.LoanInstallment;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomerLimitIsNotEnoughException;
import com.example.demo.exception.LoanAlreadyPaidException;
import com.example.demo.exception.LoanNotFoundException;
import com.example.demo.exception.NotEnoughBalanceException;
import com.example.demo.model.request.CreateLoanRequest;
import com.example.demo.model.request.PayInstallmentRequest;
import com.example.demo.model.response.CreateLoanResponse;
import com.example.demo.model.response.InstallmentResponse;
import com.example.demo.model.response.LoanResponse;
import com.example.demo.model.response.PayInstallmentResponse;
import com.example.demo.repository.LoanRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final CustomerService customerService;
    private final LoanRepository loanRepository;
    private final LoanInstallmentService loanInstallmentService;

    public CreateLoanResponse createLoan(CreateLoanRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Customer customer = customerService.findCustomerByUserId(user.getUserId());

        checkCustomerLimit(customer, request.getLoanAmount());

        Loan loan = Loan.builder()
                .loanAmount(request.getLoanAmount())
                .createDate(LocalDate.now())
                .customerId(customer.getCustomerId())
                .isPaid(false)
                .numberOfInstallment(request.getInstallmentCount())
                .interestRate(request.getInterestRate())
                .build();

        Loan savedLoan = loanRepository.save(loan);

        List<LoanInstallment> loanInstallments = loanInstallmentService.createLoanInstallments(savedLoan);

        return new CreateLoanResponse(savedLoan, loanInstallments);
    }

    public Loan getLoan(Long loanId) {
        return loanRepository.findById(loanId).orElseThrow(() -> new LoanNotFoundException("Loan with id '" + loanId + "' not found."));
    }

    public LoanResponse getLoanResponse(Long loanId) {
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        return loanOptional.map(LoanResponse::new)
                .orElseThrow(() -> new LoanNotFoundException("Loan with id '" + loanId + "' not found."));
    }

    public List<LoanResponse> listUserLoans(Long customerId) {
        if(!canUserOperate(customerId))
            throw new AccessDeniedException("You do not have access to this resource");

        List<Loan> loans = loanRepository.findAllByCustomerId(customerId);
        return loans.stream().map(LoanResponse::new).toList();
    }

    private void setLoanPaid(Loan loan) {
        loan.setPaid(true);
        loanRepository.save(loan);
    }

    public PayInstallmentResponse payInstallment(Long loanId, PayInstallmentRequest request) {
         Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new LoanNotFoundException("Loan not found"));

         if(loan.isPaid())
             throw new LoanAlreadyPaidException("Loan Already Paid");

         if(!canUserOperate(loan.getCustomerId()))
             throw new AccessDeniedException("You do not have access to this resource");

        List<LoanInstallment> loanInstallments = loanInstallmentService.findLoanInstallments(loanId);
        List<LoanInstallment> unpaidInstallments = loanInstallments.stream()
                .sorted(Comparator.comparing(LoanInstallment::getInstallmentId))
                .filter(installment -> !installment.isPaid())
                .toList();

        BigDecimal amount = request.getAmount();
        List<InstallmentResponse> paidInstallments = new ArrayList<>();

        for (LoanInstallment installment : unpaidInstallments) {
            BigDecimal amountToPay = calculatePaymentAmount(installment);

            if (amount.compareTo(amountToPay) < 0)
                throw new NotEnoughBalanceException("Balance is not enough to pay");

            paidInstallments.add(new InstallmentResponse(loanInstallmentService.payInstallment(installment, amountToPay)));
            amount = amount.subtract(amountToPay);
        }

        if(unpaidInstallments.size() - paidInstallments.size() == 0) {
            setLoanPaid(loan);
        }

        return PayInstallmentResponse.builder()
                .isLoanPaid(unpaidInstallments.size() - paidInstallments.size() == 0)
                .totalPaidAmount(paidInstallments.stream().map(InstallmentResponse::getInstallmentAmount).reduce(BigDecimal.ZERO, BigDecimal::add))
                .totalPaidCount(paidInstallments.size())
                .build();
    }

    public BigDecimal calculatePaymentAmount(LoanInstallment installment) {
        LocalDate dueDate = installment.getDueDate();
        LocalDate now = LocalDate.now();
        BigDecimal amount = installment.getAmount();

        if(dueDate.isBefore(now)) {
            long diff = Math.abs(ChronoUnit.DAYS.between(now, dueDate));
            return amount.add(installment.getAmount().multiply(Constant.LATE_PAY_FINE.multiply(BigDecimal.valueOf(diff))));
        }

        if(dueDate.isAfter(now)) {
            long diff = Math.abs(ChronoUnit.DAYS.between(now, dueDate));
            return amount.subtract(installment.getAmount().multiply(Constant.EARLY_PAY_REWARD.multiply(BigDecimal.valueOf(diff))));
        }

        return installment.getAmount();
    }

    public void checkCustomerLimit(Customer customer, BigDecimal loanAmount) {
        BigDecimal remainingLimit = customer.getCreditLimit().subtract(customer.getUsedCreditLimit());

        if(remainingLimit.compareTo(loanAmount) < 0)
            throw new CustomerLimitIsNotEnoughException("Customer's remaining limit: " + remainingLimit);
    }

    private boolean canUserOperate(Long ownerCustomerId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(user.isAdmin())
            return true;

        Customer customer = customerService.findCustomerByUserId(user.getUserId());

        return Objects.equals(ownerCustomerId, customer.getCustomerId());
    }
}
