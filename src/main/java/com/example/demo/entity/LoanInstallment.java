package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "loan_installments")
public class LoanInstallment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long installmentId;
    @Column(nullable = false)
    private Long loanId;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private BigDecimal paidAmount;
    @Column(nullable = false)
    private LocalDate dueDate;
    @Column(nullable = false)
    private LocalDate paymentDate;
    @Column(nullable = false)
    private boolean isPaid;
}
