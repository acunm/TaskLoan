package com.example.demo.service;

import com.example.demo.entity.Loan;
import com.example.demo.model.request.CreateLoanRequest;
import com.example.demo.model.response.CreateLoanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final CustomerService customerService;

    public CreateLoanResponse createLoan(CreateLoanRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        return null;
    }


    private List<Loan> listUserLoans() {
        return null;
    }
}
