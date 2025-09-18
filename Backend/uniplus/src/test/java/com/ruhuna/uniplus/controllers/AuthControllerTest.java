package com.ruhuna.uniplus.controllers;

import com.ruhuna.uniplus.dto.LoginRequest;
import com.ruhuna.uniplus.dto.LoginResponse;
import com.ruhuna.uniplus.models.Admin;
import com.ruhuna.uniplus.repositories.AdminRepository;
import com.ruhuna.uniplus.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    private AdminRepository adminRepo;
    private BCryptPasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private AuthController controller;

    @BeforeEach
    void setup() {
        adminRepo = mock(AdminRepository.class);
        passwordEncoder = mock(BCryptPasswordEncoder.class);
        jwtUtil = mock(JwtUtil.class);
        controller = new AuthController();

        inject(controller, "adminRepo", adminRepo);
        inject(controller, "passwordEncoder", passwordEncoder);
        inject(controller, "jwtUtil", jwtUtil);
    }

    @Test
    void login_success_returns_200_and_token() {
        // Arrange
        LoginRequest req = new LoginRequest();
        req.setEmail("admin@uni.lk");
        req.setPassword("secret123");

        Admin admin = new Admin();
        admin.setEmail("admin@uni.lk");
        admin.setName("Admin One");
        admin.setAdminId(1L);

        when(adminRepo.findByEmail("admin@uni.lk")).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches("secret123", null)).thenReturn(true);
        when(jwtUtil.generateToken("admin@uni.lk")).thenReturn("jwt-token");

        ResponseEntity<?> res = controller.login(req);

        assertThat(res.getStatusCodeValue()).isEqualTo(200);
        assertThat(res.getBody()).isInstanceOf(LoginResponse.class);
        LoginResponse body = (LoginResponse) res.getBody();
        assertThat(body.getToken()).isEqualTo("jwt-token");
        assertThat(body.getAdminId()).isEqualTo(1L);
        assertThat(body.getName()).isEqualTo("Admin One");

        ArgumentCaptor<String> subject = ArgumentCaptor.forClass(String.class);
        verify(jwtUtil).generateToken(subject.capture());
        assertThat(subject.getValue()).isEqualTo("admin@uni.lk");
    }

    @Test
    void login_with_wrong_password_throws_exception() {
        LoginRequest req = new LoginRequest();
        req.setEmail("admin@uni.lk");
        req.setPassword("wrong");

        Admin admin = new Admin();
        admin.setEmail("admin@uni.lk");
        admin.setPassword("$2a$...hash");

        when(adminRepo.findByEmail("admin@uni.lk")).thenReturn(Optional.of(admin));
        when(passwordEncoder.matches("wrong", "$2a$...hash")).thenReturn(false);

        ResponseEntity<?> res = controller.login(req);
        assertThat(res.getStatusCodeValue()).isEqualTo(401);
        assertThat(res.getBody()).isEqualTo("Invalid email or Password!");
    }

    private static void inject(Object target, String field, Object value) {
        try {
            var f = target.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
