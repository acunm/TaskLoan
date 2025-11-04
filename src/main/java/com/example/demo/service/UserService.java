package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.BadCredentialsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.jwt.JwtService;
import com.example.demo.model.request.UserLoginRequest;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public void register() {

    }

    public LoginResponse login(UserLoginRequest userLoginRequest) {
        Optional<User> userOptional = userRepository.findByUsername(userLoginRequest.getUsername());

        if(userOptional.isEmpty())
            throw new UserNotFoundException();

        User user = userOptional.get();

        if(!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword()))
            throw new BadCredentialsException();

        return LoginResponse.builder().token(jwtService.generateToken(user)).build();
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }
}
