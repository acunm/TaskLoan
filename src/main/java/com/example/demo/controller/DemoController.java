package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.entity.User;
import com.example.demo.model.request.UserRegisterRequest;
import com.example.demo.model.response.RegisterResponse;
import com.example.demo.service.CustomerService;
import com.example.demo.service.LoanService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class DemoController {
    private final DataSource dataSource;

    @GetMapping("/create-demo-data")
    public ResponseEntity<?> createDemoData() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                new ClassPathResource("sql/demo-data.sql")
        );
        populator.execute(dataSource);
        return ResponseEntity.ok().build();
    }

}
