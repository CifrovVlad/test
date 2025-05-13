package com.example.EquipmentRental.service;

import com.example.EquipmentRental.dto.AuthResponse;
import com.example.EquipmentRental.dto.LoginRequest;
import com.example.EquipmentRental.dto.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest registrRequest);
    AuthResponse authenticate(LoginRequest loginRequest);
}