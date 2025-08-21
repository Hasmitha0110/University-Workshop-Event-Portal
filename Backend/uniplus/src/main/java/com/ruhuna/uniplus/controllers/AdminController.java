package com.ruhuna.uniplus.controllers;


import com.ruhuna.uniplus.models.Admin;
import com.ruhuna.uniplus.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    public Admin create(@RequestBody Admin admin) {
        if (adminRepo.existsByEmail(admin.getEmail())) {
            throw new RuntimeException("Email already used");
        }
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepo.save(admin);
    }

    @GetMapping("/{adminId}")
    public Optional<Admin> getOne(@PathVariable Long adminId) {
        return adminRepo.findById(adminId);
    }


}
