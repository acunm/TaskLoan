package com.example.demo.controller;

import com.example.demo.Constant;
import com.example.demo.model.request.UserLoginRequest;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = Constant.ENDPOINT_USER_LOGIN)
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(userService.login(userLoginRequest));
    }

    @PostMapping(value = Constant.ENDPOINT_USER_REGISTER)
    public void register() {

    }
}
