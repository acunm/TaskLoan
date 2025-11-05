package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Loan;
import com.example.demo.entity.LoanInstallment;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomerLimitIsNotEnoughException;
import com.example.demo.exception.LoanNotFoundException;
import com.example.demo.model.request.CreateLoanRequest;
import com.example.demo.model.response.CreateLoanResponse;
import com.example.demo.model.response.LoanResponse;
import com.example.demo.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final CustomerService customerService;
    private final LoanRepository loanRepository;
    private final LoanInstallmentService loanInstallmentService;

    public CreateLoanResponse createLoan(CreateLoanRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
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

    private void checkCustomerLimit(Customer customer, BigDecimal loanAmount) {
        BigDecimal remainingLimit = customer.getCreditLimit().subtract(customer.getUsedCreditLimit());

        if(remainingLimit.compareTo(loanAmount) < 0)
            throw new CustomerLimitIsNotEnoughException("Customer's remaining limit: " + remainingLimit);
    }

    public LoanResponse getLoan(Long loanId) {
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        return loanOptional.map(LoanResponse::new)
                .orElseThrow(() -> new LoanNotFoundException("Loan with id '" + loanId + "' not found."));
    }

    public List<LoanResponse> listUserLoans(Long customerId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.findCustomerByUserId(user.getUserId());

        if(!user.isAdmin())
            customerId = customer.getCustomerId();

        List<Loan> loans = loanRepository.findAllByCustomerId(customerId);
        return loans.stream().map(LoanResponse::new).toList();
    }
}
