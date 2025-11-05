package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "loanIdGenerator")
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

    @OneToMany(mappedBy = "loan", fetch = FetchType.LAZY)
    List<LoanInstallment> loanInstallments;
}