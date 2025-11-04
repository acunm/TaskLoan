package com.example.demo;

import com.example.demo.Constant;
import com.example.demo.controller.UserController;
import com.example.demo.jwt.JwtService;
import com.example.demo.model.request.UserLoginRequest;
import com.example.demo.model.response.LoginResponse;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserLoginRequest validRequest;
    private LoginResponse mockResponse;

    @BeforeEach
    void setup() {
        validRequest = UserLoginRequest.builder().username("admin").password("123456").build();
        mockResponse = LoginResponse.builder().token("token").build();
    }

    @Test
    void login_ShouldReturnOk_WhenCredentialsAreValid() throws Exception {
        when(userService.login(validRequest)).thenReturn(mockResponse);

        mockMvc.perform(post(Constant.ENDPOINT_USER_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());

        verify(userService, times(1)).login(validRequest);
    }

    @Test
    void login_ShouldReturnBadRequest_WhenValidationFails() throws Exception {
        UserLoginRequest invalid = UserLoginRequest.builder().username("admin").password("").build();

        mockMvc.perform(post(Constant.ENDPOINT_USER_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_ShouldReturnOk_WhenCalled() throws Exception {
        mockMvc.perform(post(Constant.ENDPOINT_USER_REGISTER))
                .andExpect(status().isOk());
    }
}
