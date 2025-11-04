package com.example.demo.controller;

import com.example.demo.Constant;
import com.example.demo.exception.InvalidInstallmentOptionException;
import com.example.demo.exception.InvalidInterestRateException;
import com.example.demo.model.request.CreateLoanRequest;
import com.example.demo.model.response.CreateLoanResponse;
import com.example.demo.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;

    @PostMapping(value = Constant.ENDPOINT_LOAN)
    public ResponseEntity<CreateLoanResponse> createLoan(@RequestBody @Valid CreateLoanRequest request) {
        BigDecimal interestRate = request.getInterestRate();
        if(interestRate.compareTo(Constant.MIN_INTEREST_RATE) < 0 || interestRate.compareTo(Constant.MAX_INTEREST_RATE) > 0)
            throw new InvalidInterestRateException("Interest rate must be between 0.1 and 0.5");

        int installmentCount = request.getInstallmentCount();
        if(!Constant.VALID_INSTALLMENT_COUNTS.contains(installmentCount))
            throw new InvalidInstallmentOptionException("Installment count must be one of 6, 9, 12 and 24");


        return ResponseEntity.ok(loanService.createLoan(request));
    }

}
