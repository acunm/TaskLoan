package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.entity.User;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private UserService userService;

    public Customer saveCustomer(Customer customerToSave) {
        return customerRepository.save(customerToSave);
    }

    public Customer findCustomerByUserId(Long userId) {
        return customerRepository.findCustomerByUserId(userId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with user id: " + userId));
    }

    public Customer findCustomerByUsername(String username) {
        User user = userService.findUserByUsername(username);
        return findCustomerByUserId(user.getUserId());
    }

    @Autowired
    @Lazy
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
