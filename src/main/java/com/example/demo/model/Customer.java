package com.example.demo.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Customer {
    private Long id;
    private String name;
    private String surname;
    private BigDecimal creditLimit;
    private BigDecimal usedCreditLimit;
}
