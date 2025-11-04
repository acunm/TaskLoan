package com.example.demo.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserLoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
