package com.ruhuna.uniplus.controllers;


import com.ruhuna.uniplus.dto.LoginRequest;
import com.ruhuna.uniplus.dto.LoginResponse;
import com.ruhuna.uniplus.models.Admin;
import com.ruhuna.uniplus.repositories.AdminRepository;
import com.ruhuna.uniplus.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req){
        Admin admin =adminRepo.findByEmail(req.getEmail()).orElseThrow(() -> new RuntimeException("Invalid email or Password!"));

        if (!passwordEncoder.matches(req.getPassword(), admin.getPassword())){
            throw new RuntimeException("Invalid email or Password!");
        }

        String token = jwtUtil.generateToken(admin.getEmail());

        return ResponseEntity.ok(new LoginResponse(token, admin.getAdminId(), admin.getName()));
    }

}
