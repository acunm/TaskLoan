package com.example.demo;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Loan;
import com.example.demo.entity.LoanInstallment;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomerLimitIsNotEnoughException;
import com.example.demo.model.request.CreateLoanRequest;
import com.example.demo.model.response.CreateLoanResponse;
import com.example.demo.repository.LoanRepository;
import com.example.demo.service.CustomerService;
import com.example.demo.service.LoanInstallmentService;
import com.example.demo.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTests {
    @Mock
    private CustomerService customerService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanInstallmentService loanInstallmentService;

    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    void setupSecurityContext() {
        User mockUser = new User();
        mockUser.setUserId(1L);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(mockUser, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void createLoan_ShouldCreateLoanAndInstallments() {
        CreateLoanRequest request = CreateLoanRequest.builder()
                .loanAmount(BigDecimal.valueOf(10000))
                .installmentCount(6)
                .interestRate(BigDecimal.valueOf(0.2))
                .build();

        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setUserId(1L);
        customer.setCreditLimit(BigDecimal.valueOf(20000));
        customer.setUsedCreditLimit(BigDecimal.ZERO);

        Loan savedLoan = Loan.builder()
                .loanId(1L)
                .loanAmount(BigDecimal.valueOf(10000))
                .createDate(LocalDate.now())
                .customerId(1L)
                .isPaid(false)
                .numberOfInstallment(6)
                .interestRate(BigDecimal.valueOf(0.2))
                .build();

        List<LoanInstallment> installments = new ArrayList<>();
        LocalDate d = savedLoan.getCreateDate();
        for(int i=0; i < savedLoan.getNumberOfInstallment(); i++) {
            LoanInstallment li = new LoanInstallment();
            d = d.with(TemporalAdjusters.firstDayOfNextMonth());
            li.setPaymentDate(d);
            li.setPaid(false);
            li.setPaidAmount(BigDecimal.ZERO);
            li.setInstallmentId(i+1L);
            li.setPaymentDate(null);
            installments.add(li);
        }

        when(customerService.findCustomerByUserId(1L)).thenReturn(customer);
        when(loanRepository.save(any(Loan.class))).thenReturn(savedLoan);
        when(loanInstallmentService.createLoanInstallments(savedLoan)).thenReturn(installments);

        CreateLoanResponse response = loanService.createLoan(request);

        assertNotNull(response);
        assertEquals(installments.getFirst().getDueDate(), response.getNextDueDate());
        assertEquals(savedLoan.getLoanAmount(), response.getLoanAmount());

        verify(customerService).findCustomerByUserId(1L);
        verify(loanRepository).save(any(Loan.class));
        verify(loanInstallmentService).createLoanInstallments(savedLoan);
    }

    @Test
    void customerLimit_ShouldThrowIfNotEnoughLimit() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setUserId(1L);
        customer.setCreditLimit(BigDecimal.valueOf(20000));
        customer.setUsedCreditLimit(BigDecimal.valueOf(19999));

        assertThrows(CustomerLimitIsNotEnoughException.class,
                () -> loanService.checkCustomerLimit(customer, BigDecimal.valueOf(10000)));
    }

    @Test
    void customerLimit_ShouldNotThrowIfLimitIsEnough() {
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setUserId(1L);
        customer.setCreditLimit(BigDecimal.valueOf(20000));
        customer.setUsedCreditLimit(BigDecimal.valueOf(0));

        assertDoesNotThrow(() -> loanService.checkCustomerLimit(customer, BigDecimal.valueOf(10000)));
    }
}
