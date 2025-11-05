package com.example.demo;

import com.example.demo.config.GlobalExceptionHandler;
import com.example.demo.controller.UserController;
import com.example.demo.jwt.JwtService;
import com.example.demo.model.request.UserLoginRequest;
import com.example.demo.model.request.UserRegisterRequest;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.model.response.RegisterResponse;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@SpringBootTest
@AutoConfigureMockMvc
@Import({UserController.class})
class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    @DisplayName("Login with existing(admin) user should return 200 and valid response")
    void loginWithExistingUser_ShouldReturnOk() throws Exception {
        UserLoginRequest request = UserLoginRequest.builder().username("admin").password("123456").build();
        LoginResponse response = LoginResponse.builder().token("test").build();

        Mockito.when(userService.login(any(UserLoginRequest.class))).thenReturn(response);
        Mockito.when(jwtService.generateToken(any())).thenReturn("test");

        mockMvc.perform(post(Constant.ENDPOINT_USER_LOGIN)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("test"));
    }

    @Test
    @DisplayName("Login with nonexisting user should return 401")
    void loginWithWrongUsername_ShouldReturnUnauthorized() throws Exception {
        UserLoginRequest request = UserLoginRequest.builder().username("nonexistinguser").password("123456").build();

        Mockito.when(userService.login(any())).thenThrow(new BadCredentialsException("Invalid Credentials"));

        mockMvc.perform(post(Constant.ENDPOINT_USER_LOGIN)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Invalid Credentials"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Register should return valid response")
    void registerUser_ShouldReturnOk() throws Exception {
        UserRegisterRequest request = UserRegisterRequest.builder()
                .name("name")
                .surname("surname")
                .username("username")
                .password("password")
                .build();

        RegisterResponse response = RegisterResponse.builder().username("username").build();

        Mockito.when(userService.register(request)).thenReturn(response);

        mockMvc.perform(post(Constant.ENDPOINT_USER_REGISTER)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username"));
    }
}
