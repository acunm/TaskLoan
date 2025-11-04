package com.example.demo.service;

import com.example.demo.Constant;
import com.example.demo.entity.Customer;
import com.example.demo.entity.User;
import com.example.demo.exception.BadCredentialsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.jwt.JwtService;
import com.example.demo.model.Role;
import com.example.demo.model.request.UserLoginRequest;
import com.example.demo.model.request.UserRegisterRequest;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.model.response.RegisterResponse;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomerService customerService;

    public RegisterResponse register(UserRegisterRequest userRegisterRequest) {
        User userToRegister = new User();
        userToRegister.setUserRole(Role.CUSTOMER);
        userToRegister.setPassword(passwordEncoder.encode(userRegisterRequest.getPassword()));
        userToRegister.setUsername(userRegisterRequest.getUsername());

        User savedUser = userRepository.save(userToRegister);
        Customer savedCustomer = saveCustomer(userRegisterRequest, savedUser.getUserId());

        return RegisterResponse.builder().username(savedUser.getUsername()).loanLimit(savedCustomer.getCreditLimit()).build();
    }

    public LoginResponse login(UserLoginRequest userLoginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(userLoginRequest.getUsername());

        if(userOptional.isEmpty())
            throw new UserNotFoundException("User does not exist: " + userLoginRequest.getUsername());

        User user = userOptional.get();

        if(!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword()))
            throw new BadCredentialsException("Invalid Credentials");

        return LoginResponse.builder().token(jwtService.generateToken(user)).build();
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User does not exist: " + username));
    }

    private Customer saveCustomer(UserRegisterRequest request, Long userId) {
        Customer customerToSave = new Customer();
        customerToSave.setUserId(userId);
        customerToSave.setName(request.getName());
        customerToSave.setSurname(request.getSurname());
        customerToSave.setCreditLimit(request.getCustomCreditLimit() == null ?
                Constant.DEFAULT_CREDIT_LIMIT : request.getCustomCreditLimit());
        customerToSave.setUsedCreditLimit(BigDecimal.ZERO);

        return customerService.saveCustomer(customerToSave);
    }
}
