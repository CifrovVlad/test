package com.example.EquipmentRental.controller;

import com.example.EquipmentRental.dto.AuthResponse;
import com.example.EquipmentRental.dto.LoginRequest;
import com.example.EquipmentRental.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.EquipmentRental.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest) {
        AuthResponse clientDto1 = authService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(clientDto1);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.authenticate(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(authResponse);
    }
}