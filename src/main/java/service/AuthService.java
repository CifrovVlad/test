package service;

import dto.AuthResponse;
import dto.LoginRequest;
import dto.RegistrRequest;

public interface AuthService {

    AuthResponse register(RegistrRequest registrRequest);
    AuthResponse authenticate(LoginRequest loginRequest);
}
