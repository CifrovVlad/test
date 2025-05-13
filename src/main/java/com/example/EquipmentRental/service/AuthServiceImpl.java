package com.example.EquipmentRental.service;
import com.example.EquipmentRental.dto.AuthResponse;
import com.example.EquipmentRental.dto.LoginRequest;
import com.example.EquipmentRental.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import com.example.EquipmentRental.model.Client;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.EquipmentRental.repository.ClientRepository;
import com.example.EquipmentRental.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterRequest registrRequest) {
        Client client = new Client();
        client.setFull_name(registrRequest.getFullName());
        client.setPassword(passwordEncoder.encode(registrRequest.getPassword()));
        client.setEmail(registrRequest.getEmail());
        client.setPhone(registrRequest.getPhone());

        clientRepository.save(client);

        String token = jwtService.generateToken(client.getEmail());
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse authenticate(LoginRequest loginRequest) {
        Client client = clientRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), client.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
        String token = jwtService.generateToken(client.getEmail());
        return new AuthResponse(token);
    }
}