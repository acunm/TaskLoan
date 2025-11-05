package com.example.demo.controller;

import com.example.demo.Constant;
import com.example.demo.model.response.InstallmentResponse;
import com.example.demo.service.LoanInstallmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoanInstallmentController {
    private final LoanInstallmentService loanInstallmentService;

    @GetMapping(value = Constant.ENDPOINT_LOAN_INSTALLMENT_LIST)
    public ResponseEntity<List<InstallmentResponse>> listLoanInstallments(@PathVariable Long loanId) throws AccessDeniedException {
        return ResponseEntity.ok(loanInstallmentService.listLoanInstallments(loanId));
    }

}
