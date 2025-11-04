package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long loanId;
    @Column(nullable = false)
    private Long customerId;
    @Column(nullable = false)
    private BigDecimal loanAmount;
    @Column(nullable = false)
    private Integer numberOfInstallment;
    @Column(nullable = false)
    private LocalDate createDate;
    @Column(nullable = false)
    private boolean isPaid;
    @Column(nullable = false)
    private BigDecimal interestRate;
}